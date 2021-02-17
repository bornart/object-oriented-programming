package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Locale;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

/**
 * Razred predstavlja akciju sortiranja odabranih redaka.
 * 
 * @author borna
 *
 */
public class SortAction extends LocalizableAction {
	
	/**
	 * Pretpostavljeni <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel model;
	private String key;
	private String language;
	private ILocalizationProvider lp; //dodano
	
	/**
	 * Konstruktor.
	 * 
	 * @param model objekt tipa <code>DefaultMultipleDocumentModel</code>. Predstavlja model svih otvorenih dokumenata u aplikaciji.
	 * @param key jedinstveni ključ korišten za lokalizaciju
	 * @param lp objekt tipa sučelje <code>ILocalizationProvider</code>
	 */
	public SortAction(DefaultMultipleDocumentModel model, String key, ILocalizationProvider lp) {
		super(key, lp);
		this.model = model;
		this.key = key;
		language = lp.getCurrentLanguage();
		this.lp = lp;
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
			rows = sortRows(rows);
			rows[rows.length-1] += "\n"; //dodano
			doc.remove(offset, len);
			doc.insertString(offset, String.join("\n", rows), null);
		} catch(BadLocationException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Metoda sortira retke (uzlazno ili silazno, ovisno o predanom ključu) na temelju abecede jezika trenutno korištenog u aplikaciji.
	 * 
	 * @param rows polje redaka teksta koje je potrebno sortirati
	 * @return sortirano polje redaka teksta
	 */
	private String[] sortRows(String[] rows) {
		language = lp.getCurrentLanguage(); //

		Locale locale = new Locale(language);
		Collator collator = Collator.getInstance(locale);
		
		for(int i = 0, size = rows.length-1; i < size; i++) {
			for(int j = i+1; j <= size; j++) {
				if (key.equals("ascending") ? collator.compare(rows[i], rows[j]) > 0 : collator.compare(rows[i], rows[j]) < 0) {
					String tmp = rows[i];
					rows[i] = rows[j];
					rows[j] = tmp;
				}
			}
		}
		return rows;
	}
}
