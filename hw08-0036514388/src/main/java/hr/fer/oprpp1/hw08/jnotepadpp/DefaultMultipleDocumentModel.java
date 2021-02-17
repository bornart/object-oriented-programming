package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 * Razred predstavlja model 0, jednog ili više <code>SingleDocumentModel</code>-a.
 * 
 * @author borna
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	
	private List<SingleDocumentModel> documents;
	private SingleDocumentModel current;
	private List<MultipleDocumentListener> listeners;
	private ImageIcon greenIcon;
	private ImageIcon redIcon;
	private MultipleDocumentListener multipleListener;
	private JNotepadPP frame;
	
	/**
	 * Pretpostavljeni <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 * 
	 * @param frame Objekt tipa <code>JNotepadPP</code>.
	 * @param statusBar objekt tipa <code>StatusBar</code>; predstavlja statusnu traku u JNotepadPP.
	 */
	public DefaultMultipleDocumentModel(JNotepadPP frame, StatusBar statusBar) {
		this.frame = frame;
		documents = new ArrayList<>();
		listeners = new ArrayList<>();
		
		greenIcon = getImageIcon("icons/greenDisk.png");
		redIcon = getImageIcon("icons/redDisk.png");
		
		multipleListener = new MultipleDocumentListener() {
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {		
				if (currentModel == null) {
					frame.setTitle("JNotepad++");
					statusBar.setValues(0, 0, 0, 0);
					frame.saveDocumentAction.setEnabled(false);
					frame.disableAll();
				} else {
					int sel = statusBar.getValues(currentModel);
					frame.enableStatic();
					frame.saveDocumentAction.setEnabled(currentModel.isModified());
					frame.switchButtons(sel != 0);
					
					if (currentModel.getFilePath() == null) {
						frame.setTitle("(unnamed) - JNotepad++");
					} else {
						int activeTab = DefaultMultipleDocumentModel.this.getSelectedIndex();
						frame.setTitle(documents.get(activeTab).getFilePath() + " - JNotepad++");
					}
				}
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				model.getTextComponent().getCaret().addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						Caret c = (Caret) e.getSource();
						int sel = Math.abs(c.getDot()-c.getMark());
						
						int ln = 1;
						int col = 1;
						
						try {
							int caretpos = model.getTextComponent().getCaretPosition();
							ln = model.getTextComponent().getLineOfOffset(caretpos);
							col = caretpos - model.getTextComponent().getLineStartOffset(ln) + 1;
							ln++;
						} catch (BadLocationException e1) {
							e1.printStackTrace();
						}
						
						int length = model.getTextComponent().getText().length();
						
						statusBar.setValues(length, ln, col, sel);
						if(model == current) {
							frame.switchButtons(sel != 0);
							frame.saveDocumentAction.setEnabled(model.isModified()); 
						}
					}
				});
			}
			@Override
			public void documentRemoved(SingleDocumentModel model) {	
			}
		};
		
		listeners.add(multipleListener); 
		
		this.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel prev = current;
				
				int index = getSelectedIndex();
				if (index >= 0) {
					current = documents.get(index);
				} else {
					current = null;
				}
				multipleListener.currentDocumentChanged(prev, current);
			}
		});
	}
	
	/**
	 * Implementacija Iterator-a.
	 * 
	 * @author borna
	 *
	 */
	private static class ModelIterator implements Iterator<SingleDocumentModel> {
		int idx;
		private List<SingleDocumentModel> models;
		
		/**
		 * Konstrukor.
		 * @param docs dokumenti
		 */
		public ModelIterator(List<SingleDocumentModel> docs) {
			models = new ArrayList<>(docs);
			idx = 0;
		}
		
		@Override
		public boolean hasNext() {
			return idx < models.size();
		}

		@Override
		public SingleDocumentModel next() {
			return models.get(idx++);
		}
		
	}
	
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new ModelIterator(documents);
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		DefaultSingleDocumentModel document = new DefaultSingleDocumentModel(null, "");
		
		addDocument(document, "", "(unnamed)", null);
		
		current = document;
		this.setSelectedIndex(documents.indexOf(document));

