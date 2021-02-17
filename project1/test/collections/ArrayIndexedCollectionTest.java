package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {

	@Test
	public void testCurrentSizeOfCollection() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertEquals(0, collection.size());
	}
	
	@Test
	public void testIllegalArgumentShouldThrow() {
		assertThrows(IllegalArgumentException.class,() -> new ArrayIndexedCollection(-2));
	}
	
	@Test
	public void testNullPointerShouldThrowConstructor() {
		assertThrows(NullPointerException.class,() -> new ArrayIndexedCollection(null));
	}
	
	@Test
	public void testCopyElementsFromAnotherCollection() {
		ArrayIndexedCollection another = new ArrayIndexedCollection();
		another.add("Test");
		another.add(10);
		ArrayIndexedCollection collection = new ArrayIndexedCollection(another);
		assertEquals("Test", collection.get(0));
	}
	
	@Test
	public void testReallocatingTheSizeOfCollection() {
		ArrayIndexedCollection another = new ArrayIndexedCollection(20);
		for (int i = 0; i < 20; i++) {
			another.add(i);
		}
		ArrayIndexedCollection collection = new ArrayIndexedCollection(another);
		assertEquals(20, collection.size());
	}
	
	@Test
	public void testUsingConstructorWithTwoArguments() {
		ArrayIndexedCollection another = new ArrayIndexedCollection(20);
		another.add("another");
		ArrayIndexedCollection collection = new ArrayIndexedCollection(another, 30);
		assertNotEquals(2, collection.size());
	}
	
	@Test
	public void testNullPointerShouldThrow() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class,() -> collection.add(null));
	}
	
	@Test
	public void testShouldFillFirstEmptySpace() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		for (int i = 0; i < 10; i++) {
			collection.add(i);
		}
		collection.add("index=10");
		assertEquals("index=10", collection.get(10));
	}
	
	@Test
	public void testShouldReallocateByDoublingSize() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(20);
		for (int i = 0; i < 25; i++) {
			collection.add(i);
		}
		assertEquals(24, collection.get(24));
	}
	
	@Test
	public void testIndexOutOfBoundsShouldThrow() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("value");
		assertThrows(IndexOutOfBoundsException.class,() -> collection.get(1));
	}
	
	@Test
	public void testShouldReturnProperValue() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(8);
		assertTrue(collection.get(0).equals(8));
	}
	
	@Test 
	public void testShouldRemoveAllElements() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		
		for (int i = 0; i < 25; i++) {
			collection.add(i);
		}
		collection.addAll(other);
		collection.clear();
		assertTrue(collection.size() < 1);
	}
	
	@Test 
	public void testIndexOutOfBoundsClear() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("this should be removed");
		collection.clear();
		assertThrows(IndexOutOfBoundsException.class,() -> collection.get(0));
	}
	
	@Test 
	public void testFillsFirstEmptyPlace() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add("this should be removed");
		collection.clear();
		collection.add(10);
		assertEquals(10, collection.get(0));
	}
	
	@Test
	public void testInsertsValueAtGivenPosition() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		for (int i = 0; i < 10; i++) {
			if (i == 5) continue;
			collection.add(i);
		}
		collection.insert(5, 5);
		
		assertEquals(5, collection.indexOf(5));
	}
	
	@Test
	public void testShiftsValue() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		for (int i = 0; i < 10; i++) {
			if (i == 5) continue;
			collection.add(i);
		}
		collection.insert(5, 5);
		
		assertEquals(9, collection.indexOf(9));
	}
	
	@Test
	public void testShouldShiftsTwoTimes() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		for (int i = 0; i < 10; i++) {
			if (i == 0 || i == 1) continue;
			collection.add(i);
		}
		collection.insert(0, 0);
		collection.insert(1, 1);
		
		assertTrue(collection.size() == 10);
	}
	
	@Test
	public void testElementIsNotFound() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertTrue(collection.indexOf(null) == -1);
	}
	
	@Test
	public void testRemovesValueFromGivenPosition() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		for (int i = 0; i < 10; i++) {
			collection.add(i);
		}
		collection.remove(5);
		
		assertNotEquals(5, collection.get(5));;
	}
	
	@Test
	public void testShiftsValueToLeft() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		for (int i = 0; i < 10; i++) {
			collection.add(i);
		}
		collection.remove(0);
		
		assertEquals(0, collection.indexOf(1));
	}
	
	@Test
	public void testIndexOutOfBoundsShouldThrowRemove() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		for (int i = 0; i < 10; i++) {
			collection.add(i);
		}
		collection.remove(9);
		
		assertThrows(IndexOutOfBoundsException.class,() -> collection.get(9));
	}
}