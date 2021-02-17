package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.BinaryButton;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorDisplay;
import hr.fer.zemris.java.gui.calc.model.NumberButton;
import hr.fer.zemris.java.gui.calc.model.UnaryButton;
import hr.fer.zemris.java.gui.calc.model.XNButton;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Razred predstavlja jednostavni kalkulator.
 * 
 * @author borna
 *
 */
public class Calculator extends JFrame{
	
	private CalcModelImpl model = new CalcModelImpl();

	/**
	 * Pretpostavljeni <code>serial version UID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 */
	public Calculator() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		initGUI();
		pack();
	}
	
	/**
	 * Inicijalizacija grafičkog korisničkog sučelja.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(5));
		
		CalculatorDisplay display = new CalculatorDisplay();
		model.addCalcValueListener(display); 
		cp.add(display, new RCPosition(1,1));
		
		JButton btnEquals = b("=");
		cp.add(btnEquals, new RCPosition(1,6));
		btnEquals.addActionListener(e -> {
			try {
				double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
				model.setValue(result);
				model.clearActiveOperand();
				model.setPendingBinaryOperation(null);
				display.valueChanged(model);
			} catch(Exception ex) {
				handleException(ex);
			}
		});
		
		JButton clr = b("clr");
		cp.add(clr, new RCPosition(1,7));
		clr.addActionListener(e -> model.clear());
		
		HashMap<String, DoubleUnaryOperator> map = new HashMap<>();
		map.put("1/x", v -> 1 / v);
		cp.add(new UnaryButton("1/x", model, display, map), new RCPosition(2,1));
		
		List<UnaryButton> list = new ArrayList<>();
		
		map.clear();
		map.put("log", Math::log10);
		map.put("10^x", x -> Math.pow(10, x));
		UnaryButton log = new UnaryButton("log", model, display, map);
		list.add(log);
		cp.add(log, new RCPosition(3,1));
		
		map.clear();
		map.put("ln", Math::log);
		map.put("e^x", x -> Math.pow(Math.E, x));
		UnaryButton ln = new UnaryButton("ln", model, display, map);
		list.add(ln);
		cp.add(ln, new RCPosition(4,1));
		
		map.clear();
		map.put("sin", Math::sin);
		map.put("arcsin", Math::asin);
		UnaryButton sin = new UnaryButton("sin", model, display, map);
		list.add(sin);
		cp.add((sin), new RCPosition(2,2));
		
		map.clear();
		map.put("cos", Math::cos);
		map.put("arccos", Math::acos);
		UnaryButton cos = new UnaryButton("cos", model, display, map);
		list.add(cos);
		cp.add(cos, new RCPosition(3,2));
		
		map.clear();
		map.put("tan", Math::tan);
		map.put("arctan", Math::atan);
		UnaryButton tan = new UnaryButton("tan", model, display, map);
		list.add(tan);
		cp.add(tan, new RCPosition(4,2));
		
		map.clear();
		map.put("ctg", v -> 1/Math.tan(v));
		map.put("arcctg", v -> Math.PI / 2 - Math.atan(v));
		UnaryButton ctg = new UnaryButton("ctg", model, display, map);
		list.add(ctg);
		cp.add(ctg, new RCPosition(5,2));
		
		HashMap<String, DoubleBinaryOperator> m = new HashMap<>();
		m.put("x^n", (x, n) -> Math.pow(x, n));
		m.put("x^(1/n)", (x, n) -> Math.pow(x, 1/n));
		XNButton xn = new XNButton("x^n", model, display, m);
		cp.add(xn, new RCPosition(5,1));
		
		
		int k = 1;
		for (int i = 4; i >= 2; i--) {
			for (int j = 3; j <= 5; j++) {
				NumberButton btn = new NumberButton(Integer.toString(k), model, display);
				cp.add(btn, new RCPosition(i, j));
				k++;
			}
		}
		cp.add(new NumberButton("0", model, display), new RCPosition(5,3));
		
		JButton sign = b("+/-");
		cp.add(sign, new RCPosition(5,4));
		sign.addActionListener(e -> {
			try {
				model.swapSign();
			} catch (Exception ex) {
				handleException(ex);
			}
		});
		
		JButton dPoint = b(".");
		cp.add(dPoint, new RCPosition(5,5));
		dPoint.addActionListener(e -> {
			try {
				model.insertDecimalPoint();
			} catch (Exception ex) {
				handleException(ex);
			}
		});		
		
		cp.add(new BinaryButton("/", model, display, (a, b) -> a/b), new RCPosition(2,6));
		cp.add(new BinaryButton("*", model, display, (a, b) -> a*b), new RCPosition(3,6));
		cp.add(new BinaryButton("-", model, display, (a, b) -> a-b), new RCPosition(4,6));
		cp.add(new BinaryButton("+", model, display, Double::sum), new RCPosition(5,6));

		JButton reset = b("reset");
		cp.add(reset, new RCPosition(2,7));
		reset.addActionListener(e -> model.clearAll());
		
		JButton push = b("push");
		cp.add(push, new RCPosition(3,7));
		push.addActionListener(e -> model.push());
		
		JButton pop = b("pop");
		cp.add(pop, new RCPosition(4,7));
		pop.addActionListener(e -> {
			try {
				model.pop();
			} catch (Exception ex) {
				handleException(ex);
			}
		});
		
		JCheckBox cb = new JCheckBox("Inv");
		cp.add(cb, new RCPosition(5,7));
		cb.addActionListener(e -> {
			for (UnaryButton btn : list) {
				btn.changeCommand();
			}
			xn.changeCommand();
		});
	}
	
	/**
	 * Ispis iznimke.
	 * 
	 * @param ex iznimka koja se dogodila
	 */
	private void handleException(Exception ex) {
		System.err.println(ex.getMessage());
		model.freezeValue("err");
		model.clearAll();
	}
	
	/**
	 * Metoda oblikuje gumb: postavlja mu boju i tekst.
	 * 
	 * @param text tekst gumba
	 * @return novi objekt tipa <code>JButton</code>
	 */
	private JButton b(String text) {
		JButton b = new JButton(text);
		b.setBackground(Color.CYAN);
		b.setOpaque(true);
		return b;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
}
