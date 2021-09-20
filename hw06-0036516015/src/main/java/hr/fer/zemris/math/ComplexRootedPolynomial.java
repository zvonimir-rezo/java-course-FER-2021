package hr.fer.zemris.math;

import java.util.ArrayList;

/**
 * Class implementation of complex rooted polynomial.
 * @author Zvonimir Petar Rezo
 *
 */
public class ComplexRootedPolynomial {

	private Complex constant;
	private ArrayList<Complex> roots = new ArrayList<>();

	public static void main(String[] args) {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2, 0), Complex.ONE, Complex.ONE_NEG,
				Complex.IM, Complex.IM_NEG);
		ComplexPolynomial cp = crp.toComplexPolynom();
		System.out.println(crp);
		System.out.println(cp);
		System.out.println(cp.derive());
	}

	/**
	 * Constructor which takes arbitrary number of arguments.
	 * First argument is the constant, all other arguments are roots.
	 * @param constant - constant of the polynomial
	 * @param roots - roots of the polynomial
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = constant;
		for (Complex c : roots) {
			this.roots.add(c);
		}
	}

	/**
	 * Computes polynomial value at given point z.
	 * @param z - given point
	 * @return Polynomial value at given point.
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
		for (int i = 0; i < roots.size(); i++) {
			result = result.multiply((z.sub(roots.get(i))));
		}
		return result;
	}
	
	/**
	 * Converts this representation to ComplexPolynomial type.
	 * @return Complex polynomial value of this.
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial cp = new ComplexPolynomial(roots.get(0), Complex.ONE);
		for (int i = 1; i < roots.size(); i++) {
			cp = cp.multiply(new ComplexPolynomial(roots.get(i), Complex.ONE));
		}
		ComplexPolynomial constantPoly = new ComplexPolynomial(constant);
		return cp.multiply(constantPoly);
	}

	/**
	 * String representation of of this polynomial.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(constant.toString());
		for (int i = 0; i < roots.size(); i++) {
			sb.append("*(z-" + roots.get(i).toString() + ")");
		}
		return sb.toString();
	}

	/**
	 * Finds index of closest root for given complex number z that is within
	 * treshold. If there is no such root, returns -1
	 * first root has index 0, second index 1, etc
	 * @param z - complex number close to which the method searches for roots
	 * @param treshold - maximum distance between a root and number z
	 * @return Index of closest root to z that is within treshold.
	 */
	
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double currentDistance = treshold + 1;
		double distance;
		for (int i = 0; i < roots.size(); i++) {
			distance = roots.get(i).sub(z).module();
			if (distance <= treshold && distance < currentDistance) {
				currentDistance = distance;
				index = i;
			}
		}
		return index;
	}

}
