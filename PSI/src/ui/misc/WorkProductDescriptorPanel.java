
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

import model.spem2.Artifact ;
import model.spem2.TaskDescriptor ;
import model.spem2.WorkProductDescriptor ;
import ui.resource.Bundle ;
import ui.resource.LocaleController ;
import ui.resource.LocaleListener ;

import javax.swing.JTextArea ;

/**
 * WorkProductDescriptorPanel : panel view for a product
 * 
 * @author Conde Mickael K.
 * @author MaT
 * @version 1.0
 * 
 */
public class WorkProductDescriptorPanel extends JPanel implements Observer
{
	private static final long serialVersionUID = 2533290431292125431L ;

	private WorkProductDescriptor product = null ;

	private JPanel infoPanel = null ;

	private JScrollPane dataScrollPane = null ;

	private JPanel dataPanel = null ;

	private JPanel idPanel = null ;

	private JPanel namePanel = null ;

	private JPanel typePanel = null ;

	private JPanel descriptionPanel = null ;

	private JPanel artifactsPanel = null ;

	private JPanel inTasksPanel = null ;

	private JPanel outTasksPanel = null ;

	private JLabel idLabel = null ;

	private JTextField idTextField = null ;

	private JLabel nameLabel = null ;

	private JTextField nameTextField = null ;

	private JLabel typeLabel = null ;

	private JTextField typeTextField = null ;

	private JLabel descriptionLabel = null ;

	private JScrollPane artifactsScrollPane = null ;

	private JTable artifactsTable = null ;

	private JScrollPane inTasksScrollPane = null ;

	private JTable inTasksTable = null ;

	private JScrollPane outTasksScrollPane = null ;

	private JTable outTasksTable = null ;

	private JLabel artifactsEmptyLabel = null ;

	private JLabel inTasksEmptyLabel = null ;

	private JLabel outTasksEmptyLabel = null ;

	private JTextArea descriptionTextArea = null ;

	private JScrollPane descriptionScrollPane = null ;

	/**
	 * The locale controller for the language.
	 */
	private LocaleController controllerLocale = null ;

	/**
	 * This is the default constructor
	 */
	public WorkProductDescriptorPanel (WorkProductDescriptor _product)
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

		this.product = _product ;
		this.product.addObserver(this) ;
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
		((TitledBorder) infoPanel.getBorder()).setTitle(Bundle.getText("WorkProductDescriptorPanelInfoTitle")) ;
		idLabel.setText(Bundle.getText("WorkProductDescriptorPanelIDLabel")) ;
		nameLabel.setText(Bundle.getText("WorkProductDescriptorPanelNameLabel")) ;
		if (product.getProductType() != null)
		{
			typeLabel.setText(Bundle.getText("WorkProductDescriptorPanelTypeLabel")) ;
		}
		if (!product.getDescription().trim().equals(""))
		{
			descriptionLabel.setText(Bundle.getText("WorkProductDescriptorPanelDescriptionLabel")) ;
		}
		((TitledBorder) artifactsPanel.getBorder()).setTitle(Bundle.getText("WorkProductDescriptorPanelArtifactHead")) ;
		((TitledBorder) inTasksPanel.getBorder()).setTitle(Bundle.getText("WorkProductDescriptorPanelInHead")) ;
		((TitledBorder) outTasksPanel.getBorder()).setTitle(Bundle.getText("WorkProductDescriptorPanelOutHead")) ;
		
