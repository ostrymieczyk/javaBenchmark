package Test;

import Helper.Timer;

public class CPU {

	private static int RESULT = 0;

	public static int addInt(){
		int n = 10, count = 10_000_000;
		double runningTime = 0.0;
		do {
			double st = 0.0;
			for (int j=0; j<n; j++) {
                int i = 1;
                Timer t = new Timer();
                while (i<=count)
					RESULT += i++;
				runningTime = t.check();
				double time = runningTime / count;
				st += time;
			}
			double mean = st/n;
			System.out.printf("%6.1f  %10d%n", mean, count);
            count *= 2;
		} while (runningTime < 1e9 && count < Integer.MAX_VALUE/2);
        System.out.println("Add Int done");
		return RESULT;
	}
	
	public static int subtractInt(){
        int n = 10, count = 10_000_000;
        double runningTime = 0.0;
        do {
            double st = 0.0, sst = 0.0;
            for (int j=0; j<n; j++) {
                int i = 1;
                Timer t = new Timer();
                while (i<=count)
                    RESULT -= i++;
                runningTime = t.check();
                double time = runningTime / count;
                st += time;
            }
            double mean = st/n;
            System.out.printf("%6.1f  %10d%n", mean, count);
            count *= 2;
        } while (runningTime < 1e9 && count < Integer.MAX_VALUE/2);
        System.out.println("Substract Int done");
        return RESULT;
    }
	
	public static int multiplyInt(){
        int n = 10, count = 10_000_000;
        double runningTime = 0.0;
        do {
            double st = 0.0, sst = 0.0;
            for (int j=0; j<n; j++) {
                int i = 1;
                Timer t = new Timer();
                while (i<=count)
                    RESULT *= i++;
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
	
	public static int divideInt(){
        int n = 10, count = 10_000_000;
        double runningTime = 0.0;
        do {
            double st = 0.0, sst = 0.0;
            for (int j=0; j<n; j++) {
                int i = 1;
                Timer t = new Timer();
                while (i<=count)
                    RESULT /= i++;
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

}
