package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

/**
 * Razred predstavlja akciju prikaza statističkih podataka o trenutno otvorenom dokumentu.
 * 
 * @author borna
 *
 */
public class StatisticsAction extends LocalizableAction {
	
	/**
	 * Pretpostavljeni <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel model;
	private JFrame frame;
	
	/**
	 * Konstruktor.
	 * 
	 * @param model objekt tipa <code>DefaultMultipleDocumentModel</code>. Predstavlja model svih otvorenih dokumenata u aplikaciji.
	 * @param frame okvir na kojemu se korisniku prikazuje <code>FileChooser</code>
	 * @param s jedinstveni ključ korišten za lokalizaciju
	 * @param lp objekt tipa sučelje <code>ILocalizationProvider</code>
	 */
	public StatisticsAction(DefaultMultipleDocumentModel model, JFrame frame, String s, ILocalizationProvider provider) {
		super(s, provider);
		this.model = model;
		this.frame = frame;
		this.setEnabled(false);
		
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T")); 
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T); 
		this.putValue(Action.SHORT_DESCRIPTION, "Used to get information about the document."); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String active = model.getCurrentDocument().getTextComponent().getText();
		
		int numOfChars = active.length();
		int nonBlankChars = active.replaceAll("\\s+", "").length();
		int numOfLines = model.getCurrentDocument().getTextComponent().getLineCount();
		
		JOptionPane.showMessageDialog(frame, "Your document has "+numOfChars+" characters, "+nonBlankChars+" non-blank characters and "+numOfLines+" lines.");
	}

}
