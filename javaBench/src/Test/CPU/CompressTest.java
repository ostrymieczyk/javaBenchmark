package Test.CPU;

import Helper.ResultController;
import Helper.Timer;
import com.sauljohnson.huff.HuffmanCompressor;

import java.util.Random;

/**
 * Created by Robert on 21.11.2016.
 */
public class CompressTest {

    private static int RESULT;
    private static long TOTAL_TIME = 0;

    private static double getSpeed(byte[] data, double timeInNanoSecond) {
        return (data.length / 128) * 1e9 / timeInNanoSecond;
    }

    private static byte[] getRandomByteArrayInSize(int size){
        Random random = new Random();
        byte[] b = new byte[size];
        random.nextBytes(b);
        return b;
    }

    private static long measureCompressTime(byte[] data){
        HuffmanCompressor compressor = new HuffmanCompressor();
        Timer t = new Timer();
        RESULT += compressor.compress(data).getData().length;
        return t.check();
    }

    private static long compressTest(int loop) {
        System.out.println("\nCompressTest\n");
        long time = 0;
        for (int i = 0; i<loop; i++) {
            byte[] data = getRandomByteArrayInSize(1024 * 1024);
            time += measureCompressTime(data);
        }
        return time;

    }

    public static double warmupAndTest(int warmupLoops, int testLoops){
        TOTAL_TIME = 0;
        double a = compressTest(warmupLoops);
        TOTAL_TIME += compressTest(testLoops);
        ResultController.setCompressReslut(TOTAL_TIME);
        return a;
    }
}
