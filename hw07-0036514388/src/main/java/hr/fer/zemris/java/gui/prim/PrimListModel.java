package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Razred predstavlja model liste prim brojeva.
 * 
 * @author borna
 *
 */
public class PrimListModel implements ListModel<Integer> {
	
	private List<ListDataListener> listeners = new ArrayList<>();
	private List<Integer> primeNumbers;
	
	/**
	 * Konstruktor. Dodaje prvi primarni broj u listu.
	 */
	public PrimListModel() {
		primeNumbers = new ArrayList<Integer>();
		primeNumbers.add(1);
	}
	
	/**
	 * Metoda u listu brojeva dodaje prvi sljedeći prim broj.
	 */
	public void next() {
		int num = primeNumbers.get(primeNumbers.size()-1) + 1;
		
		while(true) {
			boolean isPrime = true;
			int root = (int) Math.sqrt(num);
			
			for (int i = 2; i <= root; i++) {
				if (num % i == 0) {
					isPrime = false;
					break;
				}
			}
			if (isPrime) {
				primeNumbers.add(num);
				break;
			}
			num++;
		}
		
		ListDataEvent e = new ListDataEvent(this,  ListDataEvent.INTERVAL_ADDED, primeNumbers.size()-1, primeNumbers.size()-1);
		
		for (ListDataListener l : listeners) {
			l.intervalAdded(e);
		}
	}
	
	@Override
	public int getSize() {
		return primeNumbers.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return primeNumbers.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}
	
}
