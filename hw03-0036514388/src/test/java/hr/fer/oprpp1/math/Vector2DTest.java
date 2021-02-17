package hr.fer.oprpp1.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2DTest {
	
	@Test
    public void testVectorConstructor() {
    	Vector2D vector = new Vector2D(10.1, 8.8);

    	assertEquals(10.10, vector.getX());
    	assertEquals(8.8, vector.getY());
    }
	
	@Test
	public void testGetterX() {
		Vector2D vector = new Vector2D(10, 8.8);
		
		assertEquals(10, vector.getX());
	}
	
	@Test
	public void testGetterY() {
		Vector2D vector = new Vector2D(10, 8.8);
		
		assertEquals(8.8, vector.getY());
	}

    @Test
    public void testVectorAdd() {
    	Vector2D vector = new Vector2D(100.1, 8.8);
        vector.add(new Vector2D(5, 7.7));

        assertTrue(105.1 - Math.abs(vector.getX()) < 1E-8);
        assertTrue(16.5 - Math.abs(vector.getY()) < 1E-8);
    }
    
    @Test
    public void testVectorAdded() {
    	Vector2D vector = new Vector2D(100.1, 8.8);
    	Vector2D vector2 = vector.added(new Vector2D(5, 7.7));

        assertNotSame(vector, vector2);
    }
    
    @Test
    public void testVectorAdded2() {
    	Vector2D vector = new Vector2D(100.1, 8.8);
    	Vector2D vector2 = vector.added(new Vector2D(5, 7.7));

        assertTrue(105.1 - Math.abs(vector2.getX()) < 1E-8);
        assertTrue(16.5 - Math.abs(vector2.getY()) < 1E-8);
    }

    @Test
    public void testVectorRotate() {
    	Vector2D vector = new Vector2D(5.5, 0);
        vector.rotate(Math.PI / 2);
      
        assertTrue(Math.abs(vector.getX()) < 1E-8);
        assertTrue(5.5 - Math.abs(vector.getY()) < 1E-8);
    }
    
    @Test
    public void testVectorRotate2() {
    	Vector2D vector = new Vector2D(8.8, 10);
        vector.rotate(Math.PI);

        assertTrue(8.8 - Math.abs(vector.getX()) < 1E-8);
        assertTrue(10 - Math.abs(vector.getY()) < 1E-8);
    }
    
    @Test
    public void testVectorRotatedNotSame() {
    	Vector2D vector = new Vector2D(5.5, 0);
    	Vector2D vector2 = vector.rotated(Math.PI);

        assertNotSame(vector2, vector);
    }
    
    @Test
    public void testVectorRotated() {
    	Vector2D vector = new Vector2D(1, 0);
    	Vector2D vector2 = vector.rotated(Math.PI / 2);

        assertTrue(1 - Math.abs(vector2.getY()) < 1E-8);
    }
    
    @Test
    public void testVectorRotated2() {
    	Vector2D vector = new Vector2D(5.5, 0);
    	Vector2D vector2 = vector.rotated(Math.PI);

        assertTrue(5.5 - Math.abs(vector2.getX()) < 1E-8);
        assertTrue(0 - Math.abs(vector2.getY()) < 1E-8);
    }
    
    @Test
    public void testVectorRotated3() {
    	Vector2D vector = new Vector2D(5.5, 8);
    	Vector2D vector2 = vector.rotated(Math.PI);

        assertTrue(-5.5 + vector2.getX() < 1E-8);
        assertTrue(-8 + vector2.getY() < 1E-8);
    }

    @Test
    public void testVectorScale() {
    	Vector2D vector = new Vector2D(5.5, 8);
        vector.scale(3.75);

        assertTrue(20.625 - Math.abs(vector.getX()) < 1E-8);
        assertTrue(30 - Math.abs(vector.getY()) < 1E-8);
    }
    
    @Test
    public void testVectorScale2() {
    	Vector2D vector = new Vector2D(5.5, 8);
        vector.scale(-5.5);

        assertTrue(-30.25 + vector.getX() < 1E-8);
        assertTrue(-44 + vector.getY() < 1E-8);
    }
    
    @Test
    public void testVectorScaled() {
    	Vector2D vector = new Vector2D(2.2, 2.2);
    	Vector2D vector2 = vector.scaled(2);

        assertNotSame(vector2, vector);
    }
    
    @Test
    public void testVectorScaled2() {
    	Vector2D vector = new Vector2D(8, 10);
    	Vector2D vector2 = vector.scaled(2);

        assertTrue(16 - Math.abs(vector2.getX()) < 1E-8);
        assertTrue(20 - Math.abs(vector2.getY()) < 1E-8);
    }
    
    @Test
    public void testVectorScaled3() {
    	Vector2D vector = new Vector2D(8, 10);
    	Vector2D vector2 = vector.scaled(-2.5);

        assertTrue(20 - Math.abs(vector2.getX()) < 1E-8);
        assertTrue(25 - Math.abs(vector2.getY()) < 1E-8);
    }
    
    @Test
    public void testVectorCopy() {
    	Vector2D vector = new Vector2D(5.5, 8.8);
    	Vector2D vector2 = vector.copy();

        assertNotSame(vector2, vector);
    }
    
    @Test
    public void testVectorCopy2() {
    	Vector2D vector = new Vector2D(5.5, 8.8);
    	Vector2D vector2 = vector.copy();

    	assertTrue(-5.5 + vector2.getX() < 1E-8);
        assertTrue(-8.8 + vector2.getY() < 1E-8);
    }
}
