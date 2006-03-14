
package ui.dialog ;

import java.awt.BorderLayout ;
import java.awt.Dimension ;
import java.awt.event.ItemEvent ;
import java.util.ArrayList ;
import java.util.Iterator ;

import javax.swing.Box ;
import javax.swing.BoxLayout ;
import javax.swing.JButton ;
import javax.swing.JComboBox ;
import javax.swing.JDialog ;
import javax.swing.JLabel ;
import javax.swing.JList ;
import javax.swing.JOptionPane ;
import javax.swing.JPanel ;
import javax.swing.JScrollPane ;

import model.spem2.Artifact ;
import model.spem2.TaskDefinition ;
import model.spem2.TaskDescriptor ;
import model.spem2.WorkProductDescriptor ;
import process.exception.DuplicateElementException;
import process.utility.BreakdownElementsControler ;
import ui.resource.Bundle ;
import ui.window.MainFrame ;

/**
 * ArtifactManagerDialog : Dialog that allows to manage input and output for an artifacts for a task
 * 
 * @author Conde Mickael K.
 * @version 1.0
 * 
 */
public class TaskDefinitionManagerDialog extends JDialog
{
	private static final long serialVersionUID = -5572636886967139357L ;

	private MainFrame owner = null ;

	private boolean inArtifacts = true ;

	private TaskDefinition task = null ;

	private ArrayList <Artifact> usingArtifactsList = null ;

	private ArrayList <Artifact> producingArtifactsList = null ;

	private String[] comboChoices = {
			Bundle.getText("TaskDefinitionManagerDialogInChoice"), Bundle.getText("TaskDefinitionManagerDialogOutChoice")
	} ;

	private JPanel mainContentPane = null ;

	private JPanel choicePanel = null ;

	private JPanel corePanel = null ;

	private JPanel buttonsPanel = null ;

	private JComboBox inOutComboBox = null ;

	private JLabel choiceLabel = null ;

	private JButton closeButton = null ;

	private JScrollPane availableScrollPane = null ;

	private JScrollPane definedScrollPane = null ;

	private JList availableList = null ;

	private JList definedList = null ;

	private JButton addButton = null ;

	private JButton removeButton = null ;

	private JPanel addPanel = null ;

	private JPanel removePanel = null ;

	/**
	 * This is the default constructor
	 */
	public TaskDefinitionManagerDialog (MainFrame _owner, TaskDefinition _task, boolean _inArtifacts)
	{
		super() ;

		this.owner = _owner ;
		this.task = _task ;
		this.inArtifacts = _inArtifacts ;

		usingArtifactsList = new ArrayList <Artifact>() ;
		producingArtifactsList = new ArrayList <Artifact>() ;

		initializeTasks() ;
		initialize() ;
	}

