package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

/**
 * Razred predstavlja akciju stvaranja novog dokumenta u JNotepadPP.
 * 
 * @author borna
 *
 */
public class CreateDocumentAction extends LocalizableAction {
	
	/**
	 * Pretpostavljeni <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel model;
	
	/**
	 * Konstruktor.
	 * 
	 * @param model objekt tipa <code>DefaultMultipleDocumentModel</code>. Predstavlja model svih otvorenih dokumenata u aplikaciji.
	 * @param s jedinstveni ključ korišten za lokalizaciju
	 * @param lp objekt tipa sučelje <code>ILocalizationProvider</code>
	 */
	public CreateDocumentAction(DefaultMultipleDocumentModel model, String s, ILocalizationProvider provider) {	
		super(s, provider);
		this.model = model;

		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N")); 
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N); 
		this.putValue(Action.SHORT_DESCRIPTION, "Used to create new document."); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		model.createNewDocument();
	}

}
