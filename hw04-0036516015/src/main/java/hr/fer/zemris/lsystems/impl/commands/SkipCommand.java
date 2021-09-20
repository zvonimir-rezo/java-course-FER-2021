package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * Class implements Command interface. 
 * Used to execute skip command.
 * @author Zvonimir Petar Rezo
 *
 */
public class SkipCommand implements Command {
	int step;
	
	/**
	 * Constructor which takes one argument.
	 * @param step - how many steps should the turtle move by
	 * running execute method
	 */
	public SkipCommand(int step) {
		this.step = step;
	}

	/**
	 * Moves the turtle by "step" number of steps.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D positionStart = currentState.getCurrentPosition();
		Vector2D unitVector = currentState.getUnitVector();
		//double angle = Math.atan2(unitVector.getY(), unitVector.getX());
		double magnitude = currentState.getEffectiveShift() * step;
		Vector2D vectorToAdd = unitVector.scaled(magnitude);
		Vector2D positionEnd = positionStart.added(vectorToAdd);
		currentState.setCurrentPosition(positionEnd);	
	}
	
	
}
