package Test.HardDrive;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Robert on 18.12.2016.
 */
public final class MeasureIOPerformance{
    static final int SIZE_GB = Integer.getInteger("sizeGB", 1);
    static File file = new File("./testFile");
    static final int BLOCK_SIZE = 4 * 1024;
    static final int blocks = (int) (((long) SIZE_GB << 30) / BLOCK_SIZE);
    final byte[] buffer = new byte[BLOCK_SIZE];

    private void measure() throws IOException, InterruptedException {
        file.deleteOnExit();
        System.out.println("Writing " + SIZE_GB + " GB " + " with ;");
        long start = System.nanoTime();
        //purgeCache();
        write(file);
        long mid = System.nanoTime();
        //purgeCache();
        System.out.println("Reading " + SIZE_GB + " GB " + " with ");
        System.out.println(read(file));
        long end = System.nanoTime();
        long size = file.length();
        System.out.printf("Write speed %.3f GB/s, read Speed %.3f GB/s%n",
                (double) size/(mid-start)*1e3, (double) size/(end-mid)*1e3);
        file.delete();
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
        int checksum =  0;
        try{
            while (in.read(buffer) != -1)
            {
                checksum +=1;
            }
        } finally {
            in.close();
        }
        return checksum;
    }


    public void measure(int warmupLoops, int testLoops) {
        try {
            for (int i = 0; i<testLoops + warmupLoops ; i++){
                measure();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
