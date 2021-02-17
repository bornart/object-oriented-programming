package hr.fer.oprpp1.hw04.db;

/**
 * Klasa predstavlja podatke svakog pojedinog studenta.
 * 
 * @author borna
 *
 */
public class StudentRecord {

	private String jmbag;
	private String firstName;
	private String lastName;
	private int finalGrade;
	
	/**
	 * Konstruktor.
	 * 
	 * @param jmbag jmbag studenta
	 * @param firstName ime studenta
	 * @param lastName prezime studenta
	 * @param finalGrade zaključna ocjena
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.finalGrade = finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	/**
	 * Getter. Metoda dohvaća jmbag trenutnog studenta.
	 * 
	 * @return jmbag studenta
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	/**
	 * Getter. Metoda dohvaća ime trenutnog studenta.
	 * 
	 * @return ime studenta
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Getter. Metoda dohvaća prezime trenutnog studenta.
	 * 
	 * @return prezime studenta
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Getter. Metoda dohvaća ocjenu trenutnog studenta.
	 * 
	 * @return ocjena studenta
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
}
