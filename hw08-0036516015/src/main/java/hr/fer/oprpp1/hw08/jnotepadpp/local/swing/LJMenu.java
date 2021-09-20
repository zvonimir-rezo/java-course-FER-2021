package hr.fer.oprpp1.hw08.jnotepadpp.local.swing;

import javax.swing.JMenu;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

/**
 * Implementation of localized JMenu
 * @author Zvonimir Petar Rezo
 *
 */
public class LJMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	private String key;
	private ILocalizationListener listener;
	private ILocalizationProvider provider;
	
	/**
	 * Constructor with two arguments
	 * @param k - key
	 * @param p - provider
	 */
	public LJMenu(String k, ILocalizationProvider p) {
		key = k;
		provider = p;
		setText(provider.getString(key));
		listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				setText(provider.getString(key));
			}
		};
		provider.addLocalizationListener(listener);
	}
	
}
