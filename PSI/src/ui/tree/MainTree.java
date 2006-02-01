
package ui.tree ;

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
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import model.HumanResource;
import model.Project;
import model.spem2.RoleDescriptor;
import process.utility.BreakdownElementsControler;
import ui.misc.MainTabbedPane;
import ui.misc.RoleDescriptorPanel;
import ui.misc.TaskDescriptorPanel;
import ui.resource.Bundle;

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

	private JPopupMenu activityPopupMenu = null ;

	private JPopupMenu roleDescriptorPopupMenu = null ;

	//private JPopupMenu taskDefinitionPopupMenu = null ;
	
	private Project project = null ;

	// private JPopupMenu resourcePopupMenu = null ;

	/**
	 * Constructor
	 * 
	 */
	public MainTree (Project _project)
	{
		super(new DefaultTreeModel(new DefaultMutableTreeNode(Bundle.getText("MainTreeDefault")))) ;

		this.project = _project ;
		
		/*
		 * Building popups
		 */
		// For ActivityTreeNode
		activityPopupMenu = new JPopupMenu() ;
		JMenuItem activityAddMenuItem = new JMenuItem(Bundle.getText("MainTreePopupAddActivity")) ;
		activityAddMenuItem.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed (java.awt.event.ActionEvent e)
			{
				System.out.println("hey") ;
				/*
				 * new TaskDescriptorAdderDialog(MainFrame.this, ((ActivityTreeNode)
				 * MainFrame.this.projectTree.getPathForRow(localLocation)
				 * .getLastPathComponent()).getActivity()) ;
				 */
			}
		}) ;
		JMenuItem activityCloseMenuItem = new JMenuItem(Bundle.getText("MainTreePopupClose")) ;
		activityPopupMenu.add(activityAddMenuItem) ;
		activityPopupMenu.addSeparator() ;
		activityPopupMenu.add(activityCloseMenuItem) ;
		
		// For RoleDescriptorTreeNode
		roleDescriptorPopupMenu = new JPopupMenu() ;
		JMenuItem roleDeleteMenuItem = new JMenuItem(Bundle.getText("MainTreePopupDeleteRole")) ;
		JMenuItem roleCloseMenuItem = new JMenuItem(Bundle.getText("MainTreePopupClose")) ;
		roleDescriptorPopupMenu.add(roleDeleteMenuItem) ;
		roleDescriptorPopupMenu.addSeparator() ;
		roleDescriptorPopupMenu.add(roleCloseMenuItem) ;
		
		
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
			if (dragNode instanceof ResourceTreeNode)
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
		 * //System.out.println("gere") ; }
		 *  } catch (NullPointerException _exc) { //System.out.println("hoo") ; //_evt. ; }
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
			if (!transferable.isDataFlavorSupported(HumanResource.RESOURCE_FLAVOR))
			{
				_evt.rejectDrop() ;
			}

			else
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

				/*else if (localTargetNode instanceof TaskDescriptorTreeNode)
				{
					Collection <RoleDescriptor> localRoles = ((TaskDescriptorTreeNode) localTargetNode).getTask().getPrimaryPerformers() ;
					Iterator <RoleDescriptor> localIterator = localRoles.iterator() ;

					while (localIterator.hasNext())
					{
						if (localResource.getPerformingRoles().contains(localIterator.next()))
						{
							if (!localResource.getPerformingTasks().contains( ((TaskDescriptorTreeNode) localTargetNode).getTask()))
							{
								localResource.getPerformingTasks().add( ((TaskDescriptorTreeNode) localTargetNode).getTask()) ;
								_evt.getDropTargetContext().dropComplete(true) ;
							}

							break ;
						}
					}
				}*/
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
		DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) getLastSelectedPathComponent() ;

		/*
		 * Task descriptors => displaying estimations
		 */
		if (localNode instanceof TaskDescriptorTreeNode)
		{
			MainTabbedPane.getInstance().add(new TaskDescriptorPanel( ((TaskDescriptorTreeNode) localNode).getTask()),
					((TaskDescriptorTreeNode) localNode).getTask().getName()) ;
		}

		/*
		 * Role descriptors => displaying role infos
		 */
		else if (localNode instanceof RoleDescriptorTreeNode)
		{
			MainTabbedPane.getInstance().add(new RoleDescriptorPanel( ((RoleDescriptorTreeNode) localNode).getRole()),
					((RoleDescriptorTreeNode) localNode).getRole().getName()) ;
		}
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
			if (_e.isPopupTrigger())
			{
				TreePath localPath = MainTree.this.getPathForRow(MainTree.this.getRowForLocation(_e.getX(), _e.getY())) ;

				if (localPath.getLastPathComponent() instanceof ActivityTreeNode)
				{
					MainTree.this.setSelectionPath(localPath) ;
					activityPopupMenu.show(_e.getComponent(), _e.getX(), _e.getY()) ;
				}
				
				else if (localPath.getLastPathComponent() instanceof RoleDescriptorTreeNode && ((RoleDescriptorTreeNode)localPath.getLastPathComponent()).getParent() instanceof ResourceTreeNode)
				{
					MainTree.this.setSelectionPath(localPath) ;
					roleDescriptorPopupMenu.show(_e.getComponent(), _e.getX(), _e.getY()) ;
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
			//System.out.println(_e) ;
		}

	}

}
