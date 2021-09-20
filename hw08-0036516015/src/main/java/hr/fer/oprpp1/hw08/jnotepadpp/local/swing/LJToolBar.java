package hr.fer.oprpp1.hw08.jnotepadpp.local.swing;

import javax.swing.JToolBar;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

/**
 * Implementation of a localized JToolBar
 * @author Zvonimir Petar Rezo
 *
 */
public class LJToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	private String key;
	private ILocalizationListener listener;
	private ILocalizationProvider provider;
	
	/**
	 * Constructor with two arguments.
	 * @param k
	 * @param p
	 */
	public LJToolBar(String k, ILocalizationProvider p) {
		key = k;
		provider = p;
		setToolTipText(provider.getString(key));
		listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				setToolTipText(provider.getString(key));
			}
		};
		provider.addLocalizationListener(listener);
	}
	
}
