
package ui.dialog ;

import java.awt.BorderLayout ;

import javax.swing.JFrame ;
import javax.swing.JPanel ;
import javax.swing.JDialog ;
import javax.swing.JProgressBar ;

/**
 * ProgressDialog : A progress bar simulation
 * 
 * @author Administrateur
 * @version 1.0
 * 
 */
public class ProgressDialog extends JDialog
{
	private static final long serialVersionUID = -8283875527881698361L ;
	private JPanel mainPanel = null;
	private JProgressBar progressBar = null;
	/**
	 * This is the default constructor
	 */
	public ProgressDialog (JFrame _owner)
	{
		super(_owner) ;
		initialize() ;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize ()
	{
        this.setContentPane(getMainPanel());
		this.setVisible(true) ;
		this.setModal(true);
		this.setPreferredSize(new java.awt.Dimension(250,100));
		this.setSize(new java.awt.Dimension(250,100));
	}

	/**
	 * This method initializes mainPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getMainPanel ()
	{
		if (mainPanel == null)
		{
			mainPanel = new JPanel() ;
			mainPanel.setLayout(new BorderLayout());
			mainPanel.add(getProgressBar(), java.awt.BorderLayout.CENTER);
		}
		return mainPanel ;
	}

	/**
	 * This method initializes progressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getProgressBar ()
	{
		if (progressBar == null)
		{
			progressBar = new JProgressBar() ;
			progressBar.setIndeterminate(true);
		}
		return progressBar ;
	}

	

}
