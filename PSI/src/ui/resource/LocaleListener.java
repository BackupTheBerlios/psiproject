package ui.resource;

import java.util.EventListener;

/**
 * LocaleListner : TODO type description
 *
 * @author Administrateur
 * @version 1.0
 *
 */
public interface LocaleListener extends EventListener 
{
    /**
     * Methode changeant la langue
     */    
    public void localeChanged (); 

}
