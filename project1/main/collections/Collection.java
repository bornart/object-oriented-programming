package hr.fer.oprpp1.custom.collections;

/**
 * Klasa Collection predstavlja kolekciju objekata. 
 *  
 * @author borna
 *
 */
public class Collection {
	
	/**
	 * Poziva konstruktor bazne klase (pretpostavljeni konstruktor).
	 */
	protected Collection() {
		super();
	}
	
	/**
	 * Metoda provjerava je li kolekcija prazna ili sadrzi objekte.
	 * 
	 * @return istina ako je kolekcija prazna ili neistina ako kolekcija sadrzi barem jedan objekt
	 */
	public boolean isEmpty() {
		return this.size() < 1;
	}
	
	/**
	 * Izracunava broj objekata pohranjenih u kolekciji.
	 * U razredu Collection ova metoda vraca 0 i ocekuje se da ce je izvedeni razred prikladno nadjacati.
	 * 
	 * @return ukupan broj objekata kolekcije
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Dodaje objekt u kolekciju.
	 * U razredu Collection ova metoda je prazna i ocekuje se da ce je izvedeni razred prikladno nadjacati.
	 * 
	 * @param value objekt koji dodajemo u kolekciju
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Provjerava sadrzi li kolekcija trazeni objekt.
	 * U razredu Collection ova metoda vraca false i ocekuje se da ce je izvedeni razred prikladno nadjacati.
	 * 
	 * @param value objekt koji zelimo pronaci unutar kolekcije
	 * @return istina ako se trazeni objekt pojavljuje u kolekciji ili neistina ukoliko kolekcija ne sadrzi trazeni objekt
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Uklanja jednu pojavu objekta iz kolekcije.
	 * U razredu Collection ova metoda vraca false i ocekuje se da ce je izvedeni razred prikladno nadjacati.
	 * 
	 * @param value objekt koji zelimo ukloniti
	 * @return istina ako kolekcija sadrzi trazeni objekt i ako je taj objekt uspjesno uklonjen
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Alocira novo polje cija je velicina jednaka velicini kolekcije. Novo polje se popunjava sadrzajem kolekcije.
	 * U razredu Collection ova metoda ne vraca rezultat i ocekuje se da ce je izvedeni razred prikladno nadjacati.
	 * 
	 * @return novostvoreno polje objekata
	 * @throws UnsupportedOperationException
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Metoda dodaje sve elemente predane kolekcije u trenutnu kolekciju.
	 * 
	 * @param other kolekcija objekata koju zelimo dodati
	 */
	public void addAll(Collection other) {
		
		/**
		 * Lokalna klasa koja nasljeduje klasu Processor. 
		 * 
		 * @author borna
		 *
		 */
		class LocalProcessor extends Processor{
			
			/**
			 * Dodaje predanu vrijednost u trenutnu kolekciju.
			 */
			@Override
			public void process(Object value) {
				Collection.this.add(value);
			}
		}
		
		LocalProcessor processor = new LocalProcessor();
		other.forEach(processor);
	}
	
	/**
	 * Poziva metodu process za svaki element kolekcije.
	 * U razredu Collection ova metoda je prazna i ocekuje se da ce je izvedeni razred prikladno nadjacati.
	 * 
	 * @param processor instanca Processor-a nad kojom se poziva metoda process
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Uklanja sve elemente iz kolekcije.
	 * U razredu Collection ova metoda je prazna i ocekuje se da ce je izvedeni razred prikladno nadjacati.
	 */
	public void clear() {
		
	}
}

