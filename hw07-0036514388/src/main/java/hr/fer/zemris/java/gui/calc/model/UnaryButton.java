package hr.fer.zemris.java.gui.calc.model;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;

/**
 * Razred koji predstavlja unarne operacije na jednostavnom kalkulatoru.
 * 
 * @author borna
 *
 */
public class UnaryButton extends JButton {
	
	/**
	 * Pretpostavljeni <code>serial version UID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	private String text;
	private Map<String, DoubleUnaryOperator> map;	
	
	/**
	 * Konstruktor. Konstruira gumb i pridjeljuje mu <code>Action Listener</code>.
	 * 
	 * @param text tekst gumba
	 * @param model model kalkulatora
	 * @param display ekran kalkulatora
	 * @param map mapa sa ključevima tipa String i vrijednostima tipa DoubleUnaryOperator
	 */
	public UnaryButton(String text, CalcModelImpl model, CalculatorDisplay display, HashMap<String, DoubleUnaryOperator> map) {
		this.text = text;
		this.map = new HashMap<>(map);
		
		setBackground(Color.CYAN);
		setOpaque(true);
		setText(text);
		
		setListener(model, display);
	}
	
	/**
	 * Metoda postavlja <code>actionListener</code> za trenutni gumb.
	 * 
	 * @param model model kalkulatora
	 * @param display ekran kalkulatora
	 */
	private void setListener(CalcModelImpl model, CalculatorDisplay display) {
		addActionListener(e -> {
			try {
				if (model.hasFrozenValue()) throw new CalculatorInputException("Neispravna naredba!");
				
				DoubleUnaryOperator op = map.get(text);
				double result = op.applyAsDouble(model.getValue());
				
				model.setValue(result);
			} catch (Exception ex) {
				model.freezeValue("err");
				System.err.println(ex.getMessage());
				model.clearAll();
			}

		});
	}
	
	/**
	 * Metoda mijenja tekstualni sadržaj gumba u ovisnosti o stanju <code>Inv</code> JCheckBox-a.
	 */
	public void changeCommand() {
		for (String key : map.keySet()) {
			if (!key.equals(text)) {
				text = key;
				setText(text);
				break;
			}
		}
	}
}
