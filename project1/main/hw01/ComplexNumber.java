package hr.fer.oprpp1.hw01;

import static java.lang.Math.*;
import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 * Razred predstavlja model nepromijenjivog kompleksnog broja.
 * 
 * @author borna
 *
 */
public class ComplexNumber {
	
	private double real;
	private double imaginary;
	
	/**
	 * Stvara novi kompleksni broj cije su komponente x i y.
	 * 
	 * @param real realni dio kompleksnog broja
	 * @param imaginary imaginarni dio kompleksnog broja
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Metoda vraca realni dio kompleksnog broja.
	 * 
	 * @return realni dio kompleksnog broja
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Metoda vraca imaginarni dio kompleksnog broja.
	 * 
	 * @return imaginarni dio kompleksnog broja
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Racuna modul kao udaljenost kompleksnog broja od sredista jedinicne kruznice.
	 * 
	 * @return modul kompleksnog broja
	 */
	public double getMagnitude() {
		return sqrt(real*real + imaginary*imaginary);
	}
	
	/**
	 * Racuna kut koji kompleksni broj zatvara sa pozitivnim dijelom x-osi.
	 * 
	 * @return vrijednost kuta u radijanima
	 */
	public double getAngle() {
		//treba paziti na kvadrante! Dodatno, racuna se kut u RADIJANIMA!
		double angle = atan(imaginary/real);
		
		if ((real < 0 && imaginary > 0) || (real < 0 && imaginary < 0)) angle += PI;
		if (real > 0 && imaginary < 0) angle += 2 * PI;

		return angle;
	}
	
	/**
	 * Racuna sumu trenutnog kompleksnog broja i predanog kompleksnog broja: this+other i vraca rezultat kao novi kompleksni broj.
	 * 
	 * @param c drugi kompleksni broj koji nadodajemo
	 * @return novi kompleksni broj koji je jednak sumi trenutnog i drugog
	 * @throws NullPointerException ako je other <code>null</code>
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * Racuna razliku trenutnog kompleksnog broja i predanog kompleksnog broja: this-other i vraca rezultat kao novi kompleksni broj.
	 * 
	 * @param c drugi kompleksni broj koji oduzimamo
	 * @return novi kompleksni broj koji je jednak razlici trenutnog i drugog
	 * @throws NullPointerException ako je other <code>null</code>
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	/**
	 * Racuna umnozak trenutnog kompleksnog broja i predanog kompleksnog broja: this*other i vraca rezultat kao novi kompleksni broj.
	 * 
	 * @param c drugi kompleksni broj koji mnozimo sa trenutnim
	 * @return novi kompleksni broj koji je jednak umnosku trenutnog i drugog
	 * @throws NullPointerException ako je other <code>null</code>
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(this.real * c.real - this.imaginary * c.imaginary, this.real * c.imaginary + c.real * this.imaginary);
	}
	
	/**
	 * Racuna rezultat dijeljenja trenutnog kompleksnog broja sa predanim kompleksnim brojen: this/other i vraca rezultat kao novi kompleksni broj.
	 * 
	 * @param c drugi kompleksni broj kojim dijelimo 
	 * @return novi kompleksni broj koji je jednak kolicniku trenutnog i drugog
	 * @throws NullPointerException ako je other <code>null</code>
	 */
	public ComplexNumber div(ComplexNumber c) {
		double x = (this.real * c.real + this.imaginary * c.imaginary) / (c.real * c.real + c.imaginary * c.imaginary);
		double y = (c.real * this.imaginary - c.imaginary * this.real) / (c.real * c.real + c.imaginary * c.imaginary); 
		
		return new ComplexNumber(x, y);
	}
	
