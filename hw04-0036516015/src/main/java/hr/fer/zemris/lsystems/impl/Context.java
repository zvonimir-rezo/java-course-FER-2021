package hr.fer.zemris.lsystems.impl;

public class Context {
	ObjectStack<TurtleState> stack;
	
	public Context() {
		stack = new ObjectStack<>(10);
	}
	
	
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	public void popState() {
		stack.pop();
	}
}
