package ui.dialog;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;

/**
 * Activity : TODO type description
 *
 * @author MB
 * @version 1.0
 *
 */
public class TaskDescriptorAdderDialog
{

	private JDialog jDialog = null;  //  @jve:decl-index=0:visual-constraint="99,12"
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel1 = null;
	private JList jList = null;
	/**
	 * This method initializes jDialog	
	 * 	
	 * @return javax.swing.JDialog	
	 */
	private JDialog getJDialog ()
	{
		if (jDialog == null)
		{
			jDialog = new JDialog() ;
			jDialog.setSize(new java.awt.Dimension(354,184));
			jDialog.setTitle("Test Activity");
			jDialog.setContentPane(getJContentPane());
		}
		return jDialog ;
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane ()
	{
		if (jContentPane == null)
		{
			jContentPane = new JPanel() ;
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel2(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane ;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel ()
	{
		if (jPanel == null)
		{
			jPanel = new JPanel() ;
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
		}
		return jPanel ;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton ()
	{
		if (jButton == null)
		{
			jButton = new JButton() ;
			jButton.setText("OK");
		}
		return jButton ;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1 ()
	{
		if (jButton1 == null)
		{
			jButton1 = new JButton() ;
			jButton1.setText("Cancel");
		}
		return jButton1 ;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1 ()
	{
		if (jPanel1 == null)
		{
			jLabel = new JLabel();
			jLabel.setText("Nom de l'activité :");
			jPanel1 = new JPanel() ;
			jPanel1.add(jLabel, null);
			jPanel1.add(getJTextField(), null);
		}
		return jPanel1 ;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField ()
	{
		if (jTextField == null)
		{
			jTextField = new JTextField() ;
			jTextField.setPreferredSize(new java.awt.Dimension(200,20));
		}
		return jTextField ;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2 ()
	{
		if (jPanel2 == null)
		{
			jLabel1 = new JLabel();
			jLabel1.setText("Rôle de l'activité : ");
			jPanel2 = new JPanel() ;
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJList(), null);
		}
		return jPanel2 ;
	}

	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJList ()
	{
		if (jList == null)
		{
			jList = new JList() ;
			jList.setPreferredSize(new java.awt.Dimension(125,22));
		}
		return jList ;
	}

}
