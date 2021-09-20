package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * Implementation of a default class for MultipleDocumentModel interface.
 * @author Zvonimir Petar Rezo
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;
	private List<SingleDocumentModel> tabs = new ArrayList<>();
	private SingleDocumentModel current;
	private List<MultipleDocumentListener> listeners = new ArrayList<>();

	/**
	 * Default constructor. Adds a change listener to the model.
	 */
	public DefaultMultipleDocumentModel() {
		this.addChangeListener(e -> {
			SingleDocumentModel old = current;
			int index = getSelectedIndex();
			current = tabs.get(index);
			notifyNewCurrent(old, current);
			
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return tabs.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel doc = new DefaultSingleDocumentModel(null, "");
		tabs.add(doc);
		notifyNewAdded(doc);
		doc.addSingleDocumentListener(new SingleDocumentListener() {
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				if (model.isModified()) {
					setIconAt(tabs.indexOf(model), getIcon("redDisk.png"));
				} else {
					setIconAt(tabs.indexOf(model), getIcon("greenDisk.png"));
				}
				
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setTitleAt(tabs.indexOf(model), model.getFilePath().toFile().getName());
				setToolTipTextAt(tabs.indexOf(model), model.getFilePath().toString());
			}
		});
		SingleDocumentModel oldCurrent = current;
		current = doc;
		notifyNewCurrent(oldCurrent, current);
		addToTabsView(doc);
		return doc;
	}

	

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if (path == null)
			throw new NullPointerException("Path can not be null.");
		for (SingleDocumentModel m : tabs) {
			if (m.getFilePath() != null && m.getFilePath().equals(path)) {
				SingleDocumentModel oldCurrent = current;
				current = m;
				notifyNewCurrent(oldCurrent, current);
				return m;
			}
		}

		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return addNewDocument(path, bytes);
	}

	/**
	 * Private method used when loading a file to add a new document.
	 * @param path - path to file
	 * @param bytes - file data in bytes
	 * @return SingleDocumentModel representing loaded document
	 */
	private SingleDocumentModel addNewDocument(Path path, byte[] bytes) {
		SingleDocumentModel newDoc = new DefaultSingleDocumentModel(path, new String(bytes, StandardCharsets.UTF_8));
		tabs.add(newDoc);
		SingleDocumentModel oldCurrent = current;
		current = newDoc;
		notifyNewCurrent(oldCurrent, newDoc);
		newDoc.addSingleDocumentListener(new SingleDocumentListener() {
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				if (model.isModified()) {
					setIconAt(tabs.indexOf(model), getIcon("redDisk.png"));
				} else {
					setIconAt(tabs.indexOf(model), getIcon("greenDisk.png"));
				}
				
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setTitleAt(tabs.indexOf(newDoc), newDoc.getFilePath().toFile().getName());
				setToolTipTextAt(tabs.indexOf(newDoc), newDoc.getFilePath().toString());
			}
		});
		addToTabsView(newDoc);
		return newDoc;
	}

	/**
	 * Adds document to tabs pane, setting its layout and adding scroll function.
	 * 
	 * @param model
	 */
	private void addToTabsView(SingleDocumentModel model) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JScrollPane(model.getTextComponent()), BorderLayout.CENTER);

		if (model.getFilePath() != null) {
			addTab(model.getFilePath().getFileName().toString(), getIcon("greenDisk.png"), panel,
					model.getFilePath().toAbsolutePath().toString());
		} else {
			addTab("Unnamed", getIcon("redDisk.png"), panel, "");
		}
		setSelectedComponent(panel);
		

	}

	/**
	 * Notifies listeners that a document was added.
	 * @param model - document that was added
	 */
	private void notifyNewAdded(SingleDocumentModel model) {
		listeners.forEach(l -> l.documentAdded(model));
	}

	/**
	 * Notifies listeners about the model having a new current document.
	 * @param oldDoc - old document
	 * @param newDoc - new document
	 */
	private void notifyNewCurrent(SingleDocumentModel oldDoc, SingleDocumentModel newDoc) {
		listeners.forEach(l -> l.currentDocumentChanged(oldDoc, newDoc));
	}

	/**
	 * Notifies listeners about a document being removed.
	 * @param model - document that was removed
	 */
	private void notifyRemoved(SingleDocumentModel model) {
		listeners.forEach(l -> l.documentRemoved(model));
	}

	/**
	 * Used to grab an icon from resources based on file name.
	 * @param fileName - name of the icon that method wants to get
	 * @return ImageIcon with given file name.
	 */
	private ImageIcon getIcon(String fileName) {
		InputStream is = this.getClass().getResourceAsStream("icons/" + fileName);
		if (is == null) {
			throw new IllegalArgumentException("Given file name does not exist.");
		}
		byte[] bytes = null;
		try {
			bytes = is.readAllBytes();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(bytes);
		Image image = icon.getImage();
		Image newImg = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		return new ImageIcon(newImg);

	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		for (SingleDocumentModel doc : tabs) {
			if (!model.equals(doc)) {
				if (doc.getFilePath() != null && doc.getFilePath().equals(newPath)) {
					throw new IllegalArgumentException();
				}
			}
		}

		byte[] bytes = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);

		if (newPath != null) {
			current.setFilePath(newPath);
		} else {
			newPath = model.getFilePath();
		}

		try {
			Files.write(newPath, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		notifyNewCurrent(current, current);
		current.setModified(false);

	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int nextCurrentIndex = tabs.indexOf(model) - 1;

		if (tabs.size() == 1)
			createNewDocument();
			
		if (nextCurrentIndex < 0) 
			nextCurrentIndex = 0;
		
		removeTabAt(tabs.indexOf(model));
		setSelectedIndex(nextCurrentIndex);
		tabs.remove(model);
		current = tabs.get(nextCurrentIndex);
		notifyRemoved(model);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return tabs.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return tabs.get(index);
	}
	
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

}
