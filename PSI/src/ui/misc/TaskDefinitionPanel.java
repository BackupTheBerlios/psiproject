
package ui.misc ;

import java.awt.Component ;
import java.awt.Dimension ;
import java.awt.FlowLayout ;
import java.util.ArrayList ;
import java.util.Collection ;
import java.util.Date ;
import java.util.Observable ;
import java.util.Observer ;

import javax.swing.Box ;
import javax.swing.BoxLayout ;
import javax.swing.DefaultCellEditor ;
import javax.swing.JButton ;
import javax.swing.JFormattedTextField ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;
import javax.swing.JScrollPane ;
import javax.swing.JTable ;
import javax.swing.JTextArea ;
import javax.swing.JTextField ;
import javax.swing.border.TitledBorder ;
import javax.swing.table.AbstractTableModel ;
import javax.swing.table.DefaultTableCellRenderer ;
import javax.swing.text.DateFormatter ;
import javax.swing.text.DefaultFormatterFactory ;

import process.utility.BreakdownElementsControler ;

import model.spem2.Artifact ;
import model.spem2.TaskDefinition ;
import model.spem2.WorkProductDescriptor ;
import ui.dialog.TaskDefinitionManagerDialog ;
import ui.resource.Bundle ;
import ui.resource.LocaleController ;
import ui.resource.LocaleListener ;
import ui.window.MainFrame ;

/**
 * TaskDefinitionPanel : Panel view for a task definition
 * 
 * @author Conde Mickael K.
 * @version 1.0
 * 
 */
public class TaskDefinitionPanel extends JPanel implements Observer
{
	private static final long serialVersionUID = 6053395905362266377L ;

	private MainFrame mainFrame = null ;

	private TaskDefinition task = null ;

	private JPanel infoPanel = null ;

	private JPanel buttonsPanel = null ;

	private JPanel planificationPanel = null ;

	private JScrollPane dataScrollPane = null ;

	private JPanel dataPanel = null ;

	private JPanel inArtifactsPanel = null ;

	private JPanel outArtifactsPanel = null ;

	private JPanel idPanel = null ;

	private JPanel namePanel = null ;

	private JPanel descriptionPanel = null ;

	private JPanel activityPanel = null ;

	private JButton saveButton = null ;

	private JButton manageButton = null ;

	private JTable planificationTable = null ;

	private JScrollPane inArtifactsScrollPane = null ;

	private JTable inArtifactsTable = null ;

	private JScrollPane outArtifactsScrollPane = null ;

	private JTable outArtifactsTable = null ;

	private JLabel activityLabel = null ;

	private JTextField activityTextField = null ;

	private JLabel idLabel = null ;

	private JTextField idTextField = null ;

	private JLabel nameLabel = null ;

	private JTextField nameTextField = null ;

	private JLabel descriptionLabel = null ;

	private JScrollPane descriptionScrollPane = null ;

	private JTextArea descriptionTextArea = null ;

	private JLabel inArtifactsEmptyLabel = null ;

	private JLabel outArtifactsEmptyLabel = null ;

	private JScrollPane planificationScrollPane = null ;

	/**
	 * The locale controller for the language.
	 */
	private LocaleController controllerLocale = null ;

	/**
	 * This is the default constructor
	 */
	public TaskDefinitionPanel (MainFrame _frame, TaskDefinition _task)
	{
		super() ;

		this.controllerLocale = LocaleController.getInstance() ;
		this.controllerLocale.addLocaleListener(new LocaleListener()
		{
			public void localeChanged ()
			{
				updateText() ;
			}
		}) ;

		this.mainFrame = _frame ;
		this.task = _task ;
		this.task.addObserver(this) ;

		initialize() ;
	}

