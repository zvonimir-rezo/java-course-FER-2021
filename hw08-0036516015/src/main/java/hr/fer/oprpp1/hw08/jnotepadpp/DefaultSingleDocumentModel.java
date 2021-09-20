package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


/**
 * Implementation of a default class for SingleDocumentModel interface.
 * @author Zvonimir Petar Rezo
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private Path path;
	private JTextArea textComponent;
	private boolean modified = false;
	private List<SingleDocumentListener> listeners = new ArrayList<>();
	
	/**
	 * Constructor with two arguments.
	 * @param path - document path
	 * @param text - document text content
	 */
	public DefaultSingleDocumentModel(Path path, String text) {
		this.path = path;
		textComponent = new JTextArea(text);
		textComponent.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				modified = true;
				notifyStatusListeners();
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				modified = true;
				notifyStatusListeners();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				modified = true;
				notifyStatusListeners();
			}	
		});
	}
	
	/**
	 * Notifies status listeners about a change in status.
	 */
	private void notifyStatusListeners() {
		listeners.forEach(l -> l.documentModifyStatusUpdated(this));
	}
	
	/**
	 * Notifies path listeners about a change in path.
	 */
	private void notifyPathListeners() {
		listeners.forEach(l -> l.documentFilePathUpdated(this));
	}
	
	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		if (path == null) {
			throw new NullPointerException("Path can not be null.");
		}
		this.path = path;
		notifyPathListeners();
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		notifyStatusListeners();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);	
	}

}
