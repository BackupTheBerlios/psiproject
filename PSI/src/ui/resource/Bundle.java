
package ui.resource ;

import java.util.Locale ;
import java.util.MissingResourceException ;
import java.util.ResourceBundle ;

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
	private static String BASENAME = "ui/resource/label" ;

	/**
	 * Resource used to load the labels (i.e. text)
	 */
	private static ResourceBundle bundle = ResourceBundle.getBundle(BASENAME,
			Locale.getDefault()) ;

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
	
	public static void setCurrentLocale(Locale locale)
    {
        bundle = ResourceBundle.getBundle(BASENAME,locale);
        Locale.setDefault(locale);
    }
}
