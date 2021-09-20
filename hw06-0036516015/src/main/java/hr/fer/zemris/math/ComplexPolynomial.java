package hr.fer.zemris.math;

import java.util.ArrayList;

/**
 * Class representing a complex polynomial modeling a
 * polynomial with complex numbers.
 * @author Zvonimir Petar Rezo
 *
 */
public class ComplexPolynomial {

	private ArrayList<Complex> factors;

	/**
	 * Constructor which takes in arbitrary number of arguments.
	 * @param factors - array of complex numbers
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = new ArrayList<Complex>();
		for (Complex f : factors) {
			this.factors.add(f);
		}

	}

	/**
	 * Returns order of this polynomial.
	 */
	public short order() {
		return (short) (factors.size()-1);
	}

	/**
	 * Computes a new polynomial which is the product of multiplication
	 * of current polynomial and the one given in arguments.
	 * @param p - polynomial to multiply with
	 * @return Result of the multiplication.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int sizeThis = this.factors.size();
		int sizeOther = p.factors.size();
		Complex[] product = new Complex[sizeThis + sizeOther - 1];
		for (int i = 0; i < product.length; i++) {
			product[i] = Complex.ZERO;
		}
		for (int i = 0; i < sizeThis; i++) {
			for (int j = 0; j < sizeOther; j++) {
				product[i+j] = product[i+j].add(this.factors.get(i).multiply(p.factors.get(j)));
			}
		}
		return new ComplexPolynomial(product);
	}

	/**
	 * Computes first derivative of this polynomial.
	 * @return First derivative of this polynomial.
	 */
	public ComplexPolynomial derive() {
		if (factors.size() - 1 == 0)
			return new ComplexPolynomial(new Complex(0, 0));
		Complex[] factorsDerived = new Complex[factors.size()-1];
		for (int i = 1; i < factors.size(); i++) {
			factorsDerived[i-1] = factors.get(i).multiply(new Complex(i, 0));
		}
		return new ComplexPolynomial(factorsDerived);
		
	}

	/**
	 * Computes complex value at given point.
	 * @param z - given point
	 * @return Complex value at a given point.
	 */
	public Complex apply(Complex z) {
		Complex result = factors.get(0);
		for (int i = 1; i < factors.size(); i++) {
			result = result.add(factors.get(i).multiply(z.power(i)));
		}
		return result;
	}

	/**
	 * String representation of this polynomial.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = factors.size()-1; i > 0; i--) {
			sb.append(factors.get(i).toString() + "*" + "z^" + i + "+");
		}
		sb.append(factors.get(0).toString());
		return sb.toString();
	}

}