	/**
	 * 
	 * This method updates texts in this table during a language changing.
	 * 
	 * @author MaT
	 * @version 1.0
	 * 
	 */
	private void updateText ()
	{
		((TitledBorder) infoPanel.getBorder()).setTitle(Bundle.getText("TaskDefinitionPanelInfoTitle")) ;
		((TitledBorder) planificationPanel.getBorder()).setTitle(Bundle.getText("TaskDefinitionPanelPlanTitle")) ;
		((TitledBorder) inArtifactsPanel.getBorder()).setTitle(Bundle.getText("TaskDefinitionPanelInHead")) ;
		((TitledBorder) outArtifactsPanel.getBorder()).setTitle(Bundle.getText("TaskDefinitionPanelOutHead")) ;
		idLabel.setText(Bundle.getText("TaskDefinitionPanelIDLabel")) ;
		nameLabel.setText(Bundle.getText("TaskDefinitionPanelNameLabel")) ;
		descriptionLabel.setText(Bundle.getText("TaskDefinitionPanelDescriptionLabel")) ;
		activityLabel.setText(Bundle.getText("TaskDefinitionPanelActivityLabel")) ;
		saveButton.setText(Bundle.getText("Save")) ;
		manageButton.setText(Bundle.getText("TaskDefinitionPanelButton")) ;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize ()
	{
		inArtifactsEmptyLabel = new JLabel(Bundle.getText("TaskDefinitionPanelNoArtifact")) ;
		outArtifactsEmptyLabel = new JLabel(Bundle.getText("TaskDefinitionPanelNoArtifact")) ;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)) ;
		this.setSize(530, 312) ;
		this.add(getInfoPanel(), null) ;
		this.add(Box.createRigidArea(new Dimension(0, 5))) ;
		this.add(getButtonsPanel(), null) ;
		this.add(Box.createRigidArea(new Dimension(0, 5))) ;
		this.add(getPlanificationPanel(), null) ;
		this.add(Box.createRigidArea(new Dimension(0, 5))) ;
		this.add(getDataScrollPane(), null) ;
	}

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update (Observable _observable, Object _object)
	{
		nameTextField.setText( ((TaskDefinition) _observable).getName()) ;
		descriptionTextArea.setText( ((TaskDefinition) _observable).getDescription()) ;
		MainTabbedPane.getInstance().setTitle(this, ((TaskDefinition) _observable).getName()) ;

		if ( ((TaskDefinition) _observable).getInputProducts().size() == 0)
		{
			inArtifactsPanel.removeAll() ;
			inArtifactsPanel.add(inArtifactsEmptyLabel, null) ;
			inArtifactsPanel.revalidate() ;
		}

		else
		{
			inArtifactsPanel.removeAll() ;
			inArtifactsPanel.add(getInArtifactsScrollPane(), null) ;
			inArtifactsPanel.revalidate() ;
		}

		if ( ((TaskDefinition) _observable).getOutputProducts().size() == 0)
		{
			outArtifactsPanel.removeAll() ;
			outArtifactsPanel.add(outArtifactsEmptyLabel, null) ;
			outArtifactsPanel.revalidate() ;
		}

		else
		{
			outArtifactsPanel.removeAll() ;
			outArtifactsPanel.add(getOutArtifactsScrollPane(), null) ;
			outArtifactsPanel.revalidate() ;
		}
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@ Override
	public boolean equals (Object _object)
	{
		return (_object instanceof TaskDefinitionPanel && ((TaskDefinitionPanel) _object).task.getId().equals(task.getId())) ;
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
			infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("TaskDefinitionPanelInfoTitle"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			infoPanel.add(getActivityPanel(), null) ;
			infoPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			infoPanel.add(getIdPanel(), null) ;
			infoPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			infoPanel.add(getNamePanel(), null) ;
			infoPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			infoPanel.add(getDescriptionPanel(), null) ;
		}
		return infoPanel ;
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
			buttonsPanel.setLayout(new BoxLayout(getButtonsPanel(), BoxLayout.X_AXIS)) ;
			buttonsPanel.add(getSaveButton(), null) ;
			buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0))) ;
			buttonsPanel.add(getManageButton(), null) ;
			buttonsPanel.add(Box.createHorizontalGlue()) ;
		}
		return buttonsPanel ;
	}

	/**
	 * This method initializes planificationPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPlanificationPanel ()
	{
		if (planificationPanel == null)
		{
			FlowLayout flowLayout2 = new FlowLayout() ;
			flowLayout2.setAlignment(java.awt.FlowLayout.LEFT) ;
			planificationPanel = new JPanel() ;
			planificationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("TaskDefinitionPanelPlanTitle"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			planificationPanel.setLayout(flowLayout2) ;
			planificationPanel.add(getPlanificationScrollPane(), null) ;
		}
		return planificationPanel ;
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
			dataScrollPane.setViewportView(getDataPanel()) ;
		}
		return dataScrollPane ;
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
			dataPanel.setLayout(new BoxLayout(getDataPanel(), BoxLayout.Y_AXIS)) ;
			dataPanel.add(getInArtifactsPanel(), null) ;
			dataPanel.add(getOutArtifactsPanel(), null) ;
		}
		return dataPanel ;
	}

	/**
	 * This method initializes inArtifactsPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getInArtifactsPanel ()
	{
		if (inArtifactsPanel == null)
		{
			FlowLayout flowLayout1 = new FlowLayout() ;
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT) ;
			inArtifactsPanel = new JPanel() ;
			inArtifactsPanel.setLayout(flowLayout1) ;
			inArtifactsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("TaskDefinitionPanelInHead"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;

			if (task.getInputProducts().size() == 0)
			{
				inArtifactsPanel.add(inArtifactsEmptyLabel, null) ;
			}

			else
			{
				inArtifactsPanel.add(getInArtifactsScrollPane(), null) ;
			}
		}
		return inArtifactsPanel ;
	}

	/**
	 * This method initializes outArtifactsPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getOutArtifactsPanel ()
	{
		if (outArtifactsPanel == null)
		{
			FlowLayout flowLayout = new FlowLayout() ;
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT) ;
			outArtifactsPanel = new JPanel() ;
			outArtifactsPanel.setLayout(flowLayout) ;
			outArtifactsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("TaskDefinitionPanelOutHead"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;

			if (task.getOutputProducts().size() == 0)
			{
				outArtifactsPanel.add(outArtifactsEmptyLabel, null) ;
			}

			else
			{
				outArtifactsPanel.add(getOutArtifactsScrollPane(), null) ;
			}
		}
		return outArtifactsPanel ;
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
			idLabel.setText(Bundle.getText("TaskDefinitionPanelIDLabel")) ;
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
			nameLabel.setText(Bundle.getText("TaskDefinitionPanelNameLabel")) ;
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
	 * This method initializes descriptionPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getDescriptionPanel ()
	{
		if (descriptionPanel == null)
		{
			descriptionLabel = new JLabel() ;
			descriptionLabel.setText(Bundle.getText("TaskDefinitionPanelDescriptionLabel")) ;
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
	 * This method initializes activityPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getActivityPanel ()
	{
		if (activityPanel == null)
		{
			activityLabel = new JLabel() ;
			activityLabel.setText(Bundle.getText("TaskDefinitionPanelActivityLabel")) ;
			activityLabel.setPreferredSize(new java.awt.Dimension(150, 16)) ;
			activityPanel = new JPanel() ;
			activityPanel.setLayout(new BoxLayout(getActivityPanel(), BoxLayout.X_AXIS)) ;
			activityPanel.add(activityLabel, null) ;
			activityPanel.add(getActivityTextField(), null) ;
			activityPanel.add(Box.createHorizontalGlue()) ;
		}
		return activityPanel ;
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
					BreakdownElementsControler.updateTaskDefinitionInfo(task, nameTextField.getText(), descriptionTextArea.getText()) ;
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
			manageButton = new JButton(Bundle.getText("TaskDefinitionPanelButton")) ;
			manageButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					new TaskDefinitionManagerDialog(mainFrame, task, true) ;
				}
			}) ;
		}
		return manageButton ;
	}

	/**
	 * This method initializes planificationTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getPlanificationTable ()
	{
		if (planificationTable == null)
		{
			planificationTable = new JTable(new PlanningTableModel(task)) ;
			planificationTable.setPreferredScrollableViewportSize(new Dimension(500, planificationTable.getRowHeight() * 2)) ;
			planificationTable.setDefaultEditor(Date.class, new DateFieldEditor()) ;
			planificationTable.setDefaultRenderer(Date.class, new DateFieldRenderer()) ;
		}
		return planificationTable ;
	}

	/**
	 * This method initializes inArtifactsScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getInArtifactsScrollPane ()
	{
		if (inArtifactsScrollPane == null)
		{
			inArtifactsScrollPane = new JScrollPane() ;
			inArtifactsScrollPane.setViewportView(getInArtifactsTable()) ;
		}
		return inArtifactsScrollPane ;
	}

	/**
	 * This method initializes inArtifactsTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getInArtifactsTable ()
	{
		if (inArtifactsTable == null)
		{
			inArtifactsTable = new JTable(new ArtifactsTableModel(task, true)) ;
			inArtifactsTable.setPreferredScrollableViewportSize(new Dimension(500, inArtifactsTable.getRowHeight() * 4)) ;
		}
		return inArtifactsTable ;
	}

	/**
	 * This method initializes outArtifactsScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getOutArtifactsScrollPane ()
	{
		if (outArtifactsScrollPane == null)
		{
			outArtifactsScrollPane = new JScrollPane() ;
			outArtifactsScrollPane.setViewportView(getOutArtifactsTable()) ;
		}
		return outArtifactsScrollPane ;
	}

	/**
	 * This method initializes outArtifactsTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getOutArtifactsTable ()
	{
		if (outArtifactsTable == null)
		{
			outArtifactsTable = new JTable(new ArtifactsTableModel(task, false)) ;
			outArtifactsTable.setPreferredScrollableViewportSize(new Dimension(500, outArtifactsTable.getRowHeight() * 4)) ;
		}
		return outArtifactsTable ;
	}

	/**
	 * This method initializes activityTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getActivityTextField ()
	{
		if (activityTextField == null)
		{
			activityTextField = new JTextField(30) ;
			activityTextField.setMaximumSize(new Dimension(400, 20)) ;
			activityTextField.setBackground(java.awt.Color.white) ;
			activityTextField.setText(task.getTask().getName()) ;
			activityTextField.setEditable(false) ;
		}
		return activityTextField ;
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
			idTextField.setMaximumSize(new Dimension(400, 20)) ;
			idTextField.setBackground(java.awt.Color.white) ;
			idTextField.setText(task.getId()) ;
			idTextField.setEditable(false) ;
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
			nameTextField.setMaximumSize(new Dimension(400, 20)) ;
			nameTextField.setText(task.getName()) ;
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
			descriptionScrollPane.setMaximumSize(new java.awt.Dimension(410, 68)) ;
			descriptionScrollPane.setViewportView(getDescriptionTextArea()) ;
		}
		return descriptionScrollPane ;
	}

	/**
	 * This method initializes planificationScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getPlanificationScrollPane ()
	{
		if (planificationScrollPane == null)
		{
			planificationScrollPane = new JScrollPane() ;
			planificationScrollPane.setViewportView(getPlanificationTable()) ;
		}
		return planificationScrollPane ;
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
			descriptionTextArea.setText(task.getDescription()) ;
		}
		return descriptionTextArea ;
	}

	/**
	 * ArtifactsTableModel : Table model for artifacts
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	private class ArtifactsTableModel extends AbstractTableModel implements Observer
	{
		private static final long serialVersionUID = -7566448301002094444L ;

		private Collection <WorkProductDescriptor> data ;

		private ArrayList <String> head ;

		private TaskDefinition task ;

		/**
		 * The number of columns to show (id, name and mail)
		 */
		private final short COLUMN_NUMBER = 3 ;

		/**
		 * The locale controller for the language.
		 */
		private LocaleController controllerLocale = null ;

		/**
		 * Constructor
		 * 
		 * @param _artifact
		 * @param _in
		 */
		public ArtifactsTableModel (TaskDefinition _task, boolean _in)
		{
			super() ;

			this.controllerLocale = LocaleController.getInstance() ;
			this.controllerLocale.addLocaleListener(new LocaleListener()
			{
				public void localeChanged ()
				{
					updateText() ;
				}
			}) ;

			this.task = _task ;
			data = _in ? this.task.getInputProducts() : this.task.getOutputProducts() ;
			head = new ArrayList <String>() ;
			head.add(Bundle.getText("TaskDefinitionPanelTableArtifactID")) ;
			head.add(Bundle.getText("TaskDefinitionPanelTableArtifactName")) ;
			head.add(Bundle.getText("TaskDefinitionPanelTableArtifactDescription")) ;
		}

		/**
		 * 
		 * This method updates texts in this panel during a language changing.
		 * 
		 * @author MaT
		 * @version 1.0
		 * 
		 */
		private void updateText ()
		{
			if (task.getInputProducts().size() == 0)
			{
				inArtifactsEmptyLabel.setText(Bundle.getText("TaskDefinitionPanelNoArtifact")) ;
			}
			else
			{
				TaskDefinitionPanel.this.inArtifactsTable.getColumnModel().getColumn(0).setHeaderValue(Bundle.getText("TaskDefinitionPanelTableArtifactID")) ;
				TaskDefinitionPanel.this.inArtifactsTable.getColumnModel().getColumn(1).setHeaderValue(Bundle.getText("TaskDefinitionPanelTableArtifactName")) ;
				TaskDefinitionPanel.this.inArtifactsTable.getColumnModel().getColumn(2).setHeaderValue(Bundle.getText("TaskDefinitionPanelTableArtifactDescription")) ;
			}
			if (task.getOutputProducts().size() == 0)
			{
				outArtifactsEmptyLabel.setText(Bundle.getText("TaskDefinitionPanelNoArtifact")) ;
			}
			else
			{
				TaskDefinitionPanel.this.outArtifactsTable.getColumnModel().getColumn(0).setHeaderValue(Bundle.getText("TaskDefinitionPanelTableArtifactID")) ;
				TaskDefinitionPanel.this.outArtifactsTable.getColumnModel().getColumn(1).setHeaderValue(Bundle.getText("TaskDefinitionPanelTableArtifactName")) ;
				TaskDefinitionPanel.this.outArtifactsTable.getColumnModel().getColumn(2).setHeaderValue(Bundle.getText("TaskDefinitionPanelTableArtifactDescription")) ;
			}
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
					return ((Artifact) tempArray[_row]).getId() ;
				case 1:
					return ((Artifact) tempArray[_row]).getName() ;
				default:
					return ((Artifact) tempArray[_row]).getDescription() ;
			}
		}

		/**
		 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
		 */
		public void update (Observable _arg0, Object _arg1)
		{
			fireTableDataChanged() ;
		}

	}

	/**
	 * PlanningTableModel : Table model for planification
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	private class PlanningTableModel extends AbstractTableModel implements Observer
	{
		private static final long serialVersionUID = -5139748853363139948L ;

		private TaskDefinition task ;

		private ArrayList <String> head ;

		/**
		 * The number of columns to show
		 */
		private final short COLUMN_NUMBER = 4 ;

		/**
		 * number of rows (estimation and real)
		 */
		private final short ROW_NUMBER = 2 ;

		/**
		 * The locale controller for the language.
		 */
		private LocaleController controllerLocale = null ;

		/**
		 * Constructor
		 * 
		 * @param _task
		 */
		public PlanningTableModel (TaskDefinition _task)
		{
			super() ;

			this.controllerLocale = LocaleController.getInstance() ;
			this.controllerLocale.addLocaleListener(new LocaleListener()
			{
				public void localeChanged ()
				{
					updateText() ;
				}
			}) ;

			this.task = _task ;
			head = new ArrayList <String>() ;
			head.add(Bundle.getText("TaskDefinitionPanelPlanTableType")) ;
			head.add(Bundle.getText("TaskDefinitionPanelPlanTableStart")) ;
			head.add(Bundle.getText("TaskDefinitionPanelPlanTableFinish")) ;
			head.add(Bundle.getText("TaskDefinitionPanelPlanTableAmount")) ;
		}

		/**
		 * 
		 * This method updates texts in this table during a language changing.
		 * 
		 * @author MaT
		 * @version 1.0
		 * 
		 */
		private void updateText ()
		{
			TaskDefinitionPanel.this.planificationTable.getColumnModel().getColumn(0).setHeaderValue(Bundle.getText("TaskDefinitionPanelPlanTableType")) ;
			TaskDefinitionPanel.this.planificationTable.getColumnModel().getColumn(1).setHeaderValue(Bundle.getText("TaskDefinitionPanelPlanTableStart")) ;
			TaskDefinitionPanel.this.planificationTable.getColumnModel().getColumn(2).setHeaderValue(Bundle.getText("TaskDefinitionPanelPlanTableFinish")) ;
			TaskDefinitionPanel.this.planificationTable.getColumnModel().getColumn(3).setHeaderValue(Bundle.getText("TaskDefinitionPanelPlanTableAmount")) ;
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
		 */
		@ Override
		public boolean isCellEditable (int _row, int _col)
		{
			return _col > 0 ;
		}

		/**
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		public int getColumnCount ()
		{
			return COLUMN_NUMBER ;
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@ Override
		public Class <?> getColumnClass (int _col)
		{
			return getValueAt(0, _col).getClass() ;
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@ Override
		public String getColumnName (int _col)
		{
			return head.get(_col) ;
		}

		/**
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		public int getRowCount ()
		{
			return ROW_NUMBER ;
		}

		/**
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt (int _row, int _col)
		{
			switch (_row)
			{
				case 0:
					switch (_col)
					{
						case 0:
							return Bundle.getText("TaskDefinitionPanelPlanPrevision") ;
						case 1:
							return task.getPlanningData().getStartDate() ;
						case 2:
							return task.getPlanningData().getFinishDate() ;
						default:
							return task.getPlanningData().getDuration() ;
					}
				default:
					switch (_col)
					{
						case 0:
							return Bundle.getText("TaskDefinitionPanelPlanReal") ;
						case 1:
							return task.getRealData().getStartDate() ;
						case 2:
							return task.getRealData().getFinishDate() ;
						default:
							return task.getRealData().getDuration() ;
					}

			}
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
		 */
		@ Override
		public void setValueAt (Object _object, int _row, int _col)
		{
			boolean localPrevision = _row == 0 ? true : false ;
			boolean localStart = _col == 1 ? true : false ;

			if (_col == 3)
			{
				BreakdownElementsControler.setDurationForTaskDefinition(task, ((Float) _object).floatValue(), localPrevision) ;
			}
			else
			{
				BreakdownElementsControler.setDateForTaskDefinition(task, (Date) _object, localStart, localPrevision) ;
			}

		}

		/**
		 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
		 */
		public void update (Observable _arg0, Object _arg1)
		{
			fireTableDataChanged() ;
		}

	}

	/**
	 * DateFieldRenderer : Renders a date in the planning table
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	private class DateFieldRenderer extends DefaultTableCellRenderer
	{
		private static final long serialVersionUID = 3306334890531211958L ;

		/**
		 * Constructor
		 * 
		 */
		public DateFieldRenderer ()
		{
			super() ;
		}

		/**
		 * @see javax.swing.table.DefaultTableCellRenderer#setValue(java.lang.Object)
		 */
		@ Override
		protected void setValue (Object _object)
		{
			super.setValue(Bundle.dateFormat.format(_object)) ;
		}

	}

	/**
	 * DateFieldEditor : Allows correct edition in table cells for date type
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	private class DateFieldEditor extends DefaultCellEditor
	{
		private static final long serialVersionUID = 7691417260602343994L ;

		/**
		 * Constructor
		 * 
		 */
		public DateFieldEditor ()
		{
			super(new JFormattedTextField(Bundle.dateFormat)) ;

			DateFormatter localFormatter = new DateFormatter(Bundle.dateFormat) ;

			// Text field formatting
			DefaultFormatterFactory localFormatterFactory = new DefaultFormatterFactory(localFormatter) ;
			((JFormattedTextField) getComponent()).setFormatterFactory(localFormatterFactory) ;

		}

		/**
		 * @see javax.swing.DefaultCellEditor#getCellEditorValue()
		 */
		@ Override
		public Object getCellEditorValue ()
		{
			return ((JFormattedTextField) getComponent()).getValue() ;

		}

		/**
		 * @see javax.swing.DefaultCellEditor#getTableCellEditorComponent(javax.swing.JTable,
		 *      java.lang.Object, boolean, int, int)
		 */
		@ Override
		public Component getTableCellEditorComponent (JTable _table, Object _value, boolean _arg2, int _row, int _col)
		{
			JFormattedTextField ftf = (JFormattedTextField) super.getTableCellEditorComponent(_table, _value, _arg2, _row, _col) ;
			/*
			 * ((JFormattedTextField) getComponent()).setValue(_value) ; return
			 * ((JFormattedTextField) getComponent()) ;
			 */
			ftf.setValue(_value) ;
			return ftf ;
		}

	}

} // @jve:decl-index=0:visual-constraint="10,10"
