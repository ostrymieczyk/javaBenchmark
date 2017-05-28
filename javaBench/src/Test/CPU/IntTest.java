package Test.CPU;

import Controller.ResultController;
import Helper.Timer;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public class IntTest {

    /**
     * Zmienna, do ktorej zostaja przypisywane wyniki,
     * w celu unikniecia optymalizacji wprowadzanych przez JVM
     */
    private static int RESULT = 50;

    /**
     * Zmienna w ktorej trzymana jest suma czasu wykonywanych testow w nanosekudach
     */
    private static long TOTAL_TIME = 0;

    /**
     * Generuje tablice losowych liczb typu int o pdanym rozmiarze.<br>
     * Zakres generownych liczb miesci sie w granicach:
     * od {@link Integer#MIN_VALUE} do {@link Integer#MAX_VALUE}
     *
     * @param arraySize - rozmir generownej tablicy
     * @return tablica z wylosowanymi liczbami
     */
    private static int[] generateRandomIntArray(int arraySize){
        return ThreadLocalRandom
                .current()
                .ints(arraySize, Integer.MIN_VALUE, Integer.MAX_VALUE)
                .parallel()
                .map(i -> {
                    if(i == 0) i++;
                    return i;
                })
                .toArray();
    }

    /**
     * Funkcja dodajaca do zmiennej {@link IntTest#RESULT} liczby z tablicy podanej w argumencie.<br>
     * Wynik przypisywany jest do {@link IntTest#RESULT}
     *
     * @param ints Tablica liczb do dodania
     */
    private static void add(int[] ints){
        for (int i : ints) {
            RESULT += i;
        }
    }

    /**
     * Funkcja odejmujaca od zmiennej {@link IntTest#RESULT} liczby z tablicy podanej w argumencie. <br>
     * Wynik przypisywany jest do {@link IntTest#RESULT}
     *
     * @param ints Tablica liczb do odjecia
     */
    private static void subtract(int[] ints){
        for (int i : ints) {
            RESULT -= i;
        }
    }

    /**
     * Funkcja mnozaca zmienna {@link IntTest#RESULT} przez liczby z tablicy podanej w argumencie.<br>
     * Wynik przypisywany jest do {@link IntTest#RESULT}
     *
     * @param ints Tablica czynnikow mnozenia
     */
    private static void multiply(int[] ints){
        for (int i : ints) {
            RESULT *= i;
        }
    }

    /**
     * Funkcja dzielaca stala {@link Integer#MAX_VALUE} przez liczby z tablicy podanej w argumencie.<br>
     * Wynik jest dodawany i przypisywany do {@link IntTest#RESULT}
     *
     * @param ints Tablica dzielnikow
     */
    private static void divide(int[] ints){
        for (int i : ints) {
            RESULT += Integer.MAX_VALUE/i;
        }
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
     * @param loops
     * @param arraySize
     * @param testable
     * @return
     */
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

    /**
     *
     */
    private interface MathInterface {
        /**
         * @param ints
         */
        void operate(int[] ints);
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
                a = measure(warmupLoops, arraySize, IntTest::add);
                TOTAL_TIME += measure(testLoops, arraySize, IntTest::add);
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
                a = measure(warmupLoops, arraySize, IntTest::subtract);
                TOTAL_TIME += measure(testLoops, arraySize, IntTest::subtract);
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
                a = measure(warmupLoops, arraySize, IntTest::multiply);
                TOTAL_TIME += measure(testLoops, arraySize, IntTest::multiply);
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
                a = measure(warmupLoops, arraySize, IntTest::divide);
                TOTAL_TIME += measure(testLoops, arraySize, IntTest::divide);
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
        System.out.println("\nINT");
        TOTAL_TIME = 0;
        WarmAndMeasure.ADD.test(50, 300, 1_250_000);
        WarmAndMeasure.SUBTRACT.test(50, 300, 1_250_000);
        WarmAndMeasure.MULTIPLY.test(50, 300, 1_250_000);
        WarmAndMeasure.DIVIDE.test(50, 300, 1_250_000);
        System.out.println(RESULT);
        ResultController.setIntResult(TOTAL_TIME);
    }

}
