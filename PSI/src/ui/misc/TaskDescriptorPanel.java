
package ui.misc ;

import java.awt.Dimension ;
import java.awt.FlowLayout ;
import java.util.ArrayList ;
import java.util.Collection ;
import java.util.Iterator ;
import java.util.Observable ;
import java.util.Observer ;

import javax.swing.Box ;
import javax.swing.BoxLayout ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;
import javax.swing.JScrollPane ;
import javax.swing.JTable ;
import javax.swing.JTextArea ;
import javax.swing.JTextField ;
import javax.swing.border.TitledBorder ;
import javax.swing.table.AbstractTableModel ;

import model.spem2.TaskDefinition ;
import model.spem2.TaskDescriptor ;
import model.spem2.WorkProductDescriptor ;
import process.GlobalController ;
import ui.resource.Bundle ;
import ui.resource.LocaleController ;
import ui.resource.LocaleListener ;

/**
 * JPanelTaskDescriptor : TODO type description
 * 
 * @author kass
 * @version 1.0
 * 
 */
public class TaskDescriptorPanel extends JPanel implements Observer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8593037668904516931L ;

	private TaskDescriptor task = null ;

	private JPanel infoPanel = null ;

	private JScrollPane dataScrollPane = null ;

	private JPanel dataPanel = null ;

	private JPanel tasksPanel = null ;

	private JPanel idPanel = null ;

	private JPanel namePanel = null ;

	private JPanel descriptionPanel = null ;

	private JPanel inProductsPanel = null ;

	private JPanel outProductsPanel = null ;

	private JLabel idLabel = null ;

	private JTextField idTextField = null ;

	private JLabel nameLabel = null ;

	private JTextField nameTextField = null ;

	private JLabel descriptionLabel = null ;

	private JScrollPane inTasksScrollPane = null ;

	private JTable inProductsTable = null ;

	private JScrollPane outProductsScrollPane = null ;

	private JTable outProductsTable = null ;

	private JLabel inProductsEmptyLabel = null ;

	private JLabel outProductsEmptyLabel = null ;

	private JTextArea descriptionTextArea = null ;

	private JScrollPane descriptionScrollPane = null ;

	private JScrollPane tasksScrollPane = null ;

	private JTable tasksTable = null ;

	private JLabel tasksEmptyLabel = null ;

	/**
	 * The locale controller for the language.
	 */
	private LocaleController controllerLocale = null ;

	public TaskDescriptorPanel (TaskDescriptor _task)
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
		tasksEmptyLabel.setText(Bundle.getText("TaskDescriptorPanelNoTask")) ;
		inProductsEmptyLabel.setText(Bundle.getText("TaskDescriptorPanelNoProduct")) ;
		outProductsEmptyLabel.setText(Bundle.getText("TaskDescriptorPanelNoProduct")) ;
		((TitledBorder) infoPanel.getBorder()).setTitle(Bundle.getText("TaskDescriptorPanelInfoTitle")) ;
		idLabel.setText(Bundle.getText("TaskDescriptorPanelIDLabel")) ;
		nameLabel.setText(Bundle.getText("TaskDescriptorPanelNameLabel")) ;
		if (!task.getDescription().trim().equals(""))
		{
			descriptionLabel.setText(Bundle.getText("TaskDescriptorPanelDescriptionLabel")) ;
		}
		((TitledBorder) inProductsPanel.getBorder()).setTitle(Bundle.getText("TaskDescriptorPanelInHead")) ;
		((TitledBorder) outProductsPanel.getBorder()).setTitle(Bundle.getText("TaskDescriptorPanelOutHead")) ;
		((TitledBorder) tasksPanel.getBorder()).setTitle(Bundle.getText("TaskDescriptorPanelTaskHead")) ;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize ()
	{

		tasksEmptyLabel = new JLabel(Bundle.getText("TaskDescriptorPanelNoTask")) ;
		inProductsEmptyLabel = new JLabel(Bundle.getText("TaskDescriptorPanelNoProduct")) ;
		outProductsEmptyLabel = new JLabel(Bundle.getText("TaskDescriptorPanelNoProduct")) ;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)) ;
		this.setSize(533, 323) ;
		this.setComponentOrientation(java.awt.ComponentOrientation.LEFT_TO_RIGHT) ;
		this.add(getInfoPanel(), null) ;
		this.add(Box.createRigidArea(new Dimension(0, 5))) ;
		this.add(getDataScrollPane(), null) ;
	}

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update (Observable _observable, Object _object)
	{
		boolean tasksFound = false ;
		Iterator <TaskDefinition> localIt = ((TaskDescriptor) _observable).getTasks().iterator() ;
		TaskDefinition localTask ;

		while (localIt.hasNext())
		{
			localTask = localIt.next() ;
			if (GlobalController.currentIteration.getTasks().contains(localTask))
			{
				tasksFound = true ;
				break ;
			}
		}

		if (!tasksFound)
		{
			tasksPanel.removeAll() ;
			tasksPanel.add(tasksEmptyLabel, null) ;
			tasksPanel.revalidate() ;
		}

		else
		{
			tasksPanel.removeAll() ;
			tasksPanel.add(getTasksScrollPane(), null) ;
			tasksPanel.revalidate() ;
		}

	}

	private JPanel getInfoPanel ()
	{
		if (infoPanel == null)
		{
			infoPanel = new JPanel() ;
			infoPanel.setLayout(new BoxLayout(getInfoPanel(), BoxLayout.Y_AXIS)) ;
			infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("TaskDescriptorPanelInfoTitle"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			infoPanel.add(getIdPanel(), null) ;
			infoPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			infoPanel.add(getNamePanel(), null) ;

			if (!task.getDescription().trim().equals(""))
			{
				infoPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
				infoPanel.add(getDescriptionPanel(), null) ;
			}
		}
		return infoPanel ;
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
			dataPanel.add(getTasksPanel(), null) ;
			dataPanel.add(getInProductsPanel(), null) ;
			dataPanel.add(getOutProductsPanel(), null) ;
			dataPanel.add(Box.createVerticalGlue()) ;
		}
		return dataPanel ;
	}

	private JPanel getIdPanel ()
	{
		if (idPanel == null)
		{
			idLabel = new JLabel() ;
			idLabel.setText(Bundle.getText("TaskDescriptorPanelIDLabel")) ;
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
			nameLabel.setText(Bundle.getText("TaskDescriptorPanelNameLabel")) ;
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
			descriptionLabel.setText(Bundle.getText("TaskDescriptorPanelDescriptionLabel")) ;
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
			descriptionTextArea.setEditable(false) ;
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
			descriptionScrollPane.setMaximumSize(new java.awt.Dimension(410, 68)) ;
			descriptionScrollPane.setViewportView(getDescriptionTextArea()) ;
		}
		return descriptionScrollPane ;
	}

	/**
	 * This method initializes inProductsPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getInProductsPanel ()
	{
		if (inProductsPanel == null)
		{
			FlowLayout flowLayout1 = new FlowLayout() ;
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT) ;
			inProductsPanel = new JPanel() ;
			inProductsPanel.setLayout(flowLayout1) ;
			inProductsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("TaskDescriptorPanelInHead"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;

			if (task.getInputProducts().size() == 0)
			{
				inProductsPanel.add(inProductsEmptyLabel, null) ;
			}
			else
			{
				inProductsPanel.add(getInTasksScrollPane(), null) ;
			}
		}
		return inProductsPanel ;
	}

	/**
	 * This method initializes outTasksPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getOutProductsPanel ()
	{
		if (outProductsPanel == null)
		{
			FlowLayout flowLayout2 = new FlowLayout() ;
			flowLayout2.setAlignment(java.awt.FlowLayout.LEFT) ;
			outProductsPanel = new JPanel() ;
			outProductsPanel.setLayout(flowLayout2) ;
			outProductsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("TaskDescriptorPanelOutHead"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;

			if (task.getOutputProducts().size() == 0)
			{
				outProductsPanel.add(outProductsEmptyLabel, null) ;
			}
			else
			{
				outProductsPanel.add(getOutProductsScrollPane(), null) ;
			}
		}
		return outProductsPanel ;
	}

	private JTextField getIdTextField ()
	{
		if (idTextField == null)
		{
			idTextField = new JTextField(30) ;
			idTextField.setText(task.getId()) ;
			idTextField.setMaximumSize(new java.awt.Dimension(400, 20)) ;
			idTextField.setBackground(java.awt.Color.white) ;
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
			nameTextField.setText(task.getName()) ;
			nameTextField.setMaximumSize(new java.awt.Dimension(400, 20)) ;
			nameTextField.setBackground(java.awt.Color.white) ;
			nameTextField.setEditable(false) ;
		}
		return nameTextField ;
	}

	/**
	 * This method initializes artifactsPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getTasksPanel ()
	{
		if (tasksPanel == null)
		{
			FlowLayout flowLayout = new FlowLayout() ;
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT) ;
			tasksPanel = new JPanel() ;
			tasksPanel.setLayout(flowLayout) ;
			tasksPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("TaskDescriptorPanelTaskHead"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;

			boolean tasksFound = false ;
			Iterator <TaskDefinition> localIt = task.getTasks().iterator() ;
			TaskDefinition localTask ;

			while (localIt.hasNext())
			{
				localTask = localIt.next() ;
				if (GlobalController.currentIteration.getTasks().contains(localTask))
				{
					tasksFound = true ;
					break ;
				}
			}

			if (!tasksFound)
			{
				tasksPanel.add(tasksEmptyLabel, null) ;
			}
			else
			{
				tasksPanel.add(getTasksScrollPane(), null) ;

			}
		}
		return tasksPanel ;
	}

	/**
	 * Needed for "cool" behaviour of the main tab
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@ Override
	public boolean equals (Object _object)
	{
		return (_object instanceof TaskDescriptorPanel && ((TaskDescriptorPanel) _object).task.getId().equals(task.getId())) ;
	}

	/**
	 * This method initializes artifactsScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getTasksScrollPane ()
	{
		if (tasksScrollPane == null)
		{
			tasksScrollPane = new JScrollPane() ;
			tasksScrollPane.setViewportView(getTasksTable()) ;
		}
		return tasksScrollPane ;
	}

	/**
	 * This method initializes tasksTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTasksTable ()
	{
		if (tasksTable == null)
		{
			tasksTable = new JTable(new TasksTableModel(task)) ;
			tasksTable.setPreferredScrollableViewportSize(new Dimension(600, 80)) ;
			tasksTable.getColumnModel().getColumn(0).setMaxWidth(150) ;
			tasksTable.getTableHeader().setReorderingAllowed(false) ;
		}
		return tasksTable ;
	}

	/**
	 * This method initializes inProductsScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getInTasksScrollPane ()
	{
		if (inTasksScrollPane == null)
		{
			inTasksScrollPane = new JScrollPane() ;
			inTasksScrollPane.setViewportView(getInProductsTable()) ;
		}
		return inTasksScrollPane ;
	}

	/**
	 * This method initializes inProductsTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getInProductsTable ()
	{
		if (inProductsTable == null)
		{
			inProductsTable = new JTable(new WorkProductDescriptorsTableModel(task, true)) ;
			inProductsTable.setPreferredScrollableViewportSize(new Dimension(600, 80)) ;
			inProductsTable.getColumnModel().getColumn(0).setMaxWidth(150) ;
			inProductsTable.getTableHeader().setReorderingAllowed(false) ;
		}
		return inProductsTable ;
	}

	/**
	 * This method initializes outTasksScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getOutProductsScrollPane ()
	{
		if (outProductsScrollPane == null)
		{
			outProductsScrollPane = new JScrollPane() ;
			outProductsScrollPane.setViewportView(getOutProductsTable()) ;
		}
		return outProductsScrollPane ;
	}

	/**
	 * This method initializes outTasksTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getOutProductsTable ()
	{
		if (outProductsTable == null)
		{
			outProductsTable = new JTable(new WorkProductDescriptorsTableModel(task, false)) ;
			outProductsTable.setPreferredScrollableViewportSize(new Dimension(600, 80)) ;
			outProductsTable.getColumnModel().getColumn(0).setMaxWidth(150) ;
			outProductsTable.getTableHeader().setReorderingAllowed(false) ;
		}
		return outProductsTable ;
	}

	/**
	 * TasksTableModel : Table model for tasks TODO observers for rows ... :(
	 * 
	 * @author Kassem Badaoui
	 * @version 1.0
	 * 
	 */
	private class TasksTableModel extends AbstractTableModel implements Observer
	{
		private static final long serialVersionUID = -1524379895502691020L ;

		private Collection <TaskDefinition> data ;

		private ArrayList <String> head ;

		private TaskDescriptor task ;

		/**
		 * The number of columns to show (id, name and description)
		 */
		private final short COLUMN_NUMBER = 3 ;

		/**
		 * The locale controller for the language.
		 */
		private LocaleController controllerLocale = null ;

		/**
		 * Constructor
		 * 
		 * @param _role
		 */
		public TasksTableModel (TaskDescriptor _task)
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
			this.task.addObserver(this) ;

			this.data = task.getTasks() ;
			this.head = new ArrayList <String>() ;
			head.add(Bundle.getText("TaskDescriptorPanelTableTasksID")) ;
			head.add(Bundle.getText("TaskDescriptorPanelTableTasksName")) ;
			head.add(Bundle.getText("TaskDescriptorPanelTableTasksDescription")) ;
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
			// !TODO
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
			int localCount = 0 ;

			Iterator <TaskDefinition> localIt = data.iterator() ;

			while (localIt.hasNext())
			{
				if (GlobalController.currentIteration.getTasks().contains(localIt.next()))
				{
					localCount++ ;
				}
			}

			return localCount ;
		}

		/**
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt (int _row, int _col)
		{
			Collection <TaskDefinition> localCol = new ArrayList <TaskDefinition>() ;
			Iterator <TaskDefinition> localIt = data.iterator() ;
			TaskDefinition localTask ;

			while (localIt.hasNext())
			{
				localTask = localIt.next() ;
				if (GlobalController.currentIteration.getTasks().contains(localTask))
				{
					localCol.add(localTask) ;
				}
			}

			Object tempArray[] = localCol.toArray() ;
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
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@ Override
		public String getColumnName (int _index)
		{
			return head.get(_index) ;
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
		 */
		@ Override
		public boolean isCellEditable (int _row, int _col)
		{
			return false ;
		}

		/**
		 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
		 */
		public void update (Observable _observable, Object _object)
		{
			fireTableDataChanged() ;

		}
	}

	private class WorkProductDescriptorsTableModel extends AbstractTableModel
	{
		private static final long serialVersionUID = -8928082572980054226L ;

		private Collection <WorkProductDescriptor> data ;

		private ArrayList <String> head ;

		private TaskDescriptor task ;

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
		 * @param _product
		 * @param _in
		 *            in tasks or out tasks
		 */
		public WorkProductDescriptorsTableModel (TaskDescriptor _task, boolean _in)
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
			head.add(Bundle.getText("TaskDescriptorPanelTableProductsID")) ;
			head.add(Bundle.getText("TaskDescriptorPanelTableProductsName")) ;
			head.add(Bundle.getText("TaskDescriptorPanelTableProductsDescription")) ;
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
			// !TODO
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
					return ((WorkProductDescriptor) tempArray[_row]).getId() ;
				case 1:
					return ((WorkProductDescriptor) tempArray[_row]).getName() ;
				default:
					return ((WorkProductDescriptor) tempArray[_row]).getDescription() ;
			}
		}

	}
}
