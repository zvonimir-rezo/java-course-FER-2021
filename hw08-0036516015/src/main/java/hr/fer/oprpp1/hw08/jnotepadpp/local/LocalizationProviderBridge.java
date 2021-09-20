package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Implementation of a bridge to LocalizationProvider.
 * Proxy between {@link ILocalizationProvider} object and components using it.
 * @author Zvonimir Petar Rezo
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	
	private boolean connected = false;
	private ILocalizationProvider provider;
	private ILocalizationListener listener;
	private String language;

	/**
	 * Constructor which takes one argument.
	 * @param p - provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider p) {
		provider = p;
		listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}
	
	/**
	 * Disconnects from localization provider.
	 */
	public void disconnect() {
		language = provider.getCurrentLanguage();
		provider.removeLocalizationListener(listener);
		connected = false;
	}
	
	/**
	 * Connects to localization provider.
	 */
	public void connect() {
		if (!connected) {
			if (provider.getCurrentLanguage() != language)
				language = provider.getCurrentLanguage();
			provider.addLocalizationListener(listener);
			connected = true;
		}
		
	}
	
	/**
	 * Gets string binded to given key
	 * @param key - key to search by
	 * @return String binded to given key.
	 */
	public String getString(String key) {
		return provider.getString(key);
	}
	
	@Override
	public String getCurrentLanguage() {
		return provider.getCurrentLanguage();
	}
	

	
}
