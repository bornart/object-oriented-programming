package hr.fer.oprpp1.custom.collections;

/**
 * Sucelje koje nasljeduje sucelje Collection.
 * 
 * @author borna
 *
 * @param <T> parametrizacija klase <code>List</code>
 */
public interface List<T> extends Collection<T> {
	
	T get(int index);
	
	void insert(T value, int position);
	
	int indexOf(Object value);
	
	void remove(int index);
}
