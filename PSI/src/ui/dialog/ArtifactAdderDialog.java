
package ui.dialog ;

import java.awt.BorderLayout ;
import java.awt.Dimension ;

import javax.swing.Box ;
import javax.swing.BoxLayout ;
import javax.swing.JButton ;
import javax.swing.JDialog ;
import javax.swing.JLabel ;
import javax.swing.JOptionPane ;
import javax.swing.JPanel ;
import javax.swing.JScrollPane ;
import javax.swing.JTextArea ;
import javax.swing.JTextField ;

import model.spem2.WorkProductDescriptor ;
import process.exception.DuplicateElementException ;
import process.utility.BreakdownElementsControler ;
import ui.resource.Bundle ;
import ui.window.MainFrame ;

/**
 * ArtifactAdderDialog : A dialog which allows to add a new artifact to a product
 * 
 * @author Conde Mickael K.
 * @version 1.0
 * 
 */
public class ArtifactAdderDialog extends JDialog
{
	private static final long serialVersionUID = -6028085184677360818L ;

	private WorkProductDescriptor product ;

	private MainFrame owner ;

	private JPanel mainContentPane = null ;

	private JPanel mainPanel = null ;

	private JPanel buttonsPanel = null ;

	private JButton addButton = null ;

	private JButton cancelButton = null ;

	private JPanel productPanel = null ;

	private JPanel namePanel = null ;

	private JPanel descriptionPanel = null ;

	private JLabel productLabel = null ;

	private JTextField productTextField = null ;

	private JLabel nameLabel = null ;

	private JTextField nameTextField = null ;

	private JLabel descriptionLabel = null ;

	private JTextArea descriptionTextArea = null ;

	private JScrollPane descriptionScrollPane = null ;

	/**
	 * Constructor
	 * 
	 * @param _owner
	 * @param _product
	 */
	public ArtifactAdderDialog (MainFrame _owner, WorkProductDescriptor _product)
	{
		super(_owner) ;
		this.product = _product ;
		this.owner = _owner ;

		initialize() ;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize ()
	{
		this.setTitle(Bundle.getText("ArtifactAdderDialogTitle")) ;
		this.setModal(true) ;
		this.setResizable(false) ;
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE) ;
		this.setContentPane(getMainContentPane()) ;
		this.setBounds(owner.getX() + owner.getWidth() / 2 - 200, owner.getY() + owner.getHeight() / 2 - 110, 400, 220) ;
		this.setVisible(true) ;
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
			mainContentPane.add(getMainPanel(), java.awt.BorderLayout.CENTER) ;
			mainContentPane.add(getButtonsPanel(), java.awt.BorderLayout.SOUTH) ;
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
			mainPanel = new JPanel() ;
			mainPanel.setLayout(new BoxLayout(getMainPanel(), BoxLayout.Y_AXIS)) ;
			mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("ArtifactAdderDialogInfo"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			mainPanel.add(getProductPanel(), null) ;
			mainPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			mainPanel.add(getNamePanel(), null) ;
			mainPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			mainPanel.add(getDescriptionPanel(), null) ;
			mainPanel.add(Box.createVerticalGlue()) ;
		}
		return mainPanel ;
	}

