package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

/**
 * Statusna traka.
 * 
 * @author borna
 *
 */
public class StatusBar extends JPanel {
	
	/**
	 * Pretpostavljeni <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel jLength, jInfo, jTime;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	private volatile boolean stopRequested;
	
	/**
	 * Konstruktor.
	 * 
	 * @param frame objekt tipa <code>JFrame</code>. Na njemu se iscrtavaju komponente statusne trake
	 */
	public StatusBar(JFrame frame) {
		int length = 0;
		int ln = 0;
		int col = 0;
		int sel = 0;
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				stop();
			}
		});
		
		this.setLayout(new GridLayout(1, 3));
		this.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
		jLength = new JLabel("length: " + length);
		jLength.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
		this.add(jLength);
		jInfo = new JLabel(" Ln: "+ ln + "  Col: " + col + "  Sel: " + sel);
		jInfo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
		this.add(jInfo);
		jTime = new JLabel();
		jTime.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(jTime); 
		
		updateTime();
		
		Thread t = new Thread(() -> {
			while(true) {
				try {
					Thread.sleep(500); 
				} catch(Exception ex) {}
				if(stopRequested) break;
				SwingUtilities.invokeLater(()->{
					updateTime();
				});
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	/**
	 * Zaustavljanje demonske dretve.
	 */
	private void stop() {
		stopRequested = true;
	}
	
	/**
	 * Metoda dohvaća vrijednosti statusne trake trenutno otvorenog dokumenta.
	 * @param model trenutno otvoreni dokument
	 */
	public int getValues(SingleDocumentModel model) {
		JTextArea editor = model.getTextComponent();
		int sel = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
		int ln = 0, col = 0;
		
		try {
			int caretpos = model.getTextComponent().getCaretPosition();
			ln = model.getTextComponent().getLineOfOffset(caretpos);
			col = caretpos - model.getTextComponent().getLineStartOffset(ln) + 1;
			ln++;
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		int length = model.getTextComponent().getText().length();
		
		setValues(length, ln, col, sel);
		return sel;
	}
	
	/**
	 * Osvježavanje sata.
	 */
	private void updateTime() {
		jTime.setText(formatter.format(LocalDateTime.now()));
	}
	
	/**
	 * Metoda postavlja komponente statusne trake.
	 * 
	 * @param length duljina teksta
	 * @param ln broj retka
	 * @param col broj stupca
	 * @param sel veličina selekcije
	 */
	public void setValues(int length, int ln, int col, int sel) {
		jLength.setText("length: " + length);
		jInfo.setText(" Ln: "+ ln + "  Col: " + col + "  Sel: " + sel);
	}
	
	
}
