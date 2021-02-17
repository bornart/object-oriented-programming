package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * Razred nasljeđuje <code>LocalizationProviderBridge</code> i registrira se kao <code>WindowListener</code>.
 * 
 * @author borna
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	
	/**
	 * Konstruktor.
	 * 
	 * @param provider objekt tipa <code>ILocalizationProvider</code>
	 * @param frame okvir na koji se dodaje <code>WindowListener</code>
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
	}

	
}
