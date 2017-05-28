package Test.HardDrive;

import Controller.ResultController;
import Helper.Timer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public final class MeasureIOPerformance implements Runnable {
    /**
     *
     */
    private static final int SIZE_GB = 1024 * 1024 * 1024;
    /**
     *
     */
    private static final int BLOCK_SIZE = 2 * 1024 * 1024;
    /**
     *
     */
    private static final int blocks = SIZE_GB / BLOCK_SIZE;
    /**
     *
     */
    private static long TOTAL_TIME = 0;

    /**
     *
     */
    private static final List<Long> writeResults = new ArrayList<>();
    /**
     *
     */
    private static final List<Long> readResults = new ArrayList<>();

    /**
     * @param rw
     * @param i
     * @throws IOException
     */
    private static void measure(StreamRw rw, int i)
            throws IOException {
        File writeFile = new File(System.getProperty("user.dir") +
                "/temp_file_to_delete_" +
                ThreadLocalRandom.current().nextInt());
        writeFile.deleteOnExit();
        File[] readFiles = new File[3];
        Arrays.setAll(readFiles, n -> new File(
                System.getProperty("user.dir") + "/" + "readTest" + (n+1)));
        rw.write(writeFile);
        writeResults.add(TOTAL_TIME);
        TOTAL_TIME = 0;
        rw.read(readFiles[i]);
        readResults.add(TOTAL_TIME);
        TOTAL_TIME = 0;
        writeFile.delete();
    }

    /**
     *
     */
    private void addToResultController(){
        long writeTime = 0, readTime = 0;
        for(long time : writeResults) writeTime += time;
        for(long time : readResults) readTime += time;
        ResultController.setDiskReadResult(readTime);
        ResultController.setDiskWriteResult(writeTime);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i<3 ; i++){
                measure(new StreamRw(), i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        addToResultController();
    }

    /**
     *
     */
    static class StreamRw {
        final byte[] buffer = new byte[BLOCK_SIZE];

        /**
         * @param f
         * @throws IOException
         */
        public void write(File f) throws IOException {
            try (FileOutputStream out = new FileOutputStream(f)) {
                for (int i = 0; i < blocks; i++) {
                    Timer t = new Timer();
                    out.write(buffer);
                    TOTAL_TIME += t.check();
                }
            }
        }

        /**
         * @param f
         * @throws IOException
         */
        public void read(File f) throws IOException {
            int checksum =  0;
            int temp = 0;
            try (FileInputStream in = new FileInputStream(f)) {
                for (int i = 0; i < blocks; i++) {
                    Timer t = new Timer();
                    temp = in.read(buffer);
                    TOTAL_TIME += t.check();
                    checksum += buffer.hashCode();
                    if (temp == -1)
                        break;
                }
            }
        }
    }

}
