package hr.fer.oprpp1.custom.collections;

/**
 * Klasa koja pod zadanim kljucem pamti predanu vrijednost. Ovakva struktura je poznata pod nazivom mapa.
 *
 * @author borna
 *
 * @param <K> tip kljuca
 * @param <V> tip vrijednosti
 */
public class Dictionary<K, V> {
	
	private ArrayIndexedCollection<Pair<K, V>> col;
	
	/**
	 * Privatna staticka klasa <code>Pair</code> koja stvara jedan par podataka: <code>key - value</code>.
	 * 
	 * @author borna
	 *
	 * @param <K> tip kljuca
	 * @param <V> tip vrijednosti
	 */
	private static class Pair<K, V> {
		
		private K key;
		private V value;
		
		/**
		 * Konstruktor. Stvara novi primjerak klase Pair: <code>Pair(key, value)</code>
		 * @param key kljuc novog para
		 * @param value vrijednost koja se pridjeljuje kljucu
		 */
		public Pair(K key, V value) {
			if (key == null) throw new IllegalArgumentException("Kljuc ne smije biti null referenca!");
			
			this.key = key;
			this.value = value;
		}
	}
	
	/**
	 * Konstruktor. Stvara novu instancu klase <code>ArrayIndexedCollection</code> parametriziranu po tipu <code>Pair<K,V></code>.
	 */
	public Dictionary() {
		col = new ArrayIndexedCollection<Pair<K,V>>();
	}
	
	/**
	 * Metoda vraca vrijednost <code>true</code> ukoliko ne postoji niti jedan kljuc.
	 * 
	 * @return <code>true</code> ako ne postoji niti jedan kljuc u rjecniku, <code>false</code> u protivnom
	 */
	public boolean isEmpty() {
		if (col.isEmpty()) return true;
		return false;
	}
	
	/**
	 * Metoda vraca broj parova <code>key - value</code> koji se nalaze u trenutnom rjecniku.
	 * 
	 * @return velicina rijecnika (broj parova <code>key - value</code>)
	 */
	public int size() {
		return col.size();
	}
	
	/**
	 * Metoda brise citavi rjecnik.
	 */
	public void clear() {
		col.clear();
	}
	
	/**
	 * Metoda postavlja vrijednost <code>value</code> za zadani kljuc <code>key</code>. 
	 * Ukoliko je trazenom kljucu vec pripisana neka vrijednost, metoda vraca trenutnu vrijednost i umjesto nje postavlja novu vrijednost <code>value</code>. 
	 * Ako u rjecniku ne postoji zapis za neki kljuc (ovo je prvi unos vrijednosti za zadani <code>key</code>) metoda vraca null.
	 * 
	 * @param key kljuc na koji se zapisuje vrijednost
	 * @param value vrijednost koja se pridaje kljucu
	 * @return <code>null</code> ako se zapis dodaje u rjecnik, ako se "gazi" neka prethodna vrijednost onda metoda vraca tu dotadasnju vrijednost
	 * @throws NullPointerException ako je <code>key</code> null-referenca
	 */
	public V put(K key, V value) {
		if (key == null) throw new NullPointerException("Kljuc ne smije biti null-referenca!");
		
		Pair<K, V> pair = findInDictionary(key);
		if (pair != null) {
			V returningValue = pair.value;
			pair.value = value;
			return returningValue;
		}

		col.add(new Pair<K, V>(key, value));
		return null;
	}
	
	/**
	 * Metoda dohvaca vrijednost kljuca <code>key</code> ili vraca null ukoliko ne postoji pripadna vrijednost.
	 * 
	 * @param key kljuc cija se vrijednost dohvaca
	 * @return vrijednost <code>value</code> predanog kljuca ili <code>null</code> ako ne postoji pripadna vrijednost
	 */
	public V get(Object key) {
		//if (key == null) throw new IllegalArgumentException("Kljuc ne smije biti null referenca!");
		return findInDictionary(key) == null ? null : findInDictionary(key).value;
	}
	
	/**
	 * Metoda uklanja zapis za kljuc ukoliko on postoji. Pritom vraca vrijednost koja je bila zapisana u rjecniku za taj kljuc (ili vraca <code>null</code> ako kljuc ne postoji u rjecniku).
	 * 
	 * @param key kljuc ciji se zapis zeli izbrisati iz rjecnika
	 * @return vrijednost <code>value</code> kljuca koji se brise iz rjecnika ili <code>null</code> ako kljuc ne postoji u rjecniku.
	 */
	public V remove(K key) {
		//if (key == null) throw new IllegalArgumentException("Kljuc ne smije biti null referenca!");
		Pair<K, V> pair = findInDictionary(key);
		if (pair != null) {
			V returningValue = pair.value;
			col.remove(pair);
			return returningValue;
		}

		return null;
	}
	
	/**
	 * Metoda koja pronalazi kljuc u trenutnom rjecniku.
	 * 
	 * @param key kljuc ciji se zapis trazi u rjecniku
	 * @return <code>Pair<K,V></code> ako kljuc postoji u rjecniku, <code>null</code> u protivnom
	 */
	private Pair<K,V> findInDictionary(Object key) {
		ElementsGetter<Pair<K,V>> eg = col.createElementsGetter();
		
		while (eg.hasNextElement()) {
			Pair<K, V> pair = eg.getNextElement();
			if (pair.key.equals(key)) {
				return pair;
			}
		}
		
		return null;
	}
}