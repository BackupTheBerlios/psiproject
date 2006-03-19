
package ui.misc ;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import model.HumanResource;
import model.spem2.RoleDescriptor;
import model.spem2.TaskDefinition;
import model.spem2.TaskDescriptor;
import process.GlobalController;
import process.utility.BreakdownElementsControler;
import ui.resource.Bundle;
import ui.resource.LocaleController;
import ui.resource.LocaleListener;

/**
 * HumanResourcePanel : Panel describing resources
 * 
 * @author Florence MATTLER
 * @author Conde Mickael K.
 * @author MaT
 * @version 1.0
 * 
 */
public class HumanResourcePanel extends JPanel implements Observer
{
	private static final long serialVersionUID = 7889691041843745114L ;

	private HumanResource humanResource ;

	private JPanel infoPanel = null ;

	private JPanel idPanel ;

	private JLabel idLabel ;

	private JTextField idTextField = null ;

	private JPanel namePanel ;

	private JLabel nameLabel ;

	private JTextField nameTextField = null ;

	private JPanel mailPanel ;

	private JLabel mailLabel = null ;

	private JTextField mailTextField = null ;

	private JLabel rolesEmptyLabel = null ;

	private JLabel tasksDescriptorEmptyLabel = null ;

	private JPanel rolesPanel = null ;

	private JScrollPane rolesScrollPane = null ;

	private JTable rolesTable = null ;

	private JPanel tasksDescriptorPanel = null ;

	private JScrollPane tasksDescriptorScrollPane = null ;

	private JTable tasksDescriptorTable = null ;
	
	private MouseListener tasksMouseListener = null ;
	
	private MouseListener rolesMouseListener = null ;
	
	private JPopupMenu taskDefinitionPopupMenu = null ;

	private JMenuItem taskUnlinkMenuItem = null ;
	
	private JPopupMenu rolePopupMenu = null ;

	private JMenuItem roleUnlinkMenuItem = null ;

	/**
	 * The locale controller for the language.
	 */
	private LocaleController controllerLocale = null ;

	/**
	 * Constructor
	 * 
	 * @param _descriptor
	 */
	public HumanResourcePanel (HumanResource _descriptor)
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

		this.humanResource = _descriptor ;
		this.humanResource.addObserver(this) ;
		initialize() ;

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
		((TitledBorder) infoPanel.getBorder()).setTitle(Bundle.getText("HumanResourceDescriptorPanelInfoTitle")) ;
		idLabel.setText(Bundle.getText("HumanResourceDescriptorPanelIDLabel")) ;
		nameLabel.setText(Bundle.getText("HumanResourceDescriptorPanelNameLabel")) ;
		mailLabel.setText(Bundle.getText("HumanResourceDescriptorPanelMailLabel")) ;
		((TitledBorder) rolesPanel.getBorder()).setTitle(Bundle.getText("HumanResourceDescriptorPanelTableHead")) ;
		((TitledBorder) tasksDescriptorPanel.getBorder()).setTitle(Bundle.getText("HumanResourceDescriptorPanelTableTasksHead")) ;
		
		tasksDescriptorEmptyLabel.setText(Bundle.getText("HumanResourceDescriptorPanelNoTask")) ;
		rolesEmptyLabel.setText(Bundle.getText("HumanResourceDescriptorPanelNoRole")) ;
		
		taskUnlinkMenuItem.setText(Bundle.getText("HumanResourceDescriptorPanelUnlink")) ;
		roleUnlinkMenuItem.setText(Bundle.getText("HumanResourceDescriptorPanelUnlink")) ;
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
		rolesEmptyLabel = new JLabel(Bundle.getText("HumanResourceDescriptorPanelNoRole")) ;
		tasksDescriptorEmptyLabel = new JLabel(Bundle.getText("HumanResourceDescriptorPanelNoTask")) ;
		
