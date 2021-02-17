package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Klasa predstavlja implementaciju kolekcije poljem sa varijabilnim brojem elemenata. Implementira sucelje Collection.
 * @author borna
 *
 * @param <T> parametrizacija klase <code>ArrayIndexedCollection</code>
 */
public class ArrayIndexedCollection<T> implements List<T> {
	
	public static final int CAPACITY = 16;
	
	private int size;
	private T[] elements;
	private long modificationCount = 0L;
	
	/**
	 * Stvara instancu kolekcije u koju kopira elemente predane kolekcije <code>other</code>. 
	 * Kapacitet polja odreduje se po sljedecem principu: max(initialCapacity, other.size()).
	 * 
	 * @param other druga kolekcija ciji ce sadrzaj biti prekopiran u novu kolekciju
	 * @param initialCapacity inicijalni kapacitet koji ce se alocirati za polje (ukoliko kolekcija <code>other</code> nema vecu velicinu).
	 * @throws NullPointerException ako je predana null-referenca umjesto reference na drugu kolekciju
	 * @throws IllegalArgumentException ako je parametar initialCapacity postavljen na vrijednost manju od 1
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<? extends T> other, int initialCapacity) {
		if (other.isEmpty()) throw new NullPointerException("Predana je null-referenca, a ne referenca na drugu Kolekciju!");
		if (initialCapacity < 1) throw new IllegalArgumentException("Kapacitet polja ne moze biti manji od 1!");
		
		size = 0;
		
		int arrCapacity = initialCapacity > other.size() ? initialCapacity : other.size();
		elements = (T[]) new Object[arrCapacity];
		addAll(other); 
		
	}
	
	/**
	 * Stvara instancu kolekcije pozivajuci nadredeni konstruktor kojemu predaje 2 arguementa: 
	 * pretpostavljenu velicinu polja (16) i ne-null referencu na neku drugu kolekciju koja ce biti iskopirana u novostvorenu kolekciju.
	 * 
	 * @param other druga kolekcija ciji ce sadrzaj biti prekopiran u novu kolekciju
	 */
	public ArrayIndexedCollection(Collection<? extends T> other) {
		this(other, CAPACITY); 
	}
	
	/**
	 * Stvara instancu kolekcije ciji je kapacitet polja jednak vrijednosti parametra initialCapacity.
	 * 
	 * @param initialCapacity velicina (kapacitet) polja koje se stvara
	 * @throws IllegalArgumentException ako je parametar initialCapacity postavljen na vrijednost manju od 1
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) throw new IllegalArgumentException("Kapacitet polja ne moze biti manji od 1!");
		
		elements = (T[]) new Object[initialCapacity];
		size = 0;
	}
	
	/**
	 * Pretpostavljeni konstuktor stvara instancu kolekcije pozivajuci nadredeni konstruktor sa argumentom kapaciteta polja velicine 16.
	 */
	public ArrayIndexedCollection() {
		this(CAPACITY); 
	}
	
	/**
	 * Dodaje objekt u prvu slobodnu celiju unutar polja kolekcije sa konstantnom slozenoscu (izuzev slucajeva kada je potrebna realokacija). 
	 * Ukoliko je polje puno dogada se realokacija polja tako da ono moze primiti dvostruko veci broj elemenata nego prije.
	 * 
	 * @param value vrijednost koja se dodaje u kolekciju
	 * @throws NullPointerException ako je predana null-referenca
	 */
	@Override
	public void add(T value) {
		if (value == null) throw new NullPointerException("Predana je null-referenca!");
		
		if (elements.length == size) {
			elements = Arrays.copyOf(elements, elements.length*2);
			//modificationCount++; 
		}
		elements[size] = value;

		size++;
		modificationCount++;
	}
	
	/**
	 * Uklanja sve elemente iz kolekcije pritom zadrzavajuci trenutni kapacitet polja.
	 */
	@Override
	public void clear() {
		Arrays.fill(elements, null);
		size = 0;
		modificationCount++;
	}
	
