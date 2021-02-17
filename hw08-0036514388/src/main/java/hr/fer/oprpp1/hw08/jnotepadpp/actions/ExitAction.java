package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;

/**
 * Razred predstavlja akciju izlaska iz JNotepadPP.
 * 
 * @author borna
 *
 */
public class ExitAction extends LocalizableAction {

	/**
	 * Pretpostavljeni <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	private JNotepadPP npp;
	
	/**
	 * Konstruktor.
	 * 
	 * @param objekt tipa <code>JNotepadPP</code>
	 * @param s jedinstveni ključ korišten za lokalizaciju
	 * @param lp objekt tipa sučelje <code>ILocalizationProvider</code>
	 */
	public ExitAction(JNotepadPP npp, String s, ILocalizationProvider provider) {
		super(s, provider);
		this.npp = npp;
		
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F4")); 
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E); 
		this.putValue(Action.SHORT_DESCRIPTION, "Exit application."); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		npp.closeAplication();
	}

}
