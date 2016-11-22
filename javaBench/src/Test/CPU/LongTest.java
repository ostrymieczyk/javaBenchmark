package Test.CPU;

import Helper.Timer;

public class LongTest {

    private static long RESULT = 50;

    private static long addLong(){
        int n = 10, count = 10_000_000;
        double runningTime = 0.0;
        do {
            double st = 0.0;
            for (int j=0; j<n; j++) {
                Timer t = new Timer();
                for (int i = 1; i <= count; i++) {
                    long ii = (long) (i);
                    RESULT += ii;
                }
                runningTime = t.check();
                double time = runningTime / count;
                st += time;
            }
            double mean = st/n;
            System.out.printf("%6.1f  %10d%n", mean, count);
            count *= 2;
        } while (runningTime < 1e9 && count < Integer.MAX_VALUE/2);
        System.out.println("Add Long done");
        return RESULT;
    }

    private static long subtractLong(){
        int n = 10, count = 10_000_000;
        double runningTime = 0.0;
        do {
            double st = 0.0, sst = 0.0;
            for (int j=0; j<n; j++) {
                Timer t = new Timer();
                for (int i = 1; i <= count; i++) {
                    long ii = (long) (i);
                    RESULT -= ii;
                }
                runningTime = t.check();
                double time = runningTime / count;
                st += time;
            }
            double mean = st/n;
            System.out.printf("%6.1f  %10d%n", mean, count);
            count *= 2;
        } while (runningTime < 1e9 && count < Integer.MAX_VALUE/2);
        System.out.println("Substract Long done");
        return RESULT;
    }

    private static long multiplyLong(){
        int n = 10, count = 10_000_000;
        double runningTime = 0.0;
        do {
            double st = 0.0, sst = 0.0;
            for (int j=0; j<n; j++) {
                Timer t = new Timer();
                for (int i = 1; i <= count; i++) {
                    long ii = (long) (i);
                    RESULT *= ii;
                }
                runningTime = t.check();
                double time = runningTime / count;
                st += time;
            }
            double mean = st/n;
            System.out.printf("%6.1f  %10d%n", mean, count);
            count *= 2;
        } while (runningTime < 1e9 && count < Integer.MAX_VALUE/2);
        System.out.println("Multiply Long done");
        return RESULT;
    }

    private static long divideLong(){
        int n = 10, count = 10_000_000;
        double runningTime = 0.0;
        do {
            double st = 0.0, sst = 0.0;
            for (int j=0; j<n; j++) {
                Timer t = new Timer();
                for (int i = 1; i <= count; i++) {
                    long ii = (long) (i);
                    RESULT /= ii;
                }
                runningTime = t.check();
                double time = runningTime / count;
                st += time;
            }
            double mean = st/n;
            System.out.printf("%6.1f  %10d%n", mean, count);
            count *= 2;
        } while (runningTime < 1e9 && count < Integer.MAX_VALUE/2);
        System.out.println("Divide Long done");
        return RESULT;
    }

    public static long measureAll(){
        long a = addLong();
        long b = subtractLong();
        long c = multiplyLong();
        long d = divideLong();
        return a+b+c+d;
    }
}
