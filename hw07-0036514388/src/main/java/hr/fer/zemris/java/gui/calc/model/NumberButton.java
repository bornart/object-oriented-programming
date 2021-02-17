package hr.fer.zemris.java.gui.calc.model;

import java.awt.Color;

import javax.swing.JButton;

/**
 * Razred koji predstavlja numeričke gumbe [0-9] na jednostavnom kalkulatoru.
 * 
 * @author borna
 *
 */
public class NumberButton extends JButton {

	/**
	 * Pretpostavljeni <code>serial version UID</code>.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor. Konstruira gumb i pridjeljuje mu <code>Action Listener</code>.
	 * 
	 * @param text tekst gumba
	 * @param model model kalkulatora
	 * @param display ekran kalkulatora
	 */
	public NumberButton(String text, CalcModelImpl model, CalculatorDisplay display) {
		setBackground(Color.CYAN);
		setOpaque(true);
		setFont(getFont().deriveFont(25f));
		setText(text);
		
		addActionListener(e -> {
			try {
				if (model.isActiveOperandSet()) model.freezeValue(null); //dodano!
				model.insertDigit(Integer.parseInt(text));
			} catch (Exception ex) {
				model.freezeValue("err");
				System.err.println(ex.getMessage());
				model.clearAll();
			}
		});
	}
}
