
package ui.dialog ;

import java.awt.BorderLayout ;
import java.awt.Dimension;

import javax.swing.JPanel ;
import javax.swing.JDialog ;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListModel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;

import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JButton;

import ui.resource.Bundle;
import ui.window.MainFrame;

import model.spem2.Activity;
import model.spem2.BreakdownElement;
import model.spem2.DeliveryProcess;
import model.spem2.RoleDescriptor;
import model.spem2.TaskDescriptor;

/**
 * TaskDescriptorAdderDialog : TODO type description
 *
 * @author Kurvin
 * @version 1.0
 *
 */
public class TaskDescriptorAdderDialog extends JDialog implements ActionListener
{

	private JPanel jContentPane = null ;
	private JPanel southPanel = null;
	private JPanel centralPanel = null;
	private JPanel namePanel = null;
	private JPanel descriptionPanel = null;
	private JPanel rolePanel = null;
	private JLabel nameLabel = null;
	private JTextField nameTextField = null;
	private JLabel descriptionLabel = null;
	private JTextArea descriptionTextArea = null;
	private JLabel roleLabel = null;
	private JList roleList = null;
	private JButton okButton = null;
	private JButton cancelButton = null;
	private Vector <RoleDescriptor> roles = new Vector();
	private Vector <String> roleNames =new Vector();
	Collection <BreakdownElement> bdeCollection;
	Activity parentActivity;

	/**
	 * This is the default constructor
	 */
	public TaskDescriptorAdderDialog (MainFrame _mainFrame, Activity _activity)
	{
		super() ;
		parentActivity = _activity;
		//retrieving roles preset in the process and adding them to the list model
		Collection <BreakdownElement> bdeCollection =_mainFrame.getProject().getProcess().getNestedElements();
		Iterator it = bdeCollection.iterator();
		while(it.hasNext())
		{
			BreakdownElement element = (BreakdownElement)it.next();
			if(element instanceof RoleDescriptor)
			{	
				RoleDescriptor r = (RoleDescriptor)element;
				roles.add(r);
				System.out.println(r.getName());
				roleNames.add(((RoleDescriptor)element).getName());
			}
		}
		
		//roleList = new JList(roleNames);
		initialize(roleNames);
		//namePanel = getNamePanel();
		//descriptionPanel = getDescriptionPanel();
		
		
		//sets events on the buttons
		okButton.setMnemonic(KeyEvent.VK_O);
		okButton.setActionCommand("ok");
		cancelButton.setMnemonic(KeyEvent.VK_C);
		cancelButton.setActionCommand("cancel");
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		this.setVisible(true);
	}

	
	/*
	 * This method handles actions from the dialog
	 */
	public void actionPerformed(ActionEvent e) 
	{
	    
		if ("ok".equals(e.getActionCommand()))
		{
			//finds and generates an index (hightest index in the activity indices)
				
			Iterator it1 = bdeCollection.iterator();
			int max =0;
			int index;
			String nwIndex;
			while(it1.hasNext())
			{
				BreakdownElement bdElement =(BreakdownElement)it1.next();
				
				if (bdElement instanceof TaskDescriptor)
				{
					TaskDescriptor task =(TaskDescriptor)bdElement;
					try
					{
						index =Integer.parseInt(task.getId().split("_")[0]);
					}
					catch (PatternSyntaxException ex)
					{
						index =0;
					}
					if (index > max)
					{
						max = index;
					}
				
				}
			}
			Integer intmax= new Integer(max);
			nwIndex="_"+intmax.toString()+"_"+"act";
				
			//getting the roles that have been selected and storing them in a collection
			Collection <RoleDescriptor> selectedRoles =null;
			int[] selectedIndices = roleList.getSelectedIndices();
			for(int i=0;i<selectedIndices.length;i++)
			{
				selectedRoles.add(roles.get(i));
			}
			
			//creatig a new task from the values entered
			TaskDescriptor nwTask = new TaskDescriptor(nwIndex,nameTextField.getText(),descriptionTextArea.getText(),parentActivity.getDescriptor().getId());
			nwTask.setPrimaryPerformers(selectedRoles);
			//BreakdownElement nwBreakdownElement= new BreakdownElement(nwTask);
			
			//adding the new Task in the parent Activity
			//(parentActivity.getNestedElements()).add(nwBreakdownElement);
				
		} 
	    else
	    {
	    	System.out.println("dispose");
	        TaskDescriptorAdderDialog.this.dispose();
	    }
	} 

	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize (Vector <String> roleNames)
	{
		this.setSize(400, 450) ;
		this.setContentPane(getJContentPane(roleNames)) ;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane (Vector <String> roleNames)
	{
		if (jContentPane == null)
		{
			jContentPane = new JPanel() ;
			jContentPane.setLayout(new BorderLayout()) ;
			jContentPane.add(getCentralPanel(roleNames), java.awt.BorderLayout.CENTER);
			jContentPane.add(getSouthPanel(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane ;
	}

	/**
	 * This method initializes southPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSouthPanel ()
	{
		if (southPanel == null)
		{
			southPanel = new JPanel() ;
			southPanel.add(getOkButton(), null);
			southPanel.add(getCancelButton(), null);
		}
		return southPanel ;
	}

	/**
	 * This method initializes centralPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getCentralPanel (Vector <String> roleNames)
	{
		if (centralPanel == null)
		{
			centralPanel = new JPanel() ;
			centralPanel.setLayout(new BoxLayout(getCentralPanel(roleNames), BoxLayout.Y_AXIS));
			centralPanel.add(getNamePanel(), null);
			centralPanel.add(getDescriptionPanel(), null);
			centralPanel.add(getRolePanel(roleNames), null);
		}
		return centralPanel ;
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
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			nameLabel = new JLabel();
			nameLabel.setText(Bundle.getText("TaskDescriptorAdderDialogName"));
			namePanel = new JPanel() ;
			namePanel.setLayout(flowLayout);
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
			descriptionLabel.setText(Bundle.getText("TaskDescriptorAdderDialogDescription"));
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			descriptionPanel = new JPanel() ;
			descriptionPanel.setLayout(flowLayout1);
			descriptionPanel.add(descriptionLabel, null);
			descriptionPanel.add(getDescriptionTextArea(), null);
		}
		return descriptionPanel ;
	}

	/**
	 * This method initializes rolePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getRolePanel (Vector <String> roleNames)
	{
		if (rolePanel == null)
		{
			roleLabel = new JLabel();
			roleLabel.setText(Bundle.getText("TaskDescriptorAdderDialogRole"));
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			rolePanel = new JPanel() ;
			rolePanel.setLayout(flowLayout);
			rolePanel.add(roleLabel, null);
			rolePanel.add(getRoleList(roleNames), null);
		}
		return rolePanel ;
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
			nameTextField = new JTextField() ;
			nameTextField.setPreferredSize(new Dimension(200,30));
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
			descriptionTextArea = new JTextArea() ;
			descriptionTextArea.setPreferredSize(new Dimension(300,90));
		}
		return descriptionTextArea ;
	}

	/**
	 * This method initializes roleList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getRoleList (Vector <String> roleNames)
	{
		if (roleList == null)
		{
			
			roleList = new JList(roleNames) ;
			
		}
		return roleList ;
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
			okButton = new JButton(Bundle.getText("TaskDescriptorAdderDialogOk")) ;
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
			cancelButton = new JButton(Bundle.getText("TaskDescriptorAdderDialogCancel")) ;
		}
		return cancelButton ;
	}

}
