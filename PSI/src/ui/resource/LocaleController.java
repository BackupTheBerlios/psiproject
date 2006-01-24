
package ui.resource ;

import java.util.Locale ;

import javax.swing.event.EventListenerList ;

/**
 * LocalController : TODO type description
 * 
 * @author Kassem
 * @version 1.0
 * 
 */
public class LocaleController
{
	/**
	 * Liste contenant tous les LocaleListener
	 */

	private static LocaleController instance = null ;

	protected EventListenerList listenerList = new EventListenerList() ;

	/**
	 * Ajoute un LocaleListener
	 * 
	 * @param l
	 *            LocaleListener a ajouter a la liste
	 */
	public void addLocaleListener (LocaleListener l)
	{
		listenerList.add(LocaleListener.class, l) ;
	}

	/**
	 * Supprime un LocaleListener
	 * 
	 * @param l
	 *            LocaleListener a supprimer
	 */
	public void removeLocaleListener (LocaleListener l)
	{
		listenerList.remove(LocaleListener.class, l) ;
	}

	/**
	 * Change la langue de tous les LocaleListener enregistres
	 */
	public void fireLocaleChanged ()
	{
		LocaleListener[] listeners = (LocaleListener[]) listenerList.getListeners(LocaleListener.class) ;

		for (int i = 0; i < listeners.length; i++ )
		{
			listeners[i].localeChanged() ;
		}
	}

	/**
	 * Change la locale et appelle fireLocaleChanged()
	 * 
	 * @param newLocale
	 *            Nouvelle Locale
	 */
	public void changeLocale (Locale newLocale)
	{
		Locale.setDefault(newLocale) ;
		Bundle.setCurrentLocale(newLocale) ;
		fireLocaleChanged() ;
	}

	public static LocaleController getInstance ()
	{
		if (instance == null)
		{
			instance = new LocaleController() ;

		}

		return instance ;
	}
}
