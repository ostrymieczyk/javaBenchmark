package Test;

import Helper.Timer;

public class CPU {
	
	public static final int BASE = 42;

	public static int addInt(int i){
		return BASE + i;
	}
	
	public static int minusInt(int i){
		return i-i-i-i-i-i-i-i-i-i-i-i-i-i-i-i-i-i-i-i;
	}
	
	public static int razyInt(int i){
		return BASE * i;
	}
	
	public static int dzielInt(int i){
		return i / BASE;
	}
	
	public static int[] measure(int z){
		int[] n = new int[10_000_000];
		int[] v = new int[10_000_000];
		int res = 0;
		
		for (int i = 0; i<n.length; i++){
			n[i] = i;
		}
		
		Timer t = new Timer();
		
		for (int i = 0; i<n.length; i++){
			v[i] = addInt(n[i]);
		}
		
		System.out.println(t.check()/10_000_000);
		return v;

	}
}
