package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {
	
	@Test
	public void testLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
		assertFalse(oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testLessOrEquals() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
		assertFalse(oper.satisfied("Jasna", "Ana"));
		assertTrue(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertFalse(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testGreaterOrEquals() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertFalse(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Jasna", "Ana"));
		assertTrue(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testEquals() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertFalse(oper.satisfied("Ana", "Jasna"));
		assertFalse(oper.satisfied("Jasna", "Ana"));
		assertTrue(oper.satisfied("Ana", "Ana"));
		assertFalse(oper.satisfied("ana", "Ana"));
		assertFalse(oper.satisfied("Ana", "AnA"));
	}
	
	@Test
	public void testNotEquals() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Jasna", "Ana"));
		assertFalse(oper.satisfied("Ana", "Ana"));
		assertTrue(oper.satisfied("ana", "Ana"));
	}
	
	@Test
	public void testLike() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("AAA", "A*A"));
		assertFalse(oper.satisfied("Zagreb", "Aba*"));
		assertTrue(oper.satisfied("Bosnić", "Bos*ić"));
		assertTrue(oper.satisfied("Bosnić", "Bos*"));
		assertFalse(oper.satisfied("AAA", "AA*AA"));
		assertTrue(oper.satisfied("AAA", "*AA"));
		assertFalse(oper.satisfied("Zagreb", "Zagre*eb"));
		assertTrue(oper.satisfied("AAA", "AA*"));
		assertTrue(oper.satisfied("AAAA", "AA*AA"));
		assertTrue(oper.satisfied("Ana", "Ana*"));
		assertTrue(oper.satisfied("Ana", "*Ana"));
		assertTrue(oper.satisfied("Šimunović", "*imunović"));
	}
}
