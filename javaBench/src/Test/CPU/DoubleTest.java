package Test.CPU;

import Helper.Timer;

import java.util.Random;

public class DoubleTest {

    private static double RESULT = 50.0;

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

    private static double measureAdd(int loops, int intArraySize){
        System.out.println("Add Start");
        double time = 0.0;
        for (int loop = 0; loop < loops; loop++) {
            double[] intArray = generateRandomDoubleArray(intArraySize);
            Timer t = new Timer();
            for (double i : intArray) {
                RESULT += i;
            }
            time += t.check();
        }
        System.out.println(countOneOperationTime(intArraySize, loops, time)+" ns\n");
        return RESULT;
    }

    private static double measureSubstract(int loops, int intArraySize){
        System.out.println("Substract Start");
        double time = 0.0;
        for (int loop = 0; loop < loops; loop++) {
            double[] intArray = generateRandomDoubleArray(intArraySize);
            Timer t = new Timer();
            for (double i : intArray) {
                RESULT -= i;
            }
            time += t.check();
        }
        System.out.println(countOneOperationTime(intArraySize, loops, time)+" ns\n");
        return RESULT;
    }

    private static double measureMultiply(int loops, int intArraySize){
        System.out.println("Multiply Start");
        double time = 0.0;
        for (int loop = 0; loop < loops; loop++) {
            double[] intArray = generateRandomDoubleArray(intArraySize);
            Timer t = new Timer();
            for (double i : intArray) {
                RESULT *= i;
            }
            time += t.check();
        }
        System.out.println(countOneOperationTime(intArraySize, loops, time) +" ns\n");
        return RESULT;
    }

    private static double measureDivide(int loops, int ArraySize){
        System.out.println("Divide Start");
        double time = 0.0;
        for (int loop = 0; loop < loops; loop++) {
            double[] Array = generateRandomDoubleArray(ArraySize);
            Timer t = new Timer();
            for (double i : Array) {
                RESULT += 7000000000.4/i;
            }
            time += t.check();
        }
        System.out.println(countOneOperationTime(ArraySize, loops, time)+" ns\n");
        return RESULT;
    }

    private static double warmupAndMeasureAdd(int warmupLoops, int testLoops, int arrySize){
        double a = measureAdd(warmupLoops, arrySize);
        double b = measureAdd(testLoops, arrySize);
        return a + b;
    }

    private static double warmupAndMeasureSubstarct(int warmupLoops, int testLoops, int arrySize){
        double a = measureSubstract(warmupLoops, arrySize);
        double b = measureSubstract(testLoops, arrySize);
        return a + b;
    }

    private static double warmupAndMeasureMultiply(int warmupLoops, int testLoops, int arrySize){
        double a = measureMultiply(warmupLoops, arrySize);
        double b = measureMultiply(testLoops, arrySize);
        return a + b;
    }

    private static double warmupAndMeasureDivide(int warmupLoops, int testLoops, int arrySize){
        double a = measureDivide(warmupLoops, arrySize);
        double b = measureDivide(testLoops, arrySize);
        return a + b;
    }

    public static double measureAll(int warmupLoops, int loops, int size){
        System.out.println("\nDOUBLE:\n");
        double a = warmupAndMeasureAdd(warmupLoops, loops, size);
        double b = warmupAndMeasureSubstarct(warmupLoops, loops, size);
        double c = warmupAndMeasureMultiply(warmupLoops, loops, size);
        double d = warmupAndMeasureDivide(warmupLoops, loops, size);
        return a+b+c+d;
    }

}
