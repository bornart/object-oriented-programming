package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Razred <code>ComplexPolynomial</code> modelira polinom n-tog stupnja (red!) nad kompleksim brojevima.
 * Radi se o polinomu f(z) oblika zn*z^(n) + zn-1*z^(n-1) +... + z2*z^2 + z1*z + z0, gdje su z0 do zn koeficijenti koji pišu uz odgovarajuće potencije od z.
 *  
 * @author borna
 *
 */
public class ComplexPolynomial {
	
	private List<Complex> factors;
	
	/**
	 * Konstruktor kojem se predaje varijabilan broj parametara koji predstavljaju koeficijente koji se pišu uz odgovarajuće potencije polinoma.
	 * 
	 * @param factors koeficijenti koji se pišu uz odgovarajuće potencije polinoma
	 */
	public ComplexPolynomial(Complex ...factors) {
		this.factors = new ArrayList<>();
		
		if (factors.length == 0) {
			this.factors.add(Complex.ZERO);
		} else {
			this.factors.addAll(Arrays.asList(factors));
		}
	}
	
	/**
	 * Metoda vraća stupanj polinoma.
	 * 
	 * @return stupanj polinoma
	 */
	public short order() {
		return (short) (factors.size()-1);
	}
	
	/**
	 * Getter. Metoda vraća listu koeficijenata koji se pišu uz odgovarajuće potencije polinoma.
	 * 
	 * @return lista kompleksnih brojeva koji predstavljaju koeficijente uz odgovarajuće potencije polinoma
	 */
	public List<Complex> getFactors() {
		return factors;
	}

	/**
	 * Metoda izračunava i vraća kao rezultat umnožak dvaju kompleksnih polinoma.
	 * 
	 * @param p polinom n-tog stupnja koji se množi s trenutnim polinomom 
	 * @return novi objekt tipa <code>ComplexPolynomial</code> koji je nastao kao rezultat umnoška dvaju kompleksnih polinoma
	 * @throws IllegalArgumentException ako se umjesto kompleksnog polinoma kao parametar preda <code>null</code> vrijednost
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		if (p == null) throw new IllegalArgumentException("Predani kompleksni polinom ne smije biti null-referenca!");
		
		Complex[] arr = new Complex[this.order() + p.order() + 1];
		Arrays.fill(arr, Complex.ZERO);
		
		for (int i = 0; i < factors.size(); i++) {
			for (int j = 0; j < p.getFactors().size(); j++) {
				int potency = i + j;
				Complex num = factors.get(i).multiply(p.getFactors().get(j));
				
				if (arr[potency].equals(Complex.ZERO)) {
					arr[potency] = num;
				} else {
					Complex prev = arr[potency];
					arr[potency] = prev.add(num);
				}
			}
		}
		
		return new ComplexPolynomial(arr);
	}
	
	/**
	 * Metoda računa i vraća prvu derivaciju polinoma.
	 * 
	 * @return prva derivacija polinoma
	 */
	public ComplexPolynomial derive() {
		List<Complex> derFactors = new ArrayList<>();
		
		for (int i = 1; i < factors.size(); i++) {
			Complex num = factors.get(i);
			derFactors.add(new Complex(num.getRe() * i, num.getIm() * i));
		}
		
		return new ComplexPolynomial(derFactors.toArray(new Complex[derFactors.size()]));
	}
	
	/**
	 * Metoda kao parametar prima neki kompleksni broj i zatim računa vrijednost polinoma u toj točki.
	 * 
	 * @param z kompleksni broj oblika: z = x + yi
	 * @return vrijednost polinoma u točki z
	 * @throws IllegalArgumentException ako se umjesto kompleksnog broja kao parametar preda <code>null</code> vrijednost
	 */
	public Complex apply(Complex z) {
		if (z == null) throw new IllegalArgumentException("Predani kompleksni broj ne smije biti null-referenca!");
		
		Complex value = factors.get(0);
		for (int i = 1; i < factors.size(); i++) {
			value = value.add(factors.get(i).multiply(z.power(i)));
		}
		
		return value;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = factors.size() - 1; i > 0; i--) {
			sb.append("(" + factors.get(i) + ")*z^" + i + "+");
		}
		sb.append("(" + factors.get(0) + ")");
		
		return sb.toString();
	}
}
