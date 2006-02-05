
package ui.misc ;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import model.spem2.Artifact;
import model.spem2.TaskDefinition;
import model.spem2.TaskDescriptor;
import process.utility.BreakdownElementsControler;
import ui.resource.Bundle;

/**
 * ArtifactPanel : Panel view for an artifact
 * 
 * @author Conde Mickael K.
 * @version 1.0
 * 
 */
public class ArtifactPanel extends JPanel implements Observer
{
	private static final long serialVersionUID = -3397031967959716980L ;

	private Artifact artifact ;

	private JPanel infoPanel = null ;

	private JPanel inTasksPanel = null ;

	private JPanel outTasksPanel = null ;

	private JPanel idPanel = null ;

	private JPanel namePanel = null ;

	private JPanel productPanel = null ;

	private JPanel descriptionPanel = null ;

	private JLabel idLabel = null ;

	private JTextField idTextField = null ;

	private JLabel nameLabel = null ;

	private JTextField nameTextField = null ;

	private JLabel productLabel = null ;

	private JTextField productTextField = null ;

	private JLabel descriptionLabel = null ;

	private JScrollPane descriptionScrollPane = null ;

	private JTextArea descriptionTextArea = null ;

	private JLabel inTasksEmptyLabel = null ;

	private JLabel outTasksEmptyLabel = null ;

	private JScrollPane inTasksScrollPane = null ;

	private JTable inTasksTable = null ;

	private JScrollPane outTasksScrollPane = null ;

	private JTable outTasksTable = null ;

	private JPanel buttonsPanel = null;

	private JButton saveButton = null;

	private JPanel dataPanel = null;

	private JScrollPane dataScrollPane = null;

	private JButton manageButton = null;

