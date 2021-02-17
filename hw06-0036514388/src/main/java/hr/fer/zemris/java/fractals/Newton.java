package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Razred implementira potporu za slijedni prikaz fraktala temeljenih na Newton-Raphson iteracijama.
 * 
 * @author borna
 *
 */
public class Newton {
	
	public static void main(String[] args) {
		System.out.println("Dobrodošli u Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Upišite najmanje dvije nul-točke, po jednu u retku. Upišite 'done' kada ste gotovi sa upisom nul-točki.");
		
		Scanner sc = new Scanner(System.in);
		int numOfRoots = 0;
		List<Complex> roots = new ArrayList<>();
		
		while(true) {
			System.out.printf("Root %d> ", numOfRoots+1);
			
			String s = sc.nextLine();
			
			if (s.equals("done")) break;
			numOfRoots++;
			roots.add(Utility.parser(s));
		}
		sc.close();
		
		ComplexRootedPolynomial polynom = new ComplexRootedPolynomial(Complex.ONE, roots.toArray(new Complex[0]));
		
		System.out.println("Slika fraktala će se uskoro prikazati. Hvala.");
		FractalViewer.show(new MojProducer(polynom));	
	}
	
	/**
	 * Razred reproducira fraktale nad nekim dijelom kompleksne ravnine.
	 * 
	 * @author borna
	 *
	 */
	public static class MojProducer implements IFractalProducer {
		private ComplexRootedPolynomial polynom;
		
		/**
		 * Konstruktor.
		 * 
		 * @param polynom polinom tipa <code>ComplexRootedPolynomial</code>
		 */
		public MojProducer(ComplexRootedPolynomial polynom) {
			this.polynom = polynom;
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			ComplexPolynomial polynomial = polynom.toComplexPolynom();
			ComplexPolynomial derived = polynomial.derive();
			
			int m = 16 * 16 * 16;
			int offset = 0;
			
			short[] data = new short[width * height];
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break; //je li gui postavio zastavicu na true->treba zaustaviti rad! Npr ako zatrazimo dva razlicita crtanja...
				for(int x = 0; x < width; x++) {
					
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					
					Complex zn = new Complex(cre, cim); 
					Complex znold = null;
					
					double module = 0;
					int iters = 0;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iters++;
					} while(iters < m && module > 0.001);
	
					int index = polynom.indexOfClosestRootFor(zn, 0.002);
					data[offset] = (short) (index+1);
					offset++;
				}
			}
			observer.acceptResult(data, (short) (polynomial.order()+1), requestNo);
		}
	}
}
