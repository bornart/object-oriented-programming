package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Razred <code>ComplexRootedPolynomial</code> modelira polinom nad kompleksim brojevima.
 * Radi se o polinomu f(z) oblika z0*(z-z1)*(z-z2)*...*(z-zn), gdje su z1 do zn njegove nultočke, a z0 konstanta.
 * 
 * @author borna
 *
 */
public class ComplexRootedPolynomial {
	
	private Complex constant; //z0
	private List<Complex> roots; //nultočke polinoma! z1,...,zn
	
	/**
	 * Konstruktor koji kao parametre prima konstantu (z0) i varijabilan broj nultočki (z1,...,zn) polinoma.
	 * 
	 * @param constant konstanta polinoma
	 * @param roots nultoče polinoma
	 * @throws IllegalArgumentException ako se umjesto konstante z0 preda <code>null</code> vrijednost
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		if (constant == null) throw new IllegalArgumentException("Konstanta ne smije biti null-referenca!");
		
		this.constant = constant;
		this.roots = new ArrayList<>(Arrays.asList(roots));
	}
	
	/**
	 * Getter. Metoda vraća konstantu polinoma.
	 * 
	 * @return kompleksni broj koji predstavlja konstantu polinoma z0
	 */
	public Complex getConstant() {
		return constant;
	}
	
	/**
	 * Getter. Metoda vraća listu nul-točki polinoma.
	 * 
	 * @return lista kompleksnih brojeva koji predstavljaju nul-točke polinoma
	 */
	public List<Complex> getRoots() {
		return roots;
	}

	/**
	 * Metoda apply prima neki konkretan kompleksni broj z i računa koju vrijednost ima polinom u toj točki.
	 *  
	 * @param z kompleksni broj oblika: z = x + yi
	 * @return vrijednost polinoma u točki z
	 * @throws IllegalArgumentException ako se umjesto kompleksnog broja kao parametar preda <code>null</code> vrijednost
	 */
	public Complex apply(Complex z) {
		if (z == null) throw new IllegalArgumentException("Predani kompleksni broj ne smije biti null-referenca!");
		
		//f(z) = z0*(z-z1)*(z-z2)*...*(z-zn)
		Complex value = constant;
		for (int i = 0; i < roots.size(); i++) {
			value = value.multiply(z.sub(roots.get(i))); 
		}
		
		return value;
	}
	
	//PUNO jednostavniji pristup rješavanju metode toComplexPolynom...
//	public ComplexPolynomial toComplexPolynom() {
//		ComplexPolynomial polynomial = new ComplexPolynomial(roots.get(0).negate(), Complex.ONE);
//		
//		for (int i = 1; i < roots.size(); i++) {
//			ComplexPolynomial c = new ComplexPolynomial(roots.get(i).negate(), Complex.ONE);
//			polynomial = polynomial.multiply(c);
//		}
//		ComplexPolynomial cons = new ComplexPolynomial(constant);
//		polynomial = polynomial.multiply(cons);
//		return polynomial;
//	}
	
	/**
	 * Metoda pretvara objekt tipa <code>ComplexRootedPolynomial</code> u odgovarajući objekt tipa <code>ComplexPolynomial</code>.
	 * 
	 * @return objekt tipa <code>ComplexPolynomial</code>
	 */
	public ComplexPolynomial toComplexPolynom() {
		int size = roots.size(); 
		Complex[] arr = new Complex[size+1];
		
		List<Complex> superSet = new ArrayList<>();
		superSet.addAll(roots);
		
		int mult = size;
		for (int i = 0; i < size; i++) {
			List<ArrayList<Complex>> res = new ArrayList<>();
			getSubsets(superSet, mult, 0, new ArrayList<Complex>(), res, new HashSet<>());
			
			Complex result = Complex.ZERO;
			for (List<Complex> list : res) {
				
				Complex r = list.get(0);
				for (int j = 1; j < list.size(); j++) {
					r = r.multiply(list.get(j));
				}
				result = result.add(r);
			}
			
			if (size % 2 != 0) { 
				if (i % 2 == 0) result = result.negate();
			} else {
				if (i % 2 != 0) result = result.negate();
			}
			
			result = constant.multiply(result); 
			arr[i] = result;
			mult--;
		}
		arr[arr.length-1] = constant;
		return new ComplexPolynomial(arr);
	}
	
	/**
	 * Rekurzivna metoda za izračunavanje svih podskupova sa <code>mult</code> elemenata.
	 * 
	 * @param superSet nadskup (skup svih kompleksnih nultočaka)
	 * @param mult broj elemenata podskupa
	 * @param index pozicija unutar <code>superSet</code>
	 * @param subset podskup veličine <code>mult</code>
	 * @param solution lista svih formiranih podskupova
	 * @param hs set koji osigurava da se u svakom podksupu svaki element može pojaviti najviše jedan put
	 */
	private void getSubsets(List<Complex> superSet, int mult, int index, ArrayList<Complex> subset, List<ArrayList<Complex>> solution, Set<Integer> hs) {
		if (subset.size() == mult) {
			solution.add(new ArrayList<>(subset));
			hs.clear();
	        return;
		}
		
		if (index == superSet.size()) {
			hs.clear();
			return;
		}
		Complex z = superSet.get(index);
		
		if (hs.add(index)) {
			subset.add(z);
		}
		
		getSubsets(superSet, mult, index+1, subset, solution, hs);
		subset.remove(z);
		getSubsets(superSet, mult, index+1, subset, solution, hs);
	}
	
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("(" + constant + ")*");
		
		for (int i = 0; i < roots.size(); i++) {
			sb.append("(z-(" + roots.get(i) + "))");
			if (i != roots.size()-1) {
				sb.append("*");
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * Metoda u listi nul-točaka traži onu koja je najbliža predanom kompleksnom broju uz nužno zadovoljen uvjet
	 * da je udaljenost između tih kompleksnih brojeva u kompleksnoj ravnini manja od zadanog praga <code>treshold</code>.
	 * 
	 * @param z kompleksni broj oblika: z = x + yi
	 * @param treshold prag koji predstavlja maksimalnu zadovoljivu udaljenost između dva kompleksna broja 
	 * @return index najbliže nul-točke ili -1 ako ne postoji nul-točka koja je od kompleksnog broja <code>z</code> udaljena manje od zadane gornje granice <code>treshold</code>
	 * @throws IllegalArgumentException ako se umjesto kompleksnog broja kao parametar preda <code>null</code> vrijednost 
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		if (z == null) throw new IllegalArgumentException("Predani kompleksni broj ne smije biti null-referenca!");
		
		double minDistance = treshold;
		int index = -1;
		
		for (int i = 0; i < roots.size(); i++) {
			Complex c = roots.get(i);
			
			double dist = z.sub(c).module();
			
			if (dist < minDistance) {
				minDistance = dist;
				index = i;
			}
		}
		return index;
	}
}
