package hr.fer.oprpp1.hw04.db;

/**
 * Funkcijsko sučelje sa metodom <code>get</code>.
 * 
 * @author borna
 *
 */
@FunctionalInterface
public interface IFieldValueGetter {
	
	/**
	 * Metoda iz predanog objekta tipa <code>StudentRecord</code> zadržava traženi podatak o studentu i vraća ga u obliku stringa.
	 * 
	 * @param record objekta tipa <code>StudentRecord</code> koji predstavlja podatke o studentu
	 * @return traženi podatak o studentu (jedan!) preuzet iz objekta <code>record</code>
	 */
	public String get(StudentRecord record);
}
