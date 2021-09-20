package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;
/**
 * Interface for a model representing a document.
 * @author Zvonimir Petar Rezo
 *
 */
public interface SingleDocumentModel {
	/**
	 * Getter for text component of the document.
	 * @return Text component.
	 */
	JTextArea getTextComponent();

	/**
	 * Getter for document path.
	 * @return Document path.
	 */
	Path getFilePath();

	/**
	 * Setter for document path.
	 * @param path - new document path
	 */
	void setFilePath(Path path);

	/**
	 * Checks the modified flag to decide if document has bee modified.
	 * @return True if modified, false otherwise.
	 */
	boolean isModified();

	/**
	 * Setter for modified flag.
	 * @param modified - boolean value that needs to be assigned to modified flag
	 */
	void setModified(boolean modified);

	/**
	 * Adds a listener to this model.
	 * @param l - listener
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Removes a listener from this model's list of listeners.
	 * @param l - listener
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
