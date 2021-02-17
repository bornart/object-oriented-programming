package hr.fer.oprpp1.custom.collections;

/**
 * Sucelje ElementsGetter je ugovor koji korisniku (deklaracijama metoda getNextElement i hasNextElement) daje mogucnost vracanja elemenata jedan po jedan, na njegov zahtjev. 
 * 
 * @author borna
 *
 * @param <T> parametrizacija klase <code>ElementsGetter</code>
 */
public interface ElementsGetter<T> {
	
	/**
	 * Metoda provjerava nalazi li se u kolekciji jos elemenata koje moze vratiti korisniku i sukladno tome vraca boolean vrijednost true ili false.
	 * 
	 * @return true ako kolekcija sadrzi jos elemenata, false u protivnom
	 */
	boolean hasNextElement();
	
	/**
	 * Dohvaca iduci neisporuceni element iz kolekcije.
	 * 
	 * @return referenca na iduci element kolekcije koji jos uvijek nije isporucen
	 * @throws NoSuchElementException ako trazimo isporuku iduceg elementa, ali svi elementi kolekcije su vec isporuceni
	 */
	T getNextElement();
	
	/**
	 * Metoda nad svim preostalim elementima kolekcije poziva zadani procesor.
	 * 
	 * @param p Processor
	 */
	default void processRemaining(Processor<T> p) {
		
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
