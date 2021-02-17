package hr.fer.oprpp1.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ComplexNumberTest {
	
	@Test
	public void testShouldReturnRealPartOfComplexNumber() {
		ComplexNumber number = new ComplexNumber(5, 8);
		assertEquals(5, number.getReal());
	}
	
	@Test
	public void testShouldReturnImaginaryPartOfComplexNumber() {
		ComplexNumber number = new ComplexNumber(5, 0);
		assertEquals(0, number.getImaginary());
	}
	
	@Test
	public void testShouldReturnMagnitudeOfComplexNumber() {
		ComplexNumber number = new ComplexNumber(5, 8);
		assertEquals(Math.sqrt(89), number.getMagnitude());
	}
	
	@Test
	public void testShouldReturnTheAngleOfComplexNumber1() {
		ComplexNumber number = new ComplexNumber(1, 1);
		assertEquals(Math.atan(1), number.getAngle());
	}
	
	@Test
	public void testShouldReturnTheAngleOfComplexNumber2() {
		ComplexNumber number = new ComplexNumber(-1, 1);
		assertEquals(Math.atan(-1)+Math.PI, number.getAngle());
	}
	
	@Test
	public void testShouldReturnTheAngleOfComplexNumber3() {
		ComplexNumber number = new ComplexNumber(-1, -1);
		assertEquals(Math.atan(1)+Math.PI, number.getAngle());
	}
	
	@Test
	public void testShouldReturnTheAngleOfComplexNumber4() {
		ComplexNumber number = new ComplexNumber(1, -1);
		assertEquals(Math.atan(-1)+Math.PI*2, number.getAngle());
	}
	
	@Test
	public void testSubTwoComplexNumbers() {
		ComplexNumber num1 = new ComplexNumber(2.5, -1.88);
		ComplexNumber num2 = new ComplexNumber(-4.9, 0);
		
		ComplexNumber expected = new ComplexNumber(7.4, -1.88);
		assertEquals(expected, num1.sub(num2));
	}
	
	@Disabled
	@Test
	public void testMultiplyTwoComplexNumbers() {
		ComplexNumber num1 = new ComplexNumber(2.5, -1.88);
		ComplexNumber num2 = new ComplexNumber(-4.9, -3);
		
		ComplexNumber expected = new ComplexNumber(-17.89, 1.712);
		assertEquals(expected, num1.mul(num2));
	}
	
	@Test
	public void testDivideTwoComplexNumbers() {
		ComplexNumber num1 = new ComplexNumber(2.5, -1);
		ComplexNumber num2 = new ComplexNumber(-4, -3);
		
		ComplexNumber expected = new ComplexNumber(-0.28, 0.46);
		assertEquals(expected, num1.div(num2));
	}
	
	@Disabled
	@Test
	public void testComplexNumberPow() {
		ComplexNumber num = new ComplexNumber(2.5, -5);
		ComplexNumber expected = new ComplexNumber(-171.875, 31.25);
		
		assertEquals(expected, num.power(3));
	}
	
	@Disabled
	@Test
	public void testComplexNumberPow2() {
		ComplexNumber num = new ComplexNumber(-2.5, -5);
		ComplexNumber expected = new ComplexNumber(-4003.90625, 3710.9375);
		
		assertEquals(expected, num.power(5));
	}
	
	@Test
	public void testComplexNumberPow3() {
		ComplexNumber num = new ComplexNumber(-2.5, -5);
		ComplexNumber expected = new ComplexNumber(1, 0);
		
		assertEquals(expected, num.power(0));
	}
	
	@Test
	public void testIllegalArgumentShouldThrowPow() {
		ComplexNumber num = new ComplexNumber(-2.5, -5);
		assertThrows(IllegalArgumentException.class, () -> num.power(-1));
	}
	
	@Test
	public void testIllegalArgumentShouldThrowRoot() {
		ComplexNumber num = new ComplexNumber(-2.5, -5);
		assertThrows(IllegalArgumentException.class, () -> num.root(0));
	}
	
	@Disabled
	@Test
	public void testComplexNumberRoot1() {
		ComplexNumber num1 = new ComplexNumber(1, 1);
		ComplexNumber[] expected = new ComplexNumber[] {
				new ComplexNumber(2.10130339252157, 1.18973776414076),
				new ComplexNumber(-2.10130339252157, -1.18973776414076)
		};
		
		assertEquals(expected, num1.root(2));
	}
	
	@Disabled
	@Test
	public void testComplexNumberRoot2() {
		ComplexNumber num1 = new ComplexNumber(1, 1);
		ComplexNumber[] expected = new ComplexNumber[] {
				new ComplexNumber(1.0987, 0.4551),
				new ComplexNumber(-1.0987, -0.4551)
		};
		
		assertEquals(expected, num1.root(2));
	}
	
	@Test 
	public void testFromReal() {
		assertEquals(new ComplexNumber(1, 0), ComplexNumber.fromReal(1));
	}
	
	@Test 
	public void testFromImaginary() {
		assertEquals(new ComplexNumber(0, 1), ComplexNumber.fromImaginary(1));
	}
	
	@Disabled
	@Test 
	public void testFromMagnitudeAndAngle() {
		assertEquals(new ComplexNumber(1, 0), ComplexNumber.fromMagnitudeAndAngle(1, Math.PI));
	}
	
	@Test 
	public void testParser1() {
		assertEquals(new ComplexNumber(1, 0), ComplexNumber.parse("1"));
	}
	
	@Test 
	public void testParser2() {
		assertEquals(new ComplexNumber(1, -1), ComplexNumber.parse("1-i"));
	}
	
	@Test 
	public void testParser3() {
		assertEquals(new ComplexNumber(0, -1), ComplexNumber.parse("-i"));
	}
	
	@Test 
	public void testParser4() {
		assertEquals(new ComplexNumber(4, -7.5), ComplexNumber.parse("4-7.5i"));
	}
	
	@Test 
	public void testParser5() {
		assertEquals(new ComplexNumber(-5.5, -1.5), ComplexNumber.parse("-5.5-1.5i"));
	}
	
	@Test 
	public void testParser6() {
		assertEquals(new ComplexNumber(1, -2), ComplexNumber.parse("+1-2i"));
	}
}
