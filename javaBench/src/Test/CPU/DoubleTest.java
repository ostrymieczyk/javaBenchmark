package Test.CPU;

import Helper.Timer;

public class DoubleTest {

    private static double RESULT = 0.0;

    private static double addDouble(){
        int n = 10, count = 10_000_000;
        double runningTime = 0.0;
        do {
            double st = 0.0;
            for (int j=0; j<n; j++) {
                int i = 1;
                Timer t = new Timer();
                while (i<=count)
                    RESULT += (double)(i++)*1.1;
                runningTime = t.check();
                double time = runningTime / count;
                st += time;
            }
            double mean = st/n;
            System.out.printf("%6.1f  %10d%n", mean, count);
            count *= 2;
        } while (runningTime < 1e9 && count < Integer.MAX_VALUE/2);
        System.out.println("Add Double done");
        return RESULT;
    }

    private static double subtractDouble(){
        int n = 10, count = 10_000_000;
        double runningTime = 0.0;
        do {
            double st = 0.0, sst = 0.0;
            for (int j=0; j<n; j++) {
                int i = 1;
                Timer t = new Timer();
                while (i<=count)
                    RESULT -= (double)(i++)*1.1;
                runningTime = t.check();
                double time = runningTime / count;
                st += time;
            }
            double mean = st/n;
            System.out.printf("%6.1f  %10d%n", mean, count);
            count *= 2;
        } while (runningTime < 1e9 && count < Integer.MAX_VALUE/2);
        System.out.println("Substract Double done");
        return RESULT;
    }

    private static double multiplyDouble(){
        int n = 10, count = 10_000_000;
        double runningTime = 0.0;
        do {
            double st = 0.0, sst = 0.0;
            for (int j=0; j<n; j++) {
                int i = 1;
                Timer t = new Timer();
                while (i<=count)
                    RESULT *= (double)(i++)*1.1;
                runningTime = t.check();
                double time = runningTime / count;
                st += time;
            }
            double mean = st/n;
            System.out.printf("%6.1f  %10d%n", mean, count);
            count *= 2;
        } while (runningTime < 1e9 && count < Integer.MAX_VALUE/2);
        System.out.println("Multiply Double done");
        return RESULT;
    }

    private static double divideDouble(){
        int n = 10, count = 10_000_000;
        double runningTime = 0.0;
        do {
            double st = 0.0, sst = 0.0;
            for (int j=0; j<n; j++) {
                int i = 1;
                Timer t = new Timer();
                while (i<=count)
                    RESULT /= (double)(i++)*1.1;
                runningTime = t.check();
                double time = runningTime / count;
                st += time;
            }
            double mean = st/n;
            System.out.printf("%6.1f  %10d%n", mean, count);
            count *= 2;
        } while (runningTime < 1e9 && count < Integer.MAX_VALUE/2);
        System.out.println("Divide Double done");
        return RESULT;
    }

    public static double measureAll(){
        double a = addDouble();
        double b = subtractDouble();
        double c = multiplyDouble();
        double d = divideDouble();
        return a+b+c+d;
    }
}
