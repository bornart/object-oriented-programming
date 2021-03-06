package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Klasa predstavlja implementaciju dvostruko povezane liste kolekcija. Implementira sucelje Collection.
 * U klasi je definirana ugnijezdena klasa ListNode. 
 * 
 * @author borna
 *
 * @param <T> parametrizacija klase <code>LinkedListIndexedCollection</code>
 */
public class LinkedListIndexedCollection<T> implements List<T> {
		
	private int size;
	private ListNode<T> first;
	private ListNode<T> last;
	private long modificationCount = 0L;
	
	/**
	 * Pretpostavljeni konstruktor stvara novu praznu kolekciju.
	 */
	public LinkedListIndexedCollection() {
		super();
		first = last = null;
		size = 0;
	}
	
	/**
	 * Stvara novu kolekciju u koju kopira elemente iz predane reference na drugu kolekciju.
	 * 
	 * @param other referenca na drugu kolekciju ciji se elementi kopiraju u novostvorenu kolekciju
	 * @throws NullPointerException ako je predana null-referenca
	 */
	public LinkedListIndexedCollection(Collection<? extends T> other) {
		if (other.isEmpty()) throw new NullPointerException("Predana je null-referenca, a ne referenca na drugu Kolekciju!");
		
		size = 0;
		this.addAll(other);
	}
	
	/**
	 * Privatna staticka (ugnijezdena) klasa sa referencom za pohranu vrijednosti i referencama na prethodni i sljedeci cvor.
	 * 
	 * @author borna
	 *
	 */
	private static class ListNode<V> {
		
		private ListNode<V> next;
		private ListNode<V> previous;
		private V valueStorage;
		
		/**
		 * Stvara novi čvor kolekcije i smjesta ga na odgovarajucu poziciju unutar kolekcije pomocu referenci na prethodni i iduci cvor.
		 * 
		 * @param next referenca na iduci cvor
		 * @param previous referenca na prethodni cvor
		 * @param valueStorage referenca tipa Objekt za pohranu vrijednosti
		 */
		public ListNode(ListNode<V> next, ListNode<V> previous, V valueStorage) {
			super();
			this.next = next;
			this.previous = previous;
			this.valueStorage = valueStorage;
		}
	}
	
	/**
	 * Dodaje predani objekt na kraj kolekcije. Novododani element postaje element sa najvecim indeksom.
	 * 
	 * @param value vrijednost koja se dodaje u kolekciju
	 * @throws NullPointerException ako je predana null-referenca
	 */
	@Override
	public void add(T value) {
		if (value == null) throw new NullPointerException("Predana je null-referenca!");
		
		ListNode<T> newNode = new ListNode<T>(null, last, value);
		if (first == null) first = newNode;
		if (last != null) last.next = newNode;
		last = newNode;
		
		size++;
		modificationCount++;
	}
	
	/**
	 * Metoda sa slozenoscu nikada vecom od n/2+1 vraca vrijednost koja se u kolekciji nalazi na poziciji "index".
	 * 
	 * @param index redni broj cvora ciju vrijednost zelimo da metoda vrati
	 * @return referenca na vrijednost tipa objekt koja se nalazi na trazenoj poziciji u kolekciji
	 * @throws IndexOutOfBoundsException ako vrijednost parametra index nije u sljedecem intervalu: [0, size>
	 */
	public T get(int index) {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Vrijednost index-a mora biti izmedu 0 i " + (size-1) + "!");
		
		T foundValue = null;
		
		if (index > size/2) {
			//trazeni node se nalazi u gornjoj polovici kolekcije - trazim ga od zadnjeg node-a
			int i = size-1;
			for (ListNode<T> tmpNode = last; tmpNode != null; tmpNode = tmpNode.previous) {
				if (i == index) {
					foundValue = tmpNode.valueStorage;
					break;
				}
				i--;
			}
		} else {
			//u protivnom se nalazi u donjoj polovici (ili na pola u slucaju neparnog broja)
			int i = 0;
			for (ListNode<T> tmpNode = first; tmpNode != null; tmpNode = tmpNode.next) {
				if (i == index) {
					foundValue = tmpNode.valueStorage;
					break;
				}
				i++;
			}
		}
		
		return foundValue;
	}
	
	/**
	 * Uklanja sve elemente iz kolekcije.
	 */
	@Override
	public void clear() {
		for (int i = 0, j = size; i < j; i++) {
			remove(0);
		}
		modificationCount++;
	}
	
	/**
	 * Pretrazuje kolekciju i vraca poziciju na kojoj se nalazila prva pojava zadane vrijednosti.
	 * 
	 * @param value vrijednost objekta ciju poziciju unutar kolekcije zelimo saznati
	 * @return redni broj cvora u kojem se nalazi referenca na zadanu vrijednost
	 */
	public int indexOf(Object value) {
		if (!this.contains(value) || first == null) return -1;
			
		int i = 0;
		ListNode<T> tmpNode = first;
		
		while (i < size) {
			if (value.equals(tmpNode.valueStorage)) return i;
			tmpNode = tmpNode.next;
			i++;
		}
		
		return -1;
	}
	
