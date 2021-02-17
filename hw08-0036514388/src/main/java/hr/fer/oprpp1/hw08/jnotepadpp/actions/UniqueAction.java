package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

/**
 * Razred predstavlja akciju otklanjanja duplih redaka iz selekcije.
 * 
 * @author borna
 *
 */
public class UniqueAction extends LocalizableAction {
	
	/**
	 * Pretpostavljeni <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel model;
	
	/**
	 * Konstruktor.
	 * 
	 * @param model objekt tipa <code>DefaultMultipleDocumentModel</code>. Predstavlja model svih otvorenih dokumenata u aplikaciji.
	 * @param key jedinstveni ključ korišten za lokalizaciju
	 * @param lp objekt tipa sučelje <code>ILocalizationProvider</code>
	 */
	public UniqueAction(DefaultMultipleDocumentModel model, String key, ILocalizationProvider lp) {
		super(key, lp);
		this.model = model;
		this.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int len = model.getLines().get(0);
		int offset = model.getLines().get(1);
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();
		
		try {
			String text = doc.getText(offset, len);
			String[] rows = text.split("\\n");
			rows = Arrays.stream(rows).distinct().toArray(String[]::new);
			rows[rows.length-1] += "\n"; //dodano
			doc.remove(offset, len);
			doc.insertString(offset, String.join("\n", rows), null);
		} catch(BadLocationException ex) {
			ex.printStackTrace();
		}
	}
}
