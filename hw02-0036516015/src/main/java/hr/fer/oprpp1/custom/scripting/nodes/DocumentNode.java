package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Class that extends the node object.
 * Root node of the document, all other nodes are its children.
 * @author Zvonimir Petar Rezo
 *
 */
public class DocumentNode extends Node {
	
	/**
	 * Default constructor, just calls the constructor of parent class.
	 */
	public DocumentNode() {
		super();
	}

	/**
	 * Converts node to a string.
	 * @return String value of the node.
	 */
	@Override
	public String toString() {
		String str = "";
		for (Object n : children.toArray()) {
			if (n != null) {
				str += n.toString();
			} else {
				break;
			}
			
		}
		return str;
	}
	
	/**
	 * Checks if current DocumentNode is equal to another object.
	 * @return True if two objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object other) {
		if (this == null || other == null) {
			return false;
		}
		if (!(other instanceof DocumentNode)) {
			return false;
		}
		if (this == other) {
			return true;
		}
		System.out.println(this.numberOfChildren());
		System.out.println(((DocumentNode) other).numberOfChildren());
		if (this.numberOfChildren() != ((DocumentNode) other).numberOfChildren()) {
			return false;
		}
		boolean rtn = true;
		for (int i = 0; i < this.numberOfChildren(); i++) {
			if (!(this.getChild(i).equals(((DocumentNode) other).getChild(i)))) {
				rtn = false;
				break;
			}
		}
		return rtn;
		
		
		
	}
	
}
