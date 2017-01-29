package Test.HardDrive;

import java.io.*;
import java.util.Random;

/**
 * Created by Robert on 18.12.2016.
 */
public final class MeasureIOPerformance implements Runnable {
    static final int SIZE_GB = Integer.getInteger("sizeGB", 1);
    static File file = new File(System.getProperty("user.home") + "/Desktop/test.ss");
    static final int BLOCK_SIZE = 4 * 1024;
    static final int blocks = (int) (((long) SIZE_GB << 30) / BLOCK_SIZE);

    private static void measure(StreamRw rw) throws IOException, InterruptedException {
        file.deleteOnExit();

        System.out.println("Writing " + SIZE_GB + " GB " + " with " + rw);
        long start = System.nanoTime();
        //purgeCache();
        rw.write(file);
        long mid = System.nanoTime();
        //purgeCache();
        System.out.println("Reading " + SIZE_GB + " GB " + " with " + rw);
        System.out.println(rw.read(file));
        long end = System.nanoTime();
        long size = file.length();
        System.out.printf("Write speed %.3f GB/s, read Speed %.3f GB/s%n",
                (double) size/(mid-start)*1e3, (double) size/(end-mid)*1e3);
        file.delete();
    }

    @Override
    public void run() {
        try {
            measure(new StreamRw());
            for (int i = 0; i<5 ; i++){
                measure(new StreamRw());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class StreamRw {
        final byte[] buffer = new byte[BLOCK_SIZE];
        Random random = new Random();

        @Override public String toString() {
            return "Stream";
        }

        public void write(File f) throws IOException {
            FileOutputStream out = new FileOutputStream(f);
            try {
                for (int i = 0; i < blocks; i++) {
                    out.write(buffer);
                }
            } finally {
                out.close();
            }
        }

        public int read(File f) throws IOException {
            FileInputStream in = new FileInputStream(f);
            int checkum =  0;
            try{
                int read;
                while ((read = in.read(buffer)) != -1)
                {
                    checkum +=1;
                }
            } finally {
               in.close();
            }
            return checkum;
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
