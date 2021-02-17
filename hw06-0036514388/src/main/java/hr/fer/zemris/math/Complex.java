package hr.fer.zemris.math;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred <code>Complex</code> predstavlja model kompleksnog broja.
 * 
 * @author borna
 *
 */
public class Complex {
	
	private double re;
	private double im;
	
	/**
	 * Kompleksni broj čiji su realni i imaginarni dio jednaki 0 (<code>z = 0</code>). 
	 */
	public static final Complex ZERO = new Complex(0,0);
	/**
	 * Kompleksni broj oblika: <code>z = 1</code>. 
	 */
	public static final Complex ONE = new Complex(1,0);
	/**
	 * Kompleksni broj oblika: <code>z = -1</code>. 
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	/**
	 * Kompleksni broj oblika: <code>z = i</code>. 
	 */
	public static final Complex IM = new Complex(0,1);
	/**
	 * Kompleksni broj oblika: <code>z = -i</code>. 
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Konstruktor koji ne prima niti jedan parametar.
	 * Stvara novi kompleksni broj čiji su realni i imaginarni dio jednaki 0.
	 */
	public Complex() {
		this(0, 0);
	}
	
	/**
	 * Konstrukotr koji stvara novi kompleksni broj čije su komponente x i y.
	 * 
	 * @param real realni dio kompleksnog broja
	 * @param imaginary imaginarni dio kompleksnog broja
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Metoda vraća realni dio kompleksnog broja.
	 * 
	 * @return realni dio kompleksnog broja
	 */
	public double getRe() {
		return re;
	}
	
	/**
	 * Metoda vraća imaginarni dio kompleksnog broja.
	 * 
	 * @return imaginarni dio kompleksnog broja
	 */
	public double getIm() {
		return im;
	}
	
	/**
	 * Metoda računa modul kompleksnog broja kao udaljenost kompleksnog broja od središta jedinične kružnice.
	 * 
	 * @return modul kompleksnog broja
	 */
	public double module() {
		return sqrt(re*re + im*im);
	}
	
	/**
	 * Metoda računa umnožak trenutnog kompleksnog broja i predanog kompleksnog broja i vraća novi kompleksni broj kao rezultat.
	 * 
	 * @param c drugi kompleksni broj koji se množi s trenutnim
	 * @return novi kompleksni broj koji je jednak umnošku trenutnog i predanog kompleksnog broja
	 * @throws NullPointerException ako je predani kompleksni broj c jednak <code>null</code>
	 */
	public Complex multiply(Complex c) {
		if (c == null) throw new NullPointerException("Predana je null-referenca, a ne kompleksni broj!");
		
		return new Complex(this.re * c.re - this.im * c.im, this.re * c.im + c.re * this.im);
	}
	
	/**
	 * Metoda računa rezultat dijeljenja trenutnog sa predanim kompleksnim brojem i vraća novi kompleksni broj kao rezultat.
	 * 
	 * @param c drugi kompleksni broj kojim se dijeli trenutni
	 * @return novi kompleksni broj koji je jednak količniku trenutnog i predanog kompleksnog broja
	 * @throws NullPointerException ako je predani kompleksni broj c jednak <code>null</code>
	 */
	public Complex divide(Complex c) {
		if (c == null) throw new NullPointerException("Predana je null-referenca, a ne kompleksni broj!");
		
		double x = (this.re * c.re + this.im * c.im) / (c.re * c.re + c.im * c.im);
		double y = (c.re * this.im - c.im * this.re) / (c.re * c.re + c.im * c.im); 
		
		return new Complex(x, y);
	}
	
	/**
	 * Metoda računa sumu trenutnog i predanog kompleksnog broja i vraća novi kompleksni broj kao rezultat.
	 * 
	 * @param c kompleksni broj koji se zbraja s trenutnim
	 * @return novi kompleksni broj koji je jednak sumi trenutnog i predanog kompleksnog broja
	 * @throws NullPointerException ako je predani kompleksni broj c jednak <code>null</code>
	 */
	public Complex add(Complex c) {
		if (c == null) throw new NullPointerException("Predana je null-referenca, a ne kompleksni broj!");
		
		return new Complex(this.re + c.re, this.im + c.im);
	}
	
