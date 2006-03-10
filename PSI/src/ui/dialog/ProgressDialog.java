
package ui.dialog ;

import java.awt.BorderLayout ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame ;
import javax.swing.JPanel ;
import javax.swing.JDialog ;
import javax.swing.JProgressBar ;
import javax.swing.Timer;

import ui.window.MainFrame;

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
	private MainFrame owner = null ;
	private JProgressBar progressBar = null;
	private Timer timer = null;
	
	
	/**
	 * This is the default constructor
	 */
	public ProgressDialog (MainFrame _owner)
	{
		super() ;
		this.owner= _owner; 
		//initialize() ;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initialize ()
	{
        this.setContentPane(getMainPanel());
        
		this.setVisible(true) ;
		this.setModal(true);
		this.setLocationRelativeTo(owner) ;
		this.pack(); 
		this.setSize(new java.awt.Dimension(250,50));
		
		//this.setSize(new java.awt.Dimension(250,100));
		this.timer = new Timer(1000,new ActionListener(){
			public void actionPerformed (ActionEvent _e)
			{
				progressBar.setString("Waiting...");
				
			}
		});
		this.timer.start();
		
		
		

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
			progressBar = new JProgressBar(0,100) ;
			progressBar.setStringPainted(true);
			
			progressBar.setIndeterminate(true);
		}
		return progressBar ;
	}

	public void stopTimer(){
		timer.stop(); 
	}

}
