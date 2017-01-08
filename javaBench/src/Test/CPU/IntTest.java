package Test.CPU;

import Helper.Timer;

import java.util.Random;

public class IntTest {

    private static int RESULT = 50;

    public static final int ADD = 0;
    public static final int SUBSTRACT = 1;
    public static final int MULTIPLY = 2;
    public static final int DIVIDE = 3;

    protected static int[] generateRandomIntArray(int arraySize){
        Random random = new Random();
        int[] array = new int[arraySize];
        for (int i = 0; i < arraySize; i++)
            array[i] = random.nextInt(Integer.MAX_VALUE - 1) + 1;
        return array;
    }

    protected static double countOneOperationTime(int randomIntArraySize, int loops, double loopTime){
        return loopTime/(randomIntArraySize*loops);
    }

    private static void add(int[] intArray){
        for (int i : intArray) {
            RESULT += i;
        }
    }

    private static void substract(int[] intArray){
        for (int i : intArray) {
            RESULT -= i;
        }
    }

    private static void multiply(int[] intArray){
        for (int i : intArray) {
            RESULT *= i;
        }
    }

    private static void divide(int[] intArray){
        for (int i : intArray) {
            RESULT += 400000000/i;
        }
    }

    private static double measure(int loops, int intArraySize, MathInterface testable){
        double time = 0.0;
        for (int loop = 0; loop < loops; loop++) {
            int[] intArray = generateRandomIntArray(intArraySize);
            Timer t = new Timer();
            testable.operate(intArray);
            time += t.check();
        }
        System.out.println(countOneOperationTime(intArraySize, loops, time)+" ns");
        return countOneOperationTime(intArraySize, loops, time);
    }

    private interface MathInterface {
        void operate(int[] intArray);
    }

    private static double warmupAndMeasure(int mode, int warmupLoops, int testLoops, int arrySize){
        double a = 0;
        double b = 0;
        switch (mode){
            case ADD:
                System.out.println("\nADD");
                a = measure(warmupLoops, arrySize, IntTest::add);
                b = measure(testLoops, arrySize, IntTest::add);
                break;
            case SUBSTRACT:
                System.out.println("\nSUBSTRACT");
                a = measure(warmupLoops, arrySize, IntTest::substract);
                b = measure(testLoops, arrySize, IntTest::substract);
                break;
            case MULTIPLY:
                System.out.println("\nMULTIPLY");
                a = measure(warmupLoops, arrySize, IntTest::multiply);
                b = measure(testLoops, arrySize, IntTest::multiply);
                break;
            case DIVIDE:
                System.out.println("\nDIVIDE");
                a = measure(warmupLoops, arrySize, IntTest::divide);
                b = measure(testLoops, arrySize, IntTest::divide);
                break;
        }
        return a + b;
    }

    public static double measureAll(int warmupLoops, int loops, int size){
        System.out.println("\nINT");
        double a = warmupAndMeasure(ADD, warmupLoops, loops, size);
        double b = warmupAndMeasure(SUBSTRACT, warmupLoops, loops, size);
        double c = warmupAndMeasure(MULTIPLY, warmupLoops, loops, size);
        double d = warmupAndMeasure(DIVIDE, warmupLoops, loops, size);
        return a+b+c+d;
    }

}
