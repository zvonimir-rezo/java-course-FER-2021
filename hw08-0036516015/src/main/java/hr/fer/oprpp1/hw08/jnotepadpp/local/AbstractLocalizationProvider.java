package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Localization provider with abilities to register, de-register and inform listeners.
 * @author Zvonimir Petar Rezo
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	
	private List<ILocalizationListener> listeners = new ArrayList<>();
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}
	
	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Informs listeners about a localization change.
	 */
	public void fire() {
		listeners.forEach(l -> l.localizationChanged());
	}
	
}
