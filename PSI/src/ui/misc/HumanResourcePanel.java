package ui.misc;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import ui.resource.Bundle;

import model.HumanResource; 
import model.spem2.RoleDescriptor;
import model.spem2.TaskDefinition;
import model.spem2.TaskDescriptor;

/**
 * HumanResourcePanel : TODO type description
 *
 * @author Florence MATTLER
 * @version 1.0
 *
 */
public class HumanResourcePanel extends JPanel implements Observer
{
	private static final long serialVersionUID = 7889691041843745114L ;
	
	private HumanResource humanResource;
	
	private JPanel infoPanel = null;
	
	private JPanel idPanel;
	
	private JLabel idLabel;
	
	private JTextField idTextField = null;
	
	private JPanel namePanel;
	
	private JLabel nameLabel;
	
	private JTextField nameTextField = null;
	
	private JPanel mailPanel;
	
	private JLabel mailLabel = null;
	
	private JTextField mailTextField = null;
	
	private JLabel rolesEmptyLabel = null ;
	
	private JLabel tasksDescriptorEmptyLabel = null ;
	
	private JPanel rolesPanel = null;
	
	private JScrollPane rolesScrollPane = null;
	
	private JTable rolesTable = null;
	
	private JPanel tasksDescriptorPanel = null;
	
	private JScrollPane tasksDescriptorScrollPane = null;
	
	private JTable tasksDescriptorTable = null;
	
