package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

public class Calculator extends JFrame {

	private Stack<Double> stack = new Stack<>();
	private CalcModelImpl model;
	private ArrayList<CalculatorButton> inverses = new ArrayList<>();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Calculator calc = new Calculator();
			calc.setVisible(true);
		});
	}

	/**
	 * Default constructor. Sets the title, size and default close operation of the window.
	 */
	public Calculator() {
		super();
		model = new CalcModelImpl();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Calculator");
		setSize(600, 400);
		initGUI();
	}

	/**
	 * Populates the GUI.
	 */
	private void initGUI() {
		Container container = getContentPane();
		JPanel panel = new JPanel(new CalcLayout(3));
		panel.setBackground(Color.WHITE);
		JLabel display = new JLabel(model.toString());
		display.setFont(display.getFont().deriveFont(30f));
		display.setBackground(Color.yellow);
		display.setOpaque(true);
		display.setHorizontalAlignment(JLabel.RIGHT);
		model.addCalcValueListener(m -> display.setText(m.toString()));
		panel.add(display, "1,1");
		
		CalculatorButton zero = new CalculatorButton("0", b -> model.insertDigit(0));
		zero.setFont(zero.getFont().deriveFont(30f));
		panel.add(zero, "5,3");
		
		CalculatorButton one = new CalculatorButton("1", b -> model.insertDigit(1));
		one.setFont(one.getFont().deriveFont(30f));
		panel.add(one, "4,3");
		
		CalculatorButton two = new CalculatorButton("2", b -> model.insertDigit(2));
		two.setFont(two.getFont().deriveFont(30f));
		panel.add(two, "4,4");
		
		CalculatorButton three = new CalculatorButton("3", b -> model.insertDigit(3));
		three.setFont(three.getFont().deriveFont(30f));
		panel.add(three, "4,5");
		
		CalculatorButton four = new CalculatorButton("4", b -> model.insertDigit(4));
		four.setFont(four.getFont().deriveFont(30f));
		panel.add(four, "3,3");
		
		CalculatorButton five = new CalculatorButton("5", b -> model.insertDigit(5));
		five.setFont(five.getFont().deriveFont(30f));
		panel.add(five, "3,4");
		
		CalculatorButton six = new CalculatorButton("6", b -> model.insertDigit(6));
		six.setFont(six.getFont().deriveFont(30f));
		panel.add(six, "3,5");
		
		CalculatorButton seven = new CalculatorButton("7", b -> model.insertDigit(7));
		seven.setFont(seven.getFont().deriveFont(30f));
		panel.add(seven, "2,3");
		
		CalculatorButton eight = new CalculatorButton("8", b -> model.insertDigit(8));
		eight.setFont(eight.getFont().deriveFont(30f));
		panel.add(eight, "2,4");
		
		CalculatorButton nine = new CalculatorButton("9", b -> model.insertDigit(9));
		nine.setFont(nine.getFont().deriveFont(30f));
		panel.add(nine, "2,5");
		
		panel.add(new CalculatorButton("+/-", b -> model.swapSign()), "5,4");
		panel.add(new CalculatorButton(".", b -> model.insertDecimalPoint()), "5,5");
		panel.add(new CalculatorButton("clr", b -> model.clear()), "1,7");
		panel.add(new CalculatorButton("res", b -> model.clearAll()), "2,7");
		panel.add(new CalculatorButton("push", b -> stack.push(model.getValue())), "3,7");
		panel.add(new CalculatorButton("pop", b -> {
			if (!stack.isEmpty()) {
				model.setValue(stack.pop());
			} else {
				System.err.println("The stack is empty.");
			}
		}), "4,7");

		JCheckBox inverse = new JCheckBox("Inv");
		inverse.addItemListener(b -> {
			for (CalculatorButton operator : inverses) {
				operator.inverse();
			}
		});
		panel.add(inverse, "5,7");

		CalculatorButton sin = new CalculatorButton("sin", b -> {
			checkFrozenValue();
			model.setValue(Math.sin(model.getValue()));
			},
			"arcsin", b -> {
				checkFrozenValue();
				model.setValue(Math.asin(model.getValue()));
			});
		inverses.add(sin);
		panel.add(sin, "2,2");

		CalculatorButton cos = new CalculatorButton("cos", b -> {
			checkFrozenValue();
			model.setValue(Math.cos(model.getValue()));
		}, "arccos", b -> {
			checkFrozenValue();
			model.setValue(Math.acos(model.getValue()));
		});
		inverses.add(cos);
		panel.add(cos, "3,2");

		CalculatorButton tan = new CalculatorButton("tan", b -> {
			checkFrozenValue();
			model.setValue(Math.tan(model.getValue()));
		}, "arctan", b -> {
			checkFrozenValue();
			model.setValue(Math.atan(model.getValue()));
		});
		inverses.add(tan);
		panel.add(tan, "4,2");

		CalculatorButton ctg = new CalculatorButton("ctg", b -> {
			checkFrozenValue();
			model.setValue(1.0 / Math.tan(model.getValue()));
		}, "arcctg", b -> {
			checkFrozenValue();
			model.setValue(Math.PI/2 - Math.atan(model.getValue()));
		});
		inverses.add(ctg);
		panel.add(ctg, "5,2");

		CalculatorButton log = new CalculatorButton("log", b -> {
			checkFrozenValue();
			model.setValue(Math.log10(model.getValue()));
		}, "10^x", b -> {
			checkFrozenValue();
			model.setValue(Math.pow(10, model.getValue()));
		});
		inverses.add(log);
		panel.add(log, "3,1");

		CalculatorButton ln = new CalculatorButton("ln", b -> {
			checkFrozenValue();
			model.setValue(Math.log(model.getValue()));
		}, "e^x", b -> {
			checkFrozenValue();
			model.setValue(Math.pow(Math.E, model.getValue()));
		});
		inverses.add(ln);
		panel.add(ln, "4,1");
		
		CalculatorButton exp = new CalculatorButton("x^n", b -> {
			checkFrozenValue();
			secondBinary((first, second) -> Math.pow(first, second));
		}, "x^(1/n)", b -> {
			checkFrozenValue();
			secondBinary((first, second) -> Math.pow(first, 1 / second));
		});
		inverses.add(exp);
		panel.add(exp, "5,1");

		CalculatorButton reciprocal = new CalculatorButton("1/x", b -> {
			checkFrozenValue();
			model.setValue(1.0 / (model.getValue()));
		});
		panel.add(reciprocal, "2,1");

		CalculatorButton equals = new CalculatorButton("=", b -> {
			if (model.getPendingBinaryOperation() != null && model.isActiveOperandSet()) {
				model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
				model.setPendingBinaryOperation(null);
			}
		});
		panel.add(equals, "1,6");

		CalculatorButton plus = new CalculatorButton("+", b -> {
			checkFrozenValue();
			secondBinary((first, second) -> first + second);
		});
		panel.add(plus, "5,6");
		
		CalculatorButton minus = new CalculatorButton("-", b -> {
			checkFrozenValue();
			secondBinary((first, second) -> first - second);
		});
		panel.add(minus, "4,6");

		CalculatorButton mul = new CalculatorButton("*", b -> {
			checkFrozenValue();
			secondBinary((first, second) -> first * second);
		});
		panel.add(mul, "3,6");

		CalculatorButton div = new CalculatorButton("/", b -> {
			checkFrozenValue();
			secondBinary((first, second) -> first / second);
			
		});
		panel.add(div, "2,6");

		container.add(panel);
		container.setPreferredSize(panel.getPreferredSize());

	}
	
	/**
	 * Called when a binary operator is clicked. If there is a pending operator already, calculates
	 * a new value, sets that value to be an active operand and sets pending binary operation to be the
	 * operation that was clicked. If not, sets active operand and pending binary operation conveniently.
	 * @param op Binary operator that was clicked
	 */
	private void secondBinary(DoubleBinaryOperator op) {
		if (model.getPendingBinaryOperation() == null) {
            model.setActiveOperand(model.getValue());
            model.setPendingBinaryOperation(op);
            model.freezeValue(model.toString());
            model.clear();
        } else if (model.isActiveOperandSet()) {
        	double val = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
                    model.getValue());
        	model.setValue(val);
            model.setActiveOperand(val);
            model.freezeValue(model.toString());
            model.clear();

        }
	
	}
	
	/**
	 * Checks if the model currently has a frozen value.
	 * @throws CalculatorInputException if true
	 */
	private void checkFrozenValue() {
		if (model.hasFrozenValue())
			throw new CalculatorInputException();
	}
	
}
