package Test.CPU;

import Helper.Timer;
import com.sauljohnson.huff.HuffmanCompressor;

import java.util.Random;

/**
 * Created by Robert on 21.11.2016.
 */
public class CompressTest {

    private static int RESULT;

    private static double getSpeed(byte[] data, double timeInNanoSecond) {
        return (data.length / 128) * 1e9 / timeInNanoSecond;
    }

    private static byte[] getRandomByteArrayInSize(int size){
        Random random = new Random();
        byte[] b = new byte[size];
        random.nextBytes(b);
        return b;
    }

    private static double measureCompressTime(byte[] data){
        HuffmanCompressor compressor = new HuffmanCompressor();
        Timer t = new Timer();
        RESULT += compressor.compress(data).getData().length;
        return t.check();
    }

    private static int compressTest(int loop) {
        System.out.println("\nCompressTest\n");
        double speed = 0.0;
        for (int i = 0; i<loop; i++) {
            byte[] data = getRandomByteArrayInSize(1024 * 1024);
            speed += getSpeed(data, measureCompressTime(data));
        }
        speed /= loop;
        System.out.println(speed + " kb/s\n");
        return RESULT;

    }

    public static int warmupAndTest(int warmupLoops, int testLoops){
        int a = 0;
        int b = 0;
        a = compressTest(warmupLoops);
        b = compressTest(testLoops);
        return a+b;
    }
}
