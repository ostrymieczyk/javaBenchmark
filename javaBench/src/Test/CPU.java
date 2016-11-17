package Test;

import Helper.Timer;

public class CPU {
	
	public static final int BASE = 42;

	public static int addInt(int i){
		return BASE + i;
	}
	
	public static int minusInt(int i){
		return BASE - i;
	}
	
	public static int razyInt(int i){
		return BASE * i;
	}
	
	public static int dzielInt(int i){
		return i / BASE;
	}
	
	public static int measure(int[] n, MeasureInterface interf){

		int res = 0;
		for (int nn:n){
			res += interf.measure(nn);
		}
		return res;

	}
}
