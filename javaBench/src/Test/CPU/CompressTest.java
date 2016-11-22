package Test.CPU;

import Helper.Timer;
import nayuki.huffmancoding.AdaptiveHuffmanCompress;
import nayuki.huffmancoding.BitOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Random;

/**
 * Created by Robert on 21.11.2016.
 */
public class CompressTest {

    private static int RESULT = 0;
    private static InputStream is = null;
    private static ByteArrayOutputStream out = null;
    private static BitOutputStream bitOut;
    private static ByteBuffer bb;

    private static void initializeOutputStreams(){
        out = new ByteArrayOutputStream();
        bitOut = new BitOutputStream(out);
    }

    private static void closeStreams() throws IOException {
        bitOut.close();
        is.close();
        out.close();
    }

    private static void resetStreams() throws IOException {
        out.reset();
        is.reset();
    }

    private static double getSpeed(byte[] data, double timeInNanoSecond) {
        return (data.length / 128) * 1e9 / timeInNanoSecond;
    }

    private static byte[] getRandomByteArrayInSize(int size){
        Random random = new Random();
        byte[] b = new byte[size];
        random.nextBytes(b);
        return b;
    }

    private static void initializeInputStreamWithGivenData(byte[] b){
        is = new ByteArrayInputStream(b);
    }

    private static void getIntFromCompressedDataAndAddItToResult(byte[] outputData){
        RESULT += outputData.hashCode();
    }

    private static double measureCompressTime() throws IOException {
        Timer t = new Timer();
        AdaptiveHuffmanCompress.compress(is, bitOut);
        return t.check();
    }

    private static int compressTest(int loop) throws IOException {
        initializeOutputStreams();
        double speed = 0.0;
        for (int i = 0; i<loop; i++) {
            byte[] data = getRandomByteArrayInSize(10_000_000);
            initializeInputStreamWithGivenData(data);
            speed += getSpeed(data, measureCompressTime());
            getIntFromCompressedDataAndAddItToResult(out.toByteArray());
            resetStreams();
            is.close();
        }
        closeStreams();
        speed /= loop;
        System.out.println(speed + " kb/s");
        return RESULT;

    }

    public static int warmupAndTest(int warmupLoops, int testLoops){
        int a = 0;
        int b = 0;
        try {
            a = compressTest(warmupLoops);
            b = compressTest(testLoops);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a+b;
    }
}