	/**
	 * Vraca objekt koji se unutar polja kolekcije nalazi na poziciji <code>index</code>.
	 * 
	 * @param index pozicija s koje se vraca objekt iz kolekcije
	 * @return objekt koji se nalazi na poziciji <code>index</code> polja kolekcije.
	 * @throws IndexOutOfBoundsException ako je vrijednost parametra <code>index</code> izvan zadanog intervala: [0, size>
	 */
	public T get(int index) {
		if (index < 0 || index >= this.size) throw new IndexOutOfBoundsException("Vrijednost index-a mora biti izmedu 0 i " + (size-1) + "!");
		
		return elements[index];
	}
	
	/**
	 * Dodaje danu vrijednost na poziciju <code>position</code> pritom pomicuci sve ostale elemente ciji je indeks veci ili jednak od <code>position</code> za jedno mjesto prema kraju.
	 * U slucaju popunjavanja polja, dogada se realokacija tako da polje moze primiti dvostruko veci broj elemenata.
	 * Slozenost metode je n.
	 * 
	 * @param value vrijednost koja se dodaje u kolekciju
	 * @param position pozicija na koju se objekt dodaje
	 * @throws NullPointerException ako je predana null-referenca
	 *  @throws IndexOutOfBoundsException ako je vrijednost parametra <code>index</code> izvan zadanog intervala: [0, size]
	 */
	public void insert(T value, int position) {
		if (value == null) throw new NullPointerException("Predana je null-referenca!");
		if (position < 0 || position > size) throw new IndexOutOfBoundsException("Vrijednost index-a mora biti izmedu 0 i " + size + "!");
		
		if (elements.length == size) {
			elements = Arrays.copyOf(elements, elements.length*2);
		}
		
		T[] tmpArray = Arrays.copyOf(elements, elements.length); 
		
		for (int i = 0; i <= size; i++) {
			if (i < position) elements[i] = tmpArray[i];
			else if (i == position) elements[i] = value;
			else elements[i] = tmpArray[i-1];
		}

		size++;
		modificationCount++;
	}
	
	/**
	 * Pretrazuje kolekciju i vraca poziciju na kojoj se nalazila prva pojava zadane vrijednosti.
	 * 
	 * @param value vrijednost objekta ciju poziciju unutar kolekcije zelimo saznati
	 * @return redni broj celije polja u kojoj se nalazi referenca na zadanu vrijednost
	 */
	public int indexOf(Object value) {
		if (!(contains(value))) return -1;
				
		for (int i = 0; i < this.size; i++) {
			if (elements[i].equals(value)) return i; 
		}
		
		return -1;
	}
	
	/**
	 * Uklanja element sa zadane pozicije <code>index</code> iz kolekcije pritom pomicuci elemente cija je pozicija veca od danog parametra <code>index</code> za jedno mjesto ulijevo (<code>index+1</code> prelazi na poziciju <code>index</code>).
	 *  
	 * @param index pozicija s koje zelimo ukloniti element iz kolekcije
	 * @throws IndexOutOfBoundsException ako je vrijednost parametra <code>index</code> izvan zadanog intervala: [0, size>
	 */
	public void remove (int index) {
		if (index < 0 || index >= this.size) throw new IndexOutOfBoundsException("Vrijednost index-a mora biti izmedu 0 i " + (size-1) + "!");
		
		T[] tmpArray = Arrays.copyOf(elements, elements.length);
		
		for (int i = 0, j = 0; i <= this.size; i++) {
			if (i == index) continue;
			elements[j++] = tmpArray[i];
		}
		
		size--;
		modificationCount++;
	}
	
