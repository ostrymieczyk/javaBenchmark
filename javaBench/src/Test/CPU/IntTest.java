package Test.CPU;

import Helper.Timer;

import java.util.Random;

public class IntTest {

    private static int RESULT = 50;

    private static int[] generateRandomIntArray(int arraySize){
        Random random = new Random();
        int[] array = new int[arraySize];
        for (int i = 0; i < arraySize; i++)
              array[i] = random.nextInt(Integer.MAX_VALUE - 1) + 1;
        return array;
    }

    private static double countOneOperationTime(int randomIntArraySize, int loops, double loopTime){
        return loopTime/(randomIntArraySize*loops);
    }

    private static int measureAdd(int loops, int intArraySize){
        System.out.println("Add Start");
        double time = 0.0;
        for (int loop = 0; loop < loops; loop++) {
            int[] intArray = generateRandomIntArray(intArraySize);
            Timer t = new Timer();
            for (int i : intArray) {
                RESULT += i;
            }
            time += t.check();
        }
        System.out.println(countOneOperationTime(intArraySize, loops, time)+" ns\n");
        return RESULT;
    }

    private static int measureSubstract(int loops, int intArraySize){
        System.out.println("Substract Start");
        double time = 0.0;
        for (int loop = 0; loop < loops; loop++) {
            int[] intArray = generateRandomIntArray(intArraySize);
            Timer t = new Timer();
            for (int i : intArray) {
                RESULT -= i;
            }
            time += t.check();
        }
        System.out.println(countOneOperationTime(intArraySize, loops, time)+" ns\n");
        return RESULT;
    }

    private static int measureMultiply(int loops, int intArraySize){
        System.out.println("Multiply Start");
        double time = 0.0;
        for (int loop = 0; loop < loops; loop++) {
            int[] intArray = generateRandomIntArray(intArraySize);
            Timer t = new Timer();
            for (int i : intArray) {
                RESULT *= i;
            }
            time += t.check();
        }
        System.out.println(countOneOperationTime(intArraySize, loops, time)+" ns\n");
        return RESULT;
    }

    private static int measureDivide(int loops, int intArraySize){
        System.out.println("Divide Start");
        double time = 0.0;
        for (int loop = 0; loop < loops; loop++) {
            int[] intArray = generateRandomIntArray(intArraySize);
            Timer t = new Timer();
            for (int i : intArray) {
                RESULT /= i;
            }
            time += t.check();
        }
        System.out.println(countOneOperationTime(intArraySize, loops, time)+" ns\n");
        return RESULT;
    }

    private static int warmupAndMeasureAdd(int warmupLoops, int testLoops, int arrySize){
        int a = measureAdd(warmupLoops, arrySize);
        int b = measureAdd(testLoops, arrySize);
        return a + b;
    }

    private static int warmupAndMeasureSubstarct(int warmupLoops, int testLoops, int arrySize){
        int a = measureSubstract(warmupLoops, arrySize);
        int b = measureSubstract(testLoops, arrySize);
        return a + b;
    }

    private static int warmupAndMeasureMultiply(int warmupLoops, int testLoops, int arrySize){
        int a = measureMultiply(warmupLoops, arrySize);
        int b = measureMultiply(testLoops, arrySize);
        return a + b;
    }

    private static int warmupAndMeasureDivide(int warmupLoops, int testLoops, int arrySize){
        int a = measureDivide(warmupLoops, arrySize);
        int b = measureDivide(testLoops, arrySize);
        return a + b;
    }

    public static int measureAll(int warmupLoops, int loops, int size){
        System.out.println("\nINT" +"\n");
        int a = warmupAndMeasureAdd(warmupLoops, loops, size);
        int b = warmupAndMeasureSubstarct(warmupLoops, loops, size);
        int c = warmupAndMeasureMultiply(warmupLoops, loops, size);
        int d = warmupAndMeasureDivide(warmupLoops, loops, size);
        return a+b+c+d;
    }

}
