package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program which takes user input in form of complex roots
 * ant then draws a picture based on the input.
 * @author Zvonimir Petar Rezo
 *
 */
public class Newton {
	
	public static ComplexRootedPolynomial crp;
	public static ComplexPolynomial polynom;

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner sc = new Scanner(System.in);
		ArrayList<Complex> roots = new ArrayList<>();
		int rootNumber = 1;
		System.out.print("Root " + rootNumber++ + ">");
		String line = sc.nextLine();
		while (!line.toLowerCase().equals("done")) {
			roots.add(Complex.parse(line));
			System.out.print("Root " + rootNumber++ + ">");
			line = sc.nextLine();
		}
		if (roots.size() == 0) System.exit(0);
		Complex[] rootsArray = new Complex[roots.size()];
		crp = new ComplexRootedPolynomial(Complex.ONE, roots.toArray(rootsArray));
		polynom = crp.toComplexPolynom();
		FractalViewer.show(new Producer());	
	}
	
	/**
	 * Class which is used to produce the picture.
	 * @author Zvonimir Petar Rezo
	 *
	 */
	public static class Producer implements IFractalProducer {
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			int offset = 0;
			double convergenceTreshold = 0.001;
			double rootTreshold = 0.002;
			double module;
			short[] data = new short[width * height];
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cre, cim);
					int iter = 0;
					do {
						Complex numerator = polynom.apply(zn);
						Complex denominator = polynom.derive().apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
					} while(iter < m && module > convergenceTreshold);
					int index = crp.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short)(index + 1);
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynom.order() + 1), requestNo);
		}
	}

}
