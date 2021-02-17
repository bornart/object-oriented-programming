package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

/**
 * Razred predstavlja akciju izmjene slova (change case).
 * 
 * @author borna
 *
 */
public class ChangeCaseAction extends LocalizableAction {

	/**
	 * Pretpostavljeni <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel model;
	private String key;
	
	/**
	 * Konstruktor.
	 * 
	 * @param model objekt tipa <code>DefaultMultipleDocumentModel</code>. Predstavlja model svih otvorenih dokumenata u aplikaciji.
	 * @param key jedinstveni ključ korišten za lokalizaciju
	 * @param lp objekt tipa sučelje <code>ILocalizationProvider</code>
	 */
	public ChangeCaseAction(DefaultMultipleDocumentModel model, String key, ILocalizationProvider lp) {
		super(key, lp);
		this.model = model;
		this.key = key;
		this.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		
		Document doc = editor.getDocument();
		int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
		int offset = 0;
		if(len!=0) {
			offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
		} else {
			len = doc.getLength();
		}
		try {
			String text = doc.getText(offset, len);
			text = changeCase(text);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch(BadLocationException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Promjena slova s obzirom na vrijednost predanog ključa.
	 * 
	 * @param text tekst koji je potrebno izmijeniti
	 * @return izmijenjeni tekst
	 */
	private String changeCase(String text) {
		char[] arr = text.toCharArray();
		for(int i = 0; i < arr.length; i++) {
			char c = arr[i];
			if(key.equals("uppercase")) {
				arr[i] = Character.toUpperCase(c);
			} else if(key.equals("lowercase")) {
				arr[i] = Character.toLowerCase(c);
			} else {
				if (Character.isUpperCase(c)) {
					arr[i] = Character.toLowerCase(c);
				} else {
					arr[i] = Character.toUpperCase(c);
				}
			}
		}
		return new String(arr);
	}
}
