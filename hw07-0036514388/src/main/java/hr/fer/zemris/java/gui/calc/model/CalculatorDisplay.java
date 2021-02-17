package hr.fer.zemris.java.gui.calc.model;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Razred predstavlja implementaciju ekrana jednostavnog kalkulatora.
 * 
 * @author borna
 *
 */
public class CalculatorDisplay extends JLabel implements CalcValueListener{

	/**
	 * Pretpostavljeni <code>serial version UID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor. Oblikuje izgled ekrana jednostavnog kalkulatora.
	 */
	public CalculatorDisplay() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		setBackground(Color.YELLOW);
		setOpaque(true);
		setHorizontalAlignment(SwingConstants.RIGHT);
		setFont(getFont().deriveFont(25f));
	}

	@Override
	public void valueChanged(CalcModel model) {
		setText(model.toString());
		/*
		SwingUtilities.invokeLater(() -> {
			setText(model.toString());
		});
		*/
	}

}
