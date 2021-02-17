package hr.fer.oprpp1.math;

/**
 * Klasa predstavlja model dvodimenzionalnog vektora.
 * 
 * @author borna
 *
 */
public class Vector2D {
	
	private double x;
	private double y;
	
	/**
	 * Konstruktor koji stvara novi 2D vektor cije su komponente <code>x</code> i <code>y</code>.
	 * 
	 * @param x komponenta uz jedinicni vektor <code>i</code>
	 * @param y komponenta uz jedinicni vektor <code>j</code>
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter. Dohvaca komponentu uz jedinicni vektor <code>i</code>.
	 * 
	 * @return komponenta uz jedinicni vektor <code>i</code>
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Getter. Dohvaca komponentu uz jedinicni vektor <code>j</code>.
	 * 
	 * @return komponenta uz jedinicni vektor <code>j</code>
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Metoda trenutnom vektoru pribraja predani vektor <code>offset</code>: this + other.
	 * 
	 * @param offset drugi vektor koji se pribraja trenutnom vektoru
	 * @throws NullPointerException ako je <code>offset = null</code>
	 */
	public void add(Vector2D offset) {
		this.x = this.x + offset.x;
		this.y = this.y + offset.y;
	}
	
	/**
	 * Metoda zbraja trenutni i predani vektor <code>offset</code>: this + other.
	 * Kao rezultat vraca novi vektor.
	 * 
	 * @param offset drugi vektor koji se zbraja s trenutnim vektorom
	 * @return novi vektor koji je jednak zbroju trenutnog i predanog (<code>offset</code>) vektora
	 * @throws NullPointerException ako je <code>offset = null</code>
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}
	
	/**
	 * Metoda rotira trenutni vektor za kut <code>angle</code>.
	 * 
	 * @param angle kut za koji se trenutni vektor rotira
	 */
	public void rotate(double angle) {
		double valueOfX = this.x;
		this.x = Math.cos(angle) * this.x - Math.sin(angle) * this.y;
		this.y = Math.sin(angle) * valueOfX + Math.cos(angle) * this.y;
	}
	
	/**
	 * Metoda vraca novi vektor koji je jednak trenutnom vektoru rotiranom za kut <code>angle</code>.
	 * 
	 * @param angle kut rotacije vektora
	 * @return novi vektor koji je koji je jednak trenutnom vektoru rotiranom za dani kut
	 */
	public Vector2D rotated(double angle) {
		double x = Math.cos(angle) * this.x - Math.sin(angle) * this.y;
		double y = Math.sin(angle) * this.x + Math.cos(angle) * this.y;
		return new Vector2D(x, y);
	}
	
	/**
	 * Metoda skalira trenutni vektor skalarom. Postupak skaliranja oznacava mnozenje vektora sa realnom vrijednoscu <code>scaler</code>.
	 * 
	 * @param scaler realna vrijednost kojom se trenutni vektor skalira
	 */
	public void scale(double scaler) {
		this.x = scaler * this.x;
		this.y = scaler * this.y;
	}
	
	/**
	 * Metoda vraca novi vektor koji je jednak skalaru trenutnog vektora (mnozenje vektora sa realnom vrijednoscu <code>scaler</code>).
	 * 
	 * @param scaler realna vrijednost kojom se trenutni vektor skalira
	 * @return novi vektor koji je jednak umnosku trenutnog vektora i skalara
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(scaler * this.x, scaler * this.y);
	}
	
	/**
	 * Metoda vraca kopiju trenutnog dvodimenzionalnog vektora.
	 * 
	 * @return novi vektor koji ima jednake komponente i, j kao trenutni vektor (kopija trenutnog vektora)
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}
}
