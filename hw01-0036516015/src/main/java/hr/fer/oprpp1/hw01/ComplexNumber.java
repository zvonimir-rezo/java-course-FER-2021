package hr.fer.oprpp1.hw01;


/**
 * Implementation of a complex number with all
 * its functionalities.
 * @author Zvonimir Petar Rezo
 *
 */
public class ComplexNumber {

	double realPart;
	double imaginaryPart;
	
	/**
	 * Constructor method with two arguments.
	 * @param realPart - real part of a complex number
	 * @param imaginaryPart - imaginary part of a complex number
	 */
	public ComplexNumber(double realPart, double imaginaryPart) {
		this.realPart = realPart;
		this.imaginaryPart = imaginaryPart;
	}
	
	/**
	 * Factory method. Returns a new complex number with real part
	 * equal to the given argument.
	 * @param real - real part of a complex number
	 * @return New complex number with real part equal to the value given
	 * in argument
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Factory method. Returns a new complex number with imaginary part
	 * equal to the given argument.
	 * @param imaginary - imaginary part of a complex number
	 * @return New complex number with imaginary part equal to the value given
	 * in argument
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Factory method. Takes two arguments and returns a new complex
	 * number with given magnitude and angle.
	 * @param magnitude - magnitude of a complex number
	 * @param angle - angle of a complex number
	 * @return New complex number with given magnitude and angle.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double realPart = magnitude * Math.cos(angle);
		double imaginaryPart = magnitude * Math.sin(angle);
		return new ComplexNumber(realPart, imaginaryPart);
	}
	
	/**
	 * Factory method used to parse a complex number from the given string.
	 * @param s - string that needs to be parsed 
	 * @return New complex number that was parsed from the given string.
	 */
	public static ComplexNumber parse(String s) {
		
		ComplexNumber complex = null;
		String newStr;
		String[] array;
		if (!s.contains("i")) {
			if (s.indexOf("+") == 0) {
				newStr = s.substring(1, s.length());
			}
			return fromReal(Double.parseDouble(s));
		}
		if (s.equals("i") || s.equals("+i")) {
			return fromImaginary(1);
		}
		if (s.equals("-i")) {
			return fromImaginary(-1);
		}
		if (s.contains("i") && !s.contains("-") && !s.contains("+")) {
			s = s.replace("i", "");
			return fromImaginary(Double.parseDouble(s));
		}
		
		if (!s.contains("+") && s.contains("i")) {
			if (!s.contains("-")) {
				newStr = s.replace("i", "");
				complex = fromImaginary(Double.parseDouble(newStr));
			} else {
				if (s.indexOf("-") == 0 && s.indexOf("-", 1) == -1) {
					
					newStr = s.replace("i", "");
					complex = fromImaginary(Double.parseDouble(newStr));
				} else if (s.indexOf("-", 1)  > 0) {
					if (s.indexOf("-") == 0) {
						newStr =  s.substring(1, s.length());
						array = newStr.split("-");

						if (array[1].equals("i")) {
							array[1] = "1";
						}else {
							array[1] = array[1].replace("i", "");
						}

						complex = new ComplexNumber(-1 * Double.parseDouble(array[0]), -1 * Double.parseDouble(array[1]));
					} else {
						array = s.split("-");
						if (array[1] == "i") {
							array[1] = "1";
						} else {
							array[1] = array[1].replace("i", "");
						}
						complex = new ComplexNumber(Double.parseDouble(array[0]), -1 * Double.parseDouble(array[1]));
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
					complex = new ComplexNumber(Double.parseDouble(array[0]), Double.parseDouble(array[1]));
				} else if (newStr.indexOf("-") != -1) {
					array = newStr.split("-");
					complex = new ComplexNumber(Double.parseDouble(array[0]), -1 * Double.parseDouble(array[1]));
				}
			} else {
				array = s.split("\\+");
				if (array[1].equals("i")) {
					array[1] = "1";
				} else {
					array[1] = array[1].replace("i", "");
				}
				complex = new ComplexNumber(Double.parseDouble(array[0]), Double.parseDouble(array[1]));
			}
		}
		
		return complex;
		
	}
	
	/**
	 * Getter for real part of current complex number.
	 * @return Real part of current complex number.
	 */
	public double getReal() {
		return this.realPart;
	}
	
	/**
	 * Getter for imaginary part of current complex number.
	 * @return Imaginary part of current complex number.
	 */
	public double getImaginary() {
		return this.imaginaryPart;
	}
	
	/**
	 * Setter for real part of current complex number.
	 * @param realPart - value of new real part
	 */
	public void setReal(double realPart) {
		this.realPart = realPart;
	}

	/**
	 * Setter for imaginary part of current complex number.
	 * @param imaginaryPart - value of new imaginary part.
	 */
	public void setImaginary(double imaginaryPart) {
		this.imaginaryPart = imaginaryPart;
	}

	/**
	 * Getter for magnitude of current complex number.
	 * @return Magnitude of current complex number.
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(this.realPart, 2) + Math.pow(this.imaginaryPart, 2));
	}
	
	/**
	 * Getter for angle of current complex number.
	 * @return Angle of current complex number.
	 */
	public double getAngle() {
		double res = Math.atan2(this.imaginaryPart, this.realPart);
		
		return res < 0 ? (res + 2*Math.PI) : res;
	}
	
	/**
	 * Adds the complex number given in arguments to the current complex number.
	 * @param c - complex number to be added.
	 * @return Complex number that is the result of addition of two complex numbers.
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.getReal() + c.getReal(), this.getImaginary() + c.getImaginary());
	}
	
	/**
	 * Subtracts the complex number given in arguments from the current complex number.
	 * @param c - complex number to be subtracted.
	 * @return Complex number that is the result of addition of two complex numbers.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.realPart - c.getReal(), this.imaginaryPart - c.getImaginary());
	}
	
	/**
	 * Multiplies the complex number given in arguments with the current complex number.
	 * @param c - complex number to be multiplied with.
	 * @return Complex number that is the result of multiplication of two complex numbers.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(this.realPart * c.getReal() - this.imaginaryPart * c.getImaginary(), this.realPart * c.getImaginary() + this.imaginaryPart * c.getReal());
	}
	
	/**
	 * Divides the current complex number with the one given in arguments.
	 * @param c - complex number current complex number needs to be
	 * divided with.
	 * @return Complex number that is the result of division of two complex numbers.
	 */
	public ComplexNumber div(ComplexNumber c) {
		return new ComplexNumber((this.realPart * c.getReal() + this.imaginaryPart * c.getImaginary()) / Math.pow(c.getMagnitude(), 2), 
				(this.imaginaryPart * c.getReal() - this.realPart * c.getImaginary()) / Math.pow(c.getMagnitude(), 2));
	}
	
	/**
	 * Puts the current complex number to the given exponent.
	 * @param n - value of exponent
	 * @return Complex number that is the result of the operation.
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("n must be 0 or greater.");
		}
		
		double magnitude = this.getMagnitude();
		double angle = this.getAngle();
		
		return fromMagnitudeAndAngle(Math.pow(magnitude, n), angle * n);
	}
	
	/**
	 * Takes n-th root of the current complex number
	 * @param n - which root is the method supposed to take
	 * @return Complex number that is the result of the operation.
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("n must be greater than 0.");
		}
		
		ComplexNumber[] array = new ComplexNumber[n];
		double magnitude = this.getMagnitude();
		magnitude = Math.pow(magnitude, 1/(float)n);
		double angle = this.getAngle();
		
		if (angle < 0) {
			angle += 2*Math.PI;
		}
		
		for(int i = 0; i < n; i++) {
			array[i] = new ComplexNumber(magnitude * Math.cos((angle + Math.PI*2*i) / n), magnitude * Math.sin((angle + Math.PI*2*i) / n));
		}
		

		return array;
		
		
	}
	
	/**
	 * Converts complex number to string format.
	 * @return String format of the stored complex number.
	 */
	@Override
	public String toString() {
		if (this.getReal() == 0) {
			return String.valueOf(this.getImaginary() + "i");
		} else if (this.getImaginary() == 0) {
			return String.valueOf(this.getReal());
		} else if (this.imaginaryPart < 0) {
			return (String.valueOf(this.getReal()) + String.valueOf(this.getImaginary()) + "i");
		}
		return (String.valueOf(this.getReal()) + "+" + String.valueOf(this.getImaginary()) + "i");
		
		
	}
	
}
