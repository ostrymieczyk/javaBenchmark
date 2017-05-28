package Helper;

/**
 *
 */
public class Timer {
	/**
	 *
	 */
	private long start, spent = 0;

	/**
	 *
	 */
	public Timer() {
		play(); 
	}

	/**
	 * @return
	 */
	public long check() {
		return (System.nanoTime()-start+spent); 
	}

	/**
	 *
	 */
	private void play() {
		start = System.nanoTime(); 
	}
}
