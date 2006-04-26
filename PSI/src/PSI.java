import java.util.MissingResourceException;

import ui.window.MainFrame;

/**
 * PSI : Main class of the application
 * 
 * @author Conde Mickael K.
 * @version 1.0
 * 
 */
public final class PSI
{
	/**
	 * Main method for this application
	 * @author Conde Mickaël K.
	 * 
	 * @param args command args
	 */
	public static void main (String[] args)
	{		
		try
		{			
			MainFrame localMainFrame = new MainFrame() ;
			localMainFrame.setVisible(true) ;
		}
		catch (MissingResourceException e1)
		{
			System.out.println("PSI> Cannot load resource file, exiting.") ;
			System.exit(58) ;
		}
		
		catch (Exception ef)
		{
			System.out.println("PSI> Unknown exception occured while loading, exiting.") ;
			System.exit(59) ;
		}

	}

}
