package Test.HardDrive;

import Helper.Timer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Robert on 18.12.2016.
 */
public final class MeasureIOPerformance implements Runnable {
    static final int SIZE_GB = 1024 * 1024 * 1024;
    static final int BLOCK_SIZE = 2 * 1024 * 1024;
    static final int blocks = SIZE_GB / BLOCK_SIZE;
    static double RESULT = 0;

    static List<Double> writeResults = new ArrayList<>();
    static List<Double> readResults = new ArrayList<>();

    private static void measure(StreamRw rw, int i)
            throws IOException, InterruptedException {
        File writeFile = new File(System.getProperty("user.dir") +
                "/temp_file_to_delete_" +
                ThreadLocalRandom.current().nextInt());
        writeFile.deleteOnExit();
        File[] readFiles = new File[3];
        Arrays.setAll(readFiles, n -> new File(
                System.getProperty("user.dir") + "/" + "readTest" + (i+1)));
        rw.write(writeFile);
        writeResults.add(RESULT);
        RESULT = 0;
        rw.read(readFiles[i]);
        readResults.add(RESULT);
        RESULT = 0;
        writeFile.delete();
    }

    private void printMean(){
        System.out.println(writeResults.stream().mapToDouble(i -> {return i;}).sum()/(3 * SIZE_GB));
        System.out.println(readResults.stream().mapToDouble(i -> {return i;}).sum()/(3 * SIZE_GB));
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i<3 ; i++){
                measure(new StreamRw(), i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printMean();
    }

    static class StreamRw {
        final byte[] buffer = new byte[BLOCK_SIZE];

        public void write(File f) throws IOException {
            FileOutputStream out = new FileOutputStream(f);
            try {
                for (int i = 0; i < blocks; i++) {
                    Timer t = new Timer();
                    out.write(buffer);
                    RESULT += t.check();
                }
            } finally {
                out.close();
            }
        }

        public int read(File f) throws IOException {
            FileInputStream in = new FileInputStream(f);
            int checkum =  0;
            int temp = 0;
            try{
                for (int i = 0; i < blocks; i++) {
                    Timer t = new Timer();
                    temp = in.read(buffer);
                    RESULT += t.check();
                    checkum +=buffer.hashCode();
                    if(temp == -1)
                        break;
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
