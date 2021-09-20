package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;

/**
 * Exact implementation of LSystemBuilder.
 * Used to build a LSystem.
 * @author Zvonimir Petar Rezo
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	Dictionary<Character, String> dictProductions = new Dictionary<>();
	Dictionary<Character, Command> dictActions = new Dictionary<>();
	
	double unitLength = 0.1;
	double unitLengthDegreeScaler = 1;
	Vector2D origin = new Vector2D(0, 0);
	double angle = 0;
	String axiom = "";
	
	
	/**
	 * Default constructor.
	 */
	public LSystemBuilderImpl() {
		
	}
	
	/**
	 * Exact implementation of a LSystem which will be used
	 * by the LSystemBuilder class.
	 * @author Zvonimir Petar Rezo
	 *
	 */
	private class LSystemImpl implements LSystem {
		
		/**
		 * Default constructor.
		 */
		public LSystemImpl() {
			
		}
		
		/**
		 * Draws shapes in the LSystem depending on the product of generate() method.
		 */
		@Override
		public void draw(int arg0, Painter arg1) {
			Context ctx = new Context();
			TurtleState state = new TurtleState(origin, Color.BLACK, unitLength * Math.pow(unitLengthDegreeScaler, arg0), new Vector2D(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle))));
			ctx.pushState(state);
			String str = generate(arg0);
			for (int i = 0; i < str.length(); i++) {
				Command com = dictActions.get(str.charAt(i));
				if (com != null) {
					com.execute(ctx, arg1);
				}
			}
			
		}

		/**
		 * Generates a string depending on productions given in internal dictionary.
		 * @return Generated string.
		 */
		@Override
		public String generate(int arg0) {
			if (arg0 == 0) return axiom;
			String str = axiom;
			for (int i = 0; i < arg0; i++) {
				String tmp = "";
				for (int j = 0; j < str.length(); j++) {
					
					String product = dictProductions.get(str.charAt(j));
					if (product != null) {
						tmp += product;
					} else {
						tmp += str.charAt(j);
					}
				}
				str = tmp;
			}
			return str;
		}
		
	}

	/**
	 * Builds a LSystem.
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}
	
	/**
	 * Configures a LSystemBuilder from the given array of strings.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for(String s : arg0) {
			if (s.contains("/") && s.contains("unitLengthDegreeScaler")) {
				s = s.replace("/", " / ");
			}
			String[] splitted = s.split("\\s+");
			
			if (splitted[0].equals("origin")) {
				origin = new Vector2D(Double.parseDouble(splitted[1]), Double.parseDouble(splitted[2]));
			} else if (splitted[0].equals("angle")) {
				angle = Double.parseDouble(splitted[1]);
			} else if (splitted[0].equals("unitLength")) {
				unitLength = Double.parseDouble(splitted[1]);
			} else if (splitted[0].equals("unitLengthDegreeScaler")) {
				if (splitted[2].equals("/")) {
					unitLengthDegreeScaler = Double.parseDouble(splitted[1]) / Double.parseDouble(splitted[3]);
				} else {
					unitLength = Double.parseDouble(splitted[1]);
				}	
			} else if (splitted[0].equals("command")) {
				if (splitted[2].equals("draw")) {
					dictActions.put(splitted[1].charAt(0), new DrawCommand(Integer.parseInt(splitted[3])));
				} else if (splitted[2].equals("rotate")) {
					dictActions.put(splitted[1].charAt(0), new RotateCommand(Double.parseDouble(splitted[3])));
				} else if (splitted[2].equals("skip")) {
					dictActions.put(splitted[1].charAt(0), new SkipCommand(Integer.parseInt(splitted[3])));
				} else if (splitted[2].equals("push")) {
					dictActions.put(splitted[1].charAt(0), new PushCommand());
				} else if (splitted[2].equals("pop")) {
					dictActions.put(splitted[1].charAt(0), new PopCommand());
				} else if (splitted[2].equals("scale")) {
					dictActions.put(splitted[1].charAt(0), new ScaleCommand(Double.parseDouble(splitted[3])));
				} else if (splitted[2].equals("color")) {
					dictActions.put(splitted[1].charAt(0), new ColorCommand(Color.decode("0x" + splitted[3])));
				}
				
			} else if (splitted[0].equals("axiom")) {
				axiom = splitted[1];
			} else if (splitted[0].equals("production")) {
				dictProductions.put(splitted[1].charAt(0), splitted[2]);
			}
		}
		return this;
	}

	/**
	 * Registers one exact command in the dictionary.
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		String[] splitted = arg1.split(" ");
		if (splitted[0].equals("color")) {
			dictActions.put(arg0, new ColorCommand(Color.BLACK));
		} else if (splitted[0].equals("draw")) {
			System.out.println("tu sam draw " + splitted[1]);
			dictActions.put(arg0, new DrawCommand(Integer.parseInt(splitted[1])));
		} else if (splitted[0].equals("pop")) {
			dictActions.put(arg0, new PopCommand());
		} else if (splitted[0].equals("push")) {
			dictActions.put(arg0, new PushCommand());
		} else if (splitted[0].equals("rotate")) {
			System.out.println("tu sam rotate " + splitted[1]);
			dictActions.put(arg0, new RotateCommand(Double.parseDouble(splitted[1])));
		} else if (splitted[0].equals("scale")) {
			dictActions.put(arg0, new ScaleCommand(Double.parseDouble(splitted[1])));
		} else if (splitted[0].equals("skip")) {
			dictActions.put(arg0, new SkipCommand(Integer.parseInt(splitted[1])));
		}	
		return this;
	}

	/**
	 * Registers one exact production in the productions dictionary.
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		dictProductions.put(arg0, arg1);
		return this;
	}

	/**
	 * Setter for angle.
	 * @param arg0 - new angle
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		angle = arg0;
		return this;
	}
	
	/**
	 * Setter for axiom.
	 * @param arg0 - new axiom
	 */
	@Override
	public LSystemBuilder setAxiom(String arg0) {
		axiom = arg0;
		return this;
	}

	/**
	 * Setter for origin.
	 * @param arg0 - X coordinate of the origin
	 * @param arg1 - Y coordinate of the origin
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		origin = new Vector2D(arg0, arg1);
		return this;
	}

	/**
	 * Setter for unit length.
	 * @param arg0 - new unit length
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		unitLength = arg0;
		return this;
	}

	/**
	 * Setter for unit length degree scaler.
	 * @param arg0 - new unit length degree scaler
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		unitLengthDegreeScaler = arg0;
		return this;
	}

}
