package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class implements Command interface. 
 * Used to execute scale command.
 * @author Zvonimir Petar Rezo
 *
 */
public class ScaleCommand implements Command {
	double factor;
	
	/**
	 * Constructor which takes one argument.
	 * @param factor - factor at which the execute method should scale
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/**
	 * Scales effective shift of the current state by factor.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setEffectiveShift(ctx.getCurrentState().getEffectiveShift() * factor);
	}
	
}
