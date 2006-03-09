
package ui.dialog ;

import java.awt.BorderLayout ;
import javax.swing.JPanel ;
import javax.swing.JDialog ;

import model.Project;

import ui.resource.Bundle;
import ui.window.MainFrame;
import javax.swing.JButton ;
import javax.swing.JLabel ;
import javax.swing.JTextField ;

/**
 * IterationAdderDialog : Dialog that allows to add/set the current iteration for the project
 *
 * @author Conde Mickael K.
 * @version 1.0
 *
 */
public class IterationAdderDialog extends JDialog
{
	private static final long serialVersionUID = 635291606291371254L ;
	
	private JPanel mainContentPane = null ;
	
	private MainFrame owner = null ;
	
	private Project project = null ;
	
	private boolean optional = true ;

	private JPanel mainPanel = null;

	private JPanel buttonPanel = null;

	private JButton okButton = null;

	private JButton cancelButton = null;

	private JLabel numberLabel = null;

	private JTextField numberTextField = null;

	/**
	 * This is the default constructor
	 */
	public IterationAdderDialog (MainFrame _owner, Project _project, boolean _optional)
	{
		super() ;
		
		this.owner = _owner ;
		this.project = _project ;
		this.optional = _optional ;
		
		initialize() ;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize ()
	{
		this.setSize(300, 200) ;
		this.setContentPane(getMainContentPane()) ;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getMainContentPane ()
	{
		if (mainContentPane == null)
		{
			mainContentPane = new JPanel() ;
			mainContentPane.setLayout(new BorderLayout()) ;
			mainContentPane.add(getMainPanel(), java.awt.BorderLayout.CENTER);
			mainContentPane.add(getButtonPanel(), java.awt.BorderLayout.SOUTH);
		}
		return mainContentPane ;
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
			numberLabel = new JLabel();
			numberLabel.setText(Bundle.getText("IterationAdderDialogNum"));
			mainPanel = new JPanel() ;
			mainPanel.add(numberLabel, null);
			mainPanel.add(getNumberTextField(), null);
		}
		return mainPanel ;
	}

	/**
	 * This method initializes buttonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonPanel ()
	{
		if (buttonPanel == null)
		{
			buttonPanel = new JPanel() ;
			buttonPanel.add(getOkButton(), null);
			buttonPanel.add(getCancelButton(), null);
		}
		return buttonPanel ;
	}

	/**
	 * This method initializes okButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOkButton ()
	{
		if (okButton == null)
		{
			okButton = new JButton(Bundle.getText("OK")) ;
		}
		return okButton ;
	}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButton ()
	{
		if (cancelButton == null)
		{
			cancelButton = new JButton(Bundle.getText("Cancel")) ;
		}
		return cancelButton ;
	}

	/**
	 * This method initializes numberTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNumberTextField ()
	{
		if (numberTextField == null)
		{
			numberTextField = new JTextField(3) ;
		}
		return numberTextField ;
	}

}
