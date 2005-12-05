
package ui.window ;

import java.awt.HeadlessException ;

import javax.swing.JFrame ;

/**
 * Fenêtre principale de l'application
 * 
 * @author Condé Mickael K.
 * 
 */
public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1 ;

	/**
	 * @throws HeadlessException
	 */
	public MainFrame () throws HeadlessException
	{
		super() ;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new java.awt.Dimension(299,112));
			
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
