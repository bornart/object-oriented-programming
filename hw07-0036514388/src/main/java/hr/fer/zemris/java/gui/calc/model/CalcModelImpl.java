package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

/**
 * Razred predstavlja implementaciju jednog jednostavnog kalkulatora.
 * 
 * @author borna
 *
 */
public class CalcModelImpl implements CalcModel {
	
	/*
	 * ------NE ZABORAVI!------
	 * 
	 * Poziv bilo koje metode kojom korisnik mijenja upisani broj
	 * (promjena predznaka, unos znamenke, unos točke) automatski uklanja zamrznutu vrijednost.
	 */
	
	private List<CalcValueListener> listeners = new ArrayList<>();
	
	private boolean editable;
	private boolean positive;
	private String number;
	private double numValue;
	private String frozenValue;
	private double activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private boolean isActiveSet;
	private Stack<Double> stack;
	
	/**
	 * Konstruktor. Postavlja inicijalne vrijednosti.
	 */
	public CalcModelImpl() {
		frozenValue = null;
		positive = true;
		number = "";
		numValue = 0;
		editable = true; 
		pendingOperation = null;
		isActiveSet = false;
		stack = new Stack<>();
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		return numValue;
	}
	
	@Override
	public void setValue(double value) {
		number = Double.toString(value);
		numValue = value;
		editable = false;
		
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		positive = true;
		number = "";
		numValue = 0;
		editable = true; 
		
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}

	@Override
	public void clearAll() {
		positive = true;
		number = "";
		numValue = 0;
		isActiveSet = false;
		pendingOperation = null;
		editable = true; 
		
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!editable) throw new CalculatorInputException("Kalkulator nije editabilan!");
		
		positive = !positive;
		numValue *= -1;
		
		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!editable) throw new CalculatorInputException("Kalkulator nije editabilan!");
		if (number.isEmpty()) throw new CalculatorInputException("Još nije unesena niti jedna znamenka broja.");
		if (number.contains(".")) throw new CalculatorInputException("Decimalna točka već postoji u broju.");
		
		number += ".";

		for (CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
		freezeValue(null);
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!editable) throw new CalculatorInputException("Nije moguće dodavanje nove znamenke!");
		if (digit < 0 || digit > 9) throw new IllegalArgumentException("Moguće je dodati isključivo znamenku iz intervala [0-9]!");
		if (!Double.isFinite(Double.parseDouble(number + digit))) throw new CalculatorInputException("Nemoguće isparsirati u konačni decimalni broj!");
		
		if (!(digit == 0 && number.equals("0"))) {
			if (number.equals("0")) {
				number = Integer.toString(digit);
			} else {
				number += digit;
			}
			
			numValue = Double.parseDouble(number);
			if (!positive) numValue *= -1;
			
			for (CalcValueListener l : listeners) {
				l.valueChanged(this);
			}
			
			freezeValue(null);
		}
	}

	@Override
	public boolean isActiveOperandSet() {
		return isActiveSet;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveSet) throw new IllegalStateException("Aktivni operand nije postavljen");
		
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		isActiveSet = true;

	}

	@Override
	public void clearActiveOperand() {
		isActiveSet = false;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}

	@Override
	public void freezeValue(String value) {
		frozenValue = value;
	}

	@Override
	public boolean hasFrozenValue() {
		return frozenValue != null;
	}

	@Override
	public String toString() {
		if (frozenValue != null) {
			String s = frozenValue;
			if (frozenValue.equals("err")) {
				frozenValue = null;
			}
			return s;
		} else {
			if (number.isEmpty()) {
				return positive ? "0" : "-0";
			} else {
				return positive ? number : "-" + number;
			}
		}
	}
	
	/**
	 * Metoda dodaje trenutnu vrijednost na vrh stoga.
	 */
	public void push() {
		stack.push(numValue);
	}
	
	/**
	 * Metoda sa vrha stoga skida jednu vrijednost.
	 */
	public void pop() {
		if (stack.isEmpty()) throw new EmptyStackException();
		numValue = stack.pop();
	}
}