	/**
	 * Vraca broj celija polja kolekcije u kojima su pohranjeni ne-null elementi.
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Provjerava nalazi li se u kolekciji polje cija je vrijednost jednaka zadanoj vrijednosti. Ako postoji, metoda vraca true. U protivnom vraca false.
	 * 
	 * @param value vrijednost koja se trazi u kolekciji
	 * @return true se u kolekciji nalazi polje sa vrijednoscu koja odgovara zadanom parametru, false u protivnom
	 */
	@Override
	public boolean contains(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) return true;
		}

		return false;
	}
	
	/**
	 * Alocira novo polje cija je velicina jednaka velicini kolekcije. Novo polje se popunjava vrijednostima iz ne-null elemenata polja.
	 * 
	 * @return novostvoreno polje objekata
	 */
	@Override
	public Object[] toArray() {
		Object[] arr = new Object[size];
		arr = Arrays.copyOf(elements, size);
		return arr;
	}
	
	/**
	 * Metoda uklanja dani objekt iz kolekcije ukoliko ona sadrzi takav objekt i pritom vraca boolean vrijednost <code>true</code>. 
	 * Ako se u kolekciji ne nalazi objekt <code>value</code> metoda vraca boolean vrijednost <code>false</code>.
	 * 
	 * @return <code>true</code> ako je objekt uspjesno uklonjen; <code>false</code> ako objekt ne postoji u kolekciji
	 */
	@Override
	public boolean remove(Object value) {
		if (!this.contains(value)) {
			return false;
		} else {
			this.remove(this.indexOf(value));
			return true;
		}
	}
	
	/**
	 * Privatni staticki razred koji predstavlja implementaciju ElementsGettera koji zna dohvacati i vracati elemente u konkretnoj implementaciji kolekcije (ArrayIndexedCollection).
	 * 
	 * @author borna
	 *
	 */
	private static class InnerGetter<V> implements ElementsGetter<V> {
		
		private int currentElement;
		private ArrayIndexedCollection<V> outerColl;
		private long savedModificationCount;
		
		/**
		 * Konstruktor koji prima referencu na primjerak vanjske klase ArrayIndexedCollection.
		 * U konstruktoru se dodatno inicijalizira <code>currentElement</code> na vrijednost 0 zato sto se elementi iz polja vanjske klase dohvacaju od indeksa 0.
		 * Dodatno se inicijalizira i <code>savedModificationCount</code> na trenutni sadrzaj varijable <code>modificationCount</code> kolekcije cije elemente trebaju vratiti
		 * 
		 * @param coll referenca na primjerak vanjske klase (trenutna kolekcija)
		 */
		public InnerGetter(ArrayIndexedCollection<V> coll) {
			currentElement = 0;
			outerColl = coll;
			savedModificationCount = coll.modificationCount;
		}
		
		/**
		 * Metoda provjerava nalazi li se u kolekciji jos elemenata koje moze vratiti korisniku i sukladno tome vraca boolean vrijednost true ili false.
		 * 
		 * @return true ako kolekcija sadrzi jos elemenata, false u protivnom
		 * @throws ConcurrentModificationException ako je kolekcija mijenjana u procesu dohvacanja elemenata
		 */
		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != outerColl.modificationCount) throw new ConcurrentModificationException("Struktura kolekcije je promijenjena!");
			
			if (currentElement < outerColl.size) return true;
			return false;
		}
		
		/**
		 * Dohvaca iduci neisporuceni element iz kolekcije.
		 * 
		 * @return referenca na iduci element kolekcije koji jos uvijek nije isporucen
		 * @throws NoSuchElementException ako trazimo isporuku iduceg elementa, ali svi elementi kolekcije su vec isporuceni
		 * @throws ConcurrentModificationException ako je kolekcija mijenjana u procesu dohvacanja elemenata
		 */
		@Override
		public V getNextElement() {
			if (savedModificationCount != outerColl.modificationCount) throw new ConcurrentModificationException("Struktura kolekcije je promijenjena!");
			if (currentElement >= outerColl.size) throw new NoSuchElementException("Nema vise neisporucenih elemenata kolekcije!");
			
			V nextElement = outerColl.elements[currentElement];
			currentElement++;
			
			return nextElement;
		}
		
	}
	
	/**
	 * Metoda stvara objekt s kojim korisnik moze pricati "jezikom" koji specificira tip ElementsGetter.
	 * 
	 * @return referenca na novostvoreni objekt tipa ElementsGetter
	 */
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new InnerGetter<T>(this);
	}
	
}