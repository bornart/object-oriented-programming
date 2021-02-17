package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	
	@Test
	public void testDefaultConstructorSize() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertEquals(0, collection.size());
	}
	
	@Test
	public void testNullPointerShouldThrowConstructor() {
		assertThrows(NullPointerException.class,() -> new LinkedListIndexedCollection(null));
	}
	
	@Test
	public void testCopyElementsFromAnotherCollection() {
		LinkedListIndexedCollection another = new LinkedListIndexedCollection();
		another.add("Test");
		another.add(10);
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(another);
		assertEquals("Test", collection.get(0));
	}
	
	@Test
	public void testCorrectSizeAfterAddingElementsFromAnotherCollection() {
		LinkedListIndexedCollection another = new LinkedListIndexedCollection();
		another.add("Test");
		another.add(10);
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(another);
		assertEquals(2, collection.size());
	}
	
	@Test
	public void testNullPointerShouldThrow() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class,() -> collection.add(null));
	}
	
	@Test
	public void testAddingFirstElementToCollection() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("index=0");
		assertEquals("index=0", collection.get(0));
	}
	
	@Test
	public void testShouldAddElementAtTheEndOfCollection() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		for (int i = 0; i < 10; i++) {
			collection.add(i);
		}
		collection.add("index=10");
		assertEquals(10, collection.indexOf("index=10"));
	}
	
	@Test
	public void testSizeOfCollectionShouldChange() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		for (int i = 0; i < 10; i++) {
			collection.add(i);
		}
		assertTrue(collection.size() == 10);
	}
	
	@Test
	public void testIndexOutOfBoundsShouldThrow() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("value");
		assertThrows(IndexOutOfBoundsException.class,() -> collection.get(1));
	}
	
	@Test
	public void testShouldReturnProperValue() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(8);
		assertTrue(collection.get(0).equals(8));
	}
	
	@Test
	public void testShouldGetElementFromTheEndOfCollection() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		for (int i = 0; i < 10; i++) {
			collection.add(i);
		}
		collection.add("index=10");
		assertEquals("index=10", collection.get(10));
	}
	
	@Test
	public void testShouldGetFirstElementFromTheCollection() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		for (int i = 0; i < 10; i++) {
			collection.add(i);
		}
		assertTrue(collection.get(0).equals(0));
	}
	
	@Test 
	public void testShouldRemoveAllElements() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		LinkedListIndexedCollection other = new LinkedListIndexedCollection();
		
		for (int i = 0; i < 25; i++) {
			collection.add(i);
		}
		collection.addAll(other);
		collection.clear();
		assertTrue(collection.size() < 1);
	}
	
	@Test 
	public void testIndexOutOfBoundsClear() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("this should be removed");
		collection.clear();
		assertThrows(IndexOutOfBoundsException.class,() -> collection.get(0));
	}
	
	@Test 
	public void testFillsFirstEmptyPlace() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("this should be removed");
		collection.clear();
		collection.add(10);
		assertEquals(10, collection.get(0));
	}
	
	@Test
	public void testInsertsValueAtGivenPosition() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		for (int i = 0; i < 10; i++) {
			if (i == 5) continue;
			collection.add(i);
		}
		collection.insert(5, 5);
		
		assertEquals(5, collection.indexOf(5));
	}
	
	@Test
	public void testShouldShiftsTwoTimes() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		for (int i = 0; i < 10; i++) {
			if (i == 0 || i == 1) continue;
			collection.add(i);
		}
		collection.insert(0, 0);
		collection.insert(1, 1);
		
		assertTrue(collection.size() == 10);
	}
	
	@Test
	public void testShiftsValue() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		for (int i = 0; i < 10; i++) {
			if (i == 5) continue;
			collection.add(i);
		}
		collection.insert(5, 5);
		
		assertEquals(9, collection.indexOf(9));
	}
	
	@Test
	public void testNullPointerShouldThrowInsert() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class,() -> collection.insert(null, 0));
	}
	
	@Test
	public void testIndexOutOfBoundShouldThrowInsert() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class,() -> collection.insert(0, 1));
	}
	
	@Test
	public void testElementIsNotFound() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertTrue(collection.indexOf(null) == -1);
	}
	
	@Test
	public void testShouldReturnFirstOccurrenceOfGivenValue() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(1);
		collection.add(1);
		assertEquals(0, collection.indexOf(1));
	}
	
	@Test
	public void testRemovesValueFromGivenPosition() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		for (int i = 0; i < 10; i++) {
			collection.add(i);
		}
		collection.remove(5);
		
		assertNotEquals(5, collection.get(5));;
	}
	
	@Test
	public void testShiftsValueToLeft() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		for (int i = 0; i < 10; i++) {
			collection.add(i);
		}
		collection.remove(0);
		
		assertEquals(0, collection.indexOf(1));
	}
	
	@Test
	public void testIndexOutOfBoundsShouldThrowRemove() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		for (int i = 0; i < 10; i++) {
			collection.add(i);
		}
		collection.remove(9);
		
		assertThrows(IndexOutOfBoundsException.class,() -> collection.get(9));
	}
	
	@Test
	public void testRemovesOnlyValue() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add("value");
		collection.remove(0);
		
		assertEquals(0, collection.size());
	}
	
}
