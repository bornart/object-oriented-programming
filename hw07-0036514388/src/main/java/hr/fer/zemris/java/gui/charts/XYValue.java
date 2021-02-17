package hr.fer.zemris.java.gui.charts;

/**
 * 
 * @author borna
 *
 */
public class XYValue {
	
	private int x;
	private int y;
	
	/**
	 * Konstruktor.
	 * 
	 * @param x koordinata na osi x
	 * @param y koordinata na osi y
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter. Dohvaća vrijednost x-koordinate.
	 * 
	 * @return koordinata na osi x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Getter. Dohvaća vrijednost y-koordinate.
	 * 
	 * @return koordinata na osi y
	 */
	public int getY() {
		return y;
	}
}
