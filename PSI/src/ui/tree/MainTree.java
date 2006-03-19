
package ui.tree ;

import java.awt.Component ;
import java.awt.datatransfer.Transferable ;
import java.awt.datatransfer.UnsupportedFlavorException ;
import java.awt.dnd.DnDConstants ;
import java.awt.dnd.DragGestureEvent ;
import java.awt.dnd.DragGestureListener ;
import java.awt.dnd.DragSource ;
import java.awt.dnd.DragSourceDragEvent ;
import java.awt.dnd.DragSourceDropEvent ;
import java.awt.dnd.DragSourceEvent ;
import java.awt.dnd.DragSourceListener ;
import java.awt.dnd.DropTarget ;
import java.awt.dnd.DropTargetDragEvent ;
import java.awt.dnd.DropTargetDropEvent ;
import java.awt.dnd.DropTargetEvent ;
import java.awt.dnd.DropTargetListener ;
import java.awt.dnd.InvalidDnDOperationException ;
import java.awt.event.KeyAdapter ;
import java.awt.event.KeyEvent ;
import java.awt.event.MouseAdapter ;
import java.awt.event.MouseEvent ;
import java.io.IOException ;

import javax.swing.ImageIcon ;
import javax.swing.JMenuItem ;
import javax.swing.JPopupMenu ;
import javax.swing.JTree ;
import javax.swing.event.TreeSelectionEvent ;
import javax.swing.event.TreeSelectionListener ;
import javax.swing.tree.DefaultMutableTreeNode ;
import javax.swing.tree.DefaultTreeCellRenderer ;
import javax.swing.tree.DefaultTreeModel ;
import javax.swing.tree.TreePath ;

import model.HumanResource ;
import model.LogInformation ;
import model.Project ;
import model.spem2.Artifact ;
import model.spem2.RoleDescriptor ;
import model.spem2.TaskDefinition ;
import process.exception.DuplicateElementException ;
import process.utility.BreakdownElementsControler ;
import ui.dialog.ArtifactAdderDialog ;
import ui.dialog.TaskDefinitionAdderDialog ;
import ui.misc.ArtifactPanel ;
import ui.misc.HumanResourcePanel ;
import ui.misc.LogPanel ;
import ui.misc.MainTabbedPane ;
import ui.misc.RoleDescriptorPanel ;
import ui.misc.TaskDefinitionPanel ;
import ui.misc.TaskDescriptorPanel ;
import ui.misc.WorkProductDescriptorPanel ;
import ui.resource.Bundle ;
import ui.resource.LocaleController ;
import ui.resource.LocaleListener ;
import ui.window.MainFrame ;

/**
 * MainTree : surcharged JTree to handle drag and drop
 * 
 * @author Conde Mickael K
 * @author MaT
 * @version 1.0
 * 
 */
public class MainTree extends JTree implements DragGestureListener, DragSourceListener, DropTargetListener, TreeSelectionListener
{
	private static final long serialVersionUID = -3884898485242997244L ;

	private JPopupMenu roleDescriptorPopupMenu = null ;

	private JPopupMenu roleDescriptorPopupMenuBis = null ;

	private JPopupMenu workProductDescriptorPopupMenu = null ;

	private JPopupMenu taskDescriptorPopupMenu = null ;

	private JPopupMenu artifactPopupMenu = null ;

	private JPopupMenu taskDefinitionPopupMenu = null ;

	private Project project = null ;

	private MainFrame mainFrame = null ;

	private DefaultTreeModel treeModel = null ;

	private JMenuItem roleExpandMenuItem = null ;

	private JMenuItem activityCloseMenuItem = null ;

	private JMenuItem roleExpandMenuItemBis = null ;

	private JMenuItem roleUnlinkMenuItem = null ;

	private JMenuItem activityCloseMenuItemBis = null ;

	private JMenuItem workProductAddMenuItem = null ;

	private JMenuItem workProductExpandMenuItem = null ;

	private JMenuItem workProductCloseMenuItem = null ;

	private JMenuItem taskDescriptorAddMenuItem = null ;

	private JMenuItem taskDescriptorExpandMenuItem = null ;

	private JMenuItem taskDescriptorCloseMenuItem = null ;

	private JMenuItem artifactCloseMenuItem = null ;

	private JMenuItem artifactDeleteMenuItem = null ;

	private JMenuItem artifactExpandMenuItem = null ;

	private JMenuItem taskDefinitionCloseMenuItem = null ;

	private JMenuItem taskDefinitionDeleteMenuItem = null ;

	private JMenuItem taskDefinitionExpandMenuItem = null ;

	/**
	 * The locale controller for the language.
	 */
	private LocaleController controllerLocale = null ;

	// private JPopupMenu resourcePopupMenu = null ;

