package Test.CPU;

import Controller.ResultController;
import Helper.Timer;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public class DoubleTest {

    /**
     *
     */
    private static double RESULT = 50.0;
    /**
     *
     */
    private static long TOTAL_TIME = 0;


    /**
     *
     */
    private static final int ADD = 0;
    /**
     *
     */
    private static final int SUBTRACT = 1;
    /**
     *
     */
    private static final int MULTIPLY = 2;
    /**
     *
     */
    private static final int DIVIDE = 3;

    /**
     * @param arraySize
     * @return
     */
    private static double[] generateRandomDoubleArray(int arraySize){
        return ThreadLocalRandom
                .current()
                .doubles(arraySize, Double.MIN_VALUE, Double.MAX_VALUE)
                .parallel()
                .map(i -> {
                    if(i == 0) i += 1.0;
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
     * @param doubles
     */
    private static void add(double[] doubles){
        for (double i : doubles) {
            RESULT += i;
        }
    }

    /**
     * @param doubles
     */
    private static void subtract(double[] doubles){
        for (double i : doubles) {
            RESULT -= i;
        }
    }

    /**
     * @param doubles
     */
    private static void multiply(double[] doubles){
        for (double i : doubles) {
            RESULT *= i;
        }
    }

    /**
     * @param doubles
     */
    private static void divide(double[] doubles){
        for (double i : doubles) {
            RESULT += Double.MAX_VALUE/i;
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
            double[] doubles = generateRandomDoubleArray(arraySize);
            Timer t = new Timer();
            testable.operate(doubles);
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
         * @param doubles
         */
        void operate(double[] doubles);
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
                a = measure(warmupLoops, arraySize, DoubleTest::add);
                TOTAL_TIME += measure(testLoops, arraySize, DoubleTest::add);
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
                a = measure(warmupLoops, arraySize, DoubleTest::subtract);
                TOTAL_TIME += measure(testLoops, arraySize, DoubleTest::subtract);
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
                a = measure(warmupLoops, arraySize, DoubleTest::multiply);
                TOTAL_TIME += measure(testLoops, arraySize, DoubleTest::multiply);
                return a;
            }
        },
        /**
         *
         */
        DIVIDE {
            @Override
            public long test(int warmupLoops, int testLoops, int arraySize) {
                System.out.println("\nDIVIDE");
                a = measure(warmupLoops, arraySize, DoubleTest::divide);
                TOTAL_TIME += measure(testLoops, arraySize, DoubleTest::divide);
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
        System.out.println("\nDOUBLE");
        TOTAL_TIME = 0;
        WarmAndMeasure.ADD.test(50, 300, 1_250_000);
        WarmAndMeasure.SUBTRACT.test(50, 300, 1_250_000);
        WarmAndMeasure.MULTIPLY.test(50, 300, 1_250_000);
        WarmAndMeasure.DIVIDE.test(50, 300, 1_250_000);
        ResultController.setDoubleResult(TOTAL_TIME);
    }

}
