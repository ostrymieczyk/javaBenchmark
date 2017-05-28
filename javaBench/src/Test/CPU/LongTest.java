package Test.CPU;

import Controller.ResultController;
import Helper.Timer;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public class LongTest {

    /**
     *
     */
    private static long RESULT = 50;
    /**
     *
     */
    private static long TOTAL_TIME = 0;

    /**
     * @param arraySize
     * @return
     */
    private static long[] generateRandomLongArray(int arraySize){
        return ThreadLocalRandom
                .current()
                .longs(arraySize, Long.MIN_VALUE, Long.MAX_VALUE)
                .parallel()
                .map(i -> {
                    if(i == 0) i += 1L;
                    return i;
                })
                .toArray();
    }

    /**
     * @param randomIntArraySize
     * @param loops
     * @param loopTime
     * @return
     */
    private static double countOneOperationTime(int randomIntArraySize, int loops, double loopTime){
        return loopTime/(randomIntArraySize*loops);
    }

    /**
     * @param longs
     */
    private static void add(long[] longs){
        for (long i : longs) {
            RESULT += i;
        }
    }

    /**
     * @param longs
     */
    private static void substract(long[] longs){
        for (long i : longs) {
            RESULT -= i;
        }
    }

    /**
     * @param longs
     */
    private static void multiply(long[] longs){
        for (long i : longs) {
            RESULT *= i;
        }
    }

    /**
     * @param longs
     */
    private static void divide(long[] longs){
        for (long i : longs) {
            RESULT += Long.MAX_VALUE/i;
        }
    }

    /**
     * @param loops
     * @param arraySize
     * @param testable
     * @return
     */
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

    /**
     *
     */
    private interface MathInterface {
        /**
         * @param longs
         */
        void operate(long[] longs);
    }

    /**
     *
     */
    private enum WarmAndMeasure{
        /**
         *
         */
        ADD {
            @Override
            public long test(int warmupLoops, int testLoops, int arraySize) {
                System.out.println("\nADD");
                a = measure(warmupLoops, arraySize, LongTest::add);
                TOTAL_TIME += measure(testLoops, arraySize, LongTest::add);
                return a;
            }
        },
        /**
         *
         */
        SUBTRACT {
            @Override
            public long test(int warmupLoops, int testLoops, int arraySize) {
                System.out.println("\nSUBTRACT");
                a = measure(warmupLoops, arraySize, LongTest::substract);
                TOTAL_TIME += measure(testLoops, arraySize, LongTest::substract);
                return a;
            }
        },
        /**
         *
         */
        MULTIPLY {
            @Override
            public long test(int warmupLoops, int testLoops, int arraySize) {
                System.out.println("\nMULTIPLY");
                a = measure(warmupLoops, arraySize, LongTest::multiply);
                TOTAL_TIME += measure(testLoops, arraySize, LongTest::multiply);
                return a;
            }
        },
        DIVIDE {
            @Override
            public long test(int warmupLoops, int testLoops, int arraySize) {
                System.out.println("\nDIVIDE");
                a = measure(warmupLoops, arraySize, LongTest::divide);
                TOTAL_TIME += measure(testLoops, arraySize, LongTest::divide);
                return a;
            }
        };

        /**
         *
         */
        long a = 0;

        /**
         * @param warmupLoops
         * @param testLoops
         * @param arraySize
         * @return
         */
        public abstract long test(int warmupLoops, int testLoops, int arraySize);
    }

    /**
     *
     */
    public static void measureAll(){
        System.out.println("\nLONG");
        TOTAL_TIME = 0;
        WarmAndMeasure.ADD.test(50, 300, 1_250_000);
        WarmAndMeasure.SUBTRACT.test(50, 300, 1_250_000);
        WarmAndMeasure.MULTIPLY.test(50, 300, 1_250_000);
        WarmAndMeasure.DIVIDE.test(50, 300, 1_250_000);
        ResultController.setLongResult(TOTAL_TIME);
    }
}