	/**
	 * Constructor
	 * 
	 * @param _descriptor
	 */
	public HumanResourcePanel (HumanResource _descriptor)
	{
		super() ;
		this.humanResource = _descriptor ;
		this.humanResource.addObserver(this) ;
		initialize() ;

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

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)) ;
		this.setComponentOrientation(java.awt.ComponentOrientation.LEFT_TO_RIGHT) ;
		this.add(getInfoPanel(), null) ;
		this.add(Box.createRigidArea(new Dimension(0, 30))) ;
		this.add(getRolesPanel(), null) ;
		this.add(Box.createRigidArea(new Dimension(0, 30))) ;
		this.add(getTasksDescriptorPanel(), null);
		this.add(Box.createVerticalGlue()) ;
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
		this.humanResource  = _humanResource ;
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
	private JPanel getInfoPanel(){
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
	private JPanel getIdPanel(){
		if (idPanel == null)
		{
			idLabel = new JLabel() ;
			idLabel.setText(Bundle.getText("HumanResourceDescriptorPanelIDLabel")) ;
			idLabel.setPreferredSize(new java.awt.Dimension(150, 16)) ;
			idPanel = new JPanel() ;
			idPanel.setLayout(new BoxLayout(getIdPanel(), BoxLayout.X_AXIS)) ;
			idPanel.add(idLabel, null) ;
			idPanel.add(getIdTextField(), null);
			idPanel.add(Box.createHorizontalGlue()) ;
		}
		return idPanel;
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
			idTextField.setEditable(false);
			idTextField.setBackground(java.awt.Color.white);
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
			nameTextField.setBackground(java.awt.Color.white);
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
			mailTextField.setBackground(java.awt.Color.white);
			mailTextField.setMaximumSize(new java.awt.Dimension(400, 20)) ;
			mailTextField.setText(humanResource.getEmail()) ;
		}
		return mailTextField ;
	}
	
	private JScrollPane getRolesScrollPane(){
		if (rolesScrollPane == null)
		{
			rolesScrollPane = new JScrollPane() ;
			rolesScrollPane.setViewportView(getRolesTable()) ;
			rolesScrollPane.setWheelScrollingEnabled(true) ;
		}
		return rolesScrollPane;
	}
	
	private JPanel getRolesPanel(){
		if (rolesPanel == null)
		{
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			rolesPanel = new JPanel() ;
			rolesPanel.setLayout(flowLayout);
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
		return rolesPanel;
	}
	
	private JTable getRolesTable(){
		if (rolesTable == null)
		{
			rolesTable = new JTable(new RolesTableModel(humanResource)) ;
			rolesTable.setPreferredScrollableViewportSize(new Dimension(600, 80)) ;
			rolesTable.getColumnModel().getColumn(0).setMaxWidth(150) ;
			rolesTable.getTableHeader().setReorderingAllowed(false) ;
		}
		
		return rolesTable;
	}
	
	private JScrollPane getTasksDescriptorScrollPane(){
		if (tasksDescriptorScrollPane == null)
		{
			tasksDescriptorScrollPane = new JScrollPane() ;
			tasksDescriptorScrollPane.setViewportView(getTasksDescriptorTable()) ;
			tasksDescriptorScrollPane.setWheelScrollingEnabled(true) ;
		}
		return tasksDescriptorScrollPane;
	}
		
	private JPanel getTasksDescriptorPanel (){
		if (tasksDescriptorPanel == null)
		{
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			tasksDescriptorPanel = new JPanel() ;
			tasksDescriptorPanel.setLayout(flowLayout1);
			tasksDescriptorPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("HumanResourceDescriptorPanelTableTasksHead"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			tasksDescriptorPanel.setMinimumSize(new Dimension(200, 80)) ;		
			
			if (humanResource.getPerformingTasks().size() == 0)
			{
				tasksDescriptorPanel.add(tasksDescriptorEmptyLabel, null);
			}
			else
			{
				tasksDescriptorPanel.add(getTasksDescriptorScrollPane(), null);
			}
		}
		return tasksDescriptorPanel;
	}
	
	private JTable getTasksDescriptorTable(){
		if (tasksDescriptorTable == null)
		{
			tasksDescriptorTable = new JTable(new TaskDescriptorsTableModel(humanResource)) ;
			tasksDescriptorTable.setPreferredScrollableViewportSize(new Dimension(600, 80)) ;
			tasksDescriptorTable.getColumnModel().getColumn(0).setMaxWidth(150) ;
			tasksDescriptorTable.getTableHeader().setReorderingAllowed(false) ;
		}
		
		return tasksDescriptorTable;
	}
	
	
	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update (Observable _observable, Object _object)
	{
		// TODO Auto-generated method stub
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
		if ( ((HumanResource) _observable).getPerformingTasks().size() == 0)
		{
			tasksDescriptorPanel.removeAll();
			tasksDescriptorPanel.add(tasksDescriptorEmptyLabel,null);
			tasksDescriptorPanel.revalidate();
		}
		
		else
		{
			tasksDescriptorPanel.removeAll();
			tasksDescriptorPanel.add(getTasksDescriptorScrollPane(),null);
			tasksDescriptorPanel.revalidate();
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
		
		private Collection <TaskDefinition> data ;

		private ArrayList <String> head ;

		private HumanResource human ;
		
		/**
		 * The number of columns to show (id, name and description)
		 */
		private final short COLUMN_NUMBER = 3 ;


		/**
		 * Constructor
		 *
		 * @param _task
		 */
		public TaskDescriptorsTableModel (HumanResource _human)
		{
			super() ;

			this.human = _human ;
			data = this.human.getPerformingTasks();
			head = new ArrayList <String>() ;
			head.add(Bundle.getText("HumanResourceDescriptorPanelTableTasksID")) ;
			head.add(Bundle.getText("HumanResourceDescriptorPanelTableTasksName")) ;
			head.add(Bundle.getText("HumanResourceDescriptorPanelTableTasksDescription")) ;
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
					return ((TaskDescriptor)tempArray[_row]).getId() ;
				case 1:
					return ((TaskDescriptor)tempArray[_row]).getName() ;
				default:
					return ((TaskDescriptor)tempArray[_row]).getDescription() ;
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

			this.human = _human ;
			this.human.addObserver(this) ;
			this.data = humanResource.getPerformingRoles();
			this.head = new ArrayList <String>() ;
			head.add(Bundle.getText("HumanResourceDescriptorPanelTableID")) ;
			head.add(Bundle.getText("HumanResourceDescriptorPanelTableName")) ;
			head.add(Bundle.getText("HumanResourceDescriptorPanelTableDescription")) ;
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
					return ((RoleDescriptor)tempArray[_row]).getId() ;
				case 1:
					return ((RoleDescriptor)tempArray[_row]).getName() ;
				default:
					return ((RoleDescriptor)tempArray[_row]).getDescription() ;
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