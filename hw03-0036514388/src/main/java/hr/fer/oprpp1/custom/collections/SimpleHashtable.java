package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Klasa predstavlja tablicu rasprsenog adresiranja koja omogucava pohranu uredenih parova <code>(kljuc, vrijednost)</code>.
 * 
 * @author borna
 *
 * @param <K> tip kljuca
 * @param <V> tip vrijednosti
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {
	
	private TableEntry<K,V>[] table;
	private int size; //broj parova koji su pohranjeni u tablici!
	private long modificationCount;
	
	/**
	 * Javna staticka ugnijezdena klasa koja predstavlja jedan slot tablice.
	 * 
	 * @author borna
	 *
	 * @param <K>
	 * @param <V>
	 */
	public static class TableEntry<K, V> {
		
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		/**
		 * Konstruktor.
		 * 
		 * @param key kljuc
		 * @param value vrijednost pridijeljena kljucu
		 * @throws NullPointerException ako je <code>key</code> null-referenca
		 */
		public TableEntry(K key, V value) {
			if (key == null) throw new NullPointerException("Kljuc ne smije biti null-referenca!");
			
			this.key = key;
			this.value = value;
			next = null;
		}
		
		/**
		 * Getter. Metoda vraca kljuc.
		 * 
		 * @return kljuc
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * Getter. Metoda vraca vrijednost koja je pridijeljena odredenom kljucu.
		 * 
		 * @return vrijednost 
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Setter. Metoda postavlja <code>value</code> kao vrijednost para kljuc-vrijednost.
		 * 
		 * @param value postavljena vrijednost
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * Nadjacana metoda <code>toString</code> tako da ispisuje tableEntry-je u formatu: "kljuc=vrijednost".
		 */
		@Override
		public String toString() {
			String s = this.key + "=" + this.value;
			return s;
		}
	}
	
	/**
	 * Pretpostavljeni konstruktor. Stvara tablicu velicine 16 slotova.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = (TableEntry<K, V>[])new TableEntry[16];
		size = 0;
		modificationCount = 0L;
	}
	
	/**
	 * Konstruktor. Stvara tablicu velicine koja je potencija broja 2 koja je prva veca ili jednaka predanom broju (npr. ako se zada 30, bira se 32).
	 * 
	 * @param capacity broj koji predstavlja zeljeni pocetni kapacitet tablice
	 * @throws IllegalArgumentException ako je parametar <code>capacity</code> manji od 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) throw new IllegalArgumentException("Kapacitet tablice ne moze biti manji od 1!");
		
		int powerOfTwo;
		if ((capacity & (capacity - 1)) == 0) { //provjera je li parametar capacity potencija broja 2
			powerOfTwo = capacity;
		} else {
			powerOfTwo = (int)Math.pow(2, Math.floor(Math.log(capacity) / Math.log(2))+1); //log(n)/log(2) govori koliko puta broj 2 "stane" u broj n
		}
		
		table = (TableEntry<K, V>[])new TableEntry[powerOfTwo];
		size = 0;
		modificationCount = 0L;
	}
	
	/**
	 * Metoda postavlja vrijednost <code>value</code> za zadani kljuc <code>key</code> pritom pazeci na popunjenost tablice. 
	 * Ukoliko je trazenom kljucu vec pripisana neka vrijednost, metoda vraca trenutnu vrijednost i umjesto nje postavlja novu vrijednost <code>value</code>. 
	 * Ako ne postoji zapis za neki kljuc (ovo je prvi unos vrijednosti za zadani <code>key</code>) metoda vraca null.
	 * 
	 * @param key kljuc na koji se zapisuje vrijednost
	 * @param value vrijednost koja se pridaje kljucu
	 * @return <code>null</code> ako se zapis dodaje u rjecnik, ako se "gazi" neka prethodna vrijednost onda metoda vraca tu dotadasnju vrijednost
	 * @throws NullPointerException ako je <code>key</code> null-referenca
	 */
	public V put(K key, V value) {
		if (key == null) throw new NullPointerException("Kljuc ne smije biti null-referenca!");
		
		//provjera prepunjenosti:
		if ((double)size / table.length >= 0.75) {
			handleOccupancy();
		}
		
		TableEntry<K, V> node = findKeyInList(key);
		if (node != null) {
			V returningValue = node.getValue();
			node.setValue(value);
			return returningValue;
		}
		
		addTableEntryAtTheEndOfList(key, value); //potrebno je dodati zapis na kraj liste u odgovarajucem slotu!
		size++; 
		modificationCount++;
		return null;
	}
	
	/**
	 * Metoda dohvaca vrijednost kljuca <code>key</code> ili vraca <code>null</code> ukoliko ne postoji pripadna vrijednost.
	 * 
	 * @param key kljuc cija se vrijednost dohvaca
	 * @return vrijednost <code>value</code> predanog kljuca ili <code>null</code> ako ne postoji pripadna vrijednost
	 */
	public V get(Object key) {
		TableEntry<K, V> node = findKeyInList(key);
		return node == null ? null : node.getValue();
	}
	
	/**
	 * Metoda vraca broj parova koji su pohranjeni u tablici (velicinu kolekcije).
	 * 
	 * @return broj parova koji su pohranjeni u tablici 
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Metoda provjerava nalazi li se predani kljuc <code>key</code> u kolekciji. Dodatno, kljuc ne smije biti null-referenca.
	 * 
	 * @param key parametar tipa Objekta koji predstavlja kljuc cija se prisutnost u kolekciji ispituje
	 * @return <code>true</code> ako kolekcija sadrzi predani kljuc, <code>false</code> u protivnom
	 */
	public boolean containsKey(Object key) {
		if (key == null) return false;
		return findKeyInList(key) != null;
	}
	
	/**
	 * Metoda provjerava nalazi li se predana vrijednost <code>value</code> u kolekciji.
	 * Vrijednost smije biti null-referenca.
	 * 
	 * @param value parametar tipa Objekta koji predstavlja vrijednost cija se prisutnost u kolekciji ispituje
	 * @return <code>true</code> ako kolekcija sadrzi predanu vrijednost, <code>false</code> u protivnom
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> node = table[i];
			while (node != null) {
				if (value.equals(node.getValue())) {
					return true;
				}
				node = node.next;
			}
		}
		return false;
	}
	
	/**
	 * Metoda uklanja iz tablice uredeni par sa zadanim kljucem (ako takav postoji) i vraca pozivatelju tu vrijednost. U protivnom metoda vraca <code>null</code>.
	 * 
	 * @param key kljuc ciji se zapis zeli ukloniti iz kolekcije
	 * @return vrijednost kljuca ako ona postoji, null u protivnom
	 */
	public V remove(Object key) {
		if (key == null) return null;
		
		int slot = Math.abs(key.hashCode()) % table.length; 
		TableEntry<K, V> node = table[slot];
		
		if (key.equals(node.getKey())) {
			V returningValue = node.getValue();
			table[slot] = node.next;
			modificationCount++;
			size--;
			return returningValue;
		}
		
		while (node != null) {
			if (node.next.getKey().equals(key)) {
				V returningValue = node.next.getValue();
				if (node.next.next == null) {
					node.next = null; //uklanja se zadnji element iz liste
				} else {
					node.next = node.next.next;
				}
				modificationCount++;
				size--;
				return returningValue;
			}
			node = node.next;
		}
		
		return null;
	}
	
	/**
	 * Metoda provjerava je li kolekcija prazna.
	 * 
	 * @return <code>true</code> ako je kolekcija prazna, <code>false</code> u protivnom
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Nadjacana metoda <code>toString()</code> koja vraca ispis u sljedecem formatu: "[key1=value1, key2=value2, key3=value3]".
	 */
	public String toString() {
		String s = "[";
		
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> node = table[i];
			while (node != null) {
				s += node + ", ";
				node = node.next;
			}
		}
		
		return s.substring(0, s.length()-2) + "]";
	}
	
	/**
	 * Metoda vraca polje referenci na pohranjene zapise; to polje ima onoliko elemenata koliki je trenutni <code>size</code> kolekcije.
	 * 
	 * @return polje referenci na pohranjene zapise
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K,V>[] toArray() {
		TableEntry<K,V>[] tableEntries = (TableEntry<K, V>[])new TableEntry[this.size];
		int idx = 0;
		
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> node = table[i];
			while (node != null) {
				tableEntries[idx] = node;
				idx++;
				node = node.next;
			}
		}
		return tableEntries;
	}
	
	/**
	 * Metoda brise sve uredene parove iz kolekcije pritom ne mijenjajuci kapacitet same tablice.
	 */
	public void clear() {
		modificationCount++;
		size = 0;
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
	}
	
	/**
	 * Metoda trazi zadani kljuc u kolekciji pretrazujuci samo slot u kojem se kljuc potencijalno moze nalaziti.
	 * 
	 * @param key kljuc koji se trazi u kolekciji
	 * @return cvor liste ciji kljuc odgovara predanom parametru <code>key</code> ili <code>null</code> ako kljuc ne postoji u kolekciji
	 */
	private TableEntry<K, V> findKeyInList(Object key) {
		int slot = Math.abs(key.hashCode()) % table.length; //izracun u kojem slot-u se nalazi dani kljuc
		
		TableEntry<K, V> node = table[slot];
		while (node != null) {
			if (key.equals(node.getKey())) {
				return node;
			}
			node = node.next;
		}
		return null;
	}
	
	/**
	 * Metoda dodaje novi TableEntry na posljednje mjesto unutar liste odgovarajuceg slota.
	 * 
	 * @param key kljuc zapisa
	 * @param value vrijednost zapisa
	 */
	private void addTableEntryAtTheEndOfList(K key, V value) {
		int slot = Math.abs(key.hashCode()) % table.length;
		
		TableEntry<K, V> node = table[slot];
		if (node == null) {
			table[slot] = new TableEntry<K, V>(key, value);
			return;
		}
		
		while (node.next != null) {
			node = node.next;
		}
		
		node.next = new TableEntry<K, V>(key, value);
	}
	
	/**
	 * Metoda u slucaju prevelike popunjenosti tablice povecava njezin kapacitet na dvostruki pritom zadrzavajuci sve uredene parove u kolekciji.
	 */
	@SuppressWarnings("unchecked")
	private void handleOccupancy() {
		TableEntry<K, V>[] arrayOfTableEntries = (TableEntry<K, V>[])new TableEntry[this.size()];
		arrayOfTableEntries = this.toArray();
		
		int doubledSize = table.length * 2;
		table = (TableEntry<K, V>[])new TableEntry[doubledSize];
		size = 0;
		
		for (TableEntry<K, V> entry : arrayOfTableEntries) {
			put(entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * Ugnijezdeni razred koji ostvaruje iterator.
	 * 
	 * @author borna
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		
		private int currentIndex;
		//private SimpleHashtable.TableEntry<K,V>[] array; 
		private boolean allowedToRemove;
		private long savedModificationCount;
		
		/**
		 * Konstruktor.
		 */
		public IteratorImpl() {
			currentIndex = 0;
			savedModificationCount = SimpleHashtable.this.modificationCount;
			allowedToRemove = false;
			//array = (TableEntry<K, V>[])new TableEntry[SimpleHashtable.this.size()];
			//array = SimpleHashtable.this.toArray();
		}
		
		/**
		 * Metoda provjerava nalazi li se u kolekciji jos uredenih parova koje moze vratiti korisniku i sukladno tome vraca boolean vrijednost true ili false.
		 * 
		 * @return true ako kolekcija sadrzi jos uredenih parova, false u protivnom
		 * @throws ConcurrentModificationException ako je kolekcija mijenjana u procesu dohvacanja elemenata
		 */
		public boolean hasNext() {
			if (savedModificationCount != SimpleHashtable.this.modificationCount) throw new ConcurrentModificationException("Struktura kolekcije je promijenjena!");
			return SimpleHashtable.this.size() > currentIndex;
		}
		
		/**
		 * Dohvaca iduci neisporuceni uredeni par (kljuc-vrijednost) iz kolekcije.
		 * 
		 * @return iduci uredeni par kolekcije koji jos uvijek nije isporucen
		 * @throws NoSuchElementException ako trazimo isporuku iduceg para, ali svi parovi kolekcije su vec isporuceni
		 * @throws ConcurrentModificationException ako je kolekcija mijenjana u procesu dohvacanja elemenata
		 */
		public SimpleHashtable.TableEntry<K, V> next() {
			if (savedModificationCount != SimpleHashtable.this.modificationCount) throw new ConcurrentModificationException("Struktura kolekcije je promijenjena!");
			if (currentIndex >= SimpleHashtable.this.size()) throw new NoSuchElementException("Nema vise elemenata po kojima bi se moglo iterirati!");
			
			TableEntry<K, V> next = findElement(currentIndex);
			
			currentIndex++;
			allowedToRemove = true;
			return next;
			/*
			TableEntry<K, V> next = array[currentIndex];
			currentIndex++;
			allowedToRemove = true;
			return next;
			*/
		}
		
		/**
		 * Metoda brise trenutni element iz kolekcije.
		 * 
		 * @throws ConcurrentModificationException ako je kolekcija mijenjana u procesu dohvacanja elemenata
		 * @throws IllegalStateException ako se vise puta pokusava ukloniti isti par iz kolekcije
		 */
		public void remove() {
			if (savedModificationCount != SimpleHashtable.this.modificationCount) throw new ConcurrentModificationException("Struktura kolekcije je promijenjena!");
			
			if (allowedToRemove) {
				allowedToRemove = false;
				SimpleHashtable.this.remove(findElement(currentIndex-1).key);
				SimpleHashtable.this.size--;
				savedModificationCount++;
			} else {
				throw new IllegalStateException("Nemoguce je vise puta ukloniti istu vrijednost iz kolekcije!");
			}
			/*
			if (allowedToRemove) {
				allowedToRemove = false;
				SimpleHashtable.this.remove(array[currentIndex-1].key);
				SimpleHashtable.this.size--;
				savedModificationCount++;
			} else {
				throw new IllegalStateException("Nemoguce je vise puta ukloniti istu vrijednost iz kolekcije!");
			}
			*/
		}
		
		/**
		 * Metoda na temelju "index-a" pronalazi element unutar kolekcije.
		 * 
		 * @param position imaginarna pozicija elementa unutar kolekcije
		 * @return element (uredeni par) koji se nalazi na zadanoj poziciji
		 */
		private SimpleHashtable.TableEntry<K, V> findElement(int position) {
			int idx = 0;
			TableEntry<K, V> next = null;
			
			for (int i = 0; i < table.length; i++) {
				TableEntry<K, V> node = table[i];
				if (node == null) continue;
				while (node != null) {
					if (idx == position) {
						//return node; 
						next = node;
						break;
					}
					idx++;
					node = node.next;
				}
				if (next != null) break;
			}
			return next;
		}
	}
	
	/**
	 * "Metoda tvornica" koja proizvodi iteratore koji se mogu koristiti za obilazak po svim parovima koji su trenutno pohranjeni u tablici, i to redoslijedom kojim se
	 * nalaze u tablici ako se tablica prolazi od slota 0 prema dolje, unutar svakog slota od prvog cvora liste prema posljednjem.
	 * 
	 * @return novi iterator
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
}
