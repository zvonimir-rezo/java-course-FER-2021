package hr.fer.zemris.java.gui.layouts;

/**
 * Implementation of a class that contains position of a layout component.
 * @author Zvonimir Petar Rezo
 *
 */
public class RCPosition {

	private int row;
	private int column;
	
	/**
	 * Calculates hash code of a RCPosition.
	 * @return HashCode of a RCPosition.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	/**
	 * Checks if two RCPosition objects are equal
	 * @param obj - object that is compared to current object
	 * @return True if objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public static RCPosition parse(String text) {
		try {
			String[] splittedText = text.split(",");
			return new RCPosition(Integer.parseInt(splittedText[0]), Integer.parseInt(splittedText[1]));
		} catch (Exception e) {
			throw new IllegalArgumentException("Input string can not be parsed properly.");
		}
	}
	
	
	
}
