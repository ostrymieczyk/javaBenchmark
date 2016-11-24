package Test.CPU;

import Helper.Timer;
import com.sun.org.apache.regexp.internal.RE;

import java.util.Random;

/**
 * Created by Robert on 22.11.2016.
 */
public class PrimeNumberTest {

    private static int RESULT = 0;

    private static int[] generateNumbers(int size){
        Random random = new Random();
        int [] array = new int[size];
        for(int i = 0; i<array.length; i++){
            array[i] = i+100;
        }
        return array;
    }

    private static boolean isPrime(int n) {
        for(int i=2;i<n;i++) {
            if(n%i==0)
                return false;
        }
        return true;
    }

    private static int primeNumberTest(int loop){
        System.out.println("\nprimeNumberTest\n");
        double time = 0.0;
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

        return RESULT;
    }

    public static int warmupAndTest(int warmupLoops, int testLoops){
        int a = primeNumberTest(warmupLoops);
        int b = primeNumberTest(testLoops);
        return a+b;
    }

}