	/**
	 * Uklanja cvor sa zadane pozicije iz kolekcije i pomice sve ostale cvorove za jedno mjesto prema prvom cvoru (cvor sa pozicije index+1 nakon pomaka nalazit ce se na poziciji index).
	 * 
	 * @param index pozicija na kojoj se nalazi cvor koji zelimo ukloniti iz kolekcije
	 * @throws IndexOutOfBoundsException ako vrijednost parametra index nije u sljedecem intervalu: [0, size>
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Vrijednost index-a mora biti izmedu 0 i " + (size-1) + "!");
		
		if (index == 0) {
			if (first == last) {
				//radi se o jedinom node-u!
				first = last = null;
			} else {
				first = first.next;
				first.previous = null;
			}
		} else if (index == size-1) {
			last = last.previous;
			last.next = null;
		} else {
			int i = 1;
			ListNode<T> tmpNode = first.next;
			
			while (i < size-1) {
				if (i == index) {
					tmpNode.previous.next = tmpNode.next;
					tmpNode.next.previous = tmpNode.previous;
					tmpNode = null;
					break;
				}
				i++;
				tmpNode = tmpNode.next;
			}
		}
		
		size--;
		modificationCount++;
	}
	
	/**
	 * Dodaje novi cvor u kolekciju na poziciju index pritom pomicuci ostale cvorove cija je  pozicija veca ili jednaka vrijednosti index-a za jedno mjesto prema kraju kolekcije.
	 * 
	 * @param value vrijednost novog cvora
	 * @param position pozicija na koju dodajemo novi cvor
	 * @throws IndexOutOfBoundsException ako vrijednost argumenta index nije u sljedecem intervalu: [0, size]
	 * @throws NullPointerException ako je predana null-referenca
	 */
	public void insert (T value, int position) {
		if (position < 0 || position > size) throw new IndexOutOfBoundsException("Vrijednost index-a mora biti izmedu 0 i " + position + "!");
		if (value == null) throw new NullPointerException("Predana je null-referenca!");
		
		ListNode<T> newNode = new ListNode<T>(null, null, value);
		
		if (position == 0) {
			if (first == null) {
				first = last = newNode;
			} else {
				newNode.next = first;
				first.previous = newNode;
				first = newNode;
			}
		} else if (position == size) {
			last.next = newNode;
			newNode.previous = last;
			last = newNode;
		} else {
			int i = 1;
			ListNode<T> tmpNode = first.next;
			
			while (i < size) {
				if (position == i) {
					newNode.next = tmpNode;
					newNode.previous = tmpNode.previous;
					newNode.previous.next = newNode;
					tmpNode.previous = newNode;
				}
				i++;
				tmpNode = tmpNode.next;
			}
		}
		
		size++;
		modificationCount++;
	}
	
	/**
	 * Vraca broj ne-null cvorova koji su pohranjeni u kolekciji.
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Alocira novo polje cija je velicina jednaka velicini kolekcije. Novo polje se popunjava sadrzajem vrijednosti svakog cvora.
	 * 
	 * @return novostvoreno polje objekata
	 */
	@Override
	public Object[] toArray() {
		Object[] arr = new Object[size];
		int i = 0;
		ListNode<T> tmpNode = first;
		
		while (i < size) {
			arr[i] = tmpNode.valueStorage;
			i++;
			tmpNode = tmpNode.next;
		}
		
		return arr;
	}
	
	/**
	 * Provjerava nalazi li se u kolekciji cvor cija je vrijednost jednaka zadanoj vrijednosti. Ako postoji, metoda vraca true. U protivnom vraca false.
	 * 
	 * @param value vrijednost koja se trazi u kolekciji
	 * @return true se u kolekciji nalazi cvor sa vrijednoscu koja odgovara zadanom parametru, false u protivnom
	 */
	@Override
	public boolean contains(Object value) {
		for (ListNode<T> i = first; i != null; i = i.next) {
			if (i.valueStorage.equals(value)) return true;
		}
		
		return false;
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
	 * Privatni staticki razred koji predstavlja implementaciju ElementsGettera koji zna dohvacati i vracati elemente u konkretnoj implementaciji kolekcije (LinkedListIndexedCollection).
	 * 
	 * @author borna
	 *
	 */
	private static class InnerGetter<E> implements ElementsGetter<E> {
		
		private LinkedListIndexedCollection<E> outerColl;
		private ListNode<E> currentNode;
		private long savedModificationCount;
		
		/**
		 * Konstruktor koji prima referencu na primjerak vanjske klase LinkedListIndexedCollection.
		 * U konstruktoru se inicijalizira <code>currentNode</code> kao referenca na prvi cvor zato sto se elementi iz liste vanjske klase dohvacaju krenuvsi od prvog cvora.
		 * Dodatno se inicijalizira i <code>savedModificationCount</code> na trenutni sadrzaj varijable <code>modificationCount</code> kolekcije cije elemente trebaju vratiti
		 * 
		 * @param coll referenca na primjerak vanjske klase (trenutna kolekcija)
		 */
		public InnerGetter(LinkedListIndexedCollection<E> coll) {
			outerColl = coll;
			currentNode = coll.first;
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
			
			if (currentNode != null) return true;
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
		public E getNextElement() {
			if (savedModificationCount != outerColl.modificationCount) throw new ConcurrentModificationException("Struktura kolekcije je promijenjena!");
			if (currentNode == null) throw new NoSuchElementException("Nema vise neisporucenih elemenata kolekcije!");
			
			E nextElement = currentNode.valueStorage;
			currentNode = currentNode.next;
			
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
