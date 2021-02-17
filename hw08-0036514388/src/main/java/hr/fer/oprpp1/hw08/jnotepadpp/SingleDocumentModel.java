package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Sučelje predstavlja model jednog dokumenta. <br>
 * Sadrži sljedeće informacije o samom dokumentu: <br>
 * - <code>path</code> s kojeg je dokument učitan <br>
 * - status modifikacije dokumenta <br>
 * - referencu na <code>Swing</code> komponentu koja se koristi za uređivanje samog dokumenta
 * 
 * @author borna
 *
 */
public interface SingleDocumentModel {
	
	/**
	 * Metoda dohvaća tekstualnu komponentu <code>JTextArea</code>.
	 * 
	 * @return tekstualna komponenta <code>JTextArea</code>
	 */
	JTextArea getTextComponent();
	
	/**
	 * Metoda dohvaća stazu do dokumenta.
	 * 
	 * @return staza do dokumenta
	 */
	Path getFilePath();
	
	/**
	 * Metoda postavlja stazu do dokumenta. 
	 * 
	 * @param path staza
	 * @throws IllegalArgumentException ako je predana null-referenca
	 */
	void setFilePath(Path path);
	
	/**
	 * Metoda vraća boolean vrijednost je li dokument modificiran ili ne (status modifikacije dokumenta).
	 * 
	 * @return <code>true</code> ako je dokument modificiran, <code>false</code> u protivnom
	 */
	boolean isModified();
	
	/**
	 * Metoda postavlja status modifikacije dokumenta.
	 * 
	 * @param modified boolean vrijednost koja govori je li dokument bio modificiran ili ne
	 */
	void setModified(boolean modified);
	
	/**
	 * Metoda dodaje listener.
	 * 
	 * @param l objekt tipa <code>SingleDocumentListener</code>
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Metoda uklanja listener.
	 * 
	 * @param l objekt tipa <code>SingleDocumentListener</code>
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}

