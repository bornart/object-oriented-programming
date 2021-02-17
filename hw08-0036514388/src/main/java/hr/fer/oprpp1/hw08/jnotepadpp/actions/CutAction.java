package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

/**
 * Razred predstavlja akciju uklanjanja dijela teksta (cut) iz dokumenta.
 * 
 * @author borna
 *
 */
public class CutAction extends LocalizableAction {
	
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
	public CutAction(DefaultMultipleDocumentModel model, String s, ILocalizationProvider provider) {
		super(s, provider);
		this.model = model;
		this.setEnabled(false);
		
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X")); 
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X); 
		this.putValue(Action.SHORT_DESCRIPTION, "Used to cut part of (or whole) text."); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		
		Document doc = editor.getDocument();
		int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
		if(len==0) return;
		int offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
		try {
			doc.remove(offset, len);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

}
