package Test.CPU;

import Controller.ResultController;
import Helper.Timer;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public class PrimeNumberTest {

    /**
     *
     */
    private static int RESULT = 0;

    /**
     * @return
     */
    private static int[] generateNumbers(){
        return ThreadLocalRandom.current().ints(100_000,0, 10000).toArray();
    }

    /**
     * @param n
     * @return
     */
    private static boolean isPrime(int n) {
        for(int i=2;i<n;i++) {
            if(n%i==0)
                return false;
        }
        return true;
    }

    /**
     * @param loop
     * @return
     */
    private static long primeNumberTest(int loop){
        System.out.println("\nprimeNumberTest\n");
        long time = 0;
        for(int i=0; i<loop; i++) {
            int[] array = generateNumbers();
            Timer t = new Timer();
            for (int number : array) {
                if (isPrime(number))
                    RESULT += number;
            }
            time += t.check();
        }
        System.out.println(time/(1e9*loop)+" ns\n");

        return time;
    }

    /**
     *
     */
    public static void warmupAndTest(){
        long TOTAL_TIME = 0;
        double a = primeNumberTest(5);
        TOTAL_TIME += primeNumberTest(30);
        ResultController.setPrimeNumberResult(TOTAL_TIME);
    }

}
