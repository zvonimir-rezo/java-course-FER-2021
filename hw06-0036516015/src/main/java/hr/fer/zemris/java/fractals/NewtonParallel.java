package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.mandelbrot.Mandelbrot;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program which takes user input in form of complex roots
 * ant then draws a picture based on the input. Uses threads for
 * better performance.
 * @author Zvonimir Petar Rezo
 *
 */
public class NewtonParallel {
	
	public static ComplexRootedPolynomial cp;
	public static int numberOfWorkers = 0;
	public static int numberOfTracks = 0;

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		boolean workersRead = false;
		boolean tracksRead = false;
		for (int i = 0; i < args.length; i++) {
			if (!workersRead) {
				if (args[i].equals("-w")) {
					numberOfWorkers = Integer.parseInt(args[i + 1]);
					workersRead = true;
				} else if (args[i].contains("--workers=")) {
					numberOfWorkers = Integer.parseInt(args[i].substring(args[i].indexOf('=')+1));
					workersRead = true;
				}
			}
			if (!tracksRead) {
				if (args[i].equals("-t")) {
					numberOfTracks = Integer.parseInt(args[i + 1]);
					tracksRead = true;
				} else if (args[i].contains("--tracks=")) {
					numberOfTracks = Integer.parseInt(args[i].substring(args[i].indexOf('=')+1));
					tracksRead = true;
				}
			}

		}
		if (numberOfWorkers == 0) {
			numberOfWorkers = Runtime.getRuntime().availableProcessors();
		}
		if (numberOfTracks == 0) {
			numberOfTracks = 4 * Runtime.getRuntime().availableProcessors();
		}
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
		sc.close();
		if (roots.size() == 0)
			System.exit(0);
		Complex[] rootsArray = new Complex[roots.size()];
		cp = new ComplexRootedPolynomial(Complex.ONE, roots.toArray(rootsArray));
		FractalViewer.show(new Producer());
	}

	/**
	 * Represents a job of calculations needed for fractals to be produced.
	 * @author Zvonimir Petar Rezo
	 *
	 */
	public static class CalculationJob implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		public static CalculationJob NO_JOB = new CalculationJob();

		/**
		 * Empty constructor.
		 */
		private CalculationJob() {
		}

		/**
		 * Constructor which takes many arguments.
		 * @param reMin - minimal value of real part
		 * @param reMax - maximal value of real part
		 * @param imMin - minimal value of imaginary part
		 * @param imMax - maximal value of imaginary part
		 * @param width - width of the window
		 * @param height - height of the window
		 * @param yMin - height at which the job starts
		 * @param yMax - height at which the job ends
		 * @param m - max number of iterations
		 * @param data - data that was passed to the function
		 * @param cancel
		 */
		public CalculationJob(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
		}

		/**
		 * Main method. Runs calculations.
		 */
		@Override
		public void run() {
			double convergenceTreshold = 0.001;
			double rootTreshold = 0.002;
			double module;
			ComplexPolynomial polynom = cp.toComplexPolynom();
			for(int y = yMin; y <= yMax; y++) {
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
					int index = cp.indexOfClosestRootFor(zn, rootTreshold);
					data[y*width+x] = (short)(index + 1);
				}
			}

		}
	}

	/**
	 * Class used to produce the picture.
	 * @author Zvonimir Petar Rezo
	 *
	 */
	public static class Producer implements IFractalProducer {

		/**
		 * Produces the picture based on complex polynomial
		 * which is global variable of the program.
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16 * 16 * 16;
			short[] data = new short[width * height];
			int brojYPoTraci;
			if (numberOfTracks > height) {
				brojYPoTraci = 1;
			} else {
				brojYPoTraci = height / numberOfTracks;
			}
			
			final BlockingQueue<CalculationJob> queue = new LinkedBlockingQueue<>();

			Thread[] workers = new Thread[numberOfWorkers];
			for (int i = 0; i < workers.length; i++) {
				workers[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while (true) {
							CalculationJob p = null;
							try {
								p = queue.take();
								if (p == CalculationJob.NO_JOB)
									break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for (int i = 0; i < workers.length; i++) {
				workers[i].start();
			}

			for (int i = 0; i < numberOfTracks; i++) {
				int yMin = i * brojYPoTraci;
				int yMax = (i + 1) * brojYPoTraci - 1;
				if (i == numberOfTracks - 1) {
					yMax = height - 1;
				}

				CalculationJob posao = new CalculationJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data,
						cancel);
				while (true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for (int i = 0; i < workers.length; i++) {
				while (true) {
					try {
						queue.put(CalculationJob.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}

			for (int i = 0; i < workers.length; i++) {
				while (true) {
					try {
						workers[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}

			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (cp.toComplexPolynom().order() + 1), requestNo);
		}
	}

}
