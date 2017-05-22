package Test.CPU;

import Helper.ResultController;
import Helper.Timer;

import java.util.concurrent.ThreadLocalRandom;

public class IntTest {

    private static int RESULT = 50;
    private static long TOTAL_TIME = 0;

    public static final int ADD = 0;
    public static final int SUBTRACT = 1;
    public static final int MULTIPLY = 2;
    public static final int DIVIDE = 3;

    protected static int[] generateRandomIntArray(int arraySize){
        int[] ints = ThreadLocalRandom
                .current()
                .ints(arraySize, Integer.MIN_VALUE, Integer.MAX_VALUE)
                .parallel()
                .map(i -> {
                    if(i == 0) i++;
                    return i;
                })
                .toArray();
        return ints;
    }

    private static void add(int[] ints){
        for (int i : ints) {
            RESULT += i;
        }
    }

    private static void substract(int[] ints){
        for (int i : ints) {
            RESULT -= i;
        }
    }

    private static void multiply(int[] ints){
        for (int i : ints) {
            RESULT *= i;
        }
    }

    private static void divide(int[] ints){
        for (int i : ints) {
            RESULT += Integer.MAX_VALUE/i;
        }
    }

    private static double countOneOperationTime(int randomIntArraySize, int loops, double loopTime){
        return loopTime/(randomIntArraySize*loops);
    }

    private static long measure(int loops, int arraySize, MathInterface testable){
        long time = 0;
        for (int loop = 0; loop < loops; loop++) {
            int[] ints = generateRandomIntArray(arraySize);
            Timer t = new Timer();
            testable.operate(ints);
            time += t.check();
        }

        System.out.println(countOneOperationTime(arraySize, loops, time)+" ns");
        return time;
    }

    private interface MathInterface {
        void operate(int[] ints);
    }

    private static long warmupAndMeasure(int mode, int warmupLoops, int testLoops, int arraySize){
        long a = 0;
        switch (mode){
            case ADD:
                System.out.println("\nADD");
                a = measure(warmupLoops, arraySize, IntTest::add);
                TOTAL_TIME += measure(testLoops, arraySize, IntTest::add);
                break;
            case SUBTRACT:
                System.out.println("\nSUBTRACT");
                a = measure(warmupLoops, arraySize, IntTest::substract);
                TOTAL_TIME += measure(testLoops, arraySize, IntTest::substract);
                break;
            case MULTIPLY:
                System.out.println("\nMULTIPLY");
                a = measure(warmupLoops, arraySize, IntTest::multiply);
                TOTAL_TIME += measure(testLoops, arraySize, IntTest::multiply);
                break;
            case DIVIDE:
                System.out.println("\nDIVIDE");
                a = measure(warmupLoops, arraySize, IntTest::divide);
                TOTAL_TIME += measure(testLoops, arraySize, IntTest::divide);
                break;
        }
        return a;
    }

    public static long measureAll(int warmupLoops, int loops, int size){
        System.out.println("\nINT");
        TOTAL_TIME = 0;
        long a = warmupAndMeasure(ADD, warmupLoops, loops, size);
        long b = warmupAndMeasure(SUBTRACT, warmupLoops, loops, size);
        long c = warmupAndMeasure(MULTIPLY, warmupLoops, loops, size);
        long d = warmupAndMeasure(DIVIDE, warmupLoops, loops, size);
        System.out.println(RESULT);
        ResultController.setIntReslut(TOTAL_TIME);
        return a+b+c+d;
    }

}
