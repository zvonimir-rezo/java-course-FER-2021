package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementation of calculator model.
 * @author Zvonimir Petar Rezo
 *
 */
public class CalcModelImpl implements CalcModel {
	
	private boolean editable = true;
	private boolean positive = true;
	private String numberString = "";
	private double numberValue = 0;
	private String frozenValue = null;
	private double activeOperand;
	private DoubleBinaryOperator pendingOperation = null;
	private ArrayList<CalcValueListener> listeners = new ArrayList<>();
	
	/**
	 * Adds a listener to the model.
	 * @param l CalcValueListener to be added
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);	
	}

	/**
	 * Removes a listener from the model.
	 * @param l CalcValueListener to be removed
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	/**
	 * Getter for current calculator value.
	 * @return Current calculator value
	 */
	@Override
	public double getValue() {
		return numberValue;
	}

	/**
	 * Setter for calculator value.
	 * @param value value that needs to be set
	 */
	@Override
	public void setValue(double value) {
		numberValue = value;
		numberString = String.valueOf(value);
		editable = false;
		notifyListeners();
	}

	/**
	 * Checks if the the model is editable.
	 * @return True if the model is editable, false otherwise.
	 */
	@Override
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Clears current value.
	 */
	@Override
	public void clear() {
		numberString = "";
		positive = true;
		editable = true;
		notifyListeners();
	}

	/**
	 * Clears everything(active operand, current value, pending operation).
	 */
	@Override
	public void clearAll() {
		numberString = "";
		numberValue = 0;
		activeOperand = 0;
		positive = true;
		pendingOperation = null;
		editable = true;
		notifyListeners();
	}

	/**
	 * Swaps the sign of current value. Changes positive to negative and negative to positive.
	 */
	@Override
	public void swapSign() throws CalculatorInputException {
		if (!editable)
			throw new CalculatorInputException("Model is currently not editable.");
		if (!numberString.isEmpty()) {
			numberString = numberString.startsWith("-") ? numberString.substring(1) : ("-" + numberString);
			numberValue = -numberValue;
		} 
		positive = !positive;
		frozenValue = null;
		notifyListeners();
		
	}

	/**
	 * Inserts a decimal point in the current value.
	 */
	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!editable)
			throw new CalculatorInputException("Model is currently not editable.");
		if (!numberString.isEmpty()) {
			if (numberString.contains(".")) {
				throw new CalculatorInputException();
			} else {
				numberString += ".";
				notifyListeners();
			}
		} else {
			throw new CalculatorInputException("Can not add a decimal point if there are not any digits before and the number is negative.");
		}
		frozenValue = null;
	}

	/**
	 * Inserts digit to the current value.
	 * @param digit digit that was clicked and needs to be inserted
	 */
	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!editable) {
			throw new CalculatorInputException("Model is currently not editable.");
		}
		if (numberString.isEmpty()) {
			numberString = String.valueOf(digit);
			numberValue = digit;
		} else {
			if (numberString.equals("0") && digit == 0)
				return;
			if (numberString.equals("0") && digit != 0) {
				numberString = String.valueOf(digit);
				numberValue = digit;
				return;
			}
			String concatNumber = numberString + String.valueOf(digit);
			try {
				double concatedNumber = Double.parseDouble(concatNumber);
				if (concatedNumber == Double.POSITIVE_INFINITY || concatedNumber == Double.NEGATIVE_INFINITY) {
					throw new NumberFormatException();
				}
				numberString = concatNumber;
				numberValue = concatedNumber;
			}catch (NumberFormatException | NullPointerException ex) {
				throw new CalculatorInputException("Invalid digit.");
			}
			
		}
		frozenValue = null;
		notifyListeners();
		
	}

	/**
	 * Checks if the active operand is set.
	 * @return True if the operand is set, false otherwise.
	 */
	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != 0;
	}

	/**
	 * Gets current active operand
	 * @return Current active operand
	 * @throws IllegalStateException if active operand isn't set
	 */
	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet())
			throw new IllegalStateException();
		return activeOperand;
	}

	/**
	 * Setter for active operand value.
	 * @param activeOperand Active operand that needs to be set.
	 */
	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		
	}

	/**
	 * Clears active operand.
	 */
	@Override
	public void clearActiveOperand() {
		activeOperand = 0;
	}

	/**
	 * Getter for pending binary operation.
	 * @return Pending binary operation.
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	/**
	 * Setter for pending binary operation
	 * @param op Binary operation that needs to be set.
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}

	/**
	 * Freezes the given value and stores it into the frozenValue variable.
	 * @value value that needs to be frozen
	 */
	@Override
	public void freezeValue(String value) {
		frozenValue = value;
	}

	/**
	 * Checks if the model currently has a frozen value.
	 * @return True if it has frozen value, false otherwise.
	 */
	@Override
	public boolean hasFrozenValue() {
		return !(frozenValue == null);
	}
	
	/**
	 * Notifies listeners that listen for an action.
	 */
	private void notifyListeners() {
		for (CalcValueListener listener: listeners) {
			listener.valueChanged(this);
		}
	}

	/**
	 * Converts current model value to string
	 * @return String value of current value.
	 */
	@Override
	public String toString() {
		if (frozenValue != null) {
			return frozenValue;
		} else if (numberString.equals("")) {
			return positive ? "0" : "-0";
		} else if (numberString.equals("-0.0")) {
			return "-0";
		} else if (numberString.equals("0.0")) {
			return "0";
		}
		return numberString;
	}

}
