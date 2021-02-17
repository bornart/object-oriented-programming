package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasa koja predstavlja implementaciju baze podataka u kojima su pohranjeni studentski podatci.
 * 
 * @author borna
 *
 */
public class StudentDatabase {
	
	private Map<String, StudentRecord> mapOfStudensRecords;
	private List<StudentRecord> databaseList;
	
	/**
	 * Konstruktor. Pohranjuje podatke o studentima u internu mapu pritom vodeći računa da su zadovoljeni sljedeća dva uvjeta:
	 * U bazi podataka ne postoji više zapisa sa jednakim jmbag-om i ocjena smije biti iz intervala [1, 5].
	 * Ukoliko bilokoji od uvjeta nije zadovoljen program se terminira uz prikladnu poruku. 
	 * 
	 * @param lines lista stringova od kojih svaki string predstavlja jedan redak baze podataka
	 */
	public StudentDatabase(List<String> lines) {
		mapOfStudensRecords = new LinkedHashMap<>();
		databaseList = new ArrayList<>();
		
		for (String line : lines) {
			String[] elems = line.split("\\t");
			
			String jmbag = elems[0];
			String lastName = elems[1];
			String firstName = elems[2];
			int grade = Integer.parseInt(elems[3]);
			StudentRecord student = new StudentRecord(jmbag, lastName, firstName, grade);
			if (mapOfStudensRecords.put(jmbag, student) != null || grade < 1 || grade > 5) {
				System.err.println("Neispravni podatci u bazi podataka!");
				System.exit(0);
			}
			databaseList.add(student);
		}
	}

	/**
	 * Metoda dohvaća podatke o traženom studentu sa složenošću O(1).
	 * 
	 * @param jmbag jmbag studenta čiji se podatci žele dohvatiti
	 * @return objekt tipa <code>StudentRecord</code> koji predstavlja podatke o traženom studentu ili <code>null</code> ukoliko u bazi podataka ne postoji traženi student
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return mapOfStudensRecords.get(jmbag); 
	}
	
	/**
	 * Metoda iterira po podatcima svih studenata pritom pozivajući (nad podatcima svakog studenta) metodu <code>accepts</code>. 
	 * Metoda vraća listu tipa <code>StudentRecord</code> sa podatcima o svim studentima čiji su podatci "prihvaćeni" od metode <code>accepts</code> (metoda je vratila boolean vrijednost <code>true</code>).
	 * 
	 * @param filter zadani filter na temelju kojega se provodi filtriranje studenata 
	 * @return lista tipa <code>StudentRecord</code> sa podatcima o svim studentima čiji su podatci "prihvaćeni"
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> tmpList = new ArrayList<>();
		
		for(StudentRecord record : mapOfStudensRecords.values()) {
			if(filter.accepts(record)) tmpList.add(record);
		}
		return tmpList;
	}
}
