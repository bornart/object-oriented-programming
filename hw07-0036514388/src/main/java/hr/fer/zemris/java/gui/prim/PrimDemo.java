package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program koji inkrementalno generira prim brojeve i prikazuje ih u dvije liste.
 * 
 * @author borna
 *
 */
public class PrimDemo extends JFrame{
	
	/**
	 * Pretpostavljeni <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prim brojevi");
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
	}
	
	/**
	 * Inicijalizacija grafičkog korisničkog sučelja.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		list1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		list2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//list2.setSelectionModel(list1.getSelectionModel()); -> klikom na 1 element neke liste označava se isti taj element i u drugoj listi!
		
		JPanel panel = new JPanel(new GridLayout(1, 0));
		panel.add(new JScrollPane(list1));
		panel.add(new JScrollPane(list2));
		cp.add(panel, BorderLayout.CENTER);
		
		JButton btn = new JButton("sljedeći");
		cp.add(btn, BorderLayout.PAGE_END);
		btn.addActionListener(e -> {
			model.next();
		});
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new PrimDemo().setVisible(true);
		});
	}
}
