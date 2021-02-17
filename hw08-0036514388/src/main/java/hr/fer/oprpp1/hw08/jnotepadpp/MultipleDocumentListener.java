package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * Model promatrača koji je zainteresiran za dojavu o promjeni, dodavanju ili uklanjanja dokumenta.
 * 
 * @author borna
 *
 */
public interface MultipleDocumentListener {
	
	/**
	 * Metoda koja se poziva kao rezultat promjene dokumenta. 
	 * 
	 * @param previousModel prethodni model
	 * @param currentModel trenutni model
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * Metoda koja se poziva kao rezultat dodavanja novog dokumenta.
	 * 
	 * @param model model dokumenta (objekt tipa <code>SingleDocumentModel</code>) u kojem je došlo do promjene
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Metoda koja se poziva kao rezultat uklanjanja jednog dokumenta.
	 * 
	 * @param model model dokumenta (objekt tipa <code>SingleDocumentModel</code>) u kojem je došlo do promjene
	 */
	void documentRemoved(SingleDocumentModel model);
}