//		for(MultipleDocumentListener l : listeners) {
//			l.documentAdded(document);
//		}
		
		return document;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if (path == null) throw new IllegalArgumentException("Staza ne smije biti null-referenca!");
		
		File file = path.toFile();
		
		if(!Files.isReadable(path)) {
			JOptionPane.showMessageDialog(null, "File "+file.getAbsolutePath()+" doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		byte[] okteti;
		
		try {
			okteti = Files.readAllBytes(path);
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Error occurred while reading file "+file.getAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		String text = new String(okteti, StandardCharsets.UTF_8);
		
		DefaultSingleDocumentModel document = new DefaultSingleDocumentModel(path, text);
		
		int index = -1;
		boolean duplicate = false;

		Iterator<SingleDocumentModel> models = documents.iterator();
		while(models.hasNext()) {
			SingleDocumentModel m = models.next();
			if (m.getFilePath() != null) {
				if (m.getFilePath().equals(document.getFilePath())) {
					duplicate = true; 
					index = documents.indexOf(m);
					break;
				}
			}
		}
		
		if (!duplicate)  {
			addDocument(document, text, file.getName(), file.getAbsolutePath());
			index = documents.indexOf(document);
		} 
		
		current = document;
		this.setSelectedIndex(index);
		
//		for(MultipleDocumentListener l : listeners) {
//			l.documentAdded(document);
//		}
		
		return document;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if(newPath==null) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document");
			if(jfc.showSaveDialog(this)!=JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(this, "Saving was terminated.", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			newPath = jfc.getSelectedFile().toPath();
			if (existingPath(model, newPath)) {
				JOptionPane.showMessageDialog(frame, "The specified file is already opened.", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			model.setFilePath(newPath);
			this.setTitleAt(documents.indexOf(model), newPath.getFileName().toString());
		}
		
		byte[] podatci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		
		try {
			Files.write(newPath, podatci);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(
					this, 
					"Error occurred while saving file "+newPath.toFile().getAbsolutePath(), 
					"Warning", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(this, "File is saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
		
		current.setModified(false);
	}
	
	/**
	 * Metoda provjerava je li datoteka na čiju stazu želimo pohraniti drugu datoteku otvorena u aplikaciji.
	 * 
	 * @param model dokument
	 * @param newPath staza do dokumenta
	 * @return true ako je staza već otvorena u aplikaciji, false u protivnom
	 */
	private boolean existingPath(SingleDocumentModel model, Path newPath) {
		for (SingleDocumentModel doc : documents) {
			if (doc.getFilePath() != null) {
				if (doc.getFilePath().equals(newPath) && !doc.equals(model)) return true;
			}
		}
		return false;
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		if (model.isModified()) {
			saveDocument(model, model.getFilePath());
		}
		
		int index = documents.indexOf(model);
		documents.remove(index);
		this.remove(index);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		if (index < 0 || index >= documents.size()) throw new IllegalArgumentException("Neispravan index!");
		
		return documents.get(index);
	}
	
	/**
	 * Metoda dohvaća ikonu (.png datoteka).
	 * 
	 * @param icon relativna staza u tekstualnom obliku
	 * @return objekt tipa <code>ImageIcon</code>
	 */
	private ImageIcon getImageIcon(String icon) {
		InputStream is = this.getClass().getResourceAsStream(icon);
		ImageIcon image = null;
		
		if (is==null) {
			System.err.println("Pogreška pri dohvaćanju ikone!");
		} else {
			try {
				byte[] bytes = is.readAllBytes();
				image =  new ImageIcon(bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return new ImageIcon(image.getImage().getScaledInstance(12, 10, Image.SCALE_DEFAULT));
	} 
	
	/**
	 * Postupak dodavanja novog tab-a u JTabbedPane.
	 * 
	 * @param document novi model (dokument)
	 * @param text Tekstualna komponenta modela
	 * @param name ime datoteke; ako datoteka nije pohranjena na disku: (unnamed)
	 * @param path staza do datoteke; ako datoteka nije pohranjena na disku: null
	 */
	private void addDocument(DefaultSingleDocumentModel document, String text, String name, String path) {
		documents.add(document);
		JTextArea ta = document.getTextComponent();
		ta.setText(text);
		document.setModified(false);
		ImageIcon icon = greenIcon;
		this.addTab(name, icon, new JScrollPane(ta), path == null ? "(unnamed)" : path);
		
		document.addSingleDocumentListener(new SingleDocumentListener() {
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				
				if (model.isModified()) {
					DefaultMultipleDocumentModel.this.setIconAt(documents.indexOf(model), redIcon);
				} else {
					DefaultMultipleDocumentModel.this.setIconAt(documents.indexOf(model), greenIcon);
				}
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				DefaultMultipleDocumentModel.this.setTitleAt(documents.indexOf(model), model.getFilePath().getFileName().toString());
				DefaultMultipleDocumentModel.this.setToolTipTextAt(documents.indexOf(model), model.getFilePath().toString());
				frame.setTitle(documents.get(getSelectedIndex()).getFilePath() + " - JNotepad++");
			}
		});
		
		//dodano:
		for(MultipleDocumentListener l : listeners) {
			l.documentAdded(document);
		}
	}
	
	/**
	 * Metoda dohvaća vrijednosti za izračun označenih redaka.
	 * 
	 * @return lista Integer-a
	 */
	public List<Integer> getLines() {
		JTextArea editor = this.getCurrentDocument().getTextComponent();
		
		int dot = editor.getCaret().getDot();
		int mark = editor.getCaret().getMark();
		Document doc = editor.getDocument();
		Element root = doc.getDefaultRootElement();
		int len = Math.abs(dot-mark);
		int offset = 0;
		if(len!=0) {
			offset = Math.min(dot,mark);
			int firstRow = root.getElementIndex(offset); 
			int end = dot > mark ? dot : mark;
			int lastRow = root.getElementIndex(end);
			
			int endOfLastLine = 0;
			int startOfFirstLine = 0;
			try {
				startOfFirstLine = editor.getLineStartOffset(firstRow);
				endOfLastLine = editor.getLineEndOffset(lastRow);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			int first = offset-startOfFirstLine;
			offset -= first;
			len += (endOfLastLine - end) + (first);
		} else {
			len = doc.getLength();
		}
		return Arrays.asList(len, offset);
	}
}
