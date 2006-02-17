
package ui.tree ;

import java.awt.Component;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import model.HumanResource;
import model.Project;
import model.spem2.Artifact;
import model.spem2.RoleDescriptor;
import model.spem2.TaskDefinition;
import process.utility.BreakdownElementsControler;
import ui.dialog.ArtifactAdderDialog;
import ui.dialog.TaskDefinitionAdderDialog;
import ui.misc.ArtifactPanel;
import ui.misc.HumanResourcePanel;
import ui.misc.MainTabbedPane;
import ui.misc.RoleDescriptorPanel;
import ui.misc.TaskDefinitionPanel;
import ui.misc.TaskDescriptorPanel;
import ui.misc.WorkProductDescriptorPanel;
import ui.resource.Bundle;
import ui.window.MainFrame;

/**
 * MainTree : surcharged JTree to handle drag and drop
 * 
 * @author Conde Mickael K
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

	// private JPopupMenu resourcePopupMenu = null ;

	/**
	 * Constructor
	 * 
	 * @param _project
	 * @param _frame
	 */
	public MainTree (Project _project, MainFrame _frame)
	{
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
		JMenuItem roleExpandMenuItem = new JMenuItem(Bundle.getText("MainTreePopupShow")) ;
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
		JMenuItem activityCloseMenuItem = new JMenuItem(Bundle.getText("MainTreePopupClose")) ;
		roleDescriptorPopupMenu.add(roleExpandMenuItem) ;
		roleDescriptorPopupMenu.addSeparator() ;
		roleDescriptorPopupMenu.add(activityCloseMenuItem) ;

		roleDescriptorPopupMenuBis = new JPopupMenu() ;
		JMenuItem roleExpandMenuItemBis = new JMenuItem(Bundle.getText("MainTreePopupShow")) ;
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
		JMenuItem roleUnlinkMenuItem = new JMenuItem(Bundle.getText("MainTreePopupDeleteRole")) ;
		roleUnlinkMenuItem.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed (java.awt.event.ActionEvent e)
			{
				DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) getLastSelectedPathComponent() ;

				if (localNode instanceof RoleDescriptorTreeNode)
				{
					BreakdownElementsControler.unlinkRoleDescriptorAndHumanResource( ((RoleDescriptorTreeNode) localNode).getRole(),
							((ResourceTreeNode) localNode.getParent()).getResource()) ;
				}
			}
		}) ;
		JMenuItem activityCloseMenuItemBis = new JMenuItem(Bundle.getText("MainTreePopupClose")) ;
		roleDescriptorPopupMenuBis.add(roleExpandMenuItemBis) ;
		roleDescriptorPopupMenuBis.add(roleUnlinkMenuItem) ;
		roleDescriptorPopupMenuBis.addSeparator() ;
		roleDescriptorPopupMenuBis.add(activityCloseMenuItemBis) ;

		// For work products
		workProductDescriptorPopupMenu = new JPopupMenu() ;
		JMenuItem workProductAddMenuItem = new JMenuItem(Bundle.getText("MainTreePopupAddArtifact")) ;
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
		JMenuItem workProductExpandMenuItem = new JMenuItem(Bundle.getText("MainTreePopupShow")) ;
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
		JMenuItem workProductCloseMenuItem = new JMenuItem(Bundle.getText("MainTreePopupClose")) ;
		workProductDescriptorPopupMenu.add(workProductAddMenuItem) ;
		workProductDescriptorPopupMenu.add(workProductExpandMenuItem) ;
		workProductDescriptorPopupMenu.addSeparator() ;
		workProductDescriptorPopupMenu.add(workProductCloseMenuItem) ;

		// For task descriptors
		taskDescriptorPopupMenu = new JPopupMenu() ;
		JMenuItem taskDescriptorAddMenuItem = new JMenuItem(Bundle.getText("MainTreePopupAddTask")) ;
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
		JMenuItem taskDescriptorExpandMenuItem = new JMenuItem(Bundle.getText("MainTreePopupShow")) ;
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
		JMenuItem taskDescriptorCloseMenuItem = new JMenuItem(Bundle.getText("MainTreePopupClose")) ;
		taskDescriptorPopupMenu.add(taskDescriptorAddMenuItem) ;
		taskDescriptorPopupMenu.add(taskDescriptorExpandMenuItem) ;
		taskDescriptorPopupMenu.addSeparator() ;
		taskDescriptorPopupMenu.add(taskDescriptorCloseMenuItem) ;

		// For artifacts
		artifactPopupMenu = new JPopupMenu() ;
		JMenuItem artifactCloseMenuItem = new JMenuItem(Bundle.getText("MainTreePopupClose")) ;
		JMenuItem artifactExpandMenuItem = new JMenuItem(Bundle.getText("MainTreePopupShow")) ;
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
		artifactPopupMenu.addSeparator() ;
		artifactPopupMenu.add(artifactCloseMenuItem) ;

		taskDefinitionPopupMenu = new JPopupMenu() ;
		JMenuItem taskDefinitionCloseMenuItem = new JMenuItem(Bundle.getText("MainTreePopupClose")) ;
		JMenuItem taskDefinitionExpandMenuItem = new JMenuItem(Bundle.getText("MainTreePopupShow")) ;
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
		// DragSourceContext context = _evt.getDragSourceContext() ;
		// context.
		/*
		 * Point localLocation = _evt.getLocation() ; System.out.println(localLocation) ; /*try {
		 * TreePath localPath = getPathForLocation(localLocation.x, localLocation.y) ;
		 * DefaultMutableTreeNode localTargetNode = (DefaultMutableTreeNode)
		 * localPath.getLastPathComponent() ; if (localTargetNode instanceof TaskDescriptorTreeNode) {
		 * //System.out.println("gere") ; } } catch (NullPointerException _exc) {
		 * //System.out.println("hoo") ; //_evt. ; }
		 */
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
				DefaultMutableTreeNode localTargetNode = (DefaultMutableTreeNode) localPath.getLastPathComponent() ;

				// If the node is a role, then linking
				if (localTargetNode instanceof RoleDescriptorTreeNode)
				{
					BreakdownElementsControler.linkRoleDescriptorAndHumanResource( ((RoleDescriptorTreeNode) localTargetNode).getRole(), localResource) ;
					_evt.getDropTargetContext().dropComplete(true) ;
				}

				else if (localTargetNode instanceof TaskDefinitionTreeNode)
				{
					BreakdownElementsControler.linkTaskDefinitionAndHumanResource( ((TaskDefinitionTreeNode) localTargetNode).getTask(), localResource) ;
					_evt.getDropTargetContext().dropComplete(true) ;
				}

				/*
				 * else if (localTargetNode instanceof TaskDescriptorTreeNode) { Collection
				 * <RoleDescriptor> localRoles = ((TaskDescriptorTreeNode)
				 * localTargetNode).getTask().getPrimaryPerformers() ; Iterator <RoleDescriptor>
				 * localIterator = localRoles.iterator() ;
				 * 
				 * while (localIterator.hasNext()) { if
				 * (localResource.getPerformingRoles().contains(localIterator.next())) { if
				 * (!localResource.getPerformingTasks().contains( ((TaskDescriptorTreeNode)
				 * localTargetNode).getTask())) { localResource.getPerformingTasks().add(
				 * ((TaskDescriptorTreeNode) localTargetNode).getTask()) ;
				 * _evt.getDropTargetContext().dropComplete(true) ; }
				 * 
				 * break ; } } }
				 */
			}

			// A role
			else if (transferable.isDataFlavorSupported(RoleDescriptor.ROLE_FLAVOR))
			{
				RoleDescriptor localRole = (RoleDescriptor) transferable.getTransferData(RoleDescriptor.ROLE_FLAVOR) ;

				TreePath localPath = getPathForLocation(_evt.getLocation().x, _evt.getLocation().y) ;
				DefaultMutableTreeNode localTargetNode = (DefaultMutableTreeNode) localPath.getLastPathComponent() ;

				// If the node is a role, then linking
				if (localTargetNode instanceof ResourceTreeNode)
				{
					BreakdownElementsControler.linkRoleDescriptorAndHumanResource(localRole, ((ResourceTreeNode) localTargetNode).getResource()) ;
					_evt.getDropTargetContext().dropComplete(true) ;
				}

			}
			
			// An artifact
			else if (transferable.isDataFlavorSupported(Artifact.ARTIFACT_FLAVOR))
			{
				Artifact localArtifact = (Artifact) transferable.getTransferData(Artifact.ARTIFACT_FLAVOR) ;

				TreePath localPath = getPathForLocation(_evt.getLocation().x, _evt.getLocation().y) ;
				DefaultMutableTreeNode localTargetNode = (DefaultMutableTreeNode) localPath.getLastPathComponent() ;

				
				// If the node is a task
				if (localTargetNode instanceof TaskDefinitionTreeNode)
				{
					TaskDefinition localTasks[] = { ((TaskDefinitionTreeNode)localTargetNode).getTask() };
					
					if (localTasks[0].getTask().getInputProducts().contains(localArtifact.getProduct()))
					{
						BreakdownElementsControler.linkArtifactToTaskDefinitions(localArtifact,localTasks, true) ;
					}					
					
					else if (localTasks[0].getTask().getOutputProducts().contains(localArtifact.getProduct()))
					{
						BreakdownElementsControler.linkArtifactToTaskDefinitions(localArtifact,localTasks, false) ;
					}
				}				

			}
			
			// A task definition
			else if (transferable.isDataFlavorSupported(TaskDefinition.TASK_FLAVOR))
			{
				TaskDefinition localTasks[] = { (TaskDefinition) transferable.getTransferData(TaskDefinition.TASK_FLAVOR) };

				TreePath localPath = getPathForLocation(_evt.getLocation().x, _evt.getLocation().y) ;
				DefaultMutableTreeNode localTargetNode = (DefaultMutableTreeNode) localPath.getLastPathComponent() ;			
				
				// If the node is a task
				if (localTargetNode instanceof ArtifactTreeNode)
				{			
					Artifact localArtifact = ((ArtifactTreeNode)localTargetNode).getArtifact() ;
					
					if (localTasks[0].getTask().getInputProducts().contains(localArtifact.getProduct()))
					{
						BreakdownElementsControler.linkArtifactToTaskDefinitions(localArtifact,localTasks, true) ;
					}					
					
					else if (localTasks[0].getTask().getOutputProducts().contains(localArtifact.getProduct()))
					{
						BreakdownElementsControler.linkArtifactToTaskDefinitions(localArtifact,localTasks, false) ;
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
					MainTabbedPane.getInstance().addTab( ((TaskDescriptorTreeNode) localNode).getTask().getName(), new ImageIcon(getClass().getResource("/ui/resource/icon_activity.gif")),
							new TaskDescriptorPanel( ((TaskDescriptorTreeNode) localNode).getTask())) ;
				}
				
								
				/*
				 * Role descriptors => displaying role infos
				 */
				else if (localNode instanceof RoleDescriptorTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((RoleDescriptorTreeNode) localNode).getRole().getName(), new ImageIcon(getClass().getResource("/ui/resource/icon_role.gif")),
							new RoleDescriptorPanel( ((RoleDescriptorTreeNode) localNode).getRole())) ;
				}

				/*
				 * Product descriptors
				 */
				else if (localNode instanceof WorkProductDescriptorTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((WorkProductDescriptorTreeNode) localNode).getProduct().getName(), new ImageIcon(getClass().getResource("/ui/resource/icon_product.gif")), 
							new WorkProductDescriptorPanel( ((WorkProductDescriptorTreeNode) localNode).getProduct())) ;
				}

				/*
				 * Artifacts
				 */
				else if (localNode instanceof ArtifactTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((ArtifactTreeNode) localNode).getArtifact().getName(),new ImageIcon(getClass().getResource("/ui/resource/icon_artifact.gif")), 
							new ArtifactPanel(mainFrame, ((ArtifactTreeNode) localNode).getArtifact())) ;
				}

				/*
				 * Task definitions
				 */
				else if (localNode instanceof TaskDefinitionTreeNode)
				{
					MainTabbedPane.getInstance().addTab( ((TaskDefinitionTreeNode) localNode).getTask().getName(), new ImageIcon(getClass().getResource("/ui/resource/icon_task.gif")),
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
			// System.out.println(_e) ;
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

}
