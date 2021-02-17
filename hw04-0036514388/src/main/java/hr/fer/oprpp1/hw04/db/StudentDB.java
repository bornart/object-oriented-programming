package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Klasa predstavlja program koji nad bazom podataka studenata obavlja upite.
 * 
 * @author borna
 *
 */
public class StudentDB {
	
	public static void main(String[] args) throws IOException {
		
		List<String> lines = Files.readAllLines(
				 Paths.get("src/main/resources/database.txt"),
				 StandardCharsets.UTF_8
		);
		
		StudentDatabase db = new StudentDatabase(lines);
		Scanner sc = new Scanner(System.in);
		
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			
			if (!line.contains("query")) {
				System.out.println("Please write a valid query!");
				continue;
			}
			
			QueryParser parser = new QueryParser(line);
			
			if (parser.isDirectQuery()) {
				List<StudentRecord> record = new ArrayList<>();
				record.add(db.forJMBAG(parser.getQueriedJMBAG()));
				
				System.out.println("Using index for record retrieval.");
				
				if (!record.isEmpty()) {
					RecordFormatter formatter = new RecordFormatter(record);
					formatter.format();
				}
				
				System.out.println("Records selected: " + (record.isEmpty() ? "0" : "1"));
			} else {
				List<StudentRecord> records = db.filter(new QueryFilter(parser.getQuery()));
				
				if (!records.isEmpty()) {
					RecordFormatter formatter = new RecordFormatter(records);
					formatter.format();
				}
				
				System.out.println("Records selected: " + records.size());
			}
		}
		
		sc.close();
	}
}
