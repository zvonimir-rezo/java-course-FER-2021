package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Implementation of the calculator layout. Implements LayoutManager2.
 * @author Zvonimir Petar Rezo
 *
 */
public class CalcLayout implements LayoutManager2 {
	
	private int space;
	private HashMap<RCPosition, Component> components = new HashMap<>();
	
	
	/**
	 * Constructor which takes one argument.
	 * @param space - desired space between rows and columns in pixels
	 */
	public CalcLayout(int space) {
		this.space = space;
	}
	
	/**
	 * Default empty constructor
	 */
	public CalcLayout() {
		this.space = 0;
	}
	
	/**
	 * Adds given layout component to the layout.
	 * @param name - name of the component
	 * @param comp - component
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("Can't use this method.");

	}

	/**
	 * Removes the given layout component.
	 * @param comp - component that needs to be removed
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		while(components.values().remove(comp));
	}

	/**
	 * Gets the preferred layout size for the container.
	 * @param parent - container whose preferred size is calculated
	 * @return Preferred dimension of the container.
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return layoutSizeCalc(parent, 1);
	}
	
	/**
	 * Gets the maximum layout size for the container.
	 * @param target - container whose preferred size is calculated
	 * @return Maximum dimension of the container.
	 */
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return layoutSizeCalc(target, 2);
	}

	/**
	 * Gets the minimum layout size for the container.
	 * @param parent - container whose preferred size is calculated
	 * @return Minimum dimension of the container.
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return layoutSizeCalc(parent, 3);
	}

	/**
	 * Lays out the specified container.
	 * @param parent - container to be laid out
	 */
	@Override
	public void layoutContainer(Container parent) {
		int maxWidth = 0;
		int maxHeight = 0;
		for (Map.Entry<RCPosition, Component> c: components.entrySet()) {
			if (c.getValue().getMaximumSize().width > maxWidth)
				maxWidth = c.getValue().getMaximumSize().width;
			if (c.getValue().getMaximumSize().height > maxHeight)
				maxHeight = c.getValue().getMaximumSize().height;
			
		}
		int width = (int) (((parent.getWidth() / preferredLayoutSize(parent).getWidth()) * maxWidth));
		int height = (int) (((parent.getHeight() / preferredLayoutSize(parent).getHeight()) * maxHeight));
		
		for (Map.Entry<RCPosition, Component> c: components.entrySet()) {
			RCPosition pos = c.getKey();
			if (pos.getColumn() == 1 && pos.getRow() == 1) {
				c.getValue().setBounds(0, 0, 5 * width + 4 * space, height);
			} else {
				c.getValue().setBounds((pos.getColumn()-1) * (width + space), (pos.getRow()-1) * (height + space), width, height);
			}
		}
		
	}

	/**
	 * Adds the given component to the layout.
	 * @param comp - component that needs to be added
	 * @param constraints - constraints for the component
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition position;
		if (constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
			if ((position.getRow() < 1 || position.getRow() > 5) || (position.getColumn() < 1 || position.getColumn() > 7)
					|| (position.getRow() == 1 && position.getColumn() > 1 && position.getColumn() < 6)) {
				throw new CalcLayoutException("Invalid row or column number.");
			}
			
		} else if (constraints instanceof String) {
			position = RCPosition.parse((String)constraints);
			if ((position.getRow() < 1 || position.getRow() > 5) || (position.getColumn() < 1 || position.getColumn() > 7)
					|| (position.getRow() == 1 && position.getColumn() > 1 && position.getColumn() < 6)) {
				throw new CalcLayoutException("Invalid row or column number.");
			}
		} else {
			throw new IllegalArgumentException("Invalid type of constraints object.");
		}
		if (constraints == null || comp == null) {
			throw new NullPointerException("Argument can not be null while adding the component.");
		}
		if (components.get(position) != null)
			throw new CalcLayoutException("Position is already taken.");
		components.put(position, comp);
	}

	
	/**
	 * Calculates the size in layout for given component.
	 * @param comp - component whose size is calculated
	 * @param rbr - integer value which represents one of three options: 1-preferred size, 2-maximum size, 3-minumum size
	 * @return Dimension calculated based on given parameters.
	 */
	private Dimension layoutSizeCalc(Container comp, int rbr) {
		int width = 0;
		int height = 0;
		Insets insets = comp.getInsets();
		if (rbr == 1) {
			for (Map.Entry<RCPosition, Component> c: components.entrySet()) {
				int preferredWidth = c.getValue().getPreferredSize().width;
				int preferredHeight = c.getValue().getPreferredSize().height;

				if (preferredWidth > width)
					width = preferredWidth;
				if (preferredHeight > height)
					height = preferredHeight;
			}
		} else if (rbr == 2) {
			for (Map.Entry<RCPosition, Component> c: components.entrySet()) {
				int maxWidth = c.getValue().getMaximumSize().width;
				int maxHeight = c.getValue().getMaximumSize().height;

				if (maxWidth > width)
					width = maxWidth;
				if (maxHeight > height)
					height = maxHeight;
			}
		} else if (rbr == 3) {
			for (Map.Entry<RCPosition, Component> c: components.entrySet()) {
				int minWidth = c.getValue().getMinimumSize().width;
				int minHeight = c.getValue().getMinimumSize().height;

				if (minWidth > width)
					width = minWidth;
				if (minHeight > height)
					height = minHeight;
			}
		}
		int widthSum = width * 7 + space * 6 + insets.left + insets.right;
		int heightSum = height * 5 + space * 4 + insets.top + insets.bottom;
		return new Dimension(widthSum, heightSum);
		
	}

	/**
	 * Always returns 0.
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	/**
	 * Always returns 0.
	 */
	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	/**
	 * Empty method.
	 */
	@Override
	public void invalidateLayout(Container target) {

	}

}
