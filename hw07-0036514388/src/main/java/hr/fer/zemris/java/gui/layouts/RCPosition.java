package hr.fer.zemris.java.gui.layouts;

/**
 * Implementacija ograničenja koja se koriste u razred <code>CalcLayout</code>.
 * <p>
 * Ograničenja predstavljaju koordinate oblika (redak, stupac).
 * 
 * @author borna
 *
 */
public class RCPosition {
	
	private int row;
	private int column;
	
	/**
	 * Konstruktor razreda <code>RCPosition</code>. 
	 * 
	 * @param row redni broj retka 
	 * @param column redni broj stupca
	 * @throws CalcLayoutException prilikom nedozvoljenog korištenja ograničenja
	 */
	public RCPosition(int row, int column) {
		if (row < 1 || row > 5 || column < 1 || column > 7) throw new CalcLayoutException("Broj retka mora biti u intervalu [1,5], a broj stupca u intervalu [1,7]");
		if (row == 1 && column > 1 && column < 6) throw new CalcLayoutException("Zabranjeno je korištenje ograničenje (1,s) gdje je s>1 && s<6");
		
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Dohvaća redni broj retka. Numeracija redaka kreće od 1.
	 * 
	 * @return redni broj retka
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Dohvaća redni broj stupca. Numeracija stupaca kreće od 1.
	 * 
	 * @return redni broj stupca
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Metoda tvornica koja prima tekstovnu specifikaciju i vraća odgovarajući objekt tipa <code>RCPosition</code>.
	 * 
	 * @param text tekstovna specifikacija oblika <code>"brojRetka,brojStupca"</code>
	 * @return objekt tipa <code>RCPosition</code> koji odgovara primljenom ulaznom Stringu
	 * @throws IllegalArgumentException ako je tekstovna specifikacija neispravno zadana
	 */
	public static RCPosition parse(String text) {
		if (!text.matches("[+-]?\\d+,[+-]?\\d+")) throw new IllegalArgumentException("Neispravno zadana tekstovna specifikacija!");
		
		String[] coordinates = text.split(",");
		
		//regexom sam provjerio da se radi o cijelim brojevima! (dozvoljavam i negativne brojeve, iznimka ce se pojaviti u konstruktoru)
		int x = Integer.parseInt(coordinates[0]);
		int y = Integer.parseInt(coordinates[1]);
		
		return new RCPosition(x, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	
}
