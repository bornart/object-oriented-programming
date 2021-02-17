package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QueryParserTest {
	
	@Test 
	public void testQueryParser() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertTrue(qp1.isDirectQuery());
		assertEquals("0123456789", qp1.getQueriedJMBAG());
		assertEquals(1, qp1.getQuery().size());
	}
	
	@Test 
	public void testQueryParser2() {
		QueryParser qp1 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertFalse(qp1.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> qp1.getQueriedJMBAG());
		assertEquals(2, qp1.getQuery().size());
	}
}
