package hr.fer.oprpp1.hw04.db;

/**
 * Funkcijsko sučelje s metodom <code>accepts</code>.
 * 
 * @author borna
 *
 */
@FunctionalInterface
public interface IFilter {
	
	/**
	 * Metoda obavlja provjeru nad predanim parametrom tipa <code>StudentRecord</code> i u skladu s rezultatom provjere vraća boolean vrijednost <code>true</code> ili <code>false</code>.
	 * 
	 * @param record studentovi podatci
	 * @return <code>true</code> ako studentovi podatci zadovoljavaju uvjet, <code>false</code> u protivnom
	 */
	public boolean accepts(StudentRecord record);
}
