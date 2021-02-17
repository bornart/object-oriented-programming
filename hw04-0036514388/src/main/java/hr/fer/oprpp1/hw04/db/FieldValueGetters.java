package hr.fer.oprpp1.hw04.db;

/**
 * Klasa koja implementira mogućnost dohvata različitih podataka pojedinog studenta.
 * 
 * @author borna
 *
 */
public class FieldValueGetters {
	
	/**
	 * Konstanta tipa <code>IFieldValueGetter</code> koja omogućuje dohvat studentovog imena.
	 */
	public static final IFieldValueGetter FIRST_NAME = StudentRecord::getFirstName;
	
	/**
	 * Konstanta tipa <code>IFieldValueGetter</code> koja omogućuje dohvat studentovog prezimena.
	 */
	public static final IFieldValueGetter LAST_NAME = StudentRecord::getLastName;
	
	/**
	 * Konstanta tipa <code>IFieldValueGetter</code> koja omogućuje dohvat studentovog jmbag-a.
	 */
	public static final IFieldValueGetter JMBAG = StudentRecord::getJmbag;
}
