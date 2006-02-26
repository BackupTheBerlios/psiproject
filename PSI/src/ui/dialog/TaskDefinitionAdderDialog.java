
package ui.dialog ;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import process.exception.DuplicateElementException;
import process.utility.BreakdownElementsControler;

import model.spem2.TaskDescriptor;
import ui.resource.Bundle;
import ui.window.MainFrame;

/**
 * TaskDefinitionAdderDialog : A dialog which allows to add a new task definition
 * 
 * @author Conde Mickael K.
 * @version 1.0
 * 
 */
public class TaskDefinitionAdderDialog extends JDialog
{

	private static final long serialVersionUID = -9198925854230249585L ;

	private TaskDescriptor task = null ;

	private MainFrame owner = null ;

	private JPanel mainContentPane = null ;

	private JPanel mainPanel = null ;

	private JPanel buttonsPanel = null ;

	private JButton addButton = null ;

	private JButton cancelButton = null ;

	private JPanel taskDescriptorPanel = null;

	private JPanel namePanel = null;

	private JPanel descriptionPanel = null;

	private JLabel taskDescriptorLabel = null;

	private JTextField taskDescriptorTextField = null;

	private JLabel nameLabel = null;

	private JTextField nameTextField = null;

	private JLabel descriptionLabel = null;

	private JScrollPane descriptionScrollPane = null;

	private JTextArea descriptionTextArea = null;

	/**
	 * This is the default constructor
	 */
	public TaskDefinitionAdderDialog (MainFrame _owner, TaskDescriptor _task)
	{
		super(_owner) ;
		this.owner = _owner ;
		this.task = _task ;

		initialize() ;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize ()
	{
		this.setTitle(Bundle.getText("TaskDefinitionAdderDialogTitle")) ;
		this.setModal(true);
		this.setResizable(false);
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
			mainPanel.setLayout(new BoxLayout(getMainPanel(), BoxLayout.Y_AXIS));
			mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("TaskDefinitionAdderDialogInfo"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			mainPanel.add(getTaskDescriptorPanel(), null);
			mainPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			mainPanel.add(getNamePanel(), null);
			mainPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			mainPanel.add(getDescriptionPanel(), null);
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
			buttonsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
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
			addButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					// Checking if name is empty
					if (nameTextField.getText().trim().equals(""))
					{
						JOptionPane.showMessageDialog(TaskDefinitionAdderDialog.this, Bundle.getText("TaskDefinitionAdderDialogTaskDefinitionEmpty"), "PSI",
								JOptionPane.ERROR_MESSAGE) ;
					}

					// Then trying to add the new artifact
					else
					{
						try
						{
							BreakdownElementsControler.addTaskDefinitionIntoTaskDescriptor(task, nameTextField.getText(), descriptionTextArea.getText()) ;
							TaskDefinitionAdderDialog.this.dispose() ;
						}
						catch (DuplicateElementException exc)
						{
							JOptionPane.showMessageDialog(TaskDefinitionAdderDialog.this, Bundle.getText("TaskDefinitionAdderDialogTaskDefinitionExists"), "PSI",
									JOptionPane.ERROR_MESSAGE) ;
						}
						catch (Exception exc)
						{
							JOptionPane.showMessageDialog(TaskDefinitionAdderDialog.this, Bundle.getText("TaskDefinitionAdderDialogAddError"), "PSI",
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
					TaskDefinitionAdderDialog.this.dispose() ;
				}
			}) ;
		}
		return cancelButton ;
	}

	/**
	 * This method initializes taskDescriptorPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTaskDescriptorPanel ()
	{
		if (taskDescriptorPanel == null)
		{
			taskDescriptorLabel = new JLabel();
			taskDescriptorLabel.setText(Bundle.getText("TaskDefinitionAdderDialogDescriptorName"));
			taskDescriptorLabel.setPreferredSize(new java.awt.Dimension(150,16));
			taskDescriptorPanel = new JPanel() ;
			taskDescriptorPanel.setLayout(new BoxLayout(getTaskDescriptorPanel(), BoxLayout.X_AXIS));
			taskDescriptorPanel.setMaximumSize(new java.awt.Dimension(389, 20)) ;
			taskDescriptorPanel.add(taskDescriptorLabel, null);
			taskDescriptorPanel.add(getTaskDescriptorTextField(), null);
		}
		return taskDescriptorPanel ;
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
			nameLabel = new JLabel();
			nameLabel.setText(Bundle.getText("TaskDefinitionAdderDialogTaskDefinitionName"));
			nameLabel.setPreferredSize(new java.awt.Dimension(150,16));
			namePanel = new JPanel() ;
			namePanel.setLayout(new BoxLayout(getNamePanel(), BoxLayout.X_AXIS));
			namePanel.setMaximumSize(new java.awt.Dimension(389, 20)) ;
			namePanel.add(nameLabel, null);
			namePanel.add(getNameTextField(), null);
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
			descriptionLabel = new JLabel();
			descriptionLabel.setText(Bundle.getText("TaskDefinitionAdderDialogTaskDefinitionDescription"));
			descriptionLabel.setPreferredSize(new java.awt.Dimension(150,16));
			descriptionPanel = new JPanel() ;
			descriptionPanel.setLayout(new BoxLayout(getDescriptionPanel(), BoxLayout.X_AXIS));
			descriptionPanel.setMaximumSize(new java.awt.Dimension(389, 64)) ;
			descriptionPanel.add(descriptionLabel, null);
			descriptionPanel.add(getDescriptionScrollPane(), null);
		}
		return descriptionPanel ;
	}

	/**
	 * This method initializes taskDescriptorTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTaskDescriptorTextField ()
	{
		if (taskDescriptorTextField == null)
		{
			taskDescriptorTextField = new JTextField(20) ;
			taskDescriptorTextField.setFocusable(false) ;
			taskDescriptorTextField.setText(task.getName()) ;
			taskDescriptorTextField.setEditable(false);
		}
		return taskDescriptorTextField ;
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
					TaskDefinitionAdderDialog.this.addButton.doClick() ;
				}
			}) ;
		}
		return nameTextField ;
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
			descriptionScrollPane.setViewportView(getDescriptionTextArea());
		}
		return descriptionScrollPane ;
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

}
