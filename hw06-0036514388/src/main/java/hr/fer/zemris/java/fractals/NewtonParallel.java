package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Razred implementira potporu za paralelni (višedretveni) prikaz fraktala temeljenih na Newton-Raphson iteracijama.
 * 
 * @author borna
 *
 */
public class NewtonParallel {
	
	private static final int DEFAULT_WORKERS = Runtime.getRuntime().availableProcessors();
	private static final int DEFAULT_TRACKS = 4 * DEFAULT_WORKERS;
	
	public static void main(String[] args) {
		System.out.println("Dobrodošli u Newton-Raphson iteration-based fractal viewer.");
		
		int workers = DEFAULT_WORKERS;
		int tracks = DEFAULT_TRACKS;
		
		Scanner sc = new Scanner(System.in);
		
		//String s = sc.nextLine();
		//ovo je islo u if: !s.equals("")
		if (args.length != 0) {
			String s = String.join(" ", args); //ovo je dodano
			
			int[] wt = setParams(s, workers, tracks);
			
			if (wt[0] != workers) workers = wt[0];
			if (wt[1] != tracks) tracks = wt[1];
		}
		
		System.out.println("Upišite najmanje dvije nul-točke, po jednu u retku. Upišite 'done' kada ste gotovi sa upisom nul-točki.");
		
		int numOfRoots = 0;
		List<Complex> roots = new ArrayList<>();
		
		while(true) {
			System.out.printf("Root %d> ", numOfRoots+1);
			
			String str = sc.nextLine();
			
			if (str.equals("done")) break;
			numOfRoots++;
			roots.add(Utility.parser(str));
		}
		
		ComplexRootedPolynomial polynom = new ComplexRootedPolynomial(Complex.ONE, roots.toArray(new Complex[0]));
		
		System.out.println("Slika fraktala će se uskoro prikazati. Hvala.");
		sc.close();
		FractalViewer.show(new MojProducer(polynom, workers, tracks));	
	}
	
	/**
	 * Privatna metoda koja na temelju korisnikovog unosa postavlja broj dretvi i traka.
	 * 
	 * @param s ulazni string
	 * @param workers pretpostavljeni broj dretvi
	 * @param tracks pretpostavljeni broj traka
	 * @return vrijednosti <code>workers</code> i <code>tracks</code>
	 */
	private static int[] setParams(String s, int workers, int tracks) {
		String[] elems = s.split(" ");
		
		int i = 0;
		while (i < elems.length) {
			if(elems[i].startsWith("--workers=") || elems[i].equals("-w")) {
				if (workers != DEFAULT_WORKERS) {
					System.out.println("Zabranjeno je postavljati vrijednost istog atributa više puta.");
					System.exit(0);
				}
				
				int num;
				if (elems[i].equals("-w")) {
					num = Integer.parseInt(elems[++i]);
				} else {
					num = Integer.parseInt(elems[i].replaceAll("\\D+", ""));
				}
				workers = num;
			} else if (elems[i].startsWith("--tracks=") || elems[i].equals("-t")) {
				if (tracks != DEFAULT_TRACKS) {
					System.out.println("Zabranjeno je postavljati vrijednost istog atributa više puta.");
					System.exit(0);
				}
				
				int num;
				if (elems[i].equals("-t")) {
					num = Integer.parseInt(elems[++i]);
				} else {
					num = Integer.parseInt(elems[i].replaceAll("\\D+", ""));
				}
				
				if (num < 1) {
					System.out.println("Broj traka ne smije biti manji od 1!");
					System.exit(0);
				}
				tracks = num;
			} else {
				System.out.println("Unesena je neispravna naredba. Molimo pokrenite program ispočetka.");
				System.exit(0);
			}
			i++;
		}
		return new int[] {workers, tracks};
	}
	
	
	/**
	 * Razred predstavlja posao koji svaka pojedina dretva mora obaviti.
	 * 
	 * @author borna
	 *
	 */
	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna(); 
		ComplexRootedPolynomial polynom;
		
		private PosaoIzracuna() {
		}
		
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial polynom) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.polynom = polynom;
		}
		
		@Override
		public void run() {
			
			ComplexPolynomial polynomial = polynom.toComplexPolynom();
			ComplexPolynomial derived = polynomial.derive();
			
			int offset = yMin * width;

			for(int y = yMin; y <= yMax; y++) {
				if(cancel.get()) break; 
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
		}
	}
	
	/**
	 * Razred reproducira fraktale nad nekim dijelom kompleksne ravnine.
	 * 
	 * @author borna
	 *
	 */
	public static class MojProducer implements IFractalProducer {
		private int workers;
		private int tracks;
		private ComplexRootedPolynomial polynom;
		
		/**
		 * Konstruktor.
		 * 
		 * @param polynom polinom tipa <code>ComplexRootedPolynomial</code>
		 * @param workers broj "radnika" (dretvi)
		 * @param tracks broj traka
		 */
		public MojProducer(ComplexRootedPolynomial polynom, int workers, int tracks) {
			this.polynom = polynom;
			this.workers = workers;
			this.tracks = tracks;
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			
			if (tracks > height) tracks = height;
			System.out.println("Workers = " + workers + ", tracks = " + tracks);
			
			int m = 16*16*16;
			short[] data = new short[width * height];
			final int brojTraka = tracks;
			int brojYPoTraci = height / brojTraka;
			
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[workers];
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if(p==PosaoIzracuna.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==brojTraka-1) {
					yMax = height-1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel, polynom);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			observer.acceptResult(data, (short) (polynom.getRoots().size()+1), requestNo);
		}
	}
}
