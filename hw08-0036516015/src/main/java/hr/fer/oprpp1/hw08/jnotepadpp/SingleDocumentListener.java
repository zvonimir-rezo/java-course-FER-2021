package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * Interface representing a listener for {@link SingleDocumentModel}.
 * @author Zvonimir Petar Rezo
 *
 */
public interface SingleDocumentListener {
	/**
	 * Called when text component is changed.
	 * @param model - document
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Called when path is changed
	 * @param model - document
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}