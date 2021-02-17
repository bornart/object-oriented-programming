package hr.fer.oprpp1.custom.collections;

/**
 * Klasa predstavlja implementaciju dvostruko povezane liste kolekcija. Nasljeduje klasu Collection.
 * U klasi je definirana ugnijezdena klasa ListNode.
 * 
 * @author borna
 *
 */
public class LinkedListIndexedCollection extends Collection {
		
	private int size;
	private ListNode first;
	private ListNode last;
	
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
	public LinkedListIndexedCollection(Collection other) {
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
	private static class ListNode {
		
		private ListNode next;
		private ListNode previous;
		private Object valueStorage;
		
		/**
		 * Stvara novi čvor kolekcije i smjesta ga na odgovarajucu poziciju unutar kolekcije pomocu referenci na prethodni i iduci cvor.
		 * 
		 * @param next referenca na iduci cvor
		 * @param previous referenca na prethodni cvor
		 * @param valueStorage referenca tipa Objekt za pohranu vrijednosti
		 */
		public ListNode(ListNode next, ListNode previous, Object valueStorage) {
			super();
			this.next = next;
			this.previous = previous;
			this.valueStorage = valueStorage;
		}
	}
	
	/**
	 * Poziva metodu processor.process za svaki element trenutne kolekcije krenuvsi od prvog cvora prema posljednjem.
	 */
	@Override
	public void forEach(Processor processor) {
		for (ListNode i = first; i != null; i = i.next) {
			processor.process(i.valueStorage);
		}
	}
	
	/**
	 * Dodaje predani objekt na kraj kolekcije. Novododani element postaje element sa najvecim indeksom.
	 * 
	 * @param value vrijednost koja se dodaje u kolekciju
	 * @throws NullPointerException ako je predana null-referenca
	 */
	@Override
	public void add(Object value) {
		if (value == null) throw new NullPointerException("Predana je null-referenca!");
		
		ListNode newNode = new ListNode(null, last, value);
		if (first == null) first = newNode;
		if (last != null) last.next = newNode;
		last = newNode;
		
		size++;
	}
	
	/**
	 * Metoda sa slozenoscu nikada vecom od n/2+1 vraca vrijednost koja se u kolekciji nalazi na poziciji "index".
	 * 
	 * @param index redni broj cvora ciju vrijednost zelimo da metoda vrati
	 * @return referenca na vrijednost tipa objekt koja se nalazi na trazenoj poziciji u kolekciji
	 * @throws IndexOutOfBoundsException ako vrijednost parametra index nije u sljedecem intervalu: [0, size>
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Vrijednost index-a mora biti izmedu 0 i " + (size-1) + "!");
		
		Object foundValue = null;
		
		if (index > size/2) {
			//trazeni node se nalazi u gornjoj polovici kolekcije - trazim ga od zadnjeg node-a
			int i = size-1;
			for (ListNode tmpNode = last; tmpNode != null; tmpNode = tmpNode.previous) {
				if (i == index) {
					foundValue = tmpNode.valueStorage;
					break;
				}
				i--;
			}
		} else {
			//u protivnom se nalazi u donjoj polovici (ili na pola u slucaju neparnog broja)
			int i = 0;
			for (ListNode tmpNode = first; tmpNode != null; tmpNode = tmpNode.next) {
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
	}
	
	/**
	 * Pretrazuje kolekciju i vraca poziciju na kojoj se nalazila prva pojava zadane vrijednosti.
	 * 
	 * @param value vrijednost objekta ciju poziciju unutar kolekcije zelimo saznati
	 * @return redni broj cvora u kojem se nalazi referenca na zadanu vrijednost
	 */
	public int indexOf(Object value) {
		if (first == null) return -1;
		
		int i = 0;
		ListNode tmpNode = first;
		
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
			ListNode tmpNode = first.next;
			
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
	}
	
	/**
	 * Dodaje novi cvor u kolekciju na poziciju index pritom pomicuci ostale cvorove cija je  pozicija veca ili jednaka vrijednosti index-a za jedno mjesto prema kraju kolekcije.
	 * 
	 * @param value vrijednost novog cvora
	 * @param position pozicija na koju dodajemo novi cvor
	 * @throws IndexOutOfBoundsException ako vrijednost argumenta index nije u sljedecem intervalu: [0, size]
	 * @throws NullPointerException ako je predana null-referenca
	 */
	public void insert (Object value, int position) {
		if (position < 0 || position > size) throw new IndexOutOfBoundsException("Vrijednost index-a mora biti izmedu 0 i " + position + "!");
		if (value == null) throw new NullPointerException("Predana je null-referenca!");
		
		ListNode newNode = new ListNode(null, null, value);
		
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
			ListNode tmpNode = first.next;
			
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
		ListNode tmpNode = first;
		
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
		for (ListNode i = first; i != null; i = i.next) {
			if (i.valueStorage.equals(value)) return true;
		}
		
		return false;
	}
	
	
}