	/**
	 * Initializes the array of available tasks
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	private void initializeTasks ()
	{
		// Input tasks
		Iterator <WorkProductDescriptor> localIterator ;
		Iterator <Artifact> localArtifactIterator ;
		Artifact localArtifact ;
		TaskDescriptor localTask = task.getTask() ;
		localIterator = localTask.getInputProducts().iterator() ;

		while (localIterator.hasNext())
		{
			localArtifactIterator = localIterator.next().getArtifacts().iterator() ;
			while (localArtifactIterator.hasNext())
			{
				localArtifact = localArtifactIterator.next() ;
				if (!task.getInputProducts().contains(localArtifact))
				{
					usingArtifactsList.add(localArtifact) ;
				}
			}
		}

		// Output tasks
		localIterator = localTask.getOutputProducts().iterator() ;
		while (localIterator.hasNext())
		{
			localArtifactIterator = localIterator.next().getArtifacts().iterator() ;
			while (localArtifactIterator.hasNext())
			{
				localArtifact = localArtifactIterator.next() ;
				if (!task.getOutputProducts().contains(localArtifact))
				{
					producingArtifactsList.add(localArtifact) ;
				}
			}
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize ()
	{
		this.setModal(true) ;
		this.setTitle(Bundle.getText("TaskDefinitionManagerDialogTitle")) ;
		this.setContentPane(getMainContentPane()) ;
		this.setBounds(owner.getX() + owner.getWidth() / 2 - 300, owner.getY() + owner.getHeight() / 2 - 200, 600, 400) ;
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
			mainContentPane.add(getChoicePanel(), java.awt.BorderLayout.NORTH) ;
			mainContentPane.add(getCorePanel(), java.awt.BorderLayout.CENTER) ;
			mainContentPane.add(getButtonsPanel(), java.awt.BorderLayout.SOUTH) ;
			mainContentPane.add(getAvailableScrollPane(), java.awt.BorderLayout.WEST) ;
			mainContentPane.add(getDefinedScrollPane(), java.awt.BorderLayout.EAST) ;
		}
		return mainContentPane ;
	}

	/**
	 * This method initializes choicePanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getChoicePanel ()
	{
		if (choicePanel == null)
		{
			choiceLabel = new JLabel() ;
			choiceLabel.setText(Bundle.getText("TaskDefinitionManagerDialogTaskType")) ;
			choicePanel = new JPanel() ;
			choicePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED)) ;
			choicePanel.add(choiceLabel, null) ;
			choicePanel.add(getInOutComboBox(), null) ;
		}
		return choicePanel ;
	}

	/**
	 * This method initializes corePanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getCorePanel ()
	{
		if (corePanel == null)
		{
			corePanel = new JPanel() ;
			corePanel.setLayout(new BoxLayout(getCorePanel(), BoxLayout.Y_AXIS)) ;
			corePanel.setPreferredSize(new java.awt.Dimension(200, 72)) ;
			corePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED)) ;
			corePanel.add(Box.createVerticalGlue()) ;
			corePanel.add(getAddPanel(), null) ;
			corePanel.add(Box.createRigidArea(new Dimension(0, 20))) ;
			corePanel.add(getRemovePanel(), null) ;
			corePanel.add(Box.createVerticalGlue()) ;

		}
		return corePanel ;
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
			buttonsPanel.add(getCloseButton(), null) ;
		}
		return buttonsPanel ;
	}

	/**
	 * This method initializes typeComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getInOutComboBox ()
	{
		if (inOutComboBox == null)
		{
			inOutComboBox = new JComboBox(comboChoices) ;
			if (!inArtifacts)
			{
				inOutComboBox.setSelectedIndex(1) ;
			}
			inOutComboBox.addItemListener(new java.awt.event.ItemListener()
			{
				/**
				 * Loading new values to the list
				 * 
				 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
				 */
				public void itemStateChanged (java.awt.event.ItemEvent e)
				{
					if (e.getStateChange() == ItemEvent.SELECTED)
					{
						addButton.setEnabled(true) ;
						removeButton.setEnabled(true) ;

						if (inOutComboBox.getSelectedIndex() == 0)
						{
							availableList.setListData(usingArtifactsList.toArray()) ;
							definedList.setListData(task.getInputProducts().toArray()) ;
							if (usingArtifactsList.size() == 0)
							{
								addButton.setEnabled(false) ;
							}
							if (task.getInputProducts().size() == 0)
							{
								removeButton.setEnabled(false) ;
							}
						}

						else
						{
							availableList.setListData(producingArtifactsList.toArray()) ;
							definedList.setListData(task.getOutputProducts().toArray()) ;
							if (producingArtifactsList.size() == 0)
							{
								addButton.setEnabled(false) ;
							}
							if (task.getOutputProducts().size() == 0)
							{
								removeButton.setEnabled(false) ;
							}
						}
					}
				}
			}) ;
		}
		return inOutComboBox ;
	}

	/**
	 * This method initializes closeButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCloseButton ()
	{
		if (closeButton == null)
		{
			closeButton = new JButton(Bundle.getText("Close")) ;
			closeButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					TaskDefinitionManagerDialog.this.dispose() ;
				}
			}) ;
		}
		return closeButton ;
	}

	/**
	 * This method initializes availableScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getAvailableScrollPane ()
	{
		if (availableScrollPane == null)
		{
			availableScrollPane = new JScrollPane() ;
			availableScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("TaskDefinitionManagerDialogAvailable"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			availableScrollPane.setPreferredSize(new java.awt.Dimension(200, 154)) ;
			availableScrollPane.setViewportView(getAvailableList()) ;
		}
		return availableScrollPane ;
	}

	/**
	 * This method initializes definedScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getDefinedScrollPane ()
	{
		if (definedScrollPane == null)
		{
			definedScrollPane = new JScrollPane() ;
			definedScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("TaskDefinitionManagerDialogDefined"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			definedScrollPane.setPreferredSize(new java.awt.Dimension(200, 154)) ;
			definedScrollPane.setViewportView(getDefinedList()) ;
		}
		return definedScrollPane ;
	}

	/**
	 * This method initializes availableList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getAvailableList ()
	{
		if (availableList == null)
		{
			availableList = new JList() ;
			if (inArtifacts)
			{
				availableList.setListData(usingArtifactsList.toArray()) ;
			}
			else
			{
				availableList.setListData(producingArtifactsList.toArray()) ;
			}
		}
		return availableList ;
	}

	/**
	 * This method initializes definedList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getDefinedList ()
	{
		if (definedList == null)
		{
			definedList = new JList() ;
			if (inArtifacts)
			{
				definedList.setListData(task.getInputProducts().toArray()) ;
			}
			else
			{
				definedList.setListData(task.getOutputProducts().toArray()) ;
			}
		}
		return definedList ;
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
			// Disabling
			if (getInOutComboBox().getSelectedIndex() == 0)
			{
				if (usingArtifactsList.size() == 0)
				{
					addButton.setEnabled(false) ;
				}
			}
			else
			{
				if (producingArtifactsList.size() == 0)
				{
					addButton.setEnabled(false) ;
				}
			}

			addButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					Object[] _localSelectedValues = availableList.getSelectedValues() ;

					if (_localSelectedValues.length < 1)
					{
						JOptionPane.showMessageDialog(TaskDefinitionManagerDialog.this, Bundle.getText("TaskDefinitionManagerDialogNoArtifactsToAdd"), "PSI",
								JOptionPane.INFORMATION_MESSAGE) ;
						return ;
					}

					addButton.setEnabled(false) ;
					removeButton.setEnabled(true) ;

					// Updating model
					try
					{
						BreakdownElementsControler.linkTaskDefinitionToArtifacts(task, _localSelectedValues, inOutComboBox.getSelectedIndex() == 0) ;
					}
					catch (DuplicateElementException exc)
					{
					}

					// Updating lists
					for (int i = 0; i < _localSelectedValues.length; i++ )
					{
						if (inOutComboBox.getSelectedIndex() == 0)
						{
							usingArtifactsList.remove(_localSelectedValues[i]) ;
						}
						else
						{
							producingArtifactsList.remove(_localSelectedValues[i]) ;
						}
					}

					// Updating jlists and buttons
					if (inOutComboBox.getSelectedIndex() == 0)
					{
						definedList.setListData(task.getInputProducts().toArray()) ;
						availableList.setListData(usingArtifactsList.toArray()) ;
						if (usingArtifactsList.size() > 0)
						{
							addButton.setEnabled(true) ;
						}
					}
					else
					{
						definedList.setListData(task.getOutputProducts().toArray()) ;
						availableList.setListData(producingArtifactsList.toArray()) ;
						if (producingArtifactsList.size() > 0)
						{
							addButton.setEnabled(true) ;
						}
					}
				}
			}) ;
		}
		return addButton ;
	}

	/**
	 * This method initializes removeButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getRemoveButton ()
	{
		if (removeButton == null)
		{
			removeButton = new JButton(Bundle.getText("Delete")) ;
			if (getInOutComboBox().getSelectedIndex() == 0)
			{
				if (task.getInputProducts().size() == 0)
				{
					removeButton.setEnabled(false) ;
				}
			}
			else
			{
				if (task.getOutputProducts().size() == 0)
				{
					removeButton.setEnabled(false) ;
				}
			}

			removeButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					Object[] _localSelectedValues = definedList.getSelectedValues() ;

					if (_localSelectedValues.length < 1)
					{
						JOptionPane.showMessageDialog(TaskDefinitionManagerDialog.this, Bundle.getText("TaskDefinitionManagerDialogNoArtifactsToRemove"), "PSI",
								JOptionPane.INFORMATION_MESSAGE) ;
						return ;
					}

					addButton.setEnabled(true) ;
					removeButton.setEnabled(false) ;

					// Updating model
					BreakdownElementsControler.unlinkTaskDefinitionAndArtifacts(task, _localSelectedValues, inOutComboBox.getSelectedIndex() == 0) ;

					// Updating lists
					for (int i = 0; i < _localSelectedValues.length; i++ )
					{
						if (inOutComboBox.getSelectedIndex() == 0)
						{
							usingArtifactsList.add((Artifact) _localSelectedValues[i]) ;
						}
						else
						{
							producingArtifactsList.add((Artifact) _localSelectedValues[i]) ;
						}
					}

					// Updating jlists and buttons
					if (inOutComboBox.getSelectedIndex() == 0)
					{
						definedList.setListData(task.getInputProducts().toArray()) ;
						availableList.setListData(usingArtifactsList.toArray()) ;
						if (task.getInputProducts().size() > 0)
						{
							removeButton.setEnabled(true) ;
						}
					}
					else
					{
						definedList.setListData(task.getOutputProducts().toArray()) ;
						availableList.setListData(producingArtifactsList.toArray()) ;
						if (task.getOutputProducts().size() > 0)
						{
							removeButton.setEnabled(true) ;
						}
					}
				}
			}) ;
		}
		return removeButton ;
	}

	/**
	 * This method initializes addPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAddPanel ()
	{
		if (addPanel == null)
		{
			addPanel = new JPanel() ;
			addPanel.setLayout(new BoxLayout(getAddPanel(), BoxLayout.X_AXIS)) ;
			addPanel.add(getAddButton(), null) ;
		}
		return addPanel ;
	}

	/**
	 * This method initializes removePanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getRemovePanel ()
	{
		if (removePanel == null)
		{
			removePanel = new JPanel() ;
			removePanel.setLayout(new BoxLayout(getRemovePanel(), BoxLayout.X_AXIS)) ;
			removePanel.add(getRemoveButton(), null) ;
		}
		return removePanel ;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
