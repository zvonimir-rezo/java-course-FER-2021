package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Implementation of a JFrame that displays primary numbers.
 * @author Zvonimir Petar Rezo
 *
 */
public class PrimDemo extends JFrame{
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            PrimDemo prozor = new PrimDemo();
            prozor.setVisible(true);
        });
	}
	
	/**
	 * Default constructor. Sets default close operation and size of the window. Runs the initGUI method.
	 */
	public PrimDemo() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		initGUI();
	}
	
	/**
	 * Populates the GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setBackground(Color.WHITE);
		PrimListModel model = new PrimListModel();
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		JScrollPane scrollFirst = new JScrollPane(list1);
		JScrollPane scrollSecond = new JScrollPane(list2);
		panel.add(scrollFirst);
		panel.add(scrollSecond);
		
		JButton buttonNext = new JButton("Next");
		buttonNext.addActionListener(b -> model.next());
		
		cp.add(panel, BorderLayout.CENTER);
		cp.add(buttonNext, BorderLayout.SOUTH);
		
	}

	

}
