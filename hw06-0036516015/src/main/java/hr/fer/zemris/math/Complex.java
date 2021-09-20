package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;


public class Complex {

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	private double realPart;
	private double imaginaryPart;

	/**
	 * Default constructor
	 */
	public Complex() {

	}

	/**
	 * Constructor which takes two arguments.
	 * 
	 * @param re - real part
	 * @param im - imaginary part
	 */
	public Complex(double re, double im) {
		realPart = re;
		imaginaryPart = im;
	}

	public double getReal() {
		return realPart;
	}

	public double getImaginary() {
		return imaginaryPart;
	}

	/**
	 * Factory method. Takes two arguments and returns a new complex number with
	 * given magnitude and angle.
	 * 
	 * @param magnitude - magnitude of a complex number
	 * @param angle     - angle of a complex number
	 * @return New complex number with given magnitude and angle.
	 */
	public static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		double realPart = magnitude * Math.cos(angle);
		double imaginaryPart = magnitude * Math.sin(angle);
		return new Complex(realPart, imaginaryPart);
	}

	/**
	 * Getter for magnitude of current complex number.
	 * 
	 * @return Magnitude of current complex number.
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(this.realPart, 2) + Math.pow(this.imaginaryPart, 2));
	}

	/**
	 * Getter for angle of current complex number.
	 * 
	 * @return Angle of current complex number.
	 */
	public double getAngle() {
		double res = Math.atan2(this.imaginaryPart, this.realPart);

		return res < 0 ? (res + 2 * Math.PI) : res;
	}

	/**
	 * Calculates module of the complex number.
	 * 
	 * @return Module of complex number.
	 */
	public double module() {
		return Math.sqrt(Math.pow(this.getReal(), 2) + Math.pow(this.getImaginary(), 2));
	}

	/**
	 * Multiplies the complex number given in arguments with the current complex
	 * number.
	 * 
	 * @param c - complex number to be multiplied with.
	 * @return Complex number that is the result of multiplication of two complex
	 *         numbers.
	 */
	public Complex multiply(Complex c) {
		return new Complex(this.realPart * c.getReal() - this.imaginaryPart * c.getImaginary(),
				this.realPart * c.getImaginary() + this.imaginaryPart * c.getReal());
	}

	/**
	 * Divides the current complex number with the one given in arguments.
	 * 
	 * @param c - complex number current complex number needs to be divided with.
	 * @return Complex number that is the result of division of two complex numbers.
	 */
	public Complex divide(Complex c) {
		return new Complex(
				(this.realPart * c.getReal() + this.imaginaryPart * c.getImaginary()) / Math.pow(c.getMagnitude(), 2),
				(this.imaginaryPart * c.getReal() - this.realPart * c.getImaginary()) / Math.pow(c.getMagnitude(), 2));
	}

	/**
	 * Adds the complex number given in arguments to the current complex number.
	 * 
	 * @param c - complex number to be added.
	 * @return Complex number that is the result of addition of two complex numbers.
	 */
	public Complex add(Complex c) {
		return new Complex(this.getReal() + c.getReal(), this.getImaginary() + c.getImaginary());
	}

	/**
	 * Subtracts the complex number given in arguments from the current complex
	 * number.
	 * 
	 * @param c - complex number to be subtracted.
	 * @return Complex number that is the result of addition of two complex numbers.
	 */
	public Complex sub(Complex c) {
		return new Complex(this.realPart - c.getReal(), this.imaginaryPart - c.getImaginary());
	}

	/**
	 * Negates the current complex number and returns its negation.
	 * 
	 * @return Negated complex number.
	 */
	public Complex negate() {
		return new Complex(this.getReal() * -1, this.getImaginary() * -1);
	}

	/**
	 * Puts the current complex number to the given exponent.
	 * 
	 * @param n - value of exponent
	 * @return Complex number that is the result of the operation.
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("n must be 0 or greater.");
		}

		double magnitude = this.getMagnitude();
		double angle = this.getAngle();

		return fromMagnitudeAndAngle(Math.pow(magnitude, n), angle * n);
	}

	/**
	 * Takes n-th root of the current complex number
	 * 
	 * @param n - which root is the method supposed to take
	 * @return Complex number that is the result of the operation.
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("n must be greater than 0.");
		}

		Complex[] array = new Complex[n];
		double magnitude = this.getMagnitude();
		magnitude = Math.pow(magnitude, 1 / (float) n);
		double angle = this.getAngle();

		if (angle < 0) {
			angle += 2 * Math.PI;
		}

		for (int i = 0; i < n; i++) {
			array[i] = new Complex(magnitude * Math.cos((angle + Math.PI * 2 * i) / n),
					magnitude * Math.sin((angle + Math.PI * 2 * i) / n));
		}

		List<Complex> roots = new ArrayList<>();
		return roots;
	}
	
public static Complex parse(String s) {
		
		Complex complex = null;
		String newStr;
		String[] array;
		if (!s.contains("i")) {
			if (s.indexOf("+") == 0) {
				newStr = s.substring(1, s.length());
			}
			return new Complex(Double.parseDouble(s), 0);
		}
		if (s.equals("i") || s.equals("+i")) {
			return new Complex(0, 1);
		}
		if (s.equals("-i")) {
			return new Complex(0, -1);
		}
		if (s.contains("i") && !s.contains("-") && !s.contains("+")) {
			s = s.replace("i", "");
			return new Complex(0, Double.parseDouble(s));
		}
		
		if (!s.contains("+") && s.contains("i")) {
			if (!s.contains("-")) {
				newStr = s.replace("i", "");
				complex = new Complex(0, Double.parseDouble(newStr));
			} else {
				if (s.indexOf("-") == 0 && s.indexOf("-", 1) == -1) {
					
					newStr = s.replace("i", "");
					complex = new Complex(0, Double.parseDouble(newStr));
				} else if (s.indexOf("-", 1)  > 0) {
					if (s.indexOf("-") == 0) {
						newStr =  s.substring(1, s.length());
						array = newStr.split("-");

						if (array[1].equals("i")) {
							array[1] = "1";
						}else {
							array[1] = array[1].replace("i", "");
						}

						complex = new Complex(-1 * Double.parseDouble(array[0]), -1 * Double.parseDouble(array[1]));
					} else {
						array = s.split("-");
						if (array[1] == "i") {
							array[1] = "1";
						} else {
							array[1] = array[1].replace("i", "");
						}
						complex = new Complex(Double.parseDouble(array[0]), -1 * Double.parseDouble(array[1]));
					}
				}
			}
		} else if (s.contains("+") && s.contains("i")) {
			if (s.indexOf("+") == 0) {
				newStr = s.substring(1, s.length());
				if (newStr.indexOf("+") != -1) {
					array = newStr.split("\\+");
					if (array[1].equals("i")) {
						array[1] = "1";
					} else {
						array[1] = array[1].replace("i", "");
					}
					complex = new Complex(Double.parseDouble(array[0]), Double.parseDouble(array[1]));
				} else if (newStr.indexOf("-") != -1) {
					array = newStr.split("-");
					complex = new Complex(Double.parseDouble(array[0]), -1 * Double.parseDouble(array[1]));
				}
			} else {
				array = s.split("\\+");
				if (array[1].equals("i")) {
					array[1] = "1";
				} else {
					array[1] = array[1].replace("i", "");
				}
				complex = new Complex(Double.parseDouble(array[0]), Double.parseDouble(array[1]));
			}
		}
		
		return complex;
		
	}

	/**
	 * Converts complex number to string format.
	 * @return String format of the stored complex number.
	 */
	@Override
	public String toString() {
		if (this.getImaginary() < 0) {
			return ("(" + String.valueOf(this.getReal()) + String.valueOf(this.getImaginary()) + "i)");
		}
		return ("(" + String.valueOf(this.getReal()) + "+" + String.valueOf(this.getImaginary()) + "i)");

	}

}
