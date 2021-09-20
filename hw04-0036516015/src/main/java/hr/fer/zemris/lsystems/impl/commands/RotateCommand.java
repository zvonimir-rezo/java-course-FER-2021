package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * Class implements Command interface. 
 * Used to execute rotate command.
 * @author Zvonimir Petar Rezo
 *
 */
public class RotateCommand implements Command {
	double angle;

	/**
	 * Constructor which takes one argument.
	 * @param angle - angle to rotate the vector of current state by, if
	 * execute method is called
	 */
	public RotateCommand(double angle) {
		this.angle = Math.toRadians(angle);
	}

	/**
	 * Rotates the vector of current state by amount given in angle variable.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		ctx.getCurrentState().getUnitVector().rotate(angle);
//		state.setUnitVector(ctx.getCurrentState().getUnitVector().rotated(angle));
	}
	
}
