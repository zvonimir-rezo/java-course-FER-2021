package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {
		
		ObjectStack stack = new ObjectStack(10);
		String[] splitted = args[0].split(" ");
		boolean numeric = true;
		int num = 0;
		for (String el: splitted) {
			try {
				num = Integer.parseInt(el);
				numeric = true;
			} catch(NumberFormatException ex) {
				numeric = false;
			}
			
			if (numeric) {
				stack.push(num);
				continue;
			} else {
				int num1 = Integer.parseInt(stack.pop().toString());
				int num2 = Integer.parseInt(stack.pop().toString());
				if (el.equals("+")) {
					stack.push(num2+num1);
				} else if (el.equals("-")) {
					stack.push(num2-num1);
				} else if (el.equals("*")) {
					stack.push(num2*num1);
				} else if (el.equals("/")) {
					if (num2 == 0) {
						System.out.println("Can not divide by zero.");
						System.exit(0);
					}
					stack.push(num2/num1);
				} else if (el == "%") {
					stack.push(num2%num1);
				} else {
					System.out.println("Expression is invalid.");
					System.exit(0);
				}
				
			}
		}
		if (stack.size() != 1)
			throw new Error("Invalid stack size.");
		else
			System.out.println(stack.pop());
		return;
		
	}

}
