package Test.CPU;

import Helper.Timer;

import java.util.Random;

public class DoubleTest {

    private static double RESULT = 50.0;

    public static final int ADD = 0;
    public static final int SUBSTRACT = 1;
    public static final int MULTIPLY = 2;
    public static final int DIVIDE = 3;

    private static double[] generateRandomDoubleArray(int arraySize){
        Random random = new Random();
        double[] array = new double[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextDouble();
            if (array[i] == 0.0)
                array[i] ++;
        }
        return array;
    }

    private static double countOneOperationTime(int randomIntArraySize, int loops, double loopTime){
        return loopTime/(randomIntArraySize*loops);
    }

    private static void add(double[] intArray){
        for (double i : intArray) {
            RESULT += i;
        }
    }

    private static void substract(double[] intArray){
        for (double i : intArray) {
            RESULT -= i;
        }
    }

    private static void multiply(double[] intArray){
        for (double i : intArray) {
            RESULT *= i;
        }
    }

    private static void divide(double[] intArray){
        for (double i : intArray) {
            RESULT += 400000000.7/i;
        }
    }

    private static double measure(int loops, int arraySize, MathInterface testable){
        double time = 0.0;
        for (int loop = 0; loop < loops; loop++) {
            double[] doubleArray = generateRandomDoubleArray(arraySize);
            Timer t = new Timer();
            testable.operate(doubleArray);
            time += t.check();
        }
        System.out.println(countOneOperationTime(arraySize, loops, time)+" ns");
        return countOneOperationTime(arraySize, loops, time);
    }

    private interface MathInterface {
        void operate(double[] doubleArray);
    }

    private static double warmupAndMeasure(int mode, int warmupLoops, int testLoops, int arrySize){
        double a = 0;
        double b = 0;
        switch (mode){
            case ADD:
                System.out.println("\nADD");
                a = measure(warmupLoops, arrySize, DoubleTest::add);
                b = measure(testLoops, arrySize, DoubleTest::add);
                break;
            case SUBSTRACT:
                System.out.println("\nSUBSTRACT");
                a = measure(warmupLoops, arrySize, DoubleTest::substract);
                b = measure(testLoops, arrySize, DoubleTest::substract);
                break;
            case MULTIPLY:
                System.out.println("\nMULTIPLY");
                a = measure(warmupLoops, arrySize, DoubleTest::multiply);
                b = measure(testLoops, arrySize, DoubleTest::multiply);
                break;
            case DIVIDE:
                System.out.println("\nDIVIDE");
                a = measure(warmupLoops, arrySize, DoubleTest::divide);
                b = measure(testLoops, arrySize, DoubleTest::divide);
                break;
        }
        return a + b;
    }

    public static double measureAll(int warmupLoops, int loops, int size){
        System.out.println("\nDOUBLE");
        double a = warmupAndMeasure(ADD, warmupLoops, loops, size);
        double b = warmupAndMeasure(SUBSTRACT, warmupLoops, loops, size);
        double c = warmupAndMeasure(MULTIPLY, warmupLoops, loops, size);
        double d = warmupAndMeasure(DIVIDE, warmupLoops, loops, size);
        return a+b+c+d;
    }

}
