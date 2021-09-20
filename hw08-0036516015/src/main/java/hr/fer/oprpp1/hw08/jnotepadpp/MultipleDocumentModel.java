package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

/**
 * Interface for a model consisting of multiple SingleDocumentModel objects.
 * @author Zvonimir Petar Rezo
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	
	/**
	 * Creates new document and adds it to the model.
	 * @return New document.
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Getter for current document variable.
	 * @return Current document.
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads a document with the given path to the model.
	 * @param path - path which leads to desired document, can not be null
	 * @return Loaded document.
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves the document to newPath.
	 * @param model - document that needs to be saved
	 * @param newPath - new document path, if null, document is saved using path associated from document
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Removes specified document. Does not check modification status or ask any
	 * questions.
	 * @param model - document which needs to be removed
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds a listener to this model.
	 * @param l - listener
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes a listener from this model's list of listeners.
	 * @param l - listener
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns number of documents currently in the model.
	 * @return Number of documents currently in the model.
	 */
	int getNumberOfDocuments();

	/**
	 * Getter for document at a given index.
	 * @param index - index of desired document
	 * @return Document at a given index.
	 */
	SingleDocumentModel getDocument(int index);
}