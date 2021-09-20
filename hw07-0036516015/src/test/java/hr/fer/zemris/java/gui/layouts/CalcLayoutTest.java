package hr.fer.zemris.java.gui.layouts;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

class CalcLayoutTest {

	@Test
	void testAddLayoutComponent() {
		CalcLayout cl = new CalcLayout();
		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(new JLabel("sd"), new RCPosition(0, 2));
		});
		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(new JLabel("sd"), new RCPosition(2, 8));
		});
		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(new JLabel("sd"), new RCPosition(1, 5));
		});
		cl.addLayoutComponent(new JLabel("sd"), new RCPosition(1, 6));
		assertThrows(CalcLayoutException.class, () -> {
			cl.addLayoutComponent(new JLabel("sd"), new RCPosition(1, 6));
		});
	}
	
	@Test
	void testDimension() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(dim.width, 152);
		assertEquals(dim.height, 158);
	}

}
