package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.ChangeCaseAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.CloseDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.CopyAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.CreateDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.CutAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.ExitAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.LoadDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.PasteAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SaveAsAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SortAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.StatisticsAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.UniqueAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

/**
 * Jednostavni uređivač teksta JNotepadPP.
 * 
 * @author borna
 *
 */
public class JNotepadPP extends JFrame {

	/**
	 * Pretpostavljeni <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel model;
	private FormLocalizationProvider flp;
	
	private ChangeCaseAction upperCase;
	private ChangeCaseAction lowerCase;
	private ChangeCaseAction invertCase;
	private SortAction asc;
	private SortAction desc;
	private UniqueAction unique;
	private CutAction cutAction;
	protected SaveDocumentAction saveDocumentAction;
	private StatisticsAction statisticsAction;
	private SaveAsAction saveAsAction;
	private CloseDocumentAction closeDocumentAction;
	private CopyAction copyAction;
	private PasteAction pasteAction;
	
	List<LocalizableAction> actions;
	List<LocalizableAction> actionsStatic;
	
	/**
	 * Konstruktor.
	 */
	public JNotepadPP() {
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		actions = new ArrayList<>();
		actionsStatic = new ArrayList<>();
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(750, 600);
		setTitle("JNotepad++");
		initGUI();
		
		WindowListener wl = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeAplication();
			}
		};
		this.addWindowListener(wl);
	}
	
	/**
	 * Metoda provjerava postoji li među otvorenim dokumentima dokument koji je modificiran.
	 * 
	 * @return pozicija modificiranog dokumenta
	 */
	public List<SingleDocumentModel> checkForUnsavedDocuments() {
		int num = model.getNumberOfDocuments();
		List<SingleDocumentModel> list = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			if (model.getDocument(i).isModified()) {
				list.add(model.getDocument(i));
			}
		}
		return list;
	}
	
	/**
	 * Metoda zatvara aplikaciju.
	 */
	public void closeAplication() {
		List<SingleDocumentModel> check = checkForUnsavedDocuments();
		if (check.isEmpty()) {
			dispose();
			return;
		}
		
		String[] options = new String[] {"Save", "Don't save", "Cancel"};
		
		int result = JOptionPane.showOptionDialog(JNotepadPP.this, "Do you want to save changes?", "Warning!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		
		switch(result) {
		case JOptionPane.CLOSED_OPTION: return;
		case 0:
			for(SingleDocumentModel m : check) {
				model.saveDocument(m, m.getFilePath());
				m.setModified(false);
			}
			dispose();
			return;
		case 1:
			dispose();
			return;
		case 2:
			return;
		}
	}
	
	/**
	 * Inicijalizacija grafičkog korisničkog sučelja.
	 */
	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout()); 
		
		//statusBar:
		StatusBar statusBar = new StatusBar(this);
		this.getContentPane().add(statusBar, BorderLayout.PAGE_END);
		
		model = new DefaultMultipleDocumentModel(this, statusBar);
		this.getContentPane().add(model, BorderLayout.CENTER);
		
		//akcije:
		LoadDocumentAction loadDocumentAction = new LoadDocumentAction(this, model, "open", flp);
		ExitAction exitAction = new ExitAction(this, "exit", flp);
		CreateDocumentAction createDocumentAction = new CreateDocumentAction(model, "new", flp);
		saveDocumentAction = new SaveDocumentAction(model, "save", flp);
		saveAsAction = new SaveAsAction(model, "saveAs", flp);
		closeDocumentAction = new CloseDocumentAction(model, "close", flp);
		cutAction = new CutAction(model, "cut", flp);
		copyAction = new CopyAction(model, "copy", flp);
		pasteAction = new PasteAction(model, copyAction, "paste", flp);
		statisticsAction = new StatisticsAction(model, this, "tabInfo", flp);
		upperCase = new ChangeCaseAction(model, "uppercase", flp);
		lowerCase = new ChangeCaseAction(model, "lowercase", flp);
		invertCase = new ChangeCaseAction(model, "invertcase", flp);
		asc = new SortAction(model, "ascending", flp);
		desc = new SortAction(model, "descending", flp);
		unique = new UniqueAction(model, "unique", flp);
		
		actionsStatic.add(saveAsAction);
		actionsStatic.add(closeDocumentAction);
		actionsStatic.add(pasteAction);
		actionsStatic.add(statisticsAction);
		//actions.add(saveDocumentAction);
		actions.add(cutAction);
		actions.add(copyAction);
		actions.add(upperCase);
		actions.add(lowerCase);
		actions.add(invertCase);
		actions.add(asc);
		actions.add(desc);
		actions.add(unique);
		
		//menu:
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu(new LocalizableAction("file", flp)); 
		menuBar.add(fileMenu);		
		fileMenu.add(new JMenuItem(createDocumentAction));
		fileMenu.add(new JMenuItem(loadDocumentAction));
		fileMenu.addSeparator(); 
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.addSeparator(); 
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.addSeparator(); 
		fileMenu.add(new JMenuItem(exitAction));
		
		JMenu editMenu = new JMenu(new LocalizableAction("edit", flp)); 
		menuBar.add(editMenu);
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));
		
		JMenu infoMenu = new JMenu(new LocalizableAction("info", flp)); 
		menuBar.add(infoMenu);
		infoMenu.add(new JMenuItem(statisticsAction));
		
		//lokalizacija:
		JMenu langMenu = new JMenu(new LocalizableAction("languages", flp)); 
		menuBar.add(langMenu);
		
		JMenuItem en = new JMenuItem("English");
		en.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("en"));
		langMenu.add(en);
		
		JMenuItem hr = new JMenuItem("Hrvatski");
		hr.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("hr"));
		langMenu.add(hr);
		
		JMenuItem de = new JMenuItem("Deutsche");
		de.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("de"));
		langMenu.add(de);
		
		//subproblem 2.3
		JMenu toolsMenu = new JMenu(new LocalizableAction("tools", flp)); 
		menuBar.add(toolsMenu);
		
		JMenu changeCase = new JMenu(new LocalizableAction("changecase", flp)); 
		toolsMenu.add(changeCase);
		changeCase.add(upperCase);
		changeCase.add(lowerCase);
		changeCase.add(invertCase);
		
		JMenu sort = new JMenu(new LocalizableAction("sort", flp)); 
		toolsMenu.add(sort);
		sort.add(asc);
		sort.add(desc);
		
		toolsMenu.add(unique);
		
		this.setJMenuBar(menuBar);
		
		//alatna traka:
		JToolBar toolBar = new JToolBar("Tools"); 
		toolBar.setFloatable(true);
		toolBar.add(new JButton(createDocumentAction));
		toolBar.add(new JButton(loadDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(pasteAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(statisticsAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(closeDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(exitAction));
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
		model.createNewDocument();
	}
	
	/**
	 * Metoda onemogućava akcije koje se nalaze u listama <code>actions</code> i <code>actionsStatic</code>.
	 */
	public void disableAll() {
		actions.forEach(a -> a.setEnabled(false));
		actionsStatic.forEach(a -> a.setEnabled(false));
	}
	
	/**
	 * Metoda omogućava akcije koje se nalaze u listi <code>actionsStatic</code>.
	 */
	public void enableStatic() {
		actionsStatic.forEach(a -> a.setEnabled(true));
	}
	
	/**
	 * Metoda u ovisnosti o predanoj zastavici <code>flag</code> mijenja dostupnost svih akcija iz liste <code>actions</code> .
	 * 
	 * @param flag boolean vrijednost koja određuje treba li omogućiti akcije ili ne
	 */
	public void switchButtons(boolean flag) {
		actions.forEach(a -> a.setEnabled(flag));
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}
}
