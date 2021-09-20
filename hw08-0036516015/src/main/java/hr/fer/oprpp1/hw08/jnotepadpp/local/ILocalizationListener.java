package hr.fer.oprpp1.hw08.jnotepadpp.local;


/**
 * Interface used for implementation of a listener for ILocalizationProvider.
 * @author Zvonimir Petar Rezo
 *
 */
public interface ILocalizationListener {
	
	/**
	 * Method called whenever a change which listener listens to occurs.
	 */
	void localizationChanged();
	
}
