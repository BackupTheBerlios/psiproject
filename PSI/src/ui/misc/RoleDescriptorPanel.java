
package ui.misc ;

import java.awt.Dimension ;
import java.awt.FlowLayout ;
import java.util.ArrayList ;
import java.util.Collection ;
import java.util.Observable ;
import java.util.Observer ;

import javax.swing.Box ;
import javax.swing.BoxLayout ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;
import javax.swing.JScrollPane ;
import javax.swing.JTable ;
import javax.swing.JTextField ;
import javax.swing.border.TitledBorder ;
import javax.swing.table.AbstractTableModel ;

import model.HumanResource ;
import model.spem2.RoleDescriptor ;
import model.spem2.TaskDescriptor ;
import ui.resource.Bundle ;
import ui.resource.LocaleController ;
import ui.resource.LocaleListener ;

/**
 * RoleDescriptorPanel : RoleDescriptor panel view
 * 
 * @author Conde Mickael K.
 * @version 1.0
 * 
 */
public class RoleDescriptorPanel extends JPanel implements Observer
{
	private static final long serialVersionUID = 7889691041843745114L ;

	private RoleDescriptor roleDescriptor ;

	private JPanel infoPanel = null ;

	private JPanel resourcesPanel = null ;

	private JPanel idPanel = null ;

	private JPanel namePanel = null ;

	private JLabel idLabel = null ;

	private JLabel nameLabel = null ;

	private JTextField nameTextField = null ;

	private JLabel resourcesEmptyLabel = null ;

	private JLabel taskDescriptorsEmptyLabel = null ;

	private JScrollPane resourcesScrollPane = null ;

	private JTable resourcesTable = null ;

	private JPanel taskDescriptorsPanel = null ;

	private JScrollPane taskDescriptorsScrollPane = null ;

	private JTable taskDescriptorsTable = null ;

	private JTextField idTextField = null ;

	/**
	 * The locale controller for the language.
	 */
	private LocaleController controllerLocale = null ;

	/**
	 * Constructor
	 * 
	 * @param _descriptor
	 */
	public RoleDescriptorPanel (RoleDescriptor _descriptor)
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

