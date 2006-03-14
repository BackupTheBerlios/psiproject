
package ui.resource ;

import java.text.SimpleDateFormat;
import java.util.Locale ;
import java.util.MissingResourceException ;
import java.util.ResourceBundle ;
import java.util.prefs.Preferences;

/**
 * Bundle : main class for using internationalization within the psi software
 * 
 * @author Condé Mickael K.
 * @version 1.0
 * 
 */
public class Bundle
{
	/**
	 * Basename to the resource file, contains the prefix
	 */
	private static final String BASENAME = "ui/resource/label" ;

	/**
	 * Resource used to load the labels (i.e. text) and other stuff
	 */
	private static Locale currentLocale = new Locale(Preferences.userRoot().get("locale", "fr"), "") ;
	
	private static ResourceBundle bundle = ResourceBundle.getBundle(BASENAME, currentLocale) ;	
	
	public static final String DATE_PATTERN = "dd'/'MM'/'yyyy" ;
	
	public static final String TIME_PATTERN = "HH:mm" ;
	
	public static final String LOG_PATTERN = "HH:mm:ss" ;
	
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN) ;
	
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_PATTERN) ;
	
	public static final SimpleDateFormat logFormat = new SimpleDateFormat(LOG_PATTERN) ;

	/**
	 * Gets the label linked to the key in the resource bundle.
	 * 
	 * @author Condé Mickael K.
	 * @version 1.0
	 * 
	 * @param _key
	 * @return String :
	 */
	public static String getText (String _key)
	{
		try
		{
			return bundle.getString(_key) ;
		}
		catch (MissingResourceException e)
		{
			return _key ;
		}

	}
	
	/**
	 * Sets the current locale
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param locale
	 */
	public static void setCurrentLocale (Locale locale)
	{
		bundle = ResourceBundle.getBundle(BASENAME, locale) ;
		currentLocale = locale ;

	}

	/**
	 * Gets the current locale
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @return Returns the currentLocale.
	 */
	public static Locale getCurrentLocale ()
	{
		return currentLocale ;
	}
}
