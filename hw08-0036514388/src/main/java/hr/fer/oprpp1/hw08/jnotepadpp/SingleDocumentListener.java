package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * Model promatrača koji je zainteresiran za dojavu o promjeni staze ili statusa modifikacije dokumenta.
 * 
 * @author borna
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * Metoda ažurira status modifikacije predanog modela.
	 * 
	 * @param model model dokumenta (objekt tipa <code>SingleDocumentModel</code>) u kojem je došlo do promjene
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Metoda ažurira stazu predanog modela.
	 * 
	 * @param model model dokumenta (objekt tipa <code>SingleDocumentModel</code>) u kojem je došlo do promjene
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}

