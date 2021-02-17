package hr.fer.oprpp1.custom.collections;

/**
 * Sucelje koje nasljeduje sucelje Collection.
 * 
 * @author borna
 *
 */
public interface List extends Collection {
	
	Object get(int index);
	
	void insert(Object value, int position);
	
	int indexOf(Object value);
	
	void remove(int index);
}
