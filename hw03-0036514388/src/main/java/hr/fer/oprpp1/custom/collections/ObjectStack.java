package hr.fer.oprpp1.custom.collections;

/**
 * Klasa ObjectStack predstavlja adapter u koristenom "design pattern-u".
 * Sadrzi metode koje korisniku omogucuju koristenje programa kao stoga pritom skrivajuci implementacijske detalje.
 * 
 * @author borna
 *
 * @param <T> parametrizacija klase <code>ObjectStack</code>
 */
public class ObjectStack<T> {
	
	private ArrayIndexedCollection<T> arrCollection;
	
	/**
	 * Konstruktor prosljeduje dani parametar instanci klase ObjectStack 
	 * 
	 * @param arrCollection kolekcija koja je po tipu referenca na razred ArrayIndexedCollection
	 */
	public ObjectStack(ArrayIndexedCollection<T> arrCollection) {
		this.arrCollection = arrCollection;
	}
	
	/**
	 * Pretpostavljeni konstruktor. Stvara novu instancu klase ArrayIndexedCollection.
	 */
	public ObjectStack() {
		this.arrCollection = new ArrayIndexedCollection<T>();
	}
	
	/**
	 * Metoda provjerava je li kolekcija prazna ili sadrzi objekte.
	 * 
	 * @return istina ako je kolekcija prazna ili neistina ako kolekcija sadrzi barem jedan objekt
	 */
	public boolean isEmpty() {
		return arrCollection.isEmpty();
	}
	
	/**
	 * Izracunava broj objekata pohranjenih u kolekciji.
	 * 
	 * @return ukupan broj objekata kolekcije
	 */
	public int size() {
		return arrCollection.size();
	}
	
	/**
	 * Zadanu vrijednost stavlja na vrh stoga.
	 * 
	 * @param value objekt koji se stavlja na vrh stoga
	 * @throws NullPointerException ako je value <code>null</code>
	 */
	public void push(T value) {
		arrCollection.add(value);
	}
	
	/**
	 * Skida sa stoga prvi objekt (vrijednost koja je posljednja stavljena na stog) i vraca ga kao povratnu vrijednost.
	 * 
	 * @return vrijednost koja je skinuta sa stoga
	 * @throws EmptyStackException ako se pokusa skinuti vrijednost sa praznog stoga
	 */
	public T pop() {
		if (this.size() < 1) throw new EmptyStackException("Stog je prazan! Nemoguce je obaviti operaciju skidanja vrijednosti sa stoga.");
			
		T obj = null;
			
		obj = arrCollection.get(this.size()-1);
		arrCollection.remove(this.size()-1);
			
		return obj;
	}
	
	/**
	 * Vraca element sa vrha stoga (posljedni element koji je stavljen na stog)
	 * 
	 * @return objekt koji se nalazi na vrhu stoga
	 * @throws EmptyStackException ako se pokusa skinuti vrijednost sa praznog stoga
	 */
	public T peek() {
		if (this.size() < 1) throw new EmptyStackException("Stog je prazan! Nemoguce je obaviti operaciju dohvata vrijednosti sa stoga.");

		return arrCollection.get(this.size()-1);
	}
	
	/**
	 * Uklanja sve elemente sa stoga.
	 */
	public void clear() {
		for (int i = 0, j = arrCollection.size(); i < j; i++) {
			arrCollection.remove(j-1-i);
		}
	}
}
