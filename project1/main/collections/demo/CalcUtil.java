package hr.fer.oprpp1.custom.collections.demo;

import java.util.Optional;

public class CalcUtil {
	
	private interface Calc {
		int solve(int first, int second);
	}
	
	static Optional<Integer> calculate(int first, int second, String operator) {
		if (operator.equals("+")) {
			Calc zbroj = (f, s) -> f + s;
		} else if (operator.equals("fact")) {

		}
		return Optional.empty();
	}
	
}
