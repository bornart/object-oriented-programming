package hr.fer.oprpp1.hw04.db;

/**
 * Funkcijsko sučelje s metodom <code>satisfied</code>.
 * 
 * @author borna
 *
 */
@FunctionalInterface
public interface IComparisonOperator {
	
	/**
	 * Metoda uspoređuje predane stringove i na temelju usporedbe vraća boolean vrijednost <code>true</code> ili <code>false</code>.
	 * 
	 * @param value1 string koji predstavlja prvi literal
	 * @param value2 string koji predstavlja drugi literal
	 * @return boolean vrijednost <code>true</code> ili <code>false</code> u ovisnosti o zadanoj usporedbi predanih parametara koji se uspoređuju
	 */
	public boolean satisfied(String value1, String value2);
}
