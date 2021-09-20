package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * Interface representing a listener for {@link MultipleDocumentModel}.
 * @author Zvonimir Petar Rezo
 *
 */
public interface MultipleDocumentListener {
	/**
	 * Called when current document of the model is changed.
	 * @param previousModel - previous document
	 * @param currentModel - new document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Called when a document is added to the model.
	 * @param model - document that was added
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Called when a document is removed from the model.
	 * @param model - document that was removed
	 */
	void documentRemoved(SingleDocumentModel model);
}
