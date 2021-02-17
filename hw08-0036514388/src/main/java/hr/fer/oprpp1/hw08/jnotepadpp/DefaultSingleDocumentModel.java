package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Razred predstavlja model jednog dokumenta.
 * 
 * @author borna
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	private Path path;
	private JTextArea textArea;
	private boolean modified;
	private List<SingleDocumentListener> listeners;
	
	/**
	 * Konstruktor.
	 * 
	 * @param path staza do datoteke
	 * @param textContent tekstualni sadržaj datoteke
	 */
	public DefaultSingleDocumentModel(Path path, String textContent) {
		this.path = path;
		textArea = new JTextArea(textContent);
		modified = false;
		
		listeners = new ArrayList<>();
		
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
			
		});

	}
	
	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		if (path == null) throw new IllegalArgumentException("Staza ne smije biti null-referenca!");
		
		this.path = path;
		
		for(SingleDocumentListener l : listeners) {
			l.documentFilePathUpdated(this);
		}
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		
		for(SingleDocumentListener l : listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}
	
}
