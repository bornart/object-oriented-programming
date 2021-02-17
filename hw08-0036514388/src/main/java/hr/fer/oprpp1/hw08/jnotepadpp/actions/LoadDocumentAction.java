package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

/**
 * Razred predstavlja akciju učitavanja dokumenta u JNotepadPP.
 * 
 * @author borna
 *
 */
public class LoadDocumentAction extends LocalizableAction {
	
	/**
	 * Pretpostavljeni <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private DefaultMultipleDocumentModel model;
	
	/**
	 * Konstruktor.
	 * 
	 * @param frame okvir na kojemu se korisniku prikazuje <code>FileChooser</code>
	 * @param model objekt tipa <code>DefaultMultipleDocumentModel</code>. Predstavlja model svih otvorenih dokumenata u aplikaciji.
	 * @param s jedinstveni ključ korišten za lokalizaciju
	 * @param lp objekt tipa sučelje <code>ILocalizationProvider</code>
	 */
	public LoadDocumentAction(JFrame frame, DefaultMultipleDocumentModel model, String s, ILocalizationProvider provider) {
		super(s, provider);
		this.frame = frame;
		this.model = model;

		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O")); 
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O); 
		this.putValue(Action.SHORT_DESCRIPTION, "Used to open existing file from disk."); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		if(fc.showOpenDialog(frame)!=JFileChooser.APPROVE_OPTION) {
			return ;
		}
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		
		model.loadDocument(filePath);
	}
}
