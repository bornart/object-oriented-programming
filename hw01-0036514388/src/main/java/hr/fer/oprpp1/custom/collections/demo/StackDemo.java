package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
	 
	public static void main(String[] args) {
		
		String[] elements = args[0].trim().split("\\s+");
		
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		ObjectStack stack = new ObjectStack(arr);
		
		for (String elem : elements) {
			if (elem.matches("-?\\d+")) {
				int num = Integer.parseInt(elem);
				stack.push(num);
			} else {
				int second = (int) stack.pop();
				int first = (int) stack.pop();
				
				if (second == 0 && elem.equals("/")) throw new IllegalArgumentException("Zabranjeno je dijeljenje sa nulom!");
				
				try {
					int result = calculateResult(first, second, elem);
					stack.push(result);
				} catch (UnsupportedOperationException e) {
					System.out.println("Izraz nije pravilno zadan. Operator mora biti jedan od sljedecih: +, -, *, / ili %");
				}
			}
		}
		
		if (stack.size() != 1) throw new Error("Pojavio se visak elemenata na stogu!");
		else System.out.println(stack.pop());
	}
	
	public static int calculateResult(int first, int second, String operator){
		int result;
		
		switch(operator) {
			case "*" -> result = first * second;
			case "/" -> result = first / second;
			case "+" -> result = first + second;
			case "-" -> result = first - second;
			case "%" -> result = first % second;
			default -> throw new UnsupportedOperationException();
		}
		
		return result;
	}
}