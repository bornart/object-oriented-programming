package hr.fer.oprpp1.custom.collections;

/**
 * Sucelje Collection predstavlja kolekciju objekata.
 * 
 * @author borna
 *
 * @param <T> parametrizacija klase <code>Collection</code>
 */
public interface Collection<T>{
	
	/**
	 * Metoda provjerava je li kolekcija prazna ili sadrzi objekte.
	 * 
	 * @return istina ako je kolekcija prazna ili neistina ako kolekcija sadrzi barem jedan objekt
	 */
	default boolean isEmpty() {
		return this.size() < 1;
	}
	
	/**
	 * Izracunava broj objekata pohranjenih u kolekciji.
	 * 
	 * @return ukupan broj objekata kolekcije
	 */
	int size();
	
	/**
	 * Dodaje objekt u kolekciju.
	 * 
	 * @param value objekt koji dodajemo u kolekciju
	 */
	void add(T value);
	
	/**
	 * Provjerava sadrzi li kolekcija trazeni objekt.
	 * 
	 * @param value objekt koji zelimo pronaci unutar kolekcije
	 * @return istina ako se trazeni objekt pojavljuje u kolekciji ili neistina ukoliko kolekcija ne sadrzi trazeni objekt
	 */
	boolean contains(Object value);
	
	/**
	 * Uklanja jednu pojavu objekta iz kolekcije.
	 * 
	 * @param value objekt koji zelimo ukloniti
	 * @return istina ako kolekcija sadrzi trazeni objekt i ako je taj objekt uspjesno uklonjen
	 */
	boolean remove(Object value);
	
	/**
	 * Alocira novo polje cija je velicina jednaka velicini kolekcije. Novo polje se popunjava sadrzajem kolekcije.
	 * 
	 * @return novostvoreno polje objekata
	 * @throws UnsupportedOperationException
	 */
	Object[] toArray();
	
	/**
	 * Metoda dodaje sve elemente predane kolekcije u trenutnu kolekciju.
	 * 
	 * @param other kolekcija objekata koju zelimo dodati
	 */
	default void addAll(Collection<? extends T> other) {
		
		/**
		 * Lokalna klasa koja implementira sucelje Processor. 
		 * 
		 * @author borna
		 *
		 */
		class LocalProcessor implements Processor<T>{
			
			/**
			 * Dodaje predanu vrijednost u trenutnu kolekciju.
			 */
			@Override
			public void process(T value) {
				Collection.this.add(value);
			}

		}
		
		LocalProcessor processor = new LocalProcessor();
		other.forEach(processor);
	}
	
	/**
	 * Poziva metodu process za svaki element kolekcije.
	 * 
	 * @param processor instanca Processor-a nad kojom se poziva metoda process
	 */
	default void forEach(Processor<? super T> processor) {
		ElementsGetter <? extends T> eg = this.createElementsGetter(); //<? extends T>
		
		while (eg.hasNextElement() ) {
			processor.process(eg.getNextElement());
		}
	}
	
	/**
	 * Uklanja sve elemente iz kolekcije.
	 */
	void clear();
	
	/**
	 * Metoda stvara objekt s kojim korisnik moze pricati "jezikom" koji specificira tip ElementsGetter.
	 * 
	 * @return referenca na novostvoreni objekt tipa ElementsGetter
	 */
	ElementsGetter<T> createElementsGetter(); //<T>
	
	/**
	 * Metoda dohvaca redom sve elemente iz kolekcije <code>col</code> i u trenutnu kolekciju na kraj dodaje sve elemente koje predani <code>tester</code> prihvati.
	 *  
	 * @param col kolekcija cije elemente kopiramo u trenutnu kolekciju
	 * @param tester Tester kojim se odreduje koji se elementi iz predane kolekcije prihvacaju
	 */
	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> eg = col.createElementsGetter(); //<? extends T>
		
		while (eg.hasNextElement()) {
			T value = eg.getNextElement();
			
			if (tester.test(value)) {
				this.add(value);
			}
		}
	}
	
}

