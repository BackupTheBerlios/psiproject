
package ui.misc ;

import javax.swing.JPanel ;
import javax.swing.JLabel ;
import java.awt.BorderLayout ;
import javax.swing.ImageIcon ;

import ui.resource.Bundle ;

/**
 * MainPanel : Some kind of welcome panel
 * 
 * @author Conde Mickael K.
 * @version 1.0
 * 
 */
public class MainPanel extends JPanel
{
	private static final long serialVersionUID = -2211854854454368505L ;

	private JLabel textLabel = null ;

	private JLabel welcomeLabel = null ;

	private JLabel iconLabel = null ;

	/**
	 * This is the default constructor
	 */
	public MainPanel ()
	{
		super() ;
		initialize() ;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize ()
	{
		iconLabel = new JLabel() ;
		iconLabel.setIcon(new ImageIcon(getClass().getResource("/ui/resource/psilogo150_150.png"))) ;
		iconLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM) ;
		textLabel = new JLabel() ;
		textLabel.setText(Bundle.getText("MainPanelMessage")) ;
		textLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP) ;
		this.setLayout(new BorderLayout()) ;
		this.setSize(300, 200) ;
		this.setBackground(java.awt.Color.white) ;
		this.add(getWelcomeLabel(), java.awt.BorderLayout.NORTH) ;
		this.add(textLabel, java.awt.BorderLayout.CENTER) ;
		this.add(iconLabel, java.awt.BorderLayout.WEST) ;
	}

	/**
	 * This method initializes welcomeLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getWelcomeLabel ()
	{
		if (welcomeLabel == null)
		{
			welcomeLabel = new JLabel() ;
			welcomeLabel.setText(Bundle.getText("MainPanelWelcome")) ;
		}
		return welcomeLabel ;
	}

}