	/**
	 * Metoda računa razliku trenutnog i predanog kompleksnog broja i vraća novi kompleksni broj kao rezultat.
	 * 
	 * @param c kompleksni broj koji se oduzima od trenutnog
	 * @return novi kompleksni broj koji je jednak razlici trenutnog i predanog kompleksnog broja
	 * @throws NullPointerException ako je predani kompleksni broj c jednak <code>null</code>
	 */
	public Complex sub(Complex c) {
		if (c == null) throw new NullPointerException("Predana je null-referenca, a ne kompleksni broj!");
		
		return new Complex(this.re - c.re, this.im - c.im);
	}
	
	/**
	 * Metoda vraća novi kompleksni broj izračunat kao negativni trenutni kompleksni broj.
	 * 
	 * @return novi kompleksni broj izračunat kao negativni trenutni kompleksni broj
	 */
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}
	
	/**
	 * Metoda računa zadanu potenciju kompleksnog broja i kao rezultat vraća novi kompleksan broj.
	 * 
	 * @param n potencija kompleksnog broja
	 * @return novi kompleksni broj koji je rezultat n-te potencije
	 * @throws IllegalArgumentException ako je vrijednost predanog parametra <code>n</code> manja od 0
	 */
	public Complex power(int n) {
		if (n < 0) throw new IllegalArgumentException("Vrijednost argumenta n mora biti >= 0.");
		
		double r = this.module();
		double x = pow(r, (float)n) * cos(n * this.angle());
		double y = pow(r, (float)n) * sin(n * this.angle());
		
		return new Complex(x , y);
	}

	/**
	 * Metoda računa korijene kompleksnog broja (od drugog do n-tog korijena) i kao rezultat vraća listu kompleksnih brojeva sa ukupno n-1 elemenata.
	 * 
	 * @param n n-ti korijen kompleksnog broja (ujedno i posljednji korijen koji je potrebno izračunati)
	 * @return lista novostvorenih kompleksnih brojeva koji su rezultat izračuna 2,...,n-tog korijena trenutnog kompleksnog broja
	 * @throws IllegalArgumentException ako je vrijednost predanog parametra <code>n</code> manja ili jednaka 0
	 */
	public List<Complex> root(int n) {
		if (n <= 0) throw new IllegalArgumentException("Vrijednost argumenta n mora biti > 0.");
		
		List<Complex> list = new ArrayList<>(); //k€[0, n-1]
		double r = this.module();
		
		for (int k = 0; k < n; k++) {
			double x = pow(r, 1.0/n) * cos((this.angle() + 2 * PI * k)/n);
			double y = pow(r, 1.0/n) * sin((this.angle() + 2 * PI * k)/n);
			list.add(new Complex(x, y));
		}
		
		return list;
	}
	
	/**
	 * Privatna metoda koja računa kut koji kompleksni broj zatvara sa pozitivnim dijelom x-osi.
	 * 
	 * @return vrijednost kuta u radijanima
	 */
	private double angle() {
		//treba paziti na kvadrante! Dodatno, racuna se kut u RADIJANIMA!
		double angle =  Math.atan2(im, re);
		return angle > 0 ? angle : angle + 2 * PI;
	}
	
	@Override
	public String toString() {
		return (re != 0 ? re : "0.0") + (im != 0 ? (im > 0 ? "+i" + im : "-i" + -im) : "+i0.0");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(im);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(re);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(im) != Double.doubleToLongBits(other.im))
			return false;
		if (Double.doubleToLongBits(re) != Double.doubleToLongBits(other.re))
			return false;
		return true;
	}
	
}
