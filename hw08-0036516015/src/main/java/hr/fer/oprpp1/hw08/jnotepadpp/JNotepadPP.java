package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.swing.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.swing.LJToolBar;
import hr.fer.oprpp1.hw08.jnotepadpp.local.swing.LocalizableAction;

/**
 * Simple text file editor called JNotepad++.
 * @author Zvonimir Petar Rezo
 *
 */
public class JNotepadPP extends JFrame {


	private static final long serialVersionUID = 1L;
	private static final String DATE_FORMATTER = "yyyy/MM/dd HH:mm:ss";
	private FormLocalizationProvider provider;
	private Action newBlankDocumentAction;
	private Action saveDocumentAction;
	private Action saveDocumentAsAction;
	private Action loadDocumentAction;
	private Action closeTabAction;
	private Action cutAction;
	private Action copyAction;
	private Action pasteAction;
	private Action statisticsAction;
	private Action exitAppAction;
	private Action enAction;
	private Action hrAction;
	private Action deAction;
	private Action toUppercaseAction;
	private Action toLowercaseAction;
	private Action invertCaseAction;
	private Action ascendingAction;
	private Action descendingAction;
	private Action uniqueAction;
	
	private JMenu toolsMenu;
	

	DefaultMultipleDocumentModel model;
	JLabel statusbarLength = new JLabel();
	JLabel statusbarLine = new JLabel();
	JLabel statusbarColumn = new JLabel();
	JLabel statusbarSelected = new JLabel();
	JLabel time = new JLabel();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JNotepadPP notepad = new JNotepadPP();
			notepad.setVisible(true);
		});
	}

	/**
	 * Default constructor. Calls initGUI().
	 */
	public JNotepadPP() {
		super();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (checkUnsavedWork()) 
					dispose();
			}
		});
		provider = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		provider.connect();
		setTitle("Notepad");
		setSize(1400, 800);
		initGUI();
	}

	/**
	 * Method which calls all other important methods. Used to initialize the user interface.
	 */
	private void initGUI() {
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		model = new DefaultMultipleDocumentModel();
//		model.addChangeListener(e -> {
//			SingleDocumentModel old = model.getCurrentDocument();
//			
//		});
		container.add(model, BorderLayout.CENTER);
		initActions();
		createActions();
		createMenus();
		createToolbars();
		model.createNewDocument();
		
		if (model.getCurrentDocument().getFilePath() == null) {
			setTitle("Untitled");
		} else {
			setTitle(model.getCurrentDocument().getFilePath().getFileName().toString() + " JNotepad++");
		}
		
		model.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {

			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
	
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (previousModel == null && currentModel == null)
					throw new IllegalArgumentException("Previous model or current model can be null, but not both.");
				
				if (model.getCurrentDocument().getFilePath() == null) {
					setTitle("Untitled");
				} else {
					setTitle(model.getCurrentDocument().getFilePath().getFileName().toString() + " JNotepad++");
				}
				
				model.getCurrentDocument().getTextComponent().addCaretListener(e -> updateStatusBar());
				updateStatusBar();
			}
		});
		
		//time
		Timer timer = new Timer(1000, e -> {
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
			time.setText(dateTime.format(formatter));
		});
		timer.start();
		
		model.getCurrentDocument().getTextComponent().addCaretListener(e -> updateStatusBar());
		updateStatusBar();

	}
	
	/**
	 * Initializes all actions notepad has to offer.
	 */
	private void initActions() {
		
		newBlankDocumentAction = new LocalizableAction("new", provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				model.createNewDocument();
				updateStatusBar();
			}

		};
		
		saveDocumentAction = new LocalizableAction("save", provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getCurrentDocument().getFilePath() == null) {
					saveDocumentAsAction.actionPerformed(e);
					return;
				}
				model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());
				JOptionPane.showMessageDialog(JNotepadPP.this, "File has been saved.", "Info",
						JOptionPane.INFORMATION_MESSAGE);
				
				
			}

		};

		saveDocumentAsAction = new LocalizableAction("saveas", provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getCurrentDocument().getFilePath() == null) {
					JFileChooser jfc = new JFileChooser();
					jfc.setDialogTitle("Save document");
					if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(JNotepadPP.this, "File was not saved.", "Info",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					model.getCurrentDocument().setFilePath(jfc.getSelectedFile().toPath());
				}
				model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());

				JOptionPane.showMessageDialog(JNotepadPP.this, "File has been saved.", "Info",
						JOptionPane.INFORMATION_MESSAGE);

			}

		};

		loadDocumentAction = new LocalizableAction("load", provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Load file");
				if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				File fileName = fc.getSelectedFile();
				Path filePath = fileName.toPath();
				if (!Files.isReadable(filePath)) {
					JOptionPane.showMessageDialog(JNotepadPP.this, "Error while trying to load selected file.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				model.loadDocument(filePath);
				
				updateStatusBar();
			}

		};

		closeTabAction = new LocalizableAction("closetab", provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkUnsavedWork())
					model.closeDocument(model.getCurrentDocument());
				updateStatusBar();
			}
			

		};

		cutAction = new LocalizableAction("cut", provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				new DefaultEditorKit.CutAction().actionPerformed(e);
				updateStatusBar();
			}

		};

		copyAction = new LocalizableAction("copy", provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				new DefaultEditorKit.CopyAction().actionPerformed(e);
				updateStatusBar();
			}

		};

		pasteAction = new LocalizableAction("paste", provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				new DefaultEditorKit.PasteAction().actionPerformed(e);
				updateStatusBar();
			}

		};

		statisticsAction = new LocalizableAction("statistics", provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				String currentText = model.getCurrentDocument().getTextComponent().getText();
				int charsCount = currentText.length();

				int spaceCount = 0;
				int newLineCount = 0;
				for (char c : currentText.toCharArray()) {
					if (c == ' ') {
						spaceCount++;
					} else if (c == '\n') {
						newLineCount++;
					}
				}

				int nonBlankCharsCount = charsCount - spaceCount;
				int linesCount = newLineCount + 1;
				
				JOptionPane.showMessageDialog(JNotepadPP.this, "Your document has " + charsCount + " characters, " + nonBlankCharsCount + " non-blank characters and " + linesCount + " lines");
			}

		};

		exitAppAction = new LocalizableAction("exit", provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkUnsavedWork())
					dispose();
				updateStatusBar();
			}

		};
		
		enAction = new LocalizableAction("en", provider) {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		};
		
		hrAction = new LocalizableAction("hr", provider) {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		};
		
		deAction = new LocalizableAction("de", provider) {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		};
		
		
		toUppercaseAction = new LocalizableAction("uppercase", provider) {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = model.getCurrentDocument().getTextComponent();
				int dotIndex = textArea.getCaret().getDot();
				int markIndex = textArea.getCaret().getMark();
				int selectedLength = Math.abs(dotIndex - markIndex);
				int startIndex = Math.min(dotIndex, markIndex);
				
				try {
					textArea.replaceRange(textArea.getText(startIndex, startIndex+selectedLength).toUpperCase(), startIndex, startIndex+selectedLength);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};
		
		
		toLowercaseAction = new LocalizableAction("lowercase", provider) {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = model.getCurrentDocument().getTextComponent();
				int dotIndex = textArea.getCaret().getDot();
				int markIndex = textArea.getCaret().getMark();
				int selectedLength = Math.abs(dotIndex - markIndex);
				int startIndex = Math.min(dotIndex, markIndex);
				
				try {
					textArea.replaceRange(textArea.getText(startIndex, startIndex+selectedLength).toLowerCase(), startIndex, startIndex+selectedLength);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};
		
		
		invertCaseAction = new LocalizableAction("invertcase", provider) {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = model.getCurrentDocument().getTextComponent();
				int dotIndex = textArea.getCaret().getDot();
				int markIndex = textArea.getCaret().getMark();
				int selectedLength = Math.abs(dotIndex - markIndex);
				int startIndex = Math.min(dotIndex, markIndex);
				
				try {
					String text = textArea.getText(startIndex, selectedLength);
					StringBuffer newStr = new StringBuffer(text);
					for (int i = 0; i < text.length(); i++) {
						Character c = text.charAt(i);
						if (Character.isLowerCase(c)) {
							newStr.setCharAt(i, Character.toUpperCase(text.charAt(i)));
						} else {
							newStr.setCharAt(i, Character.toLowerCase(text.charAt(i)));
						}
					}
					textArea.replaceRange(newStr.toString(), startIndex, startIndex+selectedLength);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};
		
		ascendingAction = new LocalizableAction("ascending", provider) {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sortText(true);
			}
		};
		
		descendingAction = new LocalizableAction("descending", provider) {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sortText(false);
			}
		};
		
		uniqueAction = new LocalizableAction("unique", provider) {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea textArea = model.getCurrentDocument().getTextComponent();
				int dotIndex = textArea.getCaret().getDot();
				int markIndex = textArea.getCaret().getMark();
				int selectedLength = Math.abs(dotIndex - markIndex);
				int startIndex = Math.min(dotIndex, markIndex);
				Document doc =  textArea.getDocument();
				int startIndexLine;
				try {
					startIndexLine = textArea.getLineStartOffset(textArea.getLineOfOffset(startIndex));
					selectedLength = textArea.getLineEndOffset(textArea.getLineOfOffset(selectedLength + startIndex));
					String str = textArea.getText(startIndexLine, selectedLength - startIndexLine);
					
					String[] splitted = str.split("\n");
					Set<String> lines = new HashSet<>(Arrays.asList(splitted));
					
					StringBuilder sb = new StringBuilder();
					String result = "";
					for (String l : lines) {
						sb.append(l + "\n");
					}
					
					sb.setLength(sb.length() - 1);
					result = sb.toString();
					
					doc.remove(startIndexLine, selectedLength);
					doc.insertString(startIndexLine, result, null);
					
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		};
	}
	
	/**
	 * Sorts selected lines using rules of currently defined language.
	 * @param ascending - if true, sorts in ascending order, sorts in descending otherwise
	 */
	private void sortText(boolean ascending) {
		JTextArea textArea = model.getCurrentDocument().getTextComponent();
		int dotIndex = textArea.getCaret().getDot();
		int markIndex = textArea.getCaret().getMark();
		int selectedLength = Math.abs(dotIndex - markIndex);
		int startIndex = Math.min(dotIndex, markIndex);
		Document doc =  textArea.getDocument();
		int startIndexLine;
		try {
			startIndexLine = textArea.getLineStartOffset(textArea.getLineOfOffset(startIndex));
			selectedLength = textArea.getLineEndOffset(textArea.getLineOfOffset(selectedLength + startIndex));
			String str = textArea.getText(startIndexLine, selectedLength - startIndexLine);
			
			// sorting
			StringBuilder sb = new StringBuilder();
			String result = "";
			Locale locale = new Locale(LocalizationProvider.getInstance().getCurrentLanguage());
			Collator collator = Collator.getInstance(locale);
			String[] splitted = str.split("\n");
			List<String> list = Arrays.asList(splitted);
			if (ascending) {
				list.sort(collator);
			} else {
				list.sort(collator.reversed());
			}
			list.forEach(s -> sb.append(s + "\n"));
			sb.setLength(sb.length() - 1);
			result = sb.toString();
			
			doc.remove(startIndexLine, selectedLength);
			doc.insertString(startIndexLine, result, null);
			
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Called when status bar needs to be updated.
	 */
	private void updateStatusBar() {
		JTextArea currentTextArea = model.getCurrentDocument().getTextComponent();
		statusbarLength.setText("length: " + currentTextArea.getText().length());
		int lineNumber;
		int columnNumber;
		try {
			lineNumber = currentTextArea.getLineOfOffset(currentTextArea.getCaretPosition());
			columnNumber = currentTextArea.getCaretPosition() - currentTextArea.getLineStartOffset(lineNumber);
			int dotIndex = currentTextArea.getCaret().getDot();
			int markIndex = currentTextArea.getCaret().getMark();
			int selectedLength = Math.abs(dotIndex - markIndex);
			statusbarLine.setText("ln: " + (lineNumber + 1));
			statusbarColumn.setText("col: " + (columnNumber + 1));
			statusbarSelected.setText("sel: " + selectedLength);
			
			if (selectedLength != 0) {
				toolsMenu.setEnabled(true);
			} else {
				toolsMenu.setEnabled(false);
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
			
	}

	/**
	 * Called when user wants to close the program.
	 * @return True if the program should be closed, false otherwise.
	 */
	private boolean checkUnsavedWork() {
		for (int i = 0; i < model.getNumberOfDocuments(); i++) {
			if (model.getDocument(i).isModified()) {
				int input = JOptionPane.showConfirmDialog(JNotepadPP.this,
						"Your work is not saved. Close anyway?", "You sure?", JOptionPane.YES_NO_OPTION);
				return input == JOptionPane.YES_OPTION;
			}
		}
		return true;
	}
	
	/**
	 * Binds actions to keyboard events.
	 */
	private void createActions() {
		newBlankDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newBlankDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		
		loadDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		loadDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		
		saveDocumentAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		saveDocumentAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		
		closeTabAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		closeTabAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		
		statisticsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Y"));
		statisticsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Y);
		
		exitAppAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exitAppAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		
		enAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control G"));
		enAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
		
		hrAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control H"));
		hrAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);
		
		deAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control J"));
		deAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_J);
		
		toLowercaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		toLowercaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		
		toUppercaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUppercaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		
		invertCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		invertCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		
		ascendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control B"));
		ascendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);
		
		descendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		descendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		
		uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control M"));
		uniqueAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_M);
	
	}

	/**
	 * Creates all menus.
	 */
	private void createMenus() {
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new LJMenu("file", provider);
		JMenuItem newBlank = new JMenuItem(newBlankDocumentAction);
		JMenuItem save = new JMenuItem(saveDocumentAction);
		JMenuItem load = new JMenuItem(loadDocumentAction);
		JMenuItem saveAs = new JMenuItem(saveDocumentAsAction);
		
		fileMenu.add(newBlank);
		fileMenu.add(load);
		fileMenu.add(save);
		fileMenu.add(saveAs);
		menubar.add(fileMenu);
		
		JMenu editMenu = new LJMenu("edit", provider);
		JMenuItem closeTab = new JMenuItem(closeTabAction);
		JMenuItem cut = new JMenuItem(cutAction);
		JMenuItem copy = new JMenuItem(copyAction);
		JMenuItem paste = new JMenuItem(pasteAction);
		JMenuItem statistics = new JMenuItem(statisticsAction);
		
		editMenu.add(closeTab);
		editMenu.add(cut);
		editMenu.add(copy);
		editMenu.add(paste);
		editMenu.add(statistics);
		menubar.add(editMenu);
		
		JMenu languageMenu = new LJMenu("languages", provider);
		JMenuItem en = new JMenuItem(enAction);
		JMenuItem hr = new JMenuItem(hrAction);
		JMenuItem de = new JMenuItem(deAction);
		
		languageMenu.add(en);
		languageMenu.add(hr);
		languageMenu.add(de);
		menubar.add(languageMenu);
		
		toolsMenu = new LJMenu("tools", provider);
		
		JMenu subMenuCase = new LJMenu("changecase", provider);
		JMenu subMenuSort = new LJMenu("sort", provider);
		
		JMenuItem uppercase = new JMenuItem(toUppercaseAction);
		JMenuItem lowercase = new JMenuItem(toLowercaseAction);
		JMenuItem invertCase = new JMenuItem(invertCaseAction);
		
		subMenuCase.add(uppercase);
		subMenuCase.add(lowercase);
		subMenuCase.add(invertCase);
		
		JMenuItem ascending = new JMenuItem(ascendingAction);
		JMenuItem descending = new JMenuItem(descendingAction);
		JMenuItem unique = new JMenuItem(uniqueAction);
		
		subMenuSort.add(ascending);
		subMenuSort.add(descending);
		subMenuSort.add(unique);
		
		toolsMenu.add(subMenuCase);
		toolsMenu.add(subMenuSort);
		menubar.add(toolsMenu);
		
		setJMenuBar(menubar);
	}

	/**
	 * Creates all toolbars.
	 */
	private void createToolbars() {
		JToolBar toolbar = new LJToolBar("tools", provider);
		toolbar.setFloatable(true);
		
		JButton newBlankButton = new JButton(newBlankDocumentAction);
		JButton saveButton = new JButton(saveDocumentAction);
		JButton saveAsButton = new JButton(saveDocumentAsAction);
		JButton loadButton = new JButton(loadDocumentAction);
		JButton closeTabButton = new JButton(closeTabAction);
		JButton cutButton = new JButton(cutAction);
		JButton copyButton = new JButton(copyAction);
		JButton pasteButton = new JButton(pasteAction);
		JButton statisticsButton = new JButton(statisticsAction);

		toolbar.add(newBlankButton);
		toolbar.add(saveButton);
		toolbar.add(saveAsButton);
		toolbar.add(loadButton);
		toolbar.add(closeTabButton);
		toolbar.add(cutButton);
		toolbar.add(copyButton);
		toolbar.add(pasteButton);
		toolbar.add(statisticsButton);
		add(toolbar, BorderLayout.PAGE_START);
		
		JToolBar statusBar = new LJToolBar("status", provider);
		JPanel panelCont = new JPanel();
		
		statusBar.setFloatable(true);
		statusBar.setLayout(new BorderLayout());	
		statusBar.add(statusbarLength, BorderLayout.WEST);
		
		panelCont.add(statusbarLine);
		panelCont.add(statusbarColumn);
		panelCont.add(statusbarSelected);
		
		statusBar.add(panelCont, BorderLayout.CENTER);
		statusBar.add(time, BorderLayout.EAST);
		add(statusBar, BorderLayout.PAGE_END);
		
	}

}
