
package ui.window ;

import java.awt.BorderLayout ;
import java.awt.Dimension ;
import java.awt.Toolkit ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;

import javax.swing.ImageIcon ;
import javax.swing.JFrame ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;
import javax.swing.JWindow ;
import javax.swing.Timer ;

/**
 * SplashScreen : Allows to display a plash screen when this software is started.
 * 
 * @author Mat.
 * @version 1.0
 * 
 */
public class SplashScreen extends JWindow
{
	private static final long serialVersionUID = -6611575714213200749L ;

	private JPanel jPanelImage = null ;

	private JLabel jLabelImage = null ;

	/**
	 * 
	 * Constructor of this splash screen.
	 * 
	 * @param _parentFrame
	 *            Her parent frame.
	 * @param _seconds
	 *            The number of seconds for the display of this splash screen.
	 */
	public SplashScreen (JFrame _parentFrame, int _seconds)
	{
		super(_parentFrame) ;
		this.getContentPane().setLayout(new BorderLayout()) ;
		this.getContentPane().add(getJPanelImage(), BorderLayout.CENTER) ;
		this.pack() ;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize() ;
		Dimension windowSize = this.getPreferredSize() ;
		this.setLocation(screenSize.width / 2 - (windowSize.width / 2), screenSize.height / 2 - (windowSize.height / 2)) ;
		this.setVisible(false) ;
		this.open(_seconds) ;
	}

	/**
	 * 
	 * Allows to have the panel containing the image.
	 * 
	 * @author MaT.
	 * @version 1.0
	 * 
	 * @return the image panel.
	 */
	public JPanel getJPanelImage ()
	{
		this.jPanelImage = new JPanel() ;
		this.jLabelImage = new JLabel(new ImageIcon(getClass().getResource("/ui/resource/psisplash500_300.png"))) ;
		this.jPanelImage.add(this.jLabelImage) ;
		this.jPanelImage.setVisible(true) ;
		return this.jPanelImage ;
	}

	/**
	 * 
	 * Allows to open this splash screen during _seconds seconds.
	 * 
	 * @author MaT.
	 * @version 1.0
	 * 
	 * @param _seconds
	 *            The number of seconds for the display of this splash screen.
	 */
	public void open (int _seconds)
	{
		Timer localtimer = new Timer(_seconds * 1000, new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				((Timer) event.getSource()).stop() ;
				close() ;
			} ;
		}) ;

		localtimer.start() ;

		this.setVisible(true) ;
	}

	/**
	 * 
	 * Allows to close this splash screen.
	 * 
	 * @author MaT.
	 * @version 1.0
	 * 
	 */
	public void close ()
	{
		this.setVisible(false) ;
		this.dispose() ;
	}

}
