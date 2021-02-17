package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FieldValueGettersTest {
	
	@Test
	public void testGetFirstName() {
		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		assertEquals("Marin", FieldValueGetters.FIRST_NAME.get(record));
	}
	
	@Test
	public void testGetLastName() {
		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		assertEquals("Akšamović", FieldValueGetters.LAST_NAME.get(record));
	}
	
	@Test
	public void testGetJmbag() {
		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		assertEquals("0000000001", FieldValueGetters.JMBAG.get(record));
	}
	
	@Test
	public void testGetFirstName1() {
		StudentRecord record = new StudentRecord("0000000015", "Glavinić Pecotić", "Kristijan", 4);
		assertEquals("Kristijan", FieldValueGetters.FIRST_NAME.get(record));
	}
	
	@Test
	public void testGetLastName1() {
		StudentRecord record = new StudentRecord("0000000015", "Glavinić Pecotić", "Kristijan", 4);
		assertEquals("Glavinić Pecotić", FieldValueGetters.LAST_NAME.get(record));
	}
	
	@Test
	public void testGetJmbag1() {
		StudentRecord record = new StudentRecord("0000000015", "Glavinić Pecotić", "Kristijan", 4);
		assertEquals("0000000015", FieldValueGetters.JMBAG.get(record));
	}
}