	/**
	 * Racuna zadanu potenciju kompleksnog broja i kao rezultat vraca novi kompleksan broj.
	 * 
	 * @param n potencija kompleksnog broja
	 * @return novi kompleksni broj koji je rezultat n-te potencije
	 */
	public ComplexNumber power(int n) {
		if (n < 0) throw new IllegalArgumentException("Vrijednost argumenta n mora biti >= 0.");
		
		double r = this.getMagnitude();
		double x = pow(r, (float)n) * cos(n * this.getAngle());
		double y = pow(r, (float)n) * sin(n * this.getAngle());
		
		return new ComplexNumber(x , y);
	}
	
	/**
	 * Racuna korijene kompleksnog broja (od drugog do n- tog korijena) i kao rezultat vraca polje sa n-1 elemenata.
	 * 
	 * @param n n-ti korijen kompleksnog broja (ujedno i posljednji izracunati korijen)
	 * @return polje novostvorenih kompleksnih brojeva koji su rezultat izracuna 2,...,n-tog korijena trenutnog kompleksnog broja
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) throw new IllegalArgumentException("Vrijednost argumenta n mora biti > 0.");
		
		ComplexNumber[] arr = new ComplexNumber[n]; //k€[0, n-1]
		double r = this.getMagnitude();
		
		for (int k = 0; k < n; k++) {
			double x = pow(r, 1.0/n) * cos((this.getAngle() + 2 * PI * k)/n);
			double y = pow(r, 1.0/n) * sin((this.getAngle() + 2 * PI * k)/n);
			arr[k] = new ComplexNumber(x, y);
		}
		
		return arr;
	}
	
	/**
	 * Nadjacana metoda koja ispisuje trenutni kompleksni broj u formatu "x+yi".
	 * 
	 * @return ispis kompleksnog broja
	 */
	@Override
	public String toString() {
		return (real != 0 ? real : "") + (imaginary != 0 ? (imaginary > 0 ? "+" : "") + imaginary + "i" : "");
	}
	
	/**
	 * Stvara novi kompleksni broj ciji je imaginarni dio 0.
	 * 
	 * @param real realni dio kompleksnog broja
	 * @return novi kompleksni broj oblika (real, 0)
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Stvara novi kompleksni broj ciji je realni dio 0.
	 * 
	 * @param imaginary imaginarni dio kompleksnog broja
	 * @return novi kompleksni broj oblika (0, imaginary)
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Stvara novi kompleksni broj iz umnoska zadanog modula i kosinusa(realni dio)/sinusa(imaginarni dio) kuta kompleksnog broja.
	 * 
	 * @param magnitude modul kompleksnog broja
	 * @param angle kut kompleksnog broja iz intervala [0, 2PI]
	 * @return novi kompleksni broj izracunat iz modula i kuta kompleksnog broja
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
	}
	
	/**
	 * Metoda pretvara zadani string u kompleksni broj.
	 * 
	 * @param s tekstualni zapis kompleksnog broja
	 * @return novi kompleksni broj koji nastaje kao rezultat parsiranja ulaznog stringa
	 * @throws IllegalArgumentException ako je zadan neispravan predznak 
	 * @throws NumberFormatException ako nije zadan kompleksan broj ili je zadan u neispravnom formatu
	 */
	public static ComplexNumber parse(String s) {
		if (s.contains("+-") || s.contains("+-") || s.contains("--") || s.contains("++")) throw new IllegalArgumentException("Neispravan argument!");

		double x = 0d, y = 0d;
		Scanner sc = new Scanner(s);
		
		if (!s.contains("i")) {
			x = Double.parseDouble(s);
		} else if (s.equals("i")){
			y = 1.;
		} else if (s.equals("-i")) {
			y = -1.;
		} else {
			sc.findInLine("([+-]?\\d*\\.?\\d*)([+-]?\\d*\\.?\\d*)i");
			
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
			}
		
		}
		
		sc.close();
		return new ComplexNumber(x, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ComplexNumber))
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		if (Double.doubleToLongBits(imaginary) != Double.doubleToLongBits(other.imaginary))
			return false;
		if (Double.doubleToLongBits(real) != Double.doubleToLongBits(other.real))
			return false;
		return true;
	}
	
}
