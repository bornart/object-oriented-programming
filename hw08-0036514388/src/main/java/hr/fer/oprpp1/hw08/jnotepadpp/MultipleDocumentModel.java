package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

/**
 * Sučelje predstavlja model sposoban čuvati nula, jedan ili više dokumenata pritom posjedujući informaciju o trenutnom dokumentu -
 * dokument koji je prikazan i na kojem korisnik trenutno radi.
 * 
 * @author borna
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	
	/**
	 * Metoda stvara novi dokument tipa <code>SingleDocumentModel</code>.
	 * 
	 * @return objekt tipa <code>SingleDocumentModel</code> koji predstavlja novostvoreni dokument
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Metoda vraća trenutno aktivni dokument.
	 * 
	 * @return objekt tipa <code>SingleDocumentModel</code> koji predstavlja trenutno aktivni dokument
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Metoda učitava novi dokument sa predane staze.
	 * 
	 * @param path staza do dokumenta
	 * @return objekt tipa <code>SingleDocumentModel</code> koji predstavlja učitani dokument
	 * @throws IllegalArgumentException ako je kao <code>path</code> predana null-referenca
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Metoda pohranjuje dokument.
	 * 
	 * @param model dokument koji se pohranjuje; objekt tipa <code>SingleDocumentModel</code>
	 * @param newPath staza na koju se pohranjuje dokument
	 * @throws IllegalArgumentException ako <code>newPath</code> već sadrži objekt tipa <code>SingleDocumentModel</code>
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Metoda gasi (zatvara) dokument.
	 * 
	 * @param model dokument koji se zatvara; objekt tipa <code>SingleDocumentModel</code>
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Metoda dodaje novi listener.
	 * 
	 * @param l objekt tipa <code>MultipleDocumentListener</code>
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Metoda uklanja listener.
	 * 
	 * @param l objekt tipa <code>MultipleDocumentListener</code>
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Metoda vraća ukupan broj dokumenata.
	 * 
	 * @return broj dokumenata
	 */
	int getNumberOfDocuments();
	
	/**
	 * Metoda dohvaća dokument sa pozicije <code>index</code>.
	 * 
	 * @param index pozicija sa koje se dohvaća dokument
	 * @return objekt tipa <code>SingleDocumentModel</code> koji predstavlja dokument dohvaćen sa željene pozicije
	 */
	SingleDocumentModel getDocument(int index);
}

