package Test.CPU;

import Controller.ResultController;
import Helper.Timer;

import java.util.concurrent.ThreadLocalRandom;

public class LongTest {

    private static long RESULT = 50;
    private static long TOTAL_TIME = 0;

    public static final int ADD = 0;
    public static final int SUBTRACT = 1;
    public static final int MULTIPLY = 2;
    public static final int DIVIDE = 3;

    protected static long[] generateRandomLongArray(int arraySize){
        long[] longs = ThreadLocalRandom
                .current()
                .longs(arraySize, Long.MIN_VALUE, Long.MAX_VALUE)
                .parallel()
                .map(i -> {
                    if(i == 0) i += 1L;
                    return i;
                })
                .toArray();
        return longs;
    }

    private static double countOneOperationTime(int randomIntArraySize, int loops, double loopTime){
        return loopTime/(randomIntArraySize*loops);
    }

    private static void add(long[] longs){
        for (long i : longs) {
            RESULT += i;
        }
    }

    private static void substract(long[] longs){
        for (long i : longs) {
            RESULT -= i;
        }
    }

    private static void multiply(long[] longs){
        for (long i : longs) {
            RESULT *= i;
        }
    }

    private static void divide(long[] longs){
        for (long i : longs) {
            RESULT += Long.MAX_VALUE/i;
        }
    }

    private static long measure(int loops, int arraySize, MathInterface testable){
        long time = 0;
        for (int loop = 0; loop < loops; loop++) {
            long[] longArray = generateRandomLongArray(arraySize);
            Timer t = new Timer();
            testable.operate(longArray);
            time += t.check();
        }
        System.out.println(countOneOperationTime(arraySize, loops, time)+" ns");
        return time;
    }

    private interface MathInterface {
        void operate(long[] longs);
    }

    private static long warmupAndMeasure(int mode, int warmupLoops, int testLoops, int arrySize){
        long a = 0;
        switch (mode){
            case ADD:
                System.out.println("\nADD");
                a = measure(warmupLoops, arrySize, LongTest::add);
                TOTAL_TIME += measure(testLoops, arrySize, LongTest::add);
                break;
            case SUBTRACT:
                System.out.println("\nSUBTRACT");
                a = measure(warmupLoops, arrySize, LongTest::substract);
                TOTAL_TIME += measure(testLoops, arrySize, LongTest::substract);
                break;
            case MULTIPLY:
                System.out.println("\nMULTIPLY");
                a = measure(warmupLoops, arrySize, LongTest::multiply);
                TOTAL_TIME += measure(testLoops, arrySize, LongTest::multiply);
                break;
            case DIVIDE:
                System.out.println("\nDIVIDE");
                a = measure(warmupLoops, arrySize, LongTest::divide);
                TOTAL_TIME += measure(testLoops, arrySize, LongTest::divide);
                break;
        }
        return a;
    }

    public static double measureAll(int warmupLoops, int loops, int size){
        System.out.println("\nLONG");
        TOTAL_TIME = 0;
        long a = warmupAndMeasure(ADD, warmupLoops, loops, size);
        long b = warmupAndMeasure(SUBTRACT, warmupLoops, loops, size);
        long c = warmupAndMeasure(MULTIPLY, warmupLoops, loops, size);
        long d = warmupAndMeasure(DIVIDE, warmupLoops, loops, size);
        ResultController.setLongReslut(TOTAL_TIME);
        return a+b+c+d;
    }
}
