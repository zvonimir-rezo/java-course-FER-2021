package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class implements Command interface. 
 * Used to execute pop command.
 * @author Zvonimir Petar Rezo
 *
 */
public class PopCommand implements Command {
	
	/**
	 * Default constructor.
	 */
	public PopCommand() {
		
	}

	/**
	 * Pops current state from top of the stack.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();	
	}

}
