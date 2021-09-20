package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class implements Command interface. 
 * Used to execute push command.
 * @author Zvonimir Petar Rezo
 *
 */
public class PushCommand implements Command {
	
	/**
	 * Pushes copy of the current state to top of the stack.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}

}