		taskDefinitionPopupMenu = new JPopupMenu() ;
		taskUnlinkMenuItem = new JMenuItem(Bundle.getText("HumanResourceDescriptorPanelUnlink")) ;
		taskUnlinkMenuItem.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed (java.awt.event.ActionEvent e)
			{				
				ArrayList <TaskDefinition> localTasks = new ArrayList <TaskDefinition>(((TaskDefinitionsTableModel)getTasksDescriptorTable().getModel()).getData()) ;
				
				ArrayList <TaskDefinition> localCol = new ArrayList <TaskDefinition>() ;
				for (int i = 0 ; i < localTasks.size() ; i++)
				{
					if (GlobalController.currentIteration.getTasks().contains(localTasks.get(i)))
					{
						localCol.add(localTasks.get(i)) ;
					}
				}
				
				for (int i = 0 ; i < getTasksDescriptorTable().getSelectedRows().length ; i++)
				{					
					BreakdownElementsControler.unlinkTaskDefinitionAndHumanResource(localCol.get(getTasksDescriptorTable().getSelectedRows()[i]), getHumanResource()) ;
				}
				
			}
		}) ;
		taskDefinitionPopupMenu.add(taskUnlinkMenuItem) ;
		
		rolePopupMenu = new JPopupMenu() ;
		roleUnlinkMenuItem = new JMenuItem(Bundle.getText("HumanResourceDescriptorPanelUnlink")) ;
		roleUnlinkMenuItem.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed (java.awt.event.ActionEvent e)
			{			
				ArrayList <RoleDescriptor> localRoles = new ArrayList <RoleDescriptor>(((RolesTableModel)getRolesTable().getModel()).getData()) ;				
				
				for (int i = 0 ; i < getRolesTable().getSelectedRows().length ; i++)
				{					
					BreakdownElementsControler.unlinkRoleDescriptorAndHumanResource(localRoles.get(getRolesTable().getSelectedRows()[i]), getHumanResource()) ;
				}
			}
		}) ;
		rolePopupMenu.add(roleUnlinkMenuItem) ;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)) ;
		this.setComponentOrientation(java.awt.ComponentOrientation.LEFT_TO_RIGHT) ;
		this.add(getInfoPanel(), null) ;
		this.add(Box.createRigidArea(new Dimension(0, 30))) ;
		this.add(getRolesPanel(), null) ;
		this.add(Box.createRigidArea(new Dimension(0, 30))) ;
		this.add(getTasksDescriptorPanel(), null) ;
		this.add(Box.createVerticalGlue()) ;
	}

	
	
	/**
	 * Getter
	 *
	 * @return Returns the rolesMouseListener.
	 */
	private MouseListener getRolesMouseListener ()
	{
		if (this.rolesMouseListener == null)
		{
			this.rolesMouseListener = new MouseAdapter()
			{
					public void mouseReleased(MouseEvent _e)
					{
						if ((_e.isPopupTrigger() || _e.getButton() == MouseEvent.BUTTON3) && getRolesTable().getSelectedRows().length > 0)
						{
							rolePopupMenu.show(_e.getComponent(), _e.getX(), _e.getY()) ;							
						}
					};
			} ;
		}
		
		return this.rolesMouseListener ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the tasksMouseListener.
	 */
	private MouseListener getTasksMouseListener ()
	{
		if (this.tasksMouseListener == null)
		{
			this.tasksMouseListener = new MouseAdapter()
			{
					public void mouseReleased(MouseEvent _e)
					{
						if ((_e.isPopupTrigger() || _e.getButton() == MouseEvent.BUTTON3) && getTasksDescriptorTable().getSelectedRows().length > 0)
						{
							taskDefinitionPopupMenu.show(_e.getComponent(), _e.getX(), _e.getY()) ;							
						}
					};
			} ;
		}
		return this.tasksMouseListener ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the humanResourceDescriptor.
	 */
	public HumanResource getHumanResource ()
	{
		return this.humanResource ;
	}

	/**
	 * Setter
	 * 
	 * @param _humanResoucre
	 *            The humanResource to set.
	 */
	public void setHumanResourceDescriptor (HumanResource _humanResource)
	{
		this.humanResource = _humanResource ;
	}

	/**
	 * Needed for "cool" behaviour of the main tab
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@ Override
	public boolean equals (Object _object)
	{
		return (_object instanceof HumanResourcePanel && ((HumanResourcePanel) _object).humanResource.getId().equals(humanResource.getId())) ;
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
			infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("HumanResourceDescriptorPanelInfoTitle"),
					javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			infoPanel.add(getIdPanel(), null) ;
			infoPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			infoPanel.add(getNamePanel(), null) ;
			infoPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			infoPanel.add(getMailPanel(), null) ;
		}
		return infoPanel ;
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
			idLabel.setText(Bundle.getText("HumanResourceDescriptorPanelIDLabel")) ;
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
			idTextField.setText(humanResource.getId()) ;
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
			nameLabel.setText(Bundle.getText("HumanResourceDescriptorPanelNameLabel")) ;
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
			nameTextField.setText(humanResource.getFullName()) ;
		}
		return nameTextField ;
	}

	/**
	 * This method initializes mailPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getMailPanel ()
	{
		if (mailPanel == null)
		{
			mailLabel = new JLabel() ;
			mailLabel.setText(Bundle.getText("HumanResourceDescriptorPanelMailLabel")) ;
			mailLabel.setPreferredSize(new java.awt.Dimension(150, 16)) ;
			mailPanel = new JPanel() ;
			mailPanel.setLayout(new BoxLayout(getMailPanel(), BoxLayout.X_AXIS)) ;
			mailPanel.add(mailLabel, null) ;
			mailPanel.add(getMailTextField(), null) ;
			mailPanel.add(Box.createHorizontalGlue()) ;
		}
		return mailPanel ;
	}

	/**
	 * This method initializes mailTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getMailTextField ()
	{
		if (mailTextField == null)
		{
			mailTextField = new JTextField(30) ;
			mailTextField.setEditable(false) ;
			mailTextField.setBackground(java.awt.Color.white) ;
			mailTextField.setMaximumSize(new java.awt.Dimension(400, 20)) ;
			mailTextField.setText(humanResource.getEmail()) ;
		}
		return mailTextField ;
	}

	private JScrollPane getRolesScrollPane ()
	{
		if (rolesScrollPane == null)
		{
			rolesScrollPane = new JScrollPane() ;
			rolesScrollPane.setViewportView(getRolesTable()) ;
			rolesScrollPane.setWheelScrollingEnabled(true) ;
		}
		return rolesScrollPane ;
	}

	private JPanel getRolesPanel ()
	{
		if (rolesPanel == null)
		{
			FlowLayout flowLayout = new FlowLayout() ;
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT) ;
			rolesPanel = new JPanel() ;
			rolesPanel.setLayout(flowLayout) ;
			rolesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("HumanResourceDescriptorPanelTableHead"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			rolesPanel.setMinimumSize(new Dimension(200, 80)) ;
			if (humanResource.getPerformingRoles().size() == 0)
			{
				rolesPanel.add(rolesEmptyLabel, null) ;
			}
			else
			{
				rolesPanel.add(getRolesScrollPane(), null) ;
			}
		}
		return rolesPanel ;
	}

	private JTable getRolesTable ()
	{
		if (rolesTable == null)
		{
			rolesTable = new JTable(new RolesTableModel(humanResource)) ;
			rolesTable.setPreferredScrollableViewportSize(new Dimension(600, 80)) ;
			rolesTable.getColumnModel().getColumn(0).setMaxWidth(150) ;
			rolesTable.getTableHeader().setReorderingAllowed(false) ;
			rolesTable.addMouseListener(getRolesMouseListener()) ;
		}

		return rolesTable ;
	}

	private JScrollPane getTasksDescriptorScrollPane ()
	{
		if (tasksDescriptorScrollPane == null)
		{
			tasksDescriptorScrollPane = new JScrollPane() ;
			tasksDescriptorScrollPane.setViewportView(getTasksDescriptorTable()) ;
			tasksDescriptorScrollPane.setWheelScrollingEnabled(true) ;
		}
		return tasksDescriptorScrollPane ;
	}

	private JPanel getTasksDescriptorPanel ()
	{
		if (tasksDescriptorPanel == null)
		{
			FlowLayout flowLayout1 = new FlowLayout() ;
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT) ;
			tasksDescriptorPanel = new JPanel() ;
			tasksDescriptorPanel.setLayout(flowLayout1) ;
			tasksDescriptorPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("HumanResourceDescriptorPanelTableTasksHead"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			tasksDescriptorPanel.setMinimumSize(new Dimension(200, 80)) ;

			boolean tasksFound = false ;
			Iterator <TaskDefinition> localIt = humanResource.getPerformingTasks().iterator() ;
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
				tasksDescriptorPanel.add(tasksDescriptorEmptyLabel, null) ;
			}
			else
			{
				tasksDescriptorPanel.add(getTasksDescriptorScrollPane(), null) ;
			}
		}
		return tasksDescriptorPanel ;
	}

	private JTable getTasksDescriptorTable ()
	{
		if (tasksDescriptorTable == null)
		{
			tasksDescriptorTable = new JTable(new TaskDefinitionsTableModel(humanResource)) ;
			tasksDescriptorTable.setPreferredScrollableViewportSize(new Dimension(600, 80)) ;
			tasksDescriptorTable.getColumnModel().getColumn(0).setMaxWidth(150) ;
			tasksDescriptorTable.getTableHeader().setReorderingAllowed(false) ;
			tasksDescriptorTable.addMouseListener(getTasksMouseListener()) ;
		}

		return tasksDescriptorTable ;
	}

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update (Observable _observable, Object _object)
	{
		if ( ((HumanResource) _observable).getPerformingRoles().size() == 0)
		{
			rolesPanel.removeAll() ;
			rolesPanel.add(rolesEmptyLabel, null) ;
			rolesPanel.revalidate() ;
		}

		else
		{
			rolesPanel.removeAll() ;
			rolesPanel.add(getRolesScrollPane(), null) ;
			rolesPanel.revalidate() ;
		}

		boolean tasksFound = false ;
		Iterator <TaskDefinition> localIt = ((HumanResource) _observable).getPerformingTasks().iterator() ;
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
			tasksDescriptorPanel.removeAll() ;
			tasksDescriptorPanel.add(tasksDescriptorEmptyLabel, null) ;
			tasksDescriptorPanel.revalidate() ;
		}

		else
		{
			tasksDescriptorPanel.removeAll() ;
			tasksDescriptorPanel.add(getTasksDescriptorScrollPane(), null) ;
			tasksDescriptorPanel.revalidate() ;
		}
	}

	/**
	 * TaskDescriptorsTableModel : table model designed to display task desc.
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	private class TaskDefinitionsTableModel extends AbstractTableModel
	{
		private static final long serialVersionUID = -405519902328407279L ;

		private Collection <TaskDefinition> data ;

		private ArrayList <String> head ;

		private HumanResource human ;

		/**
		 * The locale controller for the language.
		 */
		private LocaleController controllerLocale = null ;

		/**
		 * The number of columns to show (id, name and description)
		 */
		private final short COLUMN_NUMBER = 3 ;

		/**
		 * Constructor
		 * 
		 * @param _task
		 */
		public TaskDefinitionsTableModel (HumanResource _human)
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

			this.human = _human ;
			data = this.human.getPerformingTasks() ;
			head = new ArrayList <String>() ;
			head.add(Bundle.getText("HumanResourceDescriptorPanelTableTasksID")) ;
			head.add(Bundle.getText("HumanResourceDescriptorPanelTableTasksName")) ;
			head.add(Bundle.getText("HumanResourceDescriptorPanelTableTasksDescription")) ;
		}

		
		
		/**
		 * Getter
		 *
		 * @return Returns the data.
		 */
		public Collection <TaskDefinition> getData ()
		{
			return this.data ;
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
			boolean tasksFound = false ;
			Iterator <TaskDefinition> localIt = humanResource.getPerformingTasks().iterator() ;
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
				tasksDescriptorEmptyLabel.setText(Bundle.getText("HumanResourceDescriptorPanelNoTask")) ;
			}
			else
			{
				HumanResourcePanel.this.tasksDescriptorTable.getColumnModel().getColumn(0).setHeaderValue(Bundle.getText("HumanResourceDescriptorPanelTableTasksID")) ;
				HumanResourcePanel.this.tasksDescriptorTable.getColumnModel().getColumn(1).setHeaderValue(Bundle.getText("HumanResourceDescriptorPanelTableTasksName")) ;
				HumanResourcePanel.this.tasksDescriptorTable.getColumnModel().getColumn(2).setHeaderValue(Bundle.getText("HumanResourceDescriptorPanelTableTasksDescription")) ;
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
					return ((TaskDescriptor) tempArray[_row]).getId() ;
				case 1:
					return ((TaskDescriptor) tempArray[_row]).getName() ;
				default:
					return ((TaskDescriptor) tempArray[_row]).getDescription() ;
			}
		}

	}

	/**
	 * ResourcesTableModel : table model designed to display roles
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	private class RolesTableModel extends AbstractTableModel implements Observer
	{
		private static final long serialVersionUID = 9077566987053078985L ;

		private Collection <RoleDescriptor> data ;

		private ArrayList <String> head ;

		private HumanResource human ;

		/**
		 * The locale controller for the language.
		 */
		private LocaleController controllerLocale = null ;

		/**
		 * The number of columns to show (id, name and description)
		 */
		private final short COLUMN_NUMBER = 3 ;

		/**
		 * Constructor
		 * 
		 * @param _role
		 */
		public RolesTableModel (HumanResource _human)
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

			this.human = _human ;
			this.human.addObserver(this) ;
			this.data = humanResource.getPerformingRoles() ;
			this.head = new ArrayList <String>() ;
			head.add(Bundle.getText("HumanResourceDescriptorPanelTableID")) ;
			head.add(Bundle.getText("HumanResourceDescriptorPanelTableName")) ;
			head.add(Bundle.getText("HumanResourceDescriptorPanelTableDescription")) ;
		}

		/**
		 * Getter
		 *
		 * @return Returns the data.
		 */
		public Collection <RoleDescriptor> getData ()
		{
			return this.data ;
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
			if (humanResource.getPerformingRoles().size() == 0)
			{
				rolesEmptyLabel.setText(Bundle.getText("HumanResourceDescriptorPanelNoRole")) ;
			}
			else
			{
				HumanResourcePanel.this.rolesTable.getColumnModel().getColumn(0).setHeaderValue(Bundle.getText("HumanResourceDescriptorPanelTableID")) ;
				HumanResourcePanel.this.rolesTable.getColumnModel().getColumn(1).setHeaderValue(Bundle.getText("HumanResourceDescriptorPanelTableName")) ;
				HumanResourcePanel.this.rolesTable.getColumnModel().getColumn(2).setHeaderValue(Bundle.getText("HumanResourceDescriptorPanelTableDescription")) ;
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
					return ((RoleDescriptor) tempArray[_row]).getId() ;
				case 1:
					return ((RoleDescriptor) tempArray[_row]).getName() ;
				default:
					return ((RoleDescriptor) tempArray[_row]).getDescription() ;
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

}