	/**
	 * This is the default constructor
	 */
	public ArtifactPanel (Artifact _artifact)
	{
		super() ;
		this.artifact = _artifact ;
		this.artifact.addObserver(this) ;

		initialize() ;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize ()
	{
		inTasksEmptyLabel = new JLabel(Bundle.getText("ArtifactPanelNoTask")) ;
		outTasksEmptyLabel = new JLabel(Bundle.getText("ArtifactPanelNoTask")) ;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)) ;
		this.setSize(493, 259) ;
		this.add(getInfoPanel(), null) ;
		this.add(Box.createRigidArea(new Dimension(0, 30))) ;
		this.add(getButtonsPanel(), null);
		this.add(Box.createRigidArea(new Dimension(0, 30))) ;
		this.add(getDataScrollPane(), null);
		this.add(Box.createVerticalGlue()) ;
	}

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update (Observable _observable, Object _object)
	{
		nameTextField.setText(((Artifact) _observable).getName()) ;
		descriptionTextArea.setText(((Artifact) _observable).getDescription()) ;
		MainTabbedPane.getInstance().setTitle(this, ((Artifact) _observable).getName()) ;
		
		if ( ((Artifact) _observable).getUsingTasks().size() == 0)
		{
			inTasksPanel.removeAll() ;
			inTasksPanel.add(inTasksEmptyLabel, null) ;
			inTasksPanel.revalidate() ;
		}

		else
		{
			inTasksPanel.removeAll() ;
			inTasksPanel.add(getInTasksScrollPane(), null) ;
			inTasksPanel.revalidate() ;
		}
		
		if ( ((Artifact) _observable).getProducingTasks().size() == 0)
		{
			outTasksPanel.removeAll() ;
			outTasksPanel.add(outTasksEmptyLabel, null) ;
			outTasksPanel.revalidate() ;
		}

		else
		{
			outTasksPanel.removeAll() ;
			outTasksPanel.add(getOutTasksScrollPane(), null) ;
			outTasksPanel.revalidate() ;
		}
	}

		
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@ Override
	public boolean equals (Object _object)
	{
		return (_object instanceof ArtifactPanel && ((ArtifactPanel) _object).artifact.getId().equals(artifact.getId())) ;
	}

	/**
	 * This method initializes infoPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getInfoPanel ()
	{
		if (infoPanel == null)
		{
			infoPanel = new JPanel() ;
			infoPanel.setLayout(new BoxLayout(getInfoPanel(), BoxLayout.Y_AXIS)) ;
			infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("ArtifactPanelInfoTitle"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			infoPanel.add(getProductPanel(), null) ;
			infoPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			infoPanel.add(getIdPanel(), null) ;
			infoPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			infoPanel.add(getNamePanel(), null) ;
			infoPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			infoPanel.add(getDescriptionPanel(), null) ;
			infoPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
		}
		return infoPanel ;
	}

	/**
	 * This method initializes inTasksPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getInTasksPanel ()
	{
		if (inTasksPanel == null)
		{
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			inTasksPanel = new JPanel() ;
			inTasksPanel.setLayout(flowLayout);
			inTasksPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("ArtifactPanelInHead"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;

			
			if (artifact.getUsingTasks().size() == 0)
			{
				inTasksPanel.add(inTasksEmptyLabel, null);
			}

			else
			{
				inTasksPanel.add(getInTasksScrollPane(), null) ;
			}
		}
		return inTasksPanel ;
	}

	/**
	 * This method initializes outTasksPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getOutTasksPanel ()
	{
		if (outTasksPanel == null)
		{
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			outTasksPanel = new JPanel() ;
			outTasksPanel.setLayout(flowLayout1);
			outTasksPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("ArtifactPanelOutHead"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			
			if (artifact.getProducingTasks().size() == 0)
			{
				outTasksPanel.add(outTasksEmptyLabel, null);
			}

			else
			{
				outTasksPanel.add(getOutTasksScrollPane(), null) ;
			}
		}
		return outTasksPanel ;
	}

	/**
	 * This method initializes idPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getIdPanel ()
	{
		if (idPanel == null)
		{
			idLabel = new JLabel() ;
			idLabel.setText(Bundle.getText("ArtifactPanelIDLabel")) ;
			idLabel.setPreferredSize(new java.awt.Dimension(150, 16)) ;
			idPanel = new JPanel() ;
			idPanel.setLayout(new BoxLayout(getIdPanel(), BoxLayout.X_AXIS)) ;
			idPanel.add(idLabel, null) ;
			idPanel.add(getIdTextField(), null) ;
			idPanel.add(Box.createHorizontalGlue()) ;
		}
		return idPanel ;
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
			nameLabel.setText(Bundle.getText("ArtifactPanelNameLabel")) ;
			nameLabel.setPreferredSize(new java.awt.Dimension(150, 16)) ;
			namePanel = new JPanel() ;
			namePanel.setLayout(new BoxLayout(getNamePanel(), BoxLayout.X_AXIS)) ;
			namePanel.add(nameLabel, null) ;
			namePanel.add(getNameTextField(), null) ;
			namePanel.add(Box.createHorizontalGlue()) ;
		}
		return namePanel ;
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
			productLabel.setText(Bundle.getText("ArtifactPanelProductLabel")) ;
			productLabel.setPreferredSize(new java.awt.Dimension(150, 16)) ;
			productPanel = new JPanel() ;
			productPanel.setLayout(new BoxLayout(getProductPanel(), BoxLayout.X_AXIS)) ;
			productPanel.add(productLabel, null) ;
			productPanel.add(getProductTextField(), null) ;
			productPanel.add(Box.createHorizontalGlue()) ;
		}
		return productPanel ;
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
			descriptionLabel.setText(Bundle.getText("ArtifactPanelDescriptionLabel")) ;
			descriptionLabel.setPreferredSize(new java.awt.Dimension(150, 16)) ;
			descriptionPanel = new JPanel() ;
			descriptionPanel.setLayout(new BoxLayout(getDescriptionPanel(), BoxLayout.X_AXIS)) ;
			descriptionPanel.add(descriptionLabel, null) ;
			descriptionPanel.add(getDescriptionScrollPane(), null) ;
			descriptionPanel.add(Box.createHorizontalGlue()) ;
		}
		return descriptionPanel ;
	}

	/**
	 * This method initializes idTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getIdTextField ()
	{
		if (idTextField == null)
		{
			idTextField = new JTextField(30) ;
			idTextField.setText(artifact.getId()) ;
			idTextField.setMaximumSize(new Dimension(400, 20)) ;
			idTextField.setBackground(java.awt.Color.white);
			idTextField.setEditable(false);
		}
		return idTextField ;
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
			nameTextField = new JTextField(30) ;
			nameTextField.setText(artifact.getName()) ;
			nameTextField.setMaximumSize(new Dimension(400, 20)) ;
			nameTextField.setBackground(java.awt.Color.white);
			nameTextField.setEditable(true);
		}
		return nameTextField ;
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
			productTextField = new JTextField(30) ;
			productTextField.setText(artifact.getProduct().getName()) ;
			productTextField.setMaximumSize(new Dimension(400, 20)) ;
			productTextField.setBackground(java.awt.Color.white);
			productTextField.setEditable(false);
		}
		return productTextField ;
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
			descriptionScrollPane.setMaximumSize(new java.awt.Dimension(410, 68)) ;
			descriptionScrollPane.setViewportView(getDescriptionTextArea()) ;
		}
		return descriptionScrollPane ;
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
			buttonsPanel.setLayout(new BoxLayout(getButtonsPanel(), BoxLayout.X_AXIS));
			buttonsPanel.add(getSaveButton(), null);
			buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0))) ;
			buttonsPanel.add(getManageButton(), null);
			buttonsPanel.add(Box.createHorizontalGlue()) ;
		}
		return buttonsPanel ;
	}

	/**
	 * This method initializes saveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSaveButton ()
	{
		if (saveButton == null)
		{
			saveButton = new JButton(Bundle.getText("Save")) ;
			saveButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					BreakdownElementsControler.updateArtifactInfo(artifact, nameTextField.getText(), descriptionTextArea.getText()) ;
				}
			}) ;
		}
		return saveButton ;
	}
	
	/**
	 * This method initializes manageButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getManageButton ()
	{
		if (manageButton == null)
		{
			manageButton = new JButton(Bundle.getText("ArtifactPanelButton")) ;
			manageButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					System.out.println("huhu") ;
				}
			}) ;
		}
		return manageButton ;
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
			descriptionTextArea = new JTextArea(3, 30) ;
			descriptionTextArea.setText(artifact.getDescription()) ;
		}
		return descriptionTextArea ;
	}
	
	/**
	 * This method initializes dataPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getDataPanel ()
	{
		if (dataPanel == null)
		{
			dataPanel = new JPanel() ;
			dataPanel.setLayout(new BoxLayout(getDataPanel(), BoxLayout.Y_AXIS));
			dataPanel.add(getInTasksPanel(), null);
			dataPanel.add(Box.createRigidArea(new Dimension(0, 30))) ;
			dataPanel.add(getOutTasksPanel(), null);
			dataPanel.add(Box.createVerticalGlue()) ;
		}
		return dataPanel ;
	}

	/**
	 * This method initializes dataScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getDataScrollPane ()
	{
		if (dataScrollPane == null)
		{
			dataScrollPane = new JScrollPane() ;
			dataScrollPane.setViewportView(getDataPanel());
		}
		return dataScrollPane ;
	}

	/**
	 * This method initializes inTasksScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getInTasksScrollPane ()
	{
		if (inTasksScrollPane == null)
		{
			inTasksScrollPane = new JScrollPane() ;
			inTasksScrollPane.setViewportView(getInTasksTable()) ;
		}
		return inTasksScrollPane ;
	}

	/**
	 * This method initializes inTasksTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getInTasksTable ()
	{
		if (inTasksTable == null)
		{
			inTasksTable = new JTable(new TaskDefinitionsTableModel(artifact, true)) ;
			inTasksTable.setPreferredScrollableViewportSize(new Dimension(500, 80)) ;
			inTasksTable.getColumnModel().getColumn(0).setMaxWidth(150) ;
			inTasksTable.getTableHeader().setReorderingAllowed(false) ;
		}
		return inTasksTable ;
	}

	/**
	 * This method initializes outTasksScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getOutTasksScrollPane ()
	{
		if (outTasksScrollPane == null)
		{
			outTasksScrollPane = new JScrollPane() ;
			outTasksScrollPane.setViewportView(getOutTasksTable()) ;
		}
		return outTasksScrollPane ;
	}

	/**
	 * This method initializes outTasksTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getOutTasksTable ()
	{
		if (outTasksTable == null)
		{
			outTasksTable = new JTable(new TaskDefinitionsTableModel(artifact, false)) ;
			outTasksTable.setPreferredScrollableViewportSize(new Dimension(500, 80)) ;
			outTasksTable.getColumnModel().getColumn(0).setMaxWidth(150) ;
			outTasksTable.getTableHeader().setReorderingAllowed(false) ;
		}
		return outTasksTable ;
	}
	
	/**
	 * TaskDefinitionsTableModel : Table model for tasks (definitions)
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 *
	 */
	private class TaskDefinitionsTableModel extends AbstractTableModel implements Observer
	{
		private static final long serialVersionUID = -3066219435932496116L ;

		private Collection <TaskDescriptor> data ;

		private ArrayList <String> head ;

		private Artifact artifact ;

		/**
		 * The number of columns to show (id, name and mail)
		 */
		private final short COLUMN_NUMBER = 3 ;

		
		/**
		 * Constructor
		 *
		 * @param _artifact
		 * @param _in
		 */
		public TaskDefinitionsTableModel (Artifact _artifact, boolean _in)
		{
			super() ;

			this.artifact = _artifact ;
			data = _in ? this.artifact.getUsingTasks() : this.artifact.getProducingTasks() ;
			head = new ArrayList <String>() ;
			head.add(Bundle.getText("ArtifactPanelTableTasksID")) ;
			head.add(Bundle.getText("ArtifactPanelTableTasksName")) ;
			head.add(Bundle.getText("ArtifactPanelTableTasksDescription")) ;
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@ Override
		public String getColumnName (int _arg0)
		{
			return head.get(_arg0) ;
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
		 */
		@ Override
		public boolean isCellEditable (int _arg0, int _arg1)
		{
			return false ;
		}

		/**
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		public int getColumnCount ()
		{
			return COLUMN_NUMBER ;
		}

		/**
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		public int getRowCount ()
		{
			return data.size() ;
		}

		/**
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt (int _row, int _col)
		{
			Object tempArray[] = data.toArray() ;
			switch (_col)
			{
				case 0:
					return ((TaskDefinition) tempArray[_row]).getId() ;
				case 1:
					return ((TaskDefinition) tempArray[_row]).getName() ;
				default:
					return ((TaskDefinition) tempArray[_row]).getDescription() ;
			}
		}

		/**
		 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
		 */
		public void update (Observable _observable, Object _object)
		{
			fireTableDataChanged() ;
			
		}

	}

} // @jve:decl-index=0:visual-constraint="10,10"
