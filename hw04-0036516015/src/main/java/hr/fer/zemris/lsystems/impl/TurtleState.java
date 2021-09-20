package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

/**
 * Class which represents state of a turtle used to draw.
 * @author Zvonimir Petar Rezo
 *
 */
public class TurtleState {
	Vector2D currentPosition;
	Vector2D unitVector;
	Color currentColor;
	double effectiveShift;
	double angle;
	
	/**
	 * Constructor which takes 4 arguments
	 * @param position - current position
	 * @param color - current color of lines which turtle draws
	 * @param shift - effective shift
	 * @param angle - current angle of the turtle
	 */
	public TurtleState(Vector2D position, Color color, double shift, Vector2D unitVector) {
		currentPosition = position;
		currentColor = color;
		effectiveShift = shift;
		this.unitVector = unitVector;
	}
	
	/**
	 * Copies current turtle state.
	 * @return Copy of the current turtle state.
	 */
	public TurtleState copy() {
		return new TurtleState(currentPosition.copy(), currentColor, effectiveShift, unitVector.copy());
	}
	
	/**
	 * Getter for unit vector.
	 * @return Unit vector
	 */
	public Vector2D getUnitVector() {
		return unitVector;
	}
	
	/**
	 * Setter for unit vector
	 * @param unitVector - new unit vector
	 */
	public void setUnitVector(Vector2D unitVector) {
		this.unitVector = unitVector;
	}

	/**
	 * Getter for current position.
	 * @return Current position
	 */
	public Vector2D getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Setter for current position.
	 * @param currentPosition - new current position
	 */
	public void setCurrentPosition(Vector2D currentPosition) {
		this.currentPosition = currentPosition;
	}

	/**
	 * Getter for current color.
	 * @return Current color
	 */
	public Color getCurrentColor() {
		return currentColor;
	}

	/**
	 * Setter for current color.
	 * @param currentColor - new current color
	 */
	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}

	/**
	 * Getter for effective shift.
	 * @return Effective shift.
	 */
	public double getEffectiveShift() {
		return effectiveShift;
	}

	/**
	 * Setter for effective shift.
	 * @param effectiveShift - new effective shift
	 */
	public void setEffectiveShift(double effectiveShift) {
		this.effectiveShift = effectiveShift;
	}
	
	
	
	
	
}
