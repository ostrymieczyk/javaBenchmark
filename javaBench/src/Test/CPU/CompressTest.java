package Test.CPU;

import Controller.ResultController;
import Helper.Timer;
import com.sauljohnson.huff.HuffmanCompressor;

import java.util.Random;

/**
 *
 */
public class CompressTest {

    /**
     *
     */
    private static int RESULT;

    /**
     * @param size
     * @return
     */
    private static byte[] getRandomByteArrayInSize(int size){
        Random random = new Random();
        byte[] b = new byte[size];
        random.nextBytes(b);
        return b;
    }

    /**
     * @param data
     * @return
     */
    private static long measureCompressTime(byte[] data){
        HuffmanCompressor compressor = new HuffmanCompressor();
        Timer t = new Timer();
        RESULT += compressor.compress(data).getData().length;
        return t.check();
    }

    /**
     * @param loop
     * @return
     */
    private static long compressTest(int loop) {
        System.out.println("\nCompressTest\n");
        long time = 0;
        for (int i = 0; i<loop; i++) {
            byte[] data = getRandomByteArrayInSize(1024 * 1024);
            time += measureCompressTime(data);
        }
        return time;

    }

    /**
     *
     */
    public static void warmAndTest(){
        long TOTAL_TIME = 0;
        double a = compressTest(5);
        TOTAL_TIME += compressTest(30);
        ResultController.setCompressResult(TOTAL_TIME);
    }
}
