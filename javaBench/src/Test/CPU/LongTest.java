package Test.CPU;

import Helper.Timer;

import java.util.Random;

public class LongTest {

    private static long RESULT = 50;

    private static long[] generateRandomLongArray(int arraySize){
        Random random = new Random();
        long[] array = new long[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextLong();
            if (array[i] == 0)
                array[i] ++;
        }
        return array;
    }

    private static double countOneOperationTime(int randomIntArraySize, int loops, double loopTime){
        return loopTime/(randomIntArraySize*loops);
    }

    private static long measureAddLong(int loops, int intArraySize){
        System.out.println("Add Start");
        double time = 0.0;
        for (int loop = 0; loop < loops; loop++) {
            long[] intArray = generateRandomLongArray(intArraySize);
            Timer t = new Timer();
            for (long i : intArray) {
                RESULT += i;
            }
            time += t.check();
        }
        System.out.println(countOneOperationTime(intArraySize, loops, time)+" ns\n");
        return RESULT;
    }

    private static long measureSubstractLong(int loops, int intArraySize){
        System.out.println("Substract Start");
        double time = 0.0;
        for (int loop = 0; loop < loops; loop++) {
            long[] intArray = generateRandomLongArray(intArraySize);
            Timer t = new Timer();
            for (long i : intArray) {
                RESULT -= i;
            }
            time += t.check();
        }
        System.out.println(countOneOperationTime(intArraySize, loops, time)+" ns\n");
        return RESULT;
    }

    private static long measureMultiplyLong(int loops, int intArraySize){
        System.out.println("Multiply Start");
        double time = 0.0;
        for (int loop = 0; loop < loops; loop++) {
            long[] intArray = generateRandomLongArray(intArraySize);
            Timer t = new Timer();
            for (long i : intArray) {
                RESULT *= i;
            }
            time += t.check();
        }
        System.out.println(countOneOperationTime(intArraySize, loops, time)+" ns\n");
        return RESULT;
    }

    private static long measureDivideLong(int loops, int intArraySize){
        System.out.println("Divide Start");
        double time = 0.0;
        for (int loop = 0; loop < loops; loop++) {
            long[] intArray = generateRandomLongArray(intArraySize);
            Timer t = new Timer();
            for (long i : intArray) {
                RESULT += 700000000/i;
            }
            time += t.check();
        }
        System.out.println(countOneOperationTime(intArraySize, loops, time)+" ns\n");
        return RESULT;
    }
     private static long warmupAndMeasureAdd(int warmupLoops, int testLoops, int arrySize){
        long a = measureAddLong(warmupLoops, arrySize);
        long b = measureAddLong(testLoops, arrySize);
        return a + b;
     }

    private static long warmupAndMeasureSubstarct(int warmupLoops, int testLoops, int arrySize){
        long a = measureSubstractLong(warmupLoops, arrySize);
        long b = measureSubstractLong(testLoops, arrySize);
        return a + b;
    }

    private static long warmupAndMeasureMultiply(int warmupLoops, int testLoops, int arrySize){
        long a = measureMultiplyLong(warmupLoops, arrySize);
        long b = measureMultiplyLong(testLoops, arrySize);
        return a + b;
    }

    private static long warmupAndMeasureDivide(int warmupLoops, int testLoops, int arrySize){
        long a = measureDivideLong(warmupLoops, arrySize);
        long b = measureDivideLong(testLoops, arrySize);
        return a + b;
    }

    public static long measureAll(int warmupLoops, int loops, int size){
        System.out.println("\nLONG:\n");
        long a = warmupAndMeasureAdd(warmupLoops, loops, size);
        long b = warmupAndMeasureSubstarct(warmupLoops, loops, size);
        long c = warmupAndMeasureMultiply(warmupLoops, loops, size);
        long d = warmupAndMeasureDivide(warmupLoops, loops, size);
        return a+b+c+d;
    }
}
