package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class implements Command interface. 
 * Used to change color with which the turtle currently draws.
 * @author Zvonimir Petar Rezo
 *
 */
public class ColorCommand implements Command {
	Color color;

	/**
	 * Constructor which takes one argument.
	 * @param color - current color of the class
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	/**
	 * Changes the color of current state.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setCurrentColor(color);
		
	}
	
}