	/**
	 * This method initializes buttonsPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getButtonsPanel ()
	{
		if (buttonsPanel == null)
		{
			buttonsPanel = new JPanel() ;
			buttonsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED)) ;
			buttonsPanel.add(getAddButton(), null) ;
			buttonsPanel.add(getCancelButton(), null) ;
		}
		return buttonsPanel ;
	}

	/**
	 * This method initializes addButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddButton ()
	{
		if (addButton == null)
		{
			addButton = new JButton(Bundle.getText("Add")) ;
			addButton.setSelected(true) ;
			addButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					// Checking if name is empty
					if (nameTextField.getText().trim().equals(""))
					{
						JOptionPane.showMessageDialog(ArtifactAdderDialog.this, Bundle.getText("ArtifactAdderDialogArtifactEmpty"), "PSI",
								JOptionPane.ERROR_MESSAGE) ;
					}

					// Then trying to add the new artifact
					else
					{
						try
						{
							BreakdownElementsControler.addArtifactIntoWorkProductDescriptor(product, nameTextField.getText(), descriptionTextArea.getText()) ;
							ArtifactAdderDialog.this.dispose() ;
						}
						catch (DuplicateElementException exc)
						{
							JOptionPane.showMessageDialog(ArtifactAdderDialog.this, Bundle.getText("ArtifactAdderDialogArtifactExists"), "PSI",
									JOptionPane.ERROR_MESSAGE) ;
						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(ArtifactAdderDialog.this, Bundle.getText("ArtifactAdderDialogAddError"), "PSI",
									JOptionPane.ERROR_MESSAGE) ;
						}
					}
				}
			}) ;
		}
		return addButton ;
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
			cancelButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					ArtifactAdderDialog.this.dispose() ;
				}
			}) ;
		}
		return cancelButton ;
	}

	/**
	 * This method initializes productPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getProductPanel ()
	{
		if (productPanel == null)
		{
			productLabel = new JLabel() ;
			productLabel.setText(Bundle.getText("ArtifactAdderDialogProductName")) ;
			productLabel.setPreferredSize(new java.awt.Dimension(150, 16)) ;
			productPanel = new JPanel() ;
			productPanel.setMaximumSize(new java.awt.Dimension(389, 20)) ;
			productPanel.setLayout(new BoxLayout(getProductPanel(), BoxLayout.X_AXIS)) ;
			productPanel.add(productLabel, null) ;
			productPanel.add(getProductTextField(), null) ;
		}
		return productPanel ;
	}

	/**
	 * This method initializes namePanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getNamePanel ()
	{
		if (namePanel == null)
		{
			nameLabel = new JLabel() ;
			nameLabel.setText(Bundle.getText("ArtifactAdderDialogArtifactName")) ;
			nameLabel.setPreferredSize(new java.awt.Dimension(150, 16)) ;
			namePanel = new JPanel() ;
			namePanel.setMaximumSize(new java.awt.Dimension(389, 20)) ;
			namePanel.setLayout(new BoxLayout(getNamePanel(), BoxLayout.X_AXIS)) ;
			namePanel.add(nameLabel, null) ;
			namePanel.add(getNameTextField(), null) ;
		}
		return namePanel ;
	}

	/**
	 * This method initializes descriptionPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getDescriptionPanel ()
	{
		if (descriptionPanel == null)
		{
			descriptionLabel = new JLabel() ;
			descriptionLabel.setText(Bundle.getText("ArtifactAdderDialogArtifactDescription")) ;
			descriptionLabel.setPreferredSize(new java.awt.Dimension(150, 16)) ;
			descriptionPanel = new JPanel() ;
			descriptionPanel.setMaximumSize(new java.awt.Dimension(389, 64)) ;
			descriptionPanel.setLayout(new BoxLayout(getDescriptionPanel(), BoxLayout.X_AXIS)) ;
			descriptionPanel.add(descriptionLabel, null) ;
			descriptionPanel.add(getDescriptionScrollPane(), null) ;
		}
		return descriptionPanel ;
	}

	/**
	 * This method initializes productTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getProductTextField ()
	{
		if (productTextField == null)
		{
			productTextField = new JTextField(20) ;
			productTextField.setFocusable(false) ;
			productTextField.setEditable(false) ;
			productTextField.setText(product.getName()) ;
		}
		return productTextField ;
	}

	/**
	 * This method initializes nameTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getNameTextField ()
	{
		if (nameTextField == null)
		{
			nameTextField = new JTextField(20) ;
			nameTextField.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					ArtifactAdderDialog.this.addButton.doClick() ;
				}
			}) ;
		}
		return nameTextField ;
	}

	/**
	 * This method initializes descriptionTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getDescriptionTextArea ()
	{
		if (descriptionTextArea == null)
		{
			descriptionTextArea = new JTextArea(4, 20) ;
		}
		return descriptionTextArea ;
	}

	/**
	 * This method initializes descriptionScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getDescriptionScrollPane ()
	{
		if (descriptionScrollPane == null)
		{
			descriptionScrollPane = new JScrollPane() ;
			descriptionScrollPane.setViewportView(getDescriptionTextArea()) ;
		}
		return descriptionScrollPane ;
	}

}
