package hr.fer.zemris.lsystems.impl;
/**
 * Implementation of a 2D vector.
 * @author Zvonimir Petar Rezo
 *
 */
public class Vector2D {
	private double x;
	private double y;
	
	/**
	 * Default constructor. Takes two arguments.
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter for the x coordinate.
	 * @return X coordinate of the current vector.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Getter for the y coordinate.
	 * @return Y coordinate of the current vector.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Adds the given vector to the current vector.
	 * @param offset - vector which needs to be added to the current vector
	 */
	public void add(Vector2D offset) {
		x += offset.x;
		y += offset.y;
	}
	
	/**
	 * Factory method for generating a new vector which is
	 * the product of addition of current vector and vector given in arguments.
	 * @param offset - vector which needs to be added to the current vector
	 * @return New vector which is the product of the addition.
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(x + offset.x, y + offset.y);
	}
	
	/**
	 * Rotates the current vector by given angle.
	 * @param angle - value of the angle to rotate the vector by
	 */
	public void rotate(double angle) {
		double tmpx = Math.round(((x * Math.cos(angle)) - (y * Math.sin(angle))) * 10000.0) / 10000.0;
		double tmpy = Math.round(((x * Math.sin(angle)) + (y * Math.cos(angle))) * 10000.0) / 10000.0;
		x = tmpx;
		y = tmpy;
	}
	
	/**
	 * Factory method for generating a new vector which is
	 * the product of rotating the current vector by given angle.
	 * @param angle - value of the angle to rotate the vector by
	 * @return New vector which is the product of rotation.
	 */
	public Vector2D rotated(double angle) {
		double tmpx = Math.round(((x * Math.cos(angle)) - (y * Math.sin(angle))) * 10000.0) / 10000.0;
		double tmpy = Math.round(((x * Math.sin(angle)) + (y * Math.cos(angle))) * 10000.0) / 10000.0;
		return new Vector2D(tmpx, tmpy);
	}
	
	/**
	 * Scales the current vector by given scale.
	 * @param scaler - value of the scale to scale the current vector by
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}
	
	/**
	 * Factory method for generating a new vector which is
	 * the product of scaling the current vector by given scale value.
	 * @param scaler - value of the scale to scale the current vector by
	 * @return New vector which is the product of scaling current vector by given scale.
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x*scaler, y*scaler);
	}
	
	/**
	 * Copies current vector into another vector object.
	 * @return Copy of current vector.
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
	
	
	
}
