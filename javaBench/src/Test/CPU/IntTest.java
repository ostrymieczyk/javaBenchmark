package Test.CPU;

import Helper.Timer;

public class IntTest {

    private static int RESULT = 50;
    private static int N = 10, COUNT = 10_000_000;

    private static int addInt(){
        double runningTime = 0.0;
        do {
            double st = 0.0;
            for (int j=0; j<N; j++) {
                Timer t = new Timer();
                for (int i = 1; i <= COUNT; i++)
                    RESULT += i;
                runningTime = t.check();
                double time = runningTime / COUNT;
                st += time;
            }
            double mean = st/N;
            System.out.printf("%6.1f  %10d%n", mean, COUNT);
            COUNT *= 2;
        } while (runningTime < 1e9 && COUNT < Integer.MAX_VALUE/2);
        System.out.println("Add Int done");
        return RESULT;
    }

    private static int subtractInt(){
        double runningTime = 0.0;
        do {
            double st = 0.0, sst = 0.0;
            for (int j=0; j<N; j++) {
                Timer t = new Timer();
                for (int i = 1; i <= COUNT; i++)
                    RESULT -= i;
                runningTime = t.check();
                double time = runningTime / COUNT;
                st += time;
            }
            double mean = st/N;
            System.out.printf("%6.1f  %10d%n", mean, COUNT);
            COUNT *= 2;
        } while (runningTime < 1e9 && COUNT < Integer.MAX_VALUE/2);
        System.out.println("Substract Int done");
        return RESULT;
    }

    private static int multiplyInt(){
        int n = 10, count = 10_000_000;
        double runningTime = 0.0;
        do {
            double st = 0.0, sst = 0.0;
            for (int j=0; j<n; j++) {
                Timer t = new Timer();
                for (int i = 1; i <= count; i++)
                    RESULT *= i;
                runningTime = t.check();
                double time = runningTime / count;
                st += time;
            }
            double mean = st/n;
            System.out.printf("%6.1f  %10d%n", mean, count);
            count *= 2;
        } while (runningTime < 1e9 && count < Integer.MAX_VALUE/2);
        System.out.println("Multiply Int done");
        return RESULT;
    }

    private static int divideInt(){
        int n = 10, count = 10_000_000;
        double runningTime = 0.0;
        do {
            double st = 0.0, sst = 0.0;
            for (int j=0; j<n; j++){
                Timer t = new Timer();
                for (int i = 1; i <= count; i++)
                    RESULT /= i;
                runningTime = t.check();
                double time = runningTime / count;
                st += time;
            }
            double mean = st/n;
            System.out.printf("%6.1f  %10d%n", mean, count);
            count *= 2;
        } while (runningTime < 1e9 && count < Integer.MAX_VALUE/2);
        System.out.println("Divide Int done");
        return RESULT;
    }

    public static int measureAll(){
        int a = addInt();
        int b = subtractInt();
        int c = multiplyInt();
        int d = divideInt();
        return a+b+c+d;
    }
}
