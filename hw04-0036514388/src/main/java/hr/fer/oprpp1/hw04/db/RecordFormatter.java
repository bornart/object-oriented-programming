package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa implementira formatiranje studentskih podataka iz baze podataka.
 * 
 * @author borna
 *
 */
public class RecordFormatter {
	
	private List<StudentRecord> records;
	
	/**
	 * Konstruktor.
	 * 
	 * @param dbRecords lista tipa <code>StudentRecord</code>
	 */
	public RecordFormatter(List<StudentRecord> dbRecords) {
		records = new ArrayList<>(dbRecords);
	}
	
	/**
	 * Metoda formatira podatke na način da pronalazi širinu stupaca <code>lastName</code> i <code>firstName</code>.
	 * Poziva metodu <code>print</code>.
	 */
	public void format() {	
		int lastNameSize = 0;
		int firstNameSize = 0;
		
		for (StudentRecord record : records) {
			if (record.getFirstName().length() > firstNameSize) firstNameSize = record.getFirstName().length();
			if (record.getLastName().length() > lastNameSize) lastNameSize = record.getLastName().length();
		}
		
		print(lastNameSize, firstNameSize);
	}
	
	/**
	 * Metoda iscrtava tablicu sa podatcima o studentima koji su zadovoljili korisnički upit.
	 * 
	 * @param lastNameSize
	 * @param firstNameSize
	 */
	private void print(int lastNameSize, int firstNameSize) {
		String first = fillSpaces(firstNameSize+1, "=");
		String last = fillSpaces(lastNameSize+1, "=");
		
		String table = "+============+" + last + "+" + first + "+===+";
		System.out.println(table);
		for (int i = 0; i < records.size(); i++) {
			StudentRecord record = records.get(i);
			String row = "| " + record.getJmbag() + " | " + record.getLastName() + fillSpaces(lastNameSize-record.getLastName().length(), " ") + "| " + record.getFirstName() + fillSpaces(firstNameSize-record.getFirstName().length(), " ") + "| " + record.getFinalGrade() + " |";
			System.out.println(row);
		}
		System.out.println(table);
	}
	
	/**
	 * Metoda dopunjava mjesta u tablici zadanim znakom <code>fill</code>.
	 * 
	 * @param size broj znakova koje je potrebno dodati
	 * @param fill konkretni znak kojim je potrebno popuniti zadani broj mjesta
	 * @return string koji predstavlja nadopunu
	 */
	private String fillSpaces(int size, String fill) {
		String s = "";
		for (int i = 0; i <= size; i++) {
			s += fill;
		}
		return s;
	}
}
