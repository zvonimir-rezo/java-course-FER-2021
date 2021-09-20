package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.Tester;
/**
 * Tester class which implements Tester interface and provides a test for even integers.
 * @author Zvonimir Petar Rezo
 *
 */
public class EvenIntegerTester implements Tester {
	
	public static void main(String[] args) {
		Tester t = new EvenIntegerTester();
		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));
	}
	
	public boolean test(Object obj) {
		 if(!(obj instanceof Integer)) return false;
		 Integer i = (Integer)obj;
		 return i % 2 == 0;
	}

}


