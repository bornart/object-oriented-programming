package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class RCPositionTest {
	
	@Test
	public void testParse() {
		assertEquals(new RCPosition(1,1), RCPosition.parse("1,1"));
		assertEquals(new RCPosition(4,2), RCPosition.parse("4,2"));
		assertEquals(new RCPosition(5,7), RCPosition.parse("5,7"));
	}
	
	@Test
	public void testThrowsCalcLayoutException() {
		assertThrows(CalcLayoutException.class, () -> new RCPosition(6,7));
		assertThrows(CalcLayoutException.class, () -> new RCPosition(1,8));
		assertThrows(CalcLayoutException.class, () -> new RCPosition(-2,0));
	}
	
	@Test
	public void testThrowsCalcLayoutExceptionFirstCell() {
		assertThrows(CalcLayoutException.class, () -> new RCPosition(1,2));
		assertThrows(CalcLayoutException.class, () -> new RCPosition(1,5));
	}
}