		artifactsEmptyLabel.setText(Bundle.getText("WorkProductDescriptorPanelNoArtifact")) ;
		inTasksEmptyLabel.setText(Bundle.getText("WorkProductDescriptorPanelNoTask")) ;
		outTasksEmptyLabel.setText(Bundle.getText("WorkProductDescriptorPanelNoTask")) ;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize ()
	{
		artifactsEmptyLabel = new JLabel(Bundle.getText("WorkProductDescriptorPanelNoArtifact")) ;
		inTasksEmptyLabel = new JLabel(Bundle.getText("WorkProductDescriptorPanelNoTask")) ;
		outTasksEmptyLabel = new JLabel(Bundle.getText("WorkProductDescriptorPanelNoTask")) ;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)) ;
		this.setSize(533, 323) ;
		this.setComponentOrientation(java.awt.ComponentOrientation.LEFT_TO_RIGHT) ;
		this.add(getInfoPanel(), null) ;
		this.add(Box.createRigidArea(new Dimension(0, 30))) ;
		this.add(getDataScrollPane(), null) ;
	}

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update (Observable _observable, Object _object)
	{
		if ( ((WorkProductDescriptor) _observable).getArtifacts().size() == 0)
		{
			artifactsPanel.removeAll() ;
			artifactsPanel.add(artifactsEmptyLabel, null) ;
			artifactsPanel.revalidate() ;
		}

		else
		{
			artifactsPanel.removeAll() ;
			artifactsPanel.add(getArtifactsScrollPane(), null) ;
			artifactsPanel.revalidate() ;
		}

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
			infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("WorkProductDescriptorPanelInfoTitle"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			infoPanel.add(getIdPanel(), null) ;
			infoPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			infoPanel.add(getNamePanel(), null) ;
			if (product.getProductType() != null)
			{
				infoPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
				infoPanel.add(getTypePanel(), null) ;
			}
			if (!product.getDescription().trim().equals(""))
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
			dataPanel.add(getArtifactsPanel(), null) ;
			dataPanel.add(getInTasksPanel(), null) ;
			dataPanel.add(getOutTasksPanel(), null) ;
			dataPanel.add(Box.createVerticalGlue()) ;
		}
		return dataPanel ;
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
			idLabel.setText(Bundle.getText("WorkProductDescriptorPanelIDLabel")) ;
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
			nameLabel.setText(Bundle.getText("WorkProductDescriptorPanelNameLabel")) ;
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
	 * This method initializes typePanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getTypePanel ()
	{
		if (typePanel == null)
		{
			typeLabel = new JLabel() ;
			typeLabel.setText(Bundle.getText("WorkProductDescriptorPanelTypeLabel")) ;
			typeLabel.setPreferredSize(new java.awt.Dimension(150, 16)) ;
			typePanel = new JPanel() ;
			typePanel.setLayout(new BoxLayout(getTypePanel(), BoxLayout.X_AXIS)) ;
			typePanel.add(typeLabel, null) ;
			typePanel.add(getTypeTextField(), null) ;
			typePanel.add(Box.createHorizontalGlue()) ;
		}
		return typePanel ;
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
			descriptionLabel.setText(Bundle.getText("WorkProductDescriptorPanelDescriptionLabel")) ;
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
			descriptionTextArea.setText(product.getDescription()) ;
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
	 * This method initializes artifactsPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getArtifactsPanel ()
	{
		if (artifactsPanel == null)
		{
			FlowLayout flowLayout = new FlowLayout() ;
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT) ;
			artifactsPanel = new JPanel() ;
			artifactsPanel.setLayout(flowLayout) ;
			artifactsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("WorkProductDescriptorPanelArtifactHead"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;

			if (product.getArtifacts().size() == 0)
			{
				artifactsPanel.add(artifactsEmptyLabel, null) ;
			}
			else
			{
				artifactsPanel.add(getArtifactsScrollPane(), null) ;

			}
		}
		return artifactsPanel ;
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
			FlowLayout flowLayout1 = new FlowLayout() ;
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT) ;
			inTasksPanel = new JPanel() ;
			inTasksPanel.setLayout(flowLayout1) ;
			inTasksPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("WorkProductDescriptorPanelInHead"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;

			if (product.getUsingTasks().size() == 0)
			{
				inTasksPanel.add(inTasksEmptyLabel, null) ;
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
			FlowLayout flowLayout2 = new FlowLayout() ;
			flowLayout2.setAlignment(java.awt.FlowLayout.LEFT) ;
			outTasksPanel = new JPanel() ;
			outTasksPanel.setLayout(flowLayout2) ;
			outTasksPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("WorkProductDescriptorPanelOutHead"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;

			if (product.getProducingTasks().size() == 0)
			{
				outTasksPanel.add(outTasksEmptyLabel, null) ;
			}
			else
			{
				outTasksPanel.add(getOutTasksScrollPane(), null) ;
			}
		}
		return outTasksPanel ;
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
			idTextField.setText(product.getId()) ;
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
			nameTextField.setText(product.getName()) ;
			nameTextField.setMaximumSize(new java.awt.Dimension(400, 20)) ;
			nameTextField.setBackground(java.awt.Color.white) ;
			nameTextField.setEditable(false) ;
		}
		return nameTextField ;
	}

	/**
	 * This method initializes typeTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTypeTextField ()
	{
		if (typeTextField == null)
		{
			typeTextField = new JTextField(30) ;
			if (product.getProductType() != null)
			{
				typeTextField.setText(product.getProductType().getName()) ;
			}
			typeTextField.setMaximumSize(new java.awt.Dimension(400, 20)) ;
			typeTextField.setBackground(java.awt.Color.white) ;
			typeTextField.setEditable(false) ;
		}
		return typeTextField ;
	}

	/**
	 * Needed for "cool" behaviour of the main tab
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@ Override
	public boolean equals (Object _object)
	{
		return (_object instanceof WorkProductDescriptorPanel && ((WorkProductDescriptorPanel) _object).product.getId().equals(product.getId())) ;
	}

	/**
	 * This method initializes artifactsScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getArtifactsScrollPane ()
	{
		if (artifactsScrollPane == null)
		{
			artifactsScrollPane = new JScrollPane() ;
			artifactsScrollPane.setViewportView(getArtifactsTable()) ;
		}
		return artifactsScrollPane ;
	}

	/**
	 * This method initializes artifactsTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getArtifactsTable ()
	{
		if (artifactsTable == null)
		{
			artifactsTable = new JTable(new ArtifactsTableModel(product)) ;
			artifactsTable.setPreferredScrollableViewportSize(new Dimension(500, artifactsTable.getRowHeight() * 4)) ;
			artifactsTable.getColumnModel().getColumn(0).setMaxWidth(150) ;
			artifactsTable.getTableHeader().setReorderingAllowed(false) ;
		}
		return artifactsTable ;
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
			inTasksTable = new JTable(new TaskDescriptorsTableModel(product, true)) ;
			inTasksTable.setPreferredScrollableViewportSize(new Dimension(500, inTasksTable.getRowHeight() * 4)) ;
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
			outTasksTable = new JTable(new TaskDescriptorsTableModel(product, false)) ;
			outTasksTable.setPreferredScrollableViewportSize(new Dimension(500, outTasksTable.getRowHeight() * 4)) ;
			outTasksTable.getColumnModel().getColumn(0).setMaxWidth(150) ;
			outTasksTable.getTableHeader().setReorderingAllowed(false) ;
		}
		return outTasksTable ;
	}

	/*
	 * Table classes
	 */

	/**
	 * ArtifactsTableModel : Table model for artifacts
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	private class ArtifactsTableModel extends AbstractTableModel implements Observer
	{
		private static final long serialVersionUID = -1524379895502691020L ;

		private Collection <Artifact> data ;

		private ArrayList <String> head ;

		private WorkProductDescriptor product ;

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
		public ArtifactsTableModel (WorkProductDescriptor _product)
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

			this.product = _product ;
			this.product.addObserver(this) ;
			this.data = product.getArtifacts() ;
			this.head = new ArrayList <String>() ;
			head.add(Bundle.getText("WorkProductDescriptorPanelTableArtifactsID")) ;
			head.add(Bundle.getText("WorkProductDescriptorPanelTableArtifactsName")) ;
			head.add(Bundle.getText("WorkProductDescriptorPanelTableArtifactsDescription")) ;
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
			if (product.getArtifacts().size() == 0)
			{
				artifactsEmptyLabel.setText(Bundle.getText("WorkProductDescriptorPanelNoArtifact")) ;
			}
			else
			{
				WorkProductDescriptorPanel.this.artifactsTable.getColumnModel().getColumn(0).setHeaderValue(Bundle.getText("WorkProductDescriptorPanelTableArtifactsID")) ;
				WorkProductDescriptorPanel.this.artifactsTable.getColumnModel().getColumn(1).setHeaderValue(Bundle.getText("WorkProductDescriptorPanelTableArtifactsName")) ;
				WorkProductDescriptorPanel.this.artifactsTable.getColumnModel().getColumn(2).setHeaderValue(Bundle.getText("WorkProductDescriptorPanelTableArtifactsDescription")) ;
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
					return ((Artifact) tempArray[_row]).getId() ;
				case 1:
					return ((Artifact) tempArray[_row]).getName() ;
				default:
					return ((Artifact) tempArray[_row]).getDescription() ;
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
	 * TaskDescriptorsTableModel : Table model for tasks (descriptors)
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	private class TaskDescriptorsTableModel extends AbstractTableModel
	{
		private static final long serialVersionUID = -8928082572980054226L ;

		private Collection <TaskDescriptor> data ;

		private ArrayList <String> head ;

		private WorkProductDescriptor product ;

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
		public TaskDescriptorsTableModel (WorkProductDescriptor _product, boolean _in)
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

			this.product = _product ;
			data = _in ? this.product.getUsingTasks() : this.product.getProducingTasks() ;
			head = new ArrayList <String>() ;
			head.add(Bundle.getText("WorkProductDescriptorPanelTableTasksID")) ;
			head.add(Bundle.getText("WorkProductDescriptorPanelTableTasksName")) ;
			head.add(Bundle.getText("WorkProductDescriptorPanelTableTasksDescription")) ;
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
			if (product.getUsingTasks().size() == 0)
			{
				inTasksEmptyLabel.setText(Bundle.getText("WorkProductDescriptorPanelNoTask")) ;
			}
			else
			{
				WorkProductDescriptorPanel.this.inTasksTable.getColumnModel().getColumn(0).setHeaderValue(Bundle.getText("WorkProductDescriptorPanelTableTasksID")) ;
				WorkProductDescriptorPanel.this.inTasksTable.getColumnModel().getColumn(1).setHeaderValue(Bundle.getText("WorkProductDescriptorPanelTableTasksName")) ;
				WorkProductDescriptorPanel.this.inTasksTable.getColumnModel().getColumn(2).setHeaderValue(Bundle.getText("WorkProductDescriptorPanelTableTasksDescription")) ;
			}
			if (product.getProducingTasks().size() == 0)
			{
				outTasksEmptyLabel.setText(Bundle.getText("WorkProductDescriptorPanelNoTask")) ;
			}
			else
			{
				WorkProductDescriptorPanel.this.outTasksTable.getColumnModel().getColumn(0).setHeaderValue(Bundle.getText("WorkProductDescriptorPanelTableTasksID")) ;
				WorkProductDescriptorPanel.this.outTasksTable.getColumnModel().getColumn(1).setHeaderValue(Bundle.getText("WorkProductDescriptorPanelTableTasksName")) ;
				WorkProductDescriptorPanel.this.outTasksTable.getColumnModel().getColumn(2).setHeaderValue(Bundle.getText("WorkProductDescriptorPanelTableTasksDescription")) ;
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
