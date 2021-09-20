package hr.fer.oprpp1.hw08.jnotepadpp.local.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProviderBridge;

/**
 * 
 * @author Zvonimir Petar Rezo
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	
	/**
	 * Constructor with two arguments.
	 * @param p - parent provider
	 * @param frame - frame to which the constructor adds a window listener
	 */
	public FormLocalizationProvider(ILocalizationProvider p, JFrame frame) {
		super(p);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
			
		});
	}

}
