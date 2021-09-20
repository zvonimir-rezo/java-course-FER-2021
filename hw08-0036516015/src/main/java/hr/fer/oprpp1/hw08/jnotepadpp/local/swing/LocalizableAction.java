package hr.fer.oprpp1.hw08.jnotepadpp.local.swing;


import javax.swing.AbstractAction;
import javax.swing.Action;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

/**
 * Localized AbstractAction.
 * @author Zvonimir Petar Rezo
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private String key;
	
	/**
	 * Constructor which takes two arguments.
	 * @param k - key by which action name is searched for
	 * @param provider - provider of words binded to keys
	 */
	public LocalizableAction(String k, ILocalizationProvider provider) {
		key = k;
		putValue(Action.NAME, provider.getString(key));
		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				putValue(Action.NAME, provider.getString(key));
			}
		});
	}

}
