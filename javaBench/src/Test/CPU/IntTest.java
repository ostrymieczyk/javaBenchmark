package Test.CPU;

import Helper.Timer;

import java.util.concurrent.ThreadLocalRandom;

public class IntTest {

    private static int RESULT = 50;

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

    protected static double countOneOperationTime(int randomIntArraySize, int loops, double loopTime){
        return loopTime/(randomIntArraySize*loops);
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

    private static double measure(int loops, int intArraySize, MathInterface testable){
        double time = 0.0;
        RESULT = 50;
        for (int loop = 0; loop < loops; loop++) {
            int[] ints = generateRandomIntArray(intArraySize);
            Timer t = new Timer();
            testable.operate(ints);
            time += t.check();
        }
        System.out.println(countOneOperationTime(intArraySize, loops, time)+" ns");
        return countOneOperationTime(intArraySize, loops, time);
    }

    private interface MathInterface {
        void operate(int[] ints);
    }

    private static double warmupAndMeasure(int mode, int warmupLoops, int testLoops, int arraySize){
        double a = 0;
        double b = 0;
        switch (mode){
            case ADD:
                System.out.println("\nADD");
                a = measure(warmupLoops, arraySize, IntTest::add);
                b = measure(testLoops, arraySize, IntTest::add);
                break;
            case SUBTRACT:
                System.out.println("\nSUBTRACT");
                a = measure(warmupLoops, arraySize, IntTest::substract);
                b = measure(testLoops, arraySize, IntTest::substract);
                break;
            case MULTIPLY:
                System.out.println("\nMULTIPLY");
                a = measure(warmupLoops, arraySize, IntTest::multiply);
                b = measure(testLoops, arraySize, IntTest::multiply);
                break;
            case DIVIDE:
                System.out.println("\nDIVIDE");
                a = measure(warmupLoops, arraySize, IntTest::divide);
                b = measure(testLoops, arraySize, IntTest::divide);
                break;
        }
        return a + b;
    }

    public static double measureAll(int warmupLoops, int loops, int size){
        System.out.println("\nINT");
        double a = warmupAndMeasure(ADD, warmupLoops, loops, size);
        double b = warmupAndMeasure(SUBTRACT, warmupLoops, loops, size);
        double c = warmupAndMeasure(MULTIPLY, warmupLoops, loops, size);
        double d = warmupAndMeasure(DIVIDE, warmupLoops, loops, size);
        System.out.println(RESULT);
        return a+b+c+d;
    }

}
