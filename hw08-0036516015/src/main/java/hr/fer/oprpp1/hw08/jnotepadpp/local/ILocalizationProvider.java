package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Interface for implementation of a language provider.
 * @author Zvonimir Petar Rezo
 *
 */
public interface ILocalizationProvider {

	/**
	 * Adds a listener to provider.
	 * @param l - listener to be added
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Removes a listener from the provider
	 * @param l - listener to remove
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * Gets string for the given key. String is a word in current language that is binded to
	 * that key.
	 * @param key - key for the word/phrase
	 * @return Word/phrase binded to that key.
	 */
	String getString(String key);
	
	/**
	 * Gets current language of the provider.
	 * @return Current language.
	 */
	String getCurrentLanguage();
	
}
