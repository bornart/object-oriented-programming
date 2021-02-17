package hr.fer.zemris.java.gui.calc.model;

import java.awt.Color;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

/**
 * Razred koji predstavlja binarne operacije (+,-,*,/) na jednostavnom kalkulatoru.
 * 
 * @author borna
 *
 */
public class BinaryButton extends JButton {
	
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
	public BinaryButton(String text, CalcModelImpl model, CalculatorDisplay display, DoubleBinaryOperator op) {
		setBackground(Color.CYAN);
		setOpaque(true);
		setText(text);
		
		setListener(model, display, op);
	}
	
	/**
	 * Metoda postavlja <code>actionListener</code> za trenutni gumb.
	 * 
	 * @param model model kalkulatora
	 * @param display ekran kalkulatora
	 */
	private void setListener(CalcModelImpl model, CalculatorDisplay display, DoubleBinaryOperator op) {
		addActionListener(e -> { 
			try {
				if (model.hasFrozenValue()) throw new CalculatorInputException("Neispravna naredba!");
				
				if (model.isActiveOperandSet()) {
					double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
					model.setValue(result);
					model.freezeValue(Double.toString(result));
					model.setActiveOperand(result);
				} 
					
				model.freezeValue(Double.toString(model.getValue()));
				model.setActiveOperand(model.getValue());
				model.setPendingBinaryOperation(op);
				
				model.clear();
			} catch (Exception ex) {
				model.freezeValue("err");
				System.err.println(ex.getMessage());
				model.clearAll();
			}
		});
	}
}
