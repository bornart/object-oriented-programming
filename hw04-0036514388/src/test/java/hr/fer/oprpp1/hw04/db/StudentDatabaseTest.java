package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StudentDatabaseTest { 
	
	public List<String> readDatabase() throws IOException {
		
		List<String> lines = Files.readAllLines(
				 Paths.get("C:/OPRPP1/database/database.txt"),
				 StandardCharsets.UTF_8
		);
		return lines;
	}
	
	@Test
	public void testForJMBAG() throws IOException {
		StudentDatabase db = new StudentDatabase(readDatabase());
		
		assertEquals(new StudentRecord("0000000008", "Ćurić", "Marko", 5), db.forJMBAG("0000000008"));
	}
	
	@Test
	public void testForJMBAGReturnsNUll() throws IOException {
		StudentDatabase db = new StudentDatabase(readDatabase());
		
		assertEquals(null, db.forJMBAG("0000000100"));
	}
	
	@Test
	public void testFilterAcceptsAll() throws IOException {
		StudentDatabase db = new StudentDatabase(readDatabase());
		
		List<StudentRecord> list = db.filter(record -> true);
		assertEquals(63, list.size());
	}
	
	@Test
	public void testFilterRejectsAll() throws IOException {
		StudentDatabase db = new StudentDatabase(readDatabase());
		
		List<StudentRecord> list = db.filter(record -> false);
		assertEquals(0, list.size());
	}
}
