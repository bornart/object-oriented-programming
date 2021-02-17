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
 * Razred predstavlja akciju lijepljenja dijela teksta (paste) u dokumenta.
 * 
 * @author borna
 *
 */
public class PasteAction extends LocalizableAction {
	
	/**
	 * Pretpostavljeni <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel model;
	private CopyAction copyAction;
	
	/**
	 * Konstruktor.
	 * 
	 * @param model objekt tipa <code>DefaultMultipleDocumentModel</code>. Predstavlja model svih otvorenih dokumenata u aplikaciji.
	 * @param s jedinstveni ključ korišten za lokalizaciju
	 * @param lp objekt tipa sučelje <code>ILocalizationProvider</code>
	 */
	public PasteAction(DefaultMultipleDocumentModel model, CopyAction copyAction, String s, ILocalizationProvider provider) {
		super(s, provider);
		this.model = model;
		this.copyAction = copyAction;
		this.setEnabled(false);
		
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P")); 
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P); 
		this.putValue(Action.SHORT_DESCRIPTION, "Used to paste text."); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		
		int offset = editor.getCaret().getDot();
		String toPaste = copyAction.getCopiedText();
		
		String text = editor.getText();
		
		StringBuilder b = new StringBuilder(text);
		b.insert(offset, toPaste);
		
		editor.setText(b.toString());
	}
}
