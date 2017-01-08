package Test.HardDrive;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Random;

/**
 * Created by Robert on 18.12.2016.
 */
public final class MeasureIOPerformance {
    static final int SIZE_GB = Integer.getInteger("sizeGB", 8);
    static File[] files = new File[1];
    static final int BLOCK_SIZE = 64 * 1024;
    static final int blocks = (int) (((long) SIZE_GB << 30) / BLOCK_SIZE);
    static final byte[] acceptBuffer = new byte[555];

    public static void main(String[] args) throws IOException {

            measure(new StreamRw());
            measure(new StreamRw());

    }

    private static void measure(RW rw) throws IOException {
        for (int i = 0; i< files.length; i++) {
            files[i] = new File("C:\\Users\\Robert Ostaszewski\\test"+i+".ss");
            files[i].deleteOnExit();
        }

        System.out.println("Writing " + SIZE_GB + " GB " + " with " + rw);
        long start = System.nanoTime();
        for (File file:files)
            rw.write(file);
        long mid = System.nanoTime();
        System.out.println("Reading " + SIZE_GB + " GB " + " with " + rw);
        long checksum = 0;
        for (int i =0; i<files.length; i++)
             checksum += rw.read(files[i]);
        long end = System.nanoTime();
        long size = files[0].length();
        System.out.printf("Write speed %.3f GB/s, read Speed %.3f GB/s%n",
                (double) size/(mid-start)*files.length*1e3, (double) size/(end-mid)*(files.length)*1e3);
        System.out.println(checksum);
        for (File file:files)
            file.delete();
    }

    interface RW {
        void write(File f) throws IOException;
        long read(File f) throws IOException;
    }

    static class ChannelRw implements RW {
        final ByteBuffer directBuffer = ByteBuffer.allocateDirect(BLOCK_SIZE);

        @Override public String toString() {
            return "Channel";
        }

        @Override public void write(File f) throws IOException {

            FileChannel fc = new FileOutputStream(f).getChannel();
            try {
                for (int i = 0; i < blocks; i++) {
                    directBuffer.clear();
                    while (directBuffer.remaining() > 0) {
                        fc.write(directBuffer);
                    }
                }
            } finally {
                fc.close();
            }
        }
        @Override public long read(File f) throws IOException {
            ByteBuffer buffer = ByteBuffer.allocateDirect(BLOCK_SIZE);
            FileChannel fc = new FileInputStream(f).getChannel();
            long checksum = 0;
            try {
                for (int i = 0; i < blocks; i++) {
                    buffer.clear();
                    while (buffer.hasRemaining()) {
                        fc.read(buffer);
                    }
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        buffer.get(acceptBuffer, 0, Math.min(acceptBuffer.length, buffer.remaining()));
                        checksum += acceptBuffer.hashCode();
                    }
                }
            } finally {
                fc.close();
            }
            return checksum;
        }
    }

    static class StreamRw implements RW {
        final byte[] buffer = new byte[BLOCK_SIZE];
        Random random = new Random();

        @Override public String toString() {
            return "Stream";
        }

        @Override public void write(File f) throws IOException {
            FileOutputStream out = new FileOutputStream(f);
            try {
                for (int i = 0; i < blocks; i++) {
//                    random.nextBytes(buffer);
                    out.write(buffer);
                }
            } finally {
                out.close();
            }
        }
        @Override public long read(File f) throws IOException {

//            RandomAccessFile file1 = new RandomAccessFile(f, "r");
//            file1.setLength(blocks*BLOCK_SIZE);
            FileInputStream in = new FileInputStream(f);
            long checksum = 0;
            try{
                int read;
                while ((read = in.read(buffer)) != -1)
                {
                }
            } finally {
               in.close();
            }
            return checksum;
        }
    }


    public static void purgeCache() throws IOException, InterruptedException {
        if (System.getProperty("os.name").startsWith("Mac")) {
            new ProcessBuilder("sudo", "purge")
                    //                    .inheritIO()
                    .start().waitFor();
        } else {
            new ProcessBuilder("sudo", "su", "-c", "echo 3 > /proc/sys/vm/drop_caches")
                    //                    .inheritIO()
                    .start().waitFor();
        }
    }
}
