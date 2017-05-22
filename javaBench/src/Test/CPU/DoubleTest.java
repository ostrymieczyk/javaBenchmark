package Test.CPU;

import Controller.ResultController;
import Helper.Timer;

import java.util.concurrent.ThreadLocalRandom;

public class DoubleTest {

    private static double RESULT = 50.0;
    private static long TOTAL_TIME = 0;


    public static final int ADD = 0;
    public static final int SUBTRACT = 1;
    public static final int MULTIPLY = 2;
    public static final int DIVIDE = 3;

    private static double[] generateRandomDoubleArray(int arraySize){
        double[] doubles = ThreadLocalRandom
                .current()
                .doubles(arraySize, Double.MIN_VALUE, Double.MAX_VALUE)
                .parallel()
                .map(i -> {
                    if(i == 0) i += 1.0;
                    return i;
                })
                .toArray();
        return doubles;
    }

    private static double countOneOperationTime(int randomIntArraySize, int loops, double loopTime){
        return loopTime/(randomIntArraySize*loops);
    }

    private static void add(double[] doubles){
        for (double i : doubles) {
            RESULT += i;
        }
    }

    private static void substract(double[] doubles){
        for (double i : doubles) {
            RESULT -= i;
        }
    }

    private static void multiply(double[] doubles){
        for (double i : doubles) {
            RESULT *= i;
        }
    }

    private static void divide(double[] doubles){
        for (double i : doubles) {
            RESULT += Double.MAX_VALUE/i;
        }
    }

    private static long measure(int loops, int arraySize, MathInterface testable){
        long time = 0;
        for (int loop = 0; loop < loops; loop++) {
            double[] doubles = generateRandomDoubleArray(arraySize);
            Timer t = new Timer();
            testable.operate(doubles);
            time += t.check();
        }
        System.out.println(countOneOperationTime(arraySize, loops, time)+" ns");
        return time;
    }

    private interface MathInterface {
        void operate(double[] doubles);
    }

    private static long warmupAndMeasure(int mode, int warmupLoops, int testLoops, int arrySize){
        long a = 0;
        switch (mode){
            case ADD:
                System.out.println("\nADD");
                a = measure(warmupLoops, arrySize, DoubleTest::add);
                TOTAL_TIME += measure(testLoops, arrySize, DoubleTest::add);
                break;
            case SUBTRACT:
                System.out.println("\nSUBTRACT");
                a = measure(warmupLoops, arrySize, DoubleTest::substract);
                TOTAL_TIME += measure(testLoops, arrySize, DoubleTest::substract);
                break;
            case MULTIPLY:
                System.out.println("\nMULTIPLY");
                a = measure(warmupLoops, arrySize, DoubleTest::multiply);
                TOTAL_TIME += measure(testLoops, arrySize, DoubleTest::multiply);
                break;
            case DIVIDE:
                System.out.println("\nDIVIDE");
                a = measure(warmupLoops, arrySize, DoubleTest::divide);
                TOTAL_TIME += measure(testLoops, arrySize, DoubleTest::divide);
                break;
        }
        return a;
    }

    public static long measureAll(int warmupLoops, int loops, int size){
        System.out.println("\nDOUBLE");
        TOTAL_TIME = 0;
        long a = warmupAndMeasure(ADD, warmupLoops, loops, size);
        long b = warmupAndMeasure(SUBTRACT, warmupLoops, loops, size);
        long c = warmupAndMeasure(MULTIPLY, warmupLoops, loops, size);
        long d = warmupAndMeasure(DIVIDE, warmupLoops, loops, size);
        ResultController.setDoubleReslut(TOTAL_TIME);
        return a+b+c+d;
    }

}
