package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

/**
 * Razred predstavlja akciju kopiranja dijela teksta (copy) iz dokumenta.
 * 
 * @author borna
 *
 */
public class CopyAction extends LocalizableAction {
	
	/**
	 * Pretpostavljeni <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel model;
	private String copiedText;
	
	/**
	 * Konstruktor.
	 * 
	 * @param model objekt tipa <code>DefaultMultipleDocumentModel</code>. Predstavlja model svih otvorenih dokumenata u aplikaciji.
	 * @param s jedinstveni ključ korišten za lokalizaciju
	 * @param lp objekt tipa sučelje <code>ILocalizationProvider</code>
	 */
	public CopyAction(DefaultMultipleDocumentModel model, String s, ILocalizationProvider provider) {
		super(s, provider);
		this.model = model;
		copiedText = "";
		this.setEnabled(false);
		
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Y")); 
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Y); 
		this.putValue(Action.SHORT_DESCRIPTION, "Used to copy part of (or whole) text."); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		
		int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
		if(len==0) return;
		int offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
		
		copiedText = new String(editor.getText().toCharArray(), offset, len);
	}
	
	/**
	 * Metoda vraća dio teksta koji je kopiran.
	 * 
	 * @return dio teksta koji je kopiran
	 */
	public String getCopiedText() {
		return copiedText;
	}

}
