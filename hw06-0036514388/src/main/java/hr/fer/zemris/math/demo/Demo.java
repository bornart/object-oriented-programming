package hr.fer.zemris.math.demo;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Demo {
	
	public static void main(String[] args) {
		
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
		ComplexPolynomial cp = crp.toComplexPolynom();
		System.out.println(crp);
		System.out.println(cp);
		System.out.println(cp.derive());
		
		System.out.println("----------------------------------------");
		ComplexRootedPolynomial crp1 = new ComplexRootedPolynomial(Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
		ComplexPolynomial cp1 = crp1.toComplexPolynom();
		System.out.println(crp1);
		System.out.println(cp1);
		
		System.out.println("----------------------------------------");
		ComplexRootedPolynomial crp2 = new ComplexRootedPolynomial(Complex.ONE, Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
		ComplexPolynomial cp2 = crp2.toComplexPolynom();
		System.out.println(cp2);
		
		System.out.println("----------------------------------------");
		ComplexRootedPolynomial crp3 = new ComplexRootedPolynomial(new Complex(3,7), new Complex(3,7), new Complex(1,1));
		ComplexPolynomial cp3 = crp3.toComplexPolynom();
		System.out.println(cp3);
		
		System.out.println("----------------------------------------");
		ComplexRootedPolynomial crp4 = new ComplexRootedPolynomial(new Complex(2, 2), new Complex(2, 1), new Complex(1,1), new Complex(1,1));
		ComplexPolynomial cp4 = crp4.toComplexPolynom();
		System.out.println(cp4);
		
	}
}
