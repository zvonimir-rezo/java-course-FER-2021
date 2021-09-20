package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Implementation of a language provider.
 * @author Zvonimir Petar Rezo
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	
	private String language;
	private ResourceBundle bundle;
	private static LocalizationProvider instance;

	/**
	 * Default constructor.
	 */
	public LocalizationProvider() {
		language = "en";
		changeBundle();
	}
	
	/**
	 * Gets current instance of this class stored in static variable instance.
	 * @return Current instance of this class. If current instance is null, make a new instance.
	 */
	public static LocalizationProvider getInstance() {
		if (instance == null) {
			instance = new LocalizationProvider();
		}
		return instance;
	}
	
	/**
	 * Sets current language.
	 * @param language - language to be set
	 */
	public void setLanguage(String language) {
		this.language = language;
		changeBundle();
		fire();
	}
	
	/**
	 * Sets current bundle.
	 */
	private void changeBundle() {
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
	}
	
	/**
	 * Gets string binded to given key.
	 * @param key - key to search by
	 * @return String binded to a key.
	 */
	public String getString(String key) {
		return bundle.getString(key);
	}
	
	@Override
	public String getCurrentLanguage() {
		return language;
	}
	
}
