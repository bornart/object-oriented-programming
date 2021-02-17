package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTest {
	
    @Test
    public void testAddValueAndReturn() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        assertEquals(1,dictionary.get("First"));
    }
    
    @Test
    public void testGetIllegalKey() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
  
        assertEquals(null, dictionary.get(1));
    }
    
    @Test
    public void testGetIllegalKey2() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
  
        assertEquals(null, dictionary.get(10.8));
    }
    
    @Test
    public void testAddValueAndRemove() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        dictionary.remove("First");
        assertNull(dictionary.get("First"));
    }

    @Test
    public void testGetSize() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        assertEquals(2,dictionary.size());
    }
    
    @Test
    public void testFindNullInDictionary() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        assertEquals(null, dictionary.get("Third"));
    }

    @Test
    public void testEmpty() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        assertTrue(dictionary.isEmpty());
    }

    @Test
    public void testIsEmptyAfterClear() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        dictionary.clear();

        assertTrue(dictionary.isEmpty());
    }
    
    @Test
    public void testRemoveGetsOldValue() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        assertEquals(1,dictionary.remove("First"));
    }
    
    @Test
    public void testRemovingNonExistingKey() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        assertEquals(null, dictionary.remove("Third"));
    }
    
    @Test
    public void testThrowsNullPointerException() {
    	Dictionary<String, Integer> dictionary = new Dictionary<>();
    	
        assertThrows(NullPointerException.class, () -> dictionary.put(null, 10));
    }
    
    @Test
    public void testPutOverWrites() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        dictionary.put("First",3);

        assertEquals(3,dictionary.get("First"));
    }
    
    @Test
    public void testOverridingValue() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("First", 2);
        dictionary.put("First", 10);

        assertEquals(10, dictionary.get("First"));
    }

    @Test
    public void testRemove() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        dictionary.remove("First");

        assertEquals(1,dictionary.size());
    }
    
    @Test
    public void testClearThenPut() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);
        dictionary.clear();
        dictionary.put("Third", 3);
        
        assertEquals(1, dictionary.size());
    }
    
    @Test
    public void testUsingIllegalTypeOfKey() {
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);
        
        assertEquals(null, dictionary.get(1));
    }
    
}