		this.roleDescriptor = _descriptor ;
		this.roleDescriptor.addObserver(this) ;
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
		((TitledBorder) infoPanel.getBorder()).setTitle(Bundle.getText("RoleDescriptorPanelInfoTitle")) ;
		((TitledBorder) resourcesPanel.getBorder()).setTitle(Bundle.getText("RoleDescriptorPanelTableHead")) ;
		idLabel.setText(Bundle.getText("RoleDescriptorPanelIDLabel")) ;
		nameLabel.setText(Bundle.getText("RoleDescriptorPanelNameLabel")) ;
		((TitledBorder) taskDescriptorsPanel.getBorder()).setTitle(Bundle.getText("RoleDescriptorPanelTableTasksHead")) ;

	}

	/**
	 * Panel initialisation
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	private void initialize ()
	{
		resourcesEmptyLabel = new JLabel(Bundle.getText("RoleDescriptorPanelNoResources")) ;
		taskDescriptorsEmptyLabel = new JLabel(Bundle.getText("RoleDescriptorPanelNoTasks")) ;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)) ;
		this.setComponentOrientation(java.awt.ComponentOrientation.LEFT_TO_RIGHT) ;
		this.add(getInfoPanel(), null) ;
		this.add(Box.createRigidArea(new Dimension(0, 30))) ;
		this.add(getResourcesPanel(), null) ;
		this.add(Box.createRigidArea(new Dimension(0, 30))) ;
		this.add(getTaskDescriptorsPanel(), null) ;
		this.add(Box.createVerticalGlue()) ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the roleDescriptor.
	 */
	public RoleDescriptor getRoleDescriptor ()
	{
		return this.roleDescriptor ;
	}

	/**
	 * Setter
	 * 
	 * @param _roleDescriptor
	 *            The roleDescriptor to set.
	 */
	public void setRoleDescriptor (RoleDescriptor _roleDescriptor)
	{
		this.roleDescriptor = _roleDescriptor ;
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
			infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("RoleDescriptorPanelInfoTitle"),
					javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			infoPanel.add(getIdPanel(), null) ;
			infoPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			infoPanel.add(getNamePanel(), null) ;
		}
		return infoPanel ;
	}

	/**
	 * This method initializes resourcesPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getResourcesPanel ()
	{
		if (resourcesPanel == null)
		{
			FlowLayout flowLayout = new FlowLayout() ;
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT) ;
			resourcesPanel = new JPanel() ;
			resourcesPanel.setLayout(flowLayout) ;
			resourcesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("RoleDescriptorPanelTableHead"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			resourcesPanel.setMinimumSize(new Dimension(200, 80)) ;
			if (roleDescriptor.getPerformers().size() == 0)
			{
				resourcesPanel.add(resourcesEmptyLabel, null) ;
			}
			else
			{
				resourcesPanel.add(getResourcesScrollPane(), null) ;
			}
		}
		return resourcesPanel ;
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
			idLabel.setText(Bundle.getText("RoleDescriptorPanelIDLabel")) ;
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
	 * This method initializes idTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getIdTextField ()
	{
		if (idTextField == null)
		{
			idTextField = new JTextField(30) ;
			idTextField.setEditable(false) ;
			idTextField.setBackground(java.awt.Color.white) ;
			idTextField.setMaximumSize(new java.awt.Dimension(400, 20)) ;
			idTextField.setText(roleDescriptor.getId()) ;
		}
		return idTextField ;
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
			nameLabel.setText(Bundle.getText("RoleDescriptorPanelNameLabel")) ;
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
	 * This method initializes nameTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getNameTextField ()
	{
		if (nameTextField == null)
		{
			nameTextField = new JTextField(30) ;
			nameTextField.setEditable(false) ;
			nameTextField.setBackground(java.awt.Color.white) ;
			nameTextField.setMaximumSize(new java.awt.Dimension(400, 20)) ;
			nameTextField.setText(roleDescriptor.getName()) ;
		}
		return nameTextField ;
	}

	/**
	 * Needed for "cool" behaviour of the main tab
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@ Override
	public boolean equals (Object _object)
	{
		return (_object instanceof RoleDescriptorPanel && ((RoleDescriptorPanel) _object).roleDescriptor.getId().equals(roleDescriptor.getId())) ;
	}

	/**
	 * This method initializes resourceScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getResourcesScrollPane ()
	{
		if (resourcesScrollPane == null)
		{
			resourcesScrollPane = new JScrollPane() ;
			resourcesScrollPane.setViewportView(getResourcesTable()) ;
			resourcesScrollPane.setWheelScrollingEnabled(true) ;
		}
		return resourcesScrollPane ;
	}

	/**
	 * This method initializes resourceTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getResourcesTable ()
	{
		if (resourcesTable == null)
		{
			resourcesTable = new JTable(new ResourcesTableModel(roleDescriptor)) ;
			resourcesTable.setPreferredScrollableViewportSize(new Dimension(500, resourcesTable.getRowHeight() * 4)) ;
			resourcesTable.getColumnModel().getColumn(0).setMaxWidth(150) ;
			resourcesTable.getTableHeader().setReorderingAllowed(false) ;
		}
		return resourcesTable ;
	}

	/**
	 * This method initializes taskDescriptorsPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getTaskDescriptorsPanel ()
	{
		if (taskDescriptorsPanel == null)
		{
			FlowLayout flowLayout1 = new FlowLayout() ;
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT) ;
			taskDescriptorsPanel = new JPanel() ;
			taskDescriptorsPanel.setLayout(flowLayout1) ;
			taskDescriptorsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("RoleDescriptorPanelTableTasksHead"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			taskDescriptorsPanel.setMinimumSize(new Dimension(200, 80)) ;

			if (roleDescriptor.getPrimaryTasks().size() == 0)
			{
				taskDescriptorsPanel.add(taskDescriptorsEmptyLabel, null) ;
			}
			else
			{
				taskDescriptorsPanel.add(getTaskDescriptorsScrollPane(), null) ;
			}
		}
		return taskDescriptorsPanel ;
	}

	/**
	 * This method initializes taskDescriptorsScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getTaskDescriptorsScrollPane ()
	{
		if (taskDescriptorsScrollPane == null)
		{
			taskDescriptorsScrollPane = new JScrollPane() ;
			taskDescriptorsScrollPane.setViewportView(getTaskDescriptorsTable()) ;
		}
		return taskDescriptorsScrollPane ;
	}

	/**
	 * This method initializes taskDescriptorsTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTaskDescriptorsTable ()
	{
		if (taskDescriptorsTable == null)
		{
			taskDescriptorsTable = new JTable(new TaskDescriptorsTableModel(roleDescriptor)) ;
			taskDescriptorsTable.setPreferredScrollableViewportSize(new Dimension(500, taskDescriptorsTable.getRowHeight() * 4)) ;
			taskDescriptorsTable.getColumnModel().getColumn(0).setMaxWidth(150) ;
			taskDescriptorsTable.getTableHeader().setReorderingAllowed(false) ;
		}
		return taskDescriptorsTable ;
	}

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update (Observable _observable, Object _object)
	{
		// Checking for the tables
		if ( ((RoleDescriptor) _observable).getPerformers().size() == 0)
		{
			resourcesPanel.removeAll() ;
			resourcesPanel.add(resourcesEmptyLabel, null) ;
			resourcesPanel.revalidate() ;
		}

		else
		{
			resourcesPanel.removeAll() ;
			resourcesPanel.add(getResourcesScrollPane(), null) ;
			resourcesPanel.revalidate() ;
		}

	}

	/**
	 * ResourcesTableModel : table model designed to display roles
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	private class ResourcesTableModel extends AbstractTableModel implements Observer
	{
		private static final long serialVersionUID = 9077566987053078985L ;

		private Collection <HumanResource> data ;

		private ArrayList <String> head ;

		private RoleDescriptor role ;

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
		 * @param _role
		 */
		public ResourcesTableModel (RoleDescriptor _role)
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

			this.role = _role ;
			this.role.addObserver(this) ;
			this.data = role.getPerformers() ;
			this.head = new ArrayList <String>() ;
			head.add(Bundle.getText("RoleDescriptorPanelTableID")) ;
			head.add(Bundle.getText("RoleDescriptorPanelTableName")) ;
			head.add(Bundle.getText("RoleDescriptorPanelTableMail")) ;
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
			if (roleDescriptor.getPerformers().size() == 0)
			{
				resourcesEmptyLabel.setText(Bundle.getText("RoleDescriptorPanelNoResources")) ;
			}
			else
			{
				RoleDescriptorPanel.this.resourcesTable.getColumnModel().getColumn(0).setHeaderValue(Bundle.getText("RoleDescriptorPanelTableID")) ;
				RoleDescriptorPanel.this.resourcesTable.getColumnModel().getColumn(1).setHeaderValue(Bundle.getText("RoleDescriptorPanelTableName")) ;
				RoleDescriptorPanel.this.resourcesTable.getColumnModel().getColumn(2).setHeaderValue(Bundle.getText("RoleDescriptorPanelTableMail")) ;
			}
			
			
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
					return ((HumanResource) tempArray[_row]).getId() ;
				case 1:
					return ((HumanResource) tempArray[_row]).getFullName() ;
				default:
					return ((HumanResource) tempArray[_row]).getEmail() ;
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

	/**
	 * TaskDescriptorsTableModel : table model designed to display task desc.
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	private class TaskDescriptorsTableModel extends AbstractTableModel
	{
		private static final long serialVersionUID = -405519902328407279L ;

		private Collection <TaskDescriptor> data ;

		private ArrayList <String> head ;

		private RoleDescriptor role ;

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
		 * @param _task
		 */
		public TaskDescriptorsTableModel (RoleDescriptor _role)
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

			this.role = _role ;
			data = this.role.getPrimaryTasks() ;
			head = new ArrayList <String>() ;
			head.add(Bundle.getText("RoleDescriptorPanelTableTasksID")) ;
			head.add(Bundle.getText("RoleDescriptorPanelTableTasksName")) ;
			head.add(Bundle.getText("RoleDescriptorPanelTableTasksDescription")) ;
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
			if (roleDescriptor.getPrimaryTasks().size() == 0)
			{
				taskDescriptorsEmptyLabel.setText(Bundle.getText("RoleDescriptorPanelNoTasks")) ;
			}
			else
			{
				RoleDescriptorPanel.this.taskDescriptorsTable.getColumnModel().getColumn(0).setHeaderValue(Bundle.getText("RoleDescriptorPanelTableTasksID")) ;
				RoleDescriptorPanel.this.taskDescriptorsTable.getColumnModel().getColumn(1).setHeaderValue(Bundle.getText("RoleDescriptorPanelTableTasksName")) ;
				RoleDescriptorPanel.this.taskDescriptorsTable.getColumnModel().getColumn(2).setHeaderValue(Bundle.getText("RoleDescriptorPanelTableTasksDescription")) ;
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
					return ((TaskDescriptor) tempArray[_row]).getId() ;
				case 1:
					return ((TaskDescriptor) tempArray[_row]).getName() ;
				default:
					return ((TaskDescriptor) tempArray[_row]).getDescription() ;
			}
		}

	}

} // @jve:decl-index=0:visual-constraint="10,10"
