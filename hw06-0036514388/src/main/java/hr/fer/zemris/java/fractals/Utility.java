package hr.fer.zemris.java.fractals;

import java.util.Scanner;
import java.util.regex.MatchResult;

import hr.fer.zemris.math.Complex;

/**
 * Utility je "shared utility class" koja sprječava duplikaciju koda prilikom parsiranja korisničkih podataka.
 * 
 * @author borna
 *
 */
public final class Utility {
	
	/**
	 * Privatni konstruktor koji onemogućava neželjeno instanciranje razreda <code>Utility</code>.
	 */
	private Utility() {
		
	}
	
	/**
	 * Metoda pretvara zadani string u kompleksni broj.
	 * 
	 * @param s tekstualni zapis kompleksnog broja
	 * @return novi kompleksni broj koji nastaje kao rezultat parsiranja ulaznog stringa
	 * @throws IllegalArgumentException ako je zadan neispravan predznak 
	 * @throws NumberFormatException ako nije zadan kompleksan broj ili je zadan u neispravnom formatu
	 */
	public static Complex parser(String s) {
		if (s.contains("+-") || s.contains("+-") || s.contains("--") || s.contains("++")) throw new IllegalArgumentException("Neispravan argument!");

		double x = 0d, y = 0d;
		
		if (!s.contains("i")) {
			x = Double.parseDouble(s);
		} else if (s.equals("i")){
			y = 1.;
		} else if (s.equals("-i")) {
			y = -1.;
		} else {
			String str = s.replaceAll("i", "");
			str = str.replaceAll(" ", "");
			Scanner sc = new Scanner(str);
			sc.findInLine("([+-]?\\d*\\.?\\d*)([+-]?\\d*\\.?\\d*)");
			
			MatchResult numbers = sc.match();
			if (numbers.group(2).equals("")) {
				y = Double.parseDouble(numbers.group(1));
			} else if (numbers.group(2).equals("-")) {
				x = Double.parseDouble(numbers.group(1));
				y = -1.0;
			} else if (numbers.group(2).equals("+")) {
				x = Double.parseDouble(numbers.group(1));
				y = 1.0;
			} else {
				x = Double.parseDouble(numbers.group(1));
				y = Double.parseDouble(numbers.group(2));
				if (x == -0) x = 0;
				if (y == -0) y = 0;
			}
			sc.close();
		}
		
		return new Complex(x, y);
	}
}
