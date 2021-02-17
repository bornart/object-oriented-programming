package hr.fer.oprpp1.custom.collections.demo;

import java.util.Optional;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo2 {
	
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
				
				Optional<Integer> izracun = CalcUtil.calculate(first, second, elem);
				if (izracun.isPresent()) {
					stack.push(izracun);
				} else {
					System.out.println("Nevalja!");
				}
			}
		}
		
		if (stack.size() != 1) throw new Error("Pojavio se visak elemenata na stogu!");
		else System.out.println(stack.pop());
	}
	
}