	/**
	 * Constructor
	 * 
	 * @param _project
	 * @param _frame
	 */
	public MainTree (Project _project, MainFrame _frame)
	{
		this.controllerLocale = LocaleController.getInstance() ;
		this.controllerLocale.addLocaleListener(new LocaleListener()
		{
			public void localeChanged ()
			{
				updateText() ;
			}
		}) ;
		this.treeModel = new DefaultTreeModel(new DefaultMutableTreeNode(Bundle.getText("MainTreeDefault"))) ;
		this.setModel(treeModel) ;
		this.setCellRenderer(new MainTreeRenderer()) ;

		this.project = _project ;
		this.mainFrame = _frame ;

		/*
		 * Building popups
		 */
		// For Roles and roles bis (with delete option)
		roleDescriptorPopupMenu = new JPopupMenu() ;
		roleExpandMenuItem = new JMenuItem(Bundle.getText("MainTreePopupShow")) ;
		roleExpandMenuItem.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed (java.awt.event.ActionEvent e)
			{
				DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) getLastSelectedPathComponent() ;

				if (localNode instanceof RoleDescriptorTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((RoleDescriptorTreeNode) localNode).getRole().getName(),
							new RoleDescriptorPanel( ((RoleDescriptorTreeNode) localNode).getRole())) ;
				}

			}
		}) ;
		activityCloseMenuItem = new JMenuItem(Bundle.getText("MainTreePopupClose")) ;
		roleDescriptorPopupMenu.add(roleExpandMenuItem) ;
		roleDescriptorPopupMenu.addSeparator() ;
		roleDescriptorPopupMenu.add(activityCloseMenuItem) ;

		roleDescriptorPopupMenuBis = new JPopupMenu() ;
		roleExpandMenuItemBis = new JMenuItem(Bundle.getText("MainTreePopupShow")) ;
		roleExpandMenuItemBis.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed (java.awt.event.ActionEvent e)
			{
				DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) getLastSelectedPathComponent() ;

				if (localNode instanceof RoleDescriptorTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((RoleDescriptorTreeNode) localNode).getRole().getName(),
							new RoleDescriptorPanel( ((RoleDescriptorTreeNode) localNode).getRole())) ;
				}

			}
		}) ;
		roleUnlinkMenuItem = new JMenuItem(Bundle.getText("MainTreePopupDeleteRole")) ;
		roleUnlinkMenuItem.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed (java.awt.event.ActionEvent e)
			{
				DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) getLastSelectedPathComponent() ;

				if (localNode instanceof RoleDescriptorTreeNode)
				{
					RoleDescriptor tempRole = ((RoleDescriptorTreeNode) localNode).getRole() ;
					HumanResource tempResource = ((ResourceTreeNode) localNode.getParent()).getResource() ;
					BreakdownElementsControler.unlinkRoleDescriptorAndHumanResource(tempRole, tempResource) ;
					LogPanel.getInstance().addInformation(
							new LogInformation(Bundle.getText("MainFrameLogMessageOldMemberRole") + " : " + tempRole.getName() + " - "
									+ tempResource.getFullName())) ;
				}
			}
		}) ;
		activityCloseMenuItemBis = new JMenuItem(Bundle.getText("MainTreePopupClose")) ;
		roleDescriptorPopupMenuBis.add(roleExpandMenuItemBis) ;
		roleDescriptorPopupMenuBis.add(roleUnlinkMenuItem) ;
		roleDescriptorPopupMenuBis.addSeparator() ;
		roleDescriptorPopupMenuBis.add(activityCloseMenuItemBis) ;

		// For work products
		workProductDescriptorPopupMenu = new JPopupMenu() ;
		workProductAddMenuItem = new JMenuItem(Bundle.getText("MainTreePopupAddArtifact")) ;
		workProductAddMenuItem.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed (java.awt.event.ActionEvent e)
			{
				TreePath localPath = getSelectionPath() ;

				if (localPath.getLastPathComponent() instanceof WorkProductDescriptorTreeNode)
				{
					new ArtifactAdderDialog(MainTree.this.mainFrame, ((WorkProductDescriptorTreeNode) localPath.getLastPathComponent()).getProduct()) ;

				}

			}
		}) ;
		workProductExpandMenuItem = new JMenuItem(Bundle.getText("MainTreePopupShow")) ;
		workProductExpandMenuItem.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed (java.awt.event.ActionEvent e)
			{
				DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) getLastSelectedPathComponent() ;

				if (localNode instanceof WorkProductDescriptorTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((WorkProductDescriptorTreeNode) localNode).getProduct().getName(),
							new WorkProductDescriptorPanel( ((WorkProductDescriptorTreeNode) localNode).getProduct())) ;
				}

			}
		}) ;
		workProductCloseMenuItem = new JMenuItem(Bundle.getText("MainTreePopupClose")) ;
		workProductDescriptorPopupMenu.add(workProductAddMenuItem) ;
		workProductDescriptorPopupMenu.add(workProductExpandMenuItem) ;
		workProductDescriptorPopupMenu.addSeparator() ;
		workProductDescriptorPopupMenu.add(workProductCloseMenuItem) ;

		// For task descriptors
		taskDescriptorPopupMenu = new JPopupMenu() ;
		taskDescriptorAddMenuItem = new JMenuItem(Bundle.getText("MainTreePopupAddTask")) ;
		taskDescriptorAddMenuItem.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed (java.awt.event.ActionEvent e)
			{
				TreePath localPath = getSelectionPath() ;

				if (localPath.getLastPathComponent() instanceof TaskDescriptorTreeNode)
				{
					new TaskDefinitionAdderDialog(MainTree.this.mainFrame, ((TaskDescriptorTreeNode) localPath.getLastPathComponent()).getTask()) ;

				}

			}
		}) ;
		taskDescriptorExpandMenuItem = new JMenuItem(Bundle.getText("MainTreePopupShow")) ;
		taskDescriptorExpandMenuItem.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed (java.awt.event.ActionEvent e)
			{
				DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) getLastSelectedPathComponent() ;

				if (localNode instanceof TaskDescriptorTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((TaskDescriptorTreeNode) localNode).getTask().getName(),
							new TaskDescriptorPanel( ((TaskDescriptorTreeNode) localNode).getTask())) ;
				}

			}
		}) ;
		taskDescriptorCloseMenuItem = new JMenuItem(Bundle.getText("MainTreePopupClose")) ;
		taskDescriptorPopupMenu.add(taskDescriptorAddMenuItem) ;
		taskDescriptorPopupMenu.add(taskDescriptorExpandMenuItem) ;
		taskDescriptorPopupMenu.addSeparator() ;
		taskDescriptorPopupMenu.add(taskDescriptorCloseMenuItem) ;

		// For artifacts
		artifactPopupMenu = new JPopupMenu() ;
		artifactCloseMenuItem = new JMenuItem(Bundle.getText("MainTreePopupClose")) ;
		artifactDeleteMenuItem = new JMenuItem(Bundle.getText("Delete")) ;
		artifactDeleteMenuItem.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed (java.awt.event.ActionEvent e)
			{
				DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) getLastSelectedPathComponent() ;

				if (localNode instanceof ArtifactTreeNode)
				{
					BreakdownElementsControler.deleteArtifact( ((ArtifactTreeNode) localNode).getArtifact()) ;
					LogPanel.getInstance().addInformation(
							new LogInformation(Bundle.getText("MainFrameLogMessageArtifactDeleted") + " : "
									+ ((ArtifactTreeNode) localNode).getArtifact().getName())) ;

				}
			}
		}) ;
		artifactExpandMenuItem = new JMenuItem(Bundle.getText("MainTreePopupShow")) ;
		artifactExpandMenuItem.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed (java.awt.event.ActionEvent e)
			{
				DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) getLastSelectedPathComponent() ;

				if (localNode instanceof ArtifactTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((ArtifactTreeNode) localNode).getArtifact().getName(),
							new ArtifactPanel(mainFrame, ((ArtifactTreeNode) localNode).getArtifact())) ;
				}
			}
		}) ;
		artifactPopupMenu.add(artifactExpandMenuItem) ;
		artifactPopupMenu.add(artifactDeleteMenuItem) ;
		artifactPopupMenu.addSeparator() ;
		artifactPopupMenu.add(artifactCloseMenuItem) ;

		taskDefinitionPopupMenu = new JPopupMenu() ;
		taskDefinitionCloseMenuItem = new JMenuItem(Bundle.getText("MainTreePopupClose")) ;
		taskDefinitionDeleteMenuItem = new JMenuItem(Bundle.getText("Delete")) ;
		taskDefinitionDeleteMenuItem.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed (java.awt.event.ActionEvent e)
			{
				DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) getLastSelectedPathComponent() ;

				if (localNode instanceof TaskDefinitionTreeNode)
				{
					BreakdownElementsControler.deleteTaskDefinition( ((TaskDefinitionTreeNode) localNode).getTask(), MainTree.this.getProject()) ;
					LogPanel.getInstance().addInformation(
							new LogInformation(Bundle.getText("MainFrameLogMessageTaskDeleted") + " : "
									+ ((TaskDefinitionTreeNode) localNode).getTask().getName())) ;
				}
			}
		}) ;
		taskDefinitionExpandMenuItem = new JMenuItem(Bundle.getText("MainTreePopupShow")) ;
		taskDefinitionExpandMenuItem.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed (java.awt.event.ActionEvent e)
			{
				DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) getLastSelectedPathComponent() ;

				if (localNode instanceof TaskDefinitionTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((TaskDefinitionTreeNode) localNode).getTask().getName(),
							new TaskDefinitionPanel(mainFrame, ((TaskDefinitionTreeNode) localNode).getTask())) ;
				}

			}
		}) ;
		taskDefinitionPopupMenu.add(taskDefinitionExpandMenuItem) ;
		taskDefinitionPopupMenu.add(taskDefinitionDeleteMenuItem) ;
		taskDefinitionPopupMenu.addSeparator() ;
		taskDefinitionPopupMenu.add(taskDefinitionCloseMenuItem) ;

		/*
		 * Setting misc listeners
		 */
		this.addMouseListener(new TreeMouseListener()) ;

		this.addKeyListener(new TreeKeyListener()) ;

		this.addTreeSelectionListener(this) ;

		// Setting up the drag source
		DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_LINK, this) ;

		// Setting up the drop target
		new DropTarget(this, DnDConstants.ACTION_LINK, this) ;
	}

	/**
	 * 
	 * This method updates texts in this tree during a language changing.
	 * 
	 * @author MaT
	 * @version 1.0
	 * 
	 */
	private void updateText ()
	{
		roleExpandMenuItem.setText(Bundle.getText("MainTreePopupShow")) ;
		activityCloseMenuItem.setText(Bundle.getText("MainTreePopupClose")) ;
		roleExpandMenuItemBis.setText(Bundle.getText("MainTreePopupShow")) ;
		roleUnlinkMenuItem.setText(Bundle.getText("MainTreePopupDeleteRole")) ;
		activityCloseMenuItemBis.setText(Bundle.getText("MainTreePopupClose")) ;
		workProductAddMenuItem.setText(Bundle.getText("MainTreePopupClose")) ;
		workProductExpandMenuItem.setText(Bundle.getText("MainTreePopupShow")) ;
		workProductCloseMenuItem.setText(Bundle.getText("MainTreePopupClose")) ;
		taskDescriptorAddMenuItem.setText(Bundle.getText("MainTreePopupAddTask")) ;
		taskDescriptorExpandMenuItem.setText(Bundle.getText("MainTreePopupShow")) ;
		taskDescriptorCloseMenuItem.setText(Bundle.getText("MainTreePopupClose")) ;
		artifactCloseMenuItem.setText(Bundle.getText("MainTreePopupClose")) ;
		artifactDeleteMenuItem.setText(Bundle.getText("Delete")) ;
		artifactExpandMenuItem.setText(Bundle.getText("MainTreePopupShow")) ;
		taskDefinitionCloseMenuItem.setText(Bundle.getText("MainTreePopupClose")) ;
		taskDefinitionDeleteMenuItem.setText(Bundle.getText("Delete")) ;
		taskDefinitionExpandMenuItem.setText(Bundle.getText("MainTreePopupShow")) ;
		if(((DefaultMutableTreeNode)this.treeModel.getRoot()).getChildCount() == 0)
			((DefaultMutableTreeNode)this.treeModel.getRoot()).setUserObject(Bundle.getText("MainTreeDefault"));
	}

	/**
	 * Getter
	 * 
	 * @return Returns the treeModel.
	 */
	public DefaultTreeModel getModel ()
	{
		return this.treeModel ;
	}

	/*
	 * Specific methods
	 */

	/**
	 * Closes current project and stop displaying components
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	public void closeProject ()
	{
		project = null ;
		treeModel.setRoot(new DefaultMutableTreeNode(Bundle.getText("MainTreeDefault"))) ;
	}

	/**
	 * Generates a new tree hierarchy to display
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _project
	 */
	public void loadProject (Project _project)
	{
		this.project = _project ;
		treeModel.setRoot(new ProjectTreeNode(project, this)) ;

	}

	/*
	 * Drag listeners
	 */

	/**
	 * @see java.awt.dnd.DragGestureListener#dragGestureRecognized(java.awt.dnd.DragGestureEvent)
	 */
	public void dragGestureRecognized (DragGestureEvent _evt)
	{
		DefaultMutableTreeNode dragNode = (DefaultMutableTreeNode) getLastSelectedPathComponent() ;
		if (dragNode != null)
		{
			if (dragNode instanceof ResourceTreeNode || dragNode instanceof RoleDescriptorTreeNode || dragNode instanceof TaskDefinitionTreeNode
					|| dragNode instanceof ArtifactTreeNode)
			{
				Transferable transferable = (Transferable) dragNode.getUserObject() ;
				_evt.startDrag(DragSource.DefaultLinkDrop, transferable, this) ;
			}

			/*
			 * else { _evt.startDrag(DragSource.DefaultLinkNoDrop, new StringSelection("DND"), this) ; }
			 */
		}
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragDropEnd(java.awt.dnd.DragSourceDropEvent)
	 */
	public void dragDropEnd (DragSourceDropEvent _evt)
	{

	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragEnter(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dragEnter (DragSourceDragEvent _evt)
	{

	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragExit(java.awt.dnd.DragSourceEvent)
	 */
	public void dragExit (DragSourceEvent _evt)
	{

	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragOver(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dragOver (DragSourceDragEvent _evt)
	{
		Transferable transferable = _evt.getDragSourceContext().getTransferable() ;

		try
		{
			if (transferable.isDataFlavorSupported(HumanResource.RESOURCE_FLAVOR))
			{
				TreePath localPath = getPathForLocation(_evt.getLocation().x, _evt.getLocation().y) ;
				DefaultMutableTreeNode localTargetNode = (DefaultMutableTreeNode) localPath.getLastPathComponent() ;
				if (localTargetNode instanceof RoleDescriptorTreeNode)
				{
					_evt.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop) ;
				}
				else if (localTargetNode instanceof TaskDescriptorTreeNode)
				{
					_evt.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop) ;
				}
				else
				{
					_evt.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop) ;
				}
			}
			else if (transferable.isDataFlavorSupported(RoleDescriptor.ROLE_FLAVOR))
			{
				TreePath localPath = getPathForLocation(_evt.getLocation().x, _evt.getLocation().y) ;
				DefaultMutableTreeNode localTargetNode = (DefaultMutableTreeNode) localPath.getLastPathComponent() ;
				if (localTargetNode instanceof ResourceTreeNode)
				{
					_evt.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop) ;
				}
				else
				{
					_evt.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop) ;
				}
			}
			else if (transferable.isDataFlavorSupported(Artifact.ARTIFACT_FLAVOR))
			{
				Artifact localArtifact = (Artifact) transferable.getTransferData(Artifact.ARTIFACT_FLAVOR) ;

				TreePath localPath = getPathForLocation(_evt.getLocation().x, _evt.getLocation().y) ;

				DefaultMutableTreeNode localTargetNode = (DefaultMutableTreeNode) localPath.getLastPathComponent() ;
				if (localTargetNode instanceof TaskDefinitionTreeNode)
				{
					TaskDefinition localTasks[] = {
						((TaskDefinitionTreeNode) localTargetNode).getTask()
					} ;

					if (localTasks[0].getTask().getInputProducts().contains(localArtifact.getProduct()))
					{
						_evt.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop) ;
					}
					else if (localTasks[0].getTask().getOutputProducts().contains(localArtifact.getProduct()))
					{
						_evt.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop) ;
					}
					else
					{
						_evt.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop) ;
					}
				}
				else
				{
					_evt.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop) ;
				}
			}
			else if (transferable.isDataFlavorSupported(TaskDefinition.TASK_FLAVOR))
			{
				TaskDefinition localTasks[] = {
					(TaskDefinition) transferable.getTransferData(TaskDefinition.TASK_FLAVOR)
				} ;

				TreePath localPath = getPathForLocation(_evt.getLocation().x, _evt.getLocation().y) ;

				DefaultMutableTreeNode localTargetNode = (DefaultMutableTreeNode) localPath.getLastPathComponent() ;
				if (localTargetNode instanceof ArtifactTreeNode)
				{
					Artifact localArtifact = ((ArtifactTreeNode) localTargetNode).getArtifact() ;

					if (localTasks[0].getTask().getInputProducts().contains(localArtifact.getProduct()))
					{
						_evt.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop) ;
					}
					else if (localTasks[0].getTask().getOutputProducts().contains(localArtifact.getProduct()))
					{
						_evt.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop) ;
					}
					else
					{
						_evt.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop) ;
					}
				}
				else if (localTargetNode instanceof ResourceTreeNode)
				{
					_evt.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop) ;
				}
				else
				{
					_evt.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop) ;
				}
			}
		}
		catch (NullPointerException exc)
		{
			_evt.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop) ;
		}
		catch (UnsupportedFlavorException exc)
		{

		}
		catch (IOException exc)
		{

		}
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dropActionChanged(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dropActionChanged (DragSourceDragEvent _evt)
	{

	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragEnter (DropTargetDragEvent _arg0)
	{

	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
	 */
	public void dragExit (DropTargetEvent _evt)
	{

	}

	/**
	 * @throws IOException
	 * @throws UnsupportedFlavorException
	 * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragOver (DropTargetDragEvent _evt)
	{

	}

	/**
	 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
	 */
	public void drop (DropTargetDropEvent _evt)
	{
		try
		{
			Transferable transferable = _evt.getTransferable() ;

			// Checking that its a human resource
			if (transferable.isDataFlavorSupported(HumanResource.RESOURCE_FLAVOR))
			{
				HumanResource localResource = (HumanResource) transferable.getTransferData(HumanResource.RESOURCE_FLAVOR) ;

				// Now getting the target component
				TreePath localPath = getPathForLocation(_evt.getLocation().x, _evt.getLocation().y) ;
				DefaultMutableTreeNode localTargetNode = null ;

				try
				{
					localTargetNode = (DefaultMutableTreeNode) localPath.getLastPathComponent() ;
				}
				catch (NullPointerException exc)
				{
					_evt.rejectDrop() ;
					return ;
				}

				// If the node is a role, then linking
				if (localTargetNode instanceof RoleDescriptorTreeNode)
				{
					try
					{
						BreakdownElementsControler.linkRoleDescriptorAndHumanResource( ((RoleDescriptorTreeNode) localTargetNode).getRole(), localResource) ;
						_evt.getDropTargetContext().dropComplete(true) ;
						LogPanel.getInstance().addInformation(
								new LogInformation(Bundle.getText("MainFrameLogMessageNewMemberRole") + " : "
										+ ((RoleDescriptorTreeNode) localTargetNode).getRole().getName() + " - " + localResource.getFullName())) ;
					}
					catch (InvalidDnDOperationException exc)
					{
					}
					catch (DuplicateElementException exc)
					{
					}
				}

				else if (localTargetNode instanceof TaskDefinitionTreeNode)
				{
					try
					{
						BreakdownElementsControler.linkTaskDefinitionAndHumanResource( ((TaskDefinitionTreeNode) localTargetNode).getTask(), localResource) ;
						_evt.getDropTargetContext().dropComplete(true) ;
						LogPanel.getInstance().addInformation(
								new LogInformation(Bundle.getText("MainFrameLogMessageNewMemberTask") + " : "
										+ ((TaskDefinitionTreeNode) localTargetNode).getTask().getName() + " - " + localResource.getFullName())) ;
					}
					catch (InvalidDnDOperationException exc)
					{
						_evt.rejectDrop() ;
					}
					catch (DuplicateElementException exc)
					{
						_evt.rejectDrop() ;
					}
				}
			}

			// A role
			else if (transferable.isDataFlavorSupported(RoleDescriptor.ROLE_FLAVOR))
			{
				RoleDescriptor localRole = (RoleDescriptor) transferable.getTransferData(RoleDescriptor.ROLE_FLAVOR) ;

				TreePath localPath = getPathForLocation(_evt.getLocation().x, _evt.getLocation().y) ;
				DefaultMutableTreeNode localTargetNode = null ;

				try
				{
					localTargetNode = (DefaultMutableTreeNode) localPath.getLastPathComponent() ;
				}
				catch (NullPointerException exc)
				{
					_evt.rejectDrop() ;
					return ;
				}

				// If the node is a role, then linking
				if (localTargetNode instanceof ResourceTreeNode)
				{
					try
					{
						BreakdownElementsControler.linkRoleDescriptorAndHumanResource(localRole, ((ResourceTreeNode) localTargetNode).getResource()) ;
						_evt.getDropTargetContext().dropComplete(true) ;
						LogPanel.getInstance().addInformation(
								new LogInformation(Bundle.getText("MainFrameLogMessageNewMemberRole") + " : " + localRole.getName() + " - "
										+ ((ResourceTreeNode) localTargetNode).getResource().getFullName())) ;
					}
					catch (InvalidDnDOperationException exc)
					{
						_evt.rejectDrop() ;
					}
					catch (DuplicateElementException exc)
					{
						_evt.rejectDrop() ;
					}
				}

			}

			// An artifact
			else if (transferable.isDataFlavorSupported(Artifact.ARTIFACT_FLAVOR))
			{
				Artifact localArtifact = (Artifact) transferable.getTransferData(Artifact.ARTIFACT_FLAVOR) ;

				TreePath localPath = getPathForLocation(_evt.getLocation().x, _evt.getLocation().y) ;

				DefaultMutableTreeNode localTargetNode = null ;

				try
				{
					localTargetNode = (DefaultMutableTreeNode) localPath.getLastPathComponent() ;
				}
				catch (NullPointerException exc)
				{
					_evt.rejectDrop() ;
					return ;
				}

				// If the node is a task
				if (localTargetNode instanceof TaskDefinitionTreeNode)
				{
					TaskDefinition localTasks[] = {
						((TaskDefinitionTreeNode) localTargetNode).getTask()
					} ;

					if (localTasks[0].getTask().getInputProducts().contains(localArtifact.getProduct()))
					{
						try
						{
							BreakdownElementsControler.linkArtifactToTaskDefinitions(localArtifact, localTasks, true) ;
							_evt.getDropTargetContext().dropComplete(true) ;
							LogPanel.getInstance().addInformation(
									new LogInformation(Bundle.getText("MainFrameLogMessageNewTaskArtifact") + " : " + localTasks[0].getName() + " - "
											+ localArtifact.getName())) ;
						}
						catch (InvalidDnDOperationException exc)
						{
							_evt.rejectDrop() ;
						}
						catch (DuplicateElementException exc)
						{
							_evt.rejectDrop() ;
						}
					}

					else if (localTasks[0].getTask().getOutputProducts().contains(localArtifact.getProduct()))
					{
						try
						{
							BreakdownElementsControler.linkArtifactToTaskDefinitions(localArtifact, localTasks, false) ;
							_evt.getDropTargetContext().dropComplete(true) ;
							LogPanel.getInstance().addInformation(
									new LogInformation(Bundle.getText("MainFrameLogMessageNewTaskArtifact") + " : " + localTasks[0].getName() + " - "
											+ localArtifact.getName())) ;
						}
						catch (InvalidDnDOperationException exc)
						{
							_evt.rejectDrop() ;
						}
						catch (DuplicateElementException exc)
						{
							_evt.rejectDrop() ;
						}
					}
				}

			}

			// A task definition
			else if (transferable.isDataFlavorSupported(TaskDefinition.TASK_FLAVOR))
			{
				TaskDefinition localTasks[] = {
					(TaskDefinition) transferable.getTransferData(TaskDefinition.TASK_FLAVOR)
				} ;

				TreePath localPath = getPathForLocation(_evt.getLocation().x, _evt.getLocation().y) ;

				DefaultMutableTreeNode localTargetNode = null ;

				try
				{
					localTargetNode = (DefaultMutableTreeNode) localPath.getLastPathComponent() ;
				}
				catch (NullPointerException exc)
				{
					_evt.rejectDrop() ;
					return ;
				}

				// If the node is a task
				if (localTargetNode instanceof ArtifactTreeNode)
				{
					Artifact localArtifact = ((ArtifactTreeNode) localTargetNode).getArtifact() ;

					if (localTasks[0].getTask().getInputProducts().contains(localArtifact.getProduct()))
					{
						try
						{
							BreakdownElementsControler.linkArtifactToTaskDefinitions(localArtifact, localTasks, true) ;
							_evt.getDropTargetContext().dropComplete(true) ;
							LogPanel.getInstance().addInformation(
									new LogInformation(Bundle.getText("MainFrameLogMessageNewTaskArtifact") + " : " + localTasks[0].getName() + " - "
											+ localArtifact.getName())) ;
						}
						catch (InvalidDnDOperationException exc)
						{
							_evt.rejectDrop() ;
						}
						catch (DuplicateElementException exc)
						{
							_evt.rejectDrop() ;
						}
					}

					else if (localTasks[0].getTask().getOutputProducts().contains(localArtifact.getProduct()))
					{
						try
						{
							BreakdownElementsControler.linkArtifactToTaskDefinitions(localArtifact, localTasks, false) ;
							_evt.getDropTargetContext().dropComplete(true) ;
							LogPanel.getInstance().addInformation(
									new LogInformation(Bundle.getText("MainFrameLogMessageNewTaskArtifact") + " : " + localTasks[0].getName() + " - "
											+ localArtifact.getName())) ;
						}
						catch (InvalidDnDOperationException exc)
						{
							_evt.rejectDrop() ;
						}
						catch (DuplicateElementException exc)
						{
							_evt.rejectDrop() ;
						}
					}
				}

				else if (localTargetNode instanceof ResourceTreeNode)
				{
					HumanResource localResource = ((ResourceTreeNode) localTargetNode).getResource() ;

					try
					{
						BreakdownElementsControler.linkTaskDefinitionAndHumanResource(localTasks[0], localResource) ;
						_evt.getDropTargetContext().dropComplete(true) ;
						LogPanel.getInstance().addInformation(
								new LogInformation(Bundle.getText("MainFrameLogMessageNewMemberTask") + " : " + localTasks[0].getName() + " - "
										+ localResource.getFullName())) ;
					}
					catch (DuplicateElementException exc)
					{
						_evt.rejectDrop() ;
					}
				}
			}

			else
			{
				_evt.rejectDrop() ;
			}
		}
		catch (UnsupportedFlavorException exc)
		{
			_evt.rejectDrop() ;
		}
		catch (IOException exc)
		{
			_evt.rejectDrop() ;
		}
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dropActionChanged (DropTargetDragEvent _evt)
	{

	}

	/*
	 * Selection listeners
	 */

	/**
	 * Actually this method detects the tree node responsible of the event and occasionally adds a
	 * specific panel to the central area
	 * 
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged (TreeSelectionEvent _arg0)
	{

	}

	/*
	 * Other listeners
	 */

	/**
	 * TreeMouseListener : reacts to the mouse clicks on the tree
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	private class TreeMouseListener extends MouseAdapter
	{
		/**
		 * Constructor
		 * 
		 */
		public TreeMouseListener ()
		{
			super() ;

		}

		/**
		 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
		 */
		@ Override
		public void mouseReleased (MouseEvent _e)
		{
			if (_e.isPopupTrigger() || _e.getButton() == MouseEvent.BUTTON3)
			{
				TreePath localPath = MainTree.this.getPathForRow(MainTree.this.getRowForLocation(_e.getX(), _e.getY())) ;

				if (localPath.getLastPathComponent() instanceof RoleDescriptorTreeNode)
				{
					if ( ((DefaultMutableTreeNode) localPath.getLastPathComponent()).getParent() instanceof ResourceTreeNode)
					{
						MainTree.this.setSelectionPath(localPath) ;
						roleDescriptorPopupMenuBis.show(_e.getComponent(), _e.getX(), _e.getY()) ;
					}

					else
					{
						MainTree.this.setSelectionPath(localPath) ;
						roleDescriptorPopupMenu.show(_e.getComponent(), _e.getX(), _e.getY()) ;
					}
				}

				else if (localPath.getLastPathComponent() instanceof WorkProductDescriptorTreeNode)
				{
					MainTree.this.setSelectionPath(localPath) ;

					workProductDescriptorPopupMenu.show(_e.getComponent(), _e.getX(), _e.getY()) ;

				}

				else if (localPath.getLastPathComponent() instanceof TaskDescriptorTreeNode)
				{
					MainTree.this.setSelectionPath(localPath) ;
					taskDescriptorPopupMenu.show(_e.getComponent(), _e.getX(), _e.getY()) ;
				}

				else if (localPath.getLastPathComponent() instanceof ArtifactTreeNode)
				{
					MainTree.this.setSelectionPath(localPath) ;
					artifactPopupMenu.show(_e.getComponent(), _e.getX(), _e.getY()) ;
				}

				else if (localPath.getLastPathComponent() instanceof TaskDefinitionTreeNode)
				{
					MainTree.this.setSelectionPath(localPath) ;
					taskDefinitionPopupMenu.show(_e.getComponent(), _e.getX(), _e.getY()) ;
				}
			}
		}

		/**
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@ Override
		public void mouseClicked (MouseEvent _e)
		{
			if (_e.getButton() == MouseEvent.BUTTON1 && _e.getClickCount() == 2)
			{
				DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) getLastSelectedPathComponent() ;

				/*
				 * Task descriptors => displaying estimations
				 */
				if (localNode instanceof TaskDescriptorTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((TaskDescriptorTreeNode) localNode).getTask().getName(),
							new ImageIcon(getClass().getResource("/ui/resource/icon_activity.gif")),
							new TaskDescriptorPanel( ((TaskDescriptorTreeNode) localNode).getTask())) ;
				}

				/*
				 * Role descriptors => displaying role infos
				 */
				else if (localNode instanceof RoleDescriptorTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((RoleDescriptorTreeNode) localNode).getRole().getName(),
							new ImageIcon(getClass().getResource("/ui/resource/icon_role.gif")),
							new RoleDescriptorPanel( ((RoleDescriptorTreeNode) localNode).getRole())) ;
				}

				/*
				 * Product descriptors
				 */
				else if (localNode instanceof WorkProductDescriptorTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((WorkProductDescriptorTreeNode) localNode).getProduct().getName(),
							new ImageIcon(getClass().getResource("/ui/resource/icon_product.gif")),
							new WorkProductDescriptorPanel( ((WorkProductDescriptorTreeNode) localNode).getProduct())) ;
				}

				/*
				 * Artifacts
				 */
				else if (localNode instanceof ArtifactTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((ArtifactTreeNode) localNode).getArtifact().getName(),
							new ImageIcon(getClass().getResource("/ui/resource/icon_artifact.gif")),
							new ArtifactPanel(mainFrame, ((ArtifactTreeNode) localNode).getArtifact())) ;
				}

				/*
				 * Task definitions
				 */
				else if (localNode instanceof TaskDefinitionTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((TaskDefinitionTreeNode) localNode).getTask().getName(),
							new ImageIcon(getClass().getResource("/ui/resource/icon_task.gif")),
							new TaskDefinitionPanel(mainFrame, ((TaskDefinitionTreeNode) localNode).getTask())) ;
				}

				/*
				 * Resources definitions
				 */
				else if (localNode instanceof ResourceTreeNode)
				{
					MainTabbedPane.getInstance().add(new HumanResourcePanel( ((ResourceTreeNode) localNode).getResource()),
							((ResourceTreeNode) localNode).getResource().getFullName()) ;
				}
			}
		}
	}

	/**
	 * TreeKeyListener : reacts to key events
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	private class TreeKeyListener extends KeyAdapter
	{

		/**
		 * Constructor
		 * 
		 */
		public TreeKeyListener ()
		{
			super() ;
		}

		/**
		 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
		 */
		@ Override
		public void keyPressed (KeyEvent _e)
		{
			// Filtering events
			// Deleting artifacts, tasks and roles from members
			if (_e.getKeyCode() == KeyEvent.VK_DELETE)
			{
				DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) getLastSelectedPathComponent() ;

				if (localNode instanceof TaskDefinitionTreeNode)
				{

					BreakdownElementsControler.deleteTaskDefinition( ((TaskDefinitionTreeNode) localNode).getTask(), MainTree.this.getProject()) ;
					LogPanel.getInstance().addInformation(
							new LogInformation(Bundle.getText("MainFrameLogMessageTaskDeleted") + " : "
									+ ((TaskDefinitionTreeNode) localNode).getTask().getName())) ;
				}

				else if (localNode instanceof ArtifactTreeNode)
				{

					BreakdownElementsControler.deleteArtifact( ((ArtifactTreeNode) localNode).getArtifact()) ;
					LogPanel.getInstance().addInformation(
							new LogInformation(Bundle.getText("MainFrameLogMessageArtifactDeleted") + " : "
									+ ((ArtifactTreeNode) localNode).getArtifact().getName())) ;
				}

				else if (localNode instanceof RoleDescriptorTreeNode && localNode.getParent() instanceof ResourceTreeNode)
				{
					RoleDescriptor tempRole = ((RoleDescriptorTreeNode) localNode).getRole() ;
					HumanResource tempResource = ((ResourceTreeNode) localNode.getParent()).getResource() ;
					BreakdownElementsControler.unlinkRoleDescriptorAndHumanResource(tempRole, tempResource) ;
					LogPanel.getInstance().addInformation(
							new LogInformation(Bundle.getText("MainFrameLogMessageOldMemberRole") + " : " + tempRole.getName() + " - "
									+ tempResource.getFullName())) ;
				}
			}

			else if (_e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) getLastSelectedPathComponent() ;

				/*
				 * Task descriptors => displaying estimations
				 */
				if (localNode instanceof TaskDescriptorTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((TaskDescriptorTreeNode) localNode).getTask().getName(),
							new ImageIcon(getClass().getResource("/ui/resource/icon_activity.gif")),
							new TaskDescriptorPanel( ((TaskDescriptorTreeNode) localNode).getTask())) ;
				}

				/*
				 * Role descriptors => displaying role infos
				 */
				else if (localNode instanceof RoleDescriptorTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((RoleDescriptorTreeNode) localNode).getRole().getName(),
							new ImageIcon(getClass().getResource("/ui/resource/icon_role.gif")),
							new RoleDescriptorPanel( ((RoleDescriptorTreeNode) localNode).getRole())) ;
				}

				/*
				 * Product descriptors
				 */
				else if (localNode instanceof WorkProductDescriptorTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((WorkProductDescriptorTreeNode) localNode).getProduct().getName(),
							new ImageIcon(getClass().getResource("/ui/resource/icon_product.gif")),
							new WorkProductDescriptorPanel( ((WorkProductDescriptorTreeNode) localNode).getProduct())) ;
				}

				/*
				 * Artifacts
				 */
				else if (localNode instanceof ArtifactTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((ArtifactTreeNode) localNode).getArtifact().getName(),
							new ImageIcon(getClass().getResource("/ui/resource/icon_artifact.gif")),
							new ArtifactPanel(mainFrame, ((ArtifactTreeNode) localNode).getArtifact())) ;
				}

				/*
				 * Task definitions
				 */
				else if (localNode instanceof TaskDefinitionTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((TaskDefinitionTreeNode) localNode).getTask().getName(),
							new ImageIcon(getClass().getResource("/ui/resource/icon_task.gif")),
							new TaskDefinitionPanel(mainFrame, ((TaskDefinitionTreeNode) localNode).getTask())) ;
				}

				/*
				 * Resources definitions
				 */
				else if (localNode instanceof ResourceTreeNode)
				{
					MainTabbedPane.getInstance().add(new HumanResourcePanel( ((ResourceTreeNode) localNode).getResource()),
							((ResourceTreeNode) localNode).getResource().getFullName()) ;
				}

			}

		}

	}

	/*
	 * Renderer
	 */
	private class MainTreeRenderer extends DefaultTreeCellRenderer
	{
		private static final long serialVersionUID = 5340795009437591116L ;

		/**
		 * Constructor
		 * 
		 */
		public MainTreeRenderer ()
		{
			super() ;
		}

		/**
		 * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree,
		 *      java.lang.Object, boolean, boolean, boolean, int, boolean)
		 */
		@ Override
		public Component getTreeCellRendererComponent (JTree _tree, Object _object, boolean _selected, boolean _expanded, boolean _leaf, int _row,
				boolean _focus)
		{
			super.getTreeCellRendererComponent(_tree, _object, _selected, _expanded, _leaf, _row, _focus) ;

			// Role
			if (_object instanceof RoleDescriptorTreeNode)
			{
				setIcon(new ImageIcon(getClass().getResource("/ui/resource/icon_role.gif"))) ;
			}

			// Resources
			else if (_object instanceof ResourceTreeNode)
			{
				setIcon(new ImageIcon(getClass().getResource("/ui/resource/icon_resource.gif"))) ;
			}

			// Products and artifacts
			else if (_object instanceof WorkProductDescriptorTreeNode)
			{
				setIcon(new ImageIcon(getClass().getResource("/ui/resource/icon_product.gif"))) ;
			}

			else if (_object instanceof ArtifactTreeNode)
			{
				setIcon(new ImageIcon(getClass().getResource("/ui/resource/icon_artifact.gif"))) ;
			}

			// Tasks
			else if (_object instanceof ActivityTreeNode)
			{
				setIcon(new ImageIcon(getClass().getResource("/ui/resource/icon_work_definition.gif"))) ;
			}

			else if (_object instanceof TaskDescriptorTreeNode)
			{
				setIcon(new ImageIcon(getClass().getResource("/ui/resource/icon_activity.gif"))) ;
			}

			else if (_object instanceof TaskDefinitionTreeNode)
			{
				setIcon(new ImageIcon(getClass().getResource("/ui/resource/icon_task.gif"))) ;
			}

			// Others
			else if (_object instanceof ProjectTreeNode)
			{
				setIcon(new ImageIcon(getClass().getResource("/ui/resource/icon_project.gif"))) ;
			}

			else if (_object instanceof ProcessTreeNode)
			{
				setIcon(new ImageIcon(getClass().getResource("/ui/resource/icon_process.gif"))) ;
			}

			else if (_object instanceof ComponentTreeNode)
			{
				setIcon(new ImageIcon(getClass().getResource("/ui/resource/icon_component.gif"))) ;
			}

			return this ;
		}

	}

	/**
	 * Getter
	 * 
	 * @return Returns the project.
	 */
	public Project getProject ()
	{
		return this.project ;
	}

	/**
	 * Setter
	 * 
	 * @param _project
	 *            The project to set.
	 */
	public void setProject (Project _project)
	{
		this.project = _project ;
	}

}
