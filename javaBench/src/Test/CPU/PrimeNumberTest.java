package Test.CPU;

import Controller.ResultController;
import Helper.Timer;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Robert on 22.11.2016.
 */
public class PrimeNumberTest {

    private static int RESULT = 0;
    private static long TOTAL_TIME = 0;

    private static int[] generateNumbers(int size){
        return ThreadLocalRandom.current().ints(size,0, 10000).toArray();
    }

    private static boolean isPrime(int n) {
        for(int i=2;i<n;i++) {
            if(n%i==0)
                return false;
        }
        return true;
    }

    private static long primeNumberTest(int loop){
        System.out.println("\nprimeNumberTest\n");
        long time = 0;
        for(int i=0; i<loop; i++) {
            int[] array = generateNumbers(100_000);
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

    public static double warmupAndTest(int warmupLoops, int testLoops){
        TOTAL_TIME = 0;
        double a = primeNumberTest(warmupLoops);
        TOTAL_TIME += primeNumberTest(testLoops);
        ResultController.setPrimeNumberReslut(TOTAL_TIME);
        return a;
    }

}
