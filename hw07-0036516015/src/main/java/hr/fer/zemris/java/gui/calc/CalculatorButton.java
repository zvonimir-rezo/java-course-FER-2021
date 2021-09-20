package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Implementation of calculator button. Extends JButton.
 * @author Zvonimir Petar Rezo
 *
 */
public class CalculatorButton extends JButton {

	private String text;
	private String inverseText;
	private ActionListener action;
	private ActionListener inverseAction;
	
	/**
	 * Constructor for button which contains an operation that has an inverse.
	 * @param text button text
	 * @param action action button should perform
	 * @param inverseText button text for inverse
	 * @param inverseAction action button should perform when inversed
	 */
	public CalculatorButton(String text, ActionListener action, String inverseText, ActionListener inverseAction) {
		this.text = text;
		this.inverseText = inverseText;
		this.action = action;
		this.inverseAction = inverseAction;
		setText(text);
		addActionListener(action);
	}
	
	/**
	 * Constructor for button which does not contain an operation taht has an inverse.
	 * @param text button text
	 * @param action action button should perform
	 */
	public CalculatorButton(String text, ActionListener action) {
		this.text = text;
		this.action = action;
		setText(text);
		addActionListener(action);
	}
	
	/**
	 * Inverses the operation button should perform. Works only for buttons
	 * which have an inverse action.
	 */
	public void inverse() {
		if (getText().equals(text)) {
			setText(inverseText);
		} else {
			setText(text);
		}
		removeActionListener(action);
		addActionListener(inverseAction);
	}
	
}
