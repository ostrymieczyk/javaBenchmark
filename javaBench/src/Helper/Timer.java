package Helper;

public class Timer {
	private long start, spent = 0;
	
	public Timer() {
		play(); 
	}
	
	public long check() {
		return (System.nanoTime()-start+spent); 
	}
	
	public void pause() { 
		spent += System.nanoTime()-start; 
	}
	
	public void play() { 
		start = System.nanoTime(); 
	}
}
