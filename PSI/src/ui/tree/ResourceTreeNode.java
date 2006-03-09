
package ui.tree ;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import model.HumanResource;
import model.spem2.RoleDescriptor;

/**
 * ResourceTreeNode : tree like representation of a Human Resource
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class ResourceTreeNode extends DefaultMutableTreeNode implements Observer
{
	private static final long serialVersionUID = -2206326976916871308L ;

	private HumanResource resource = null ;
	
	private MainTree tree = null ;

	/**
	 * Constructor
	 * 
	 * @param _resource
	 */
	public ResourceTreeNode (HumanResource _resource, MainTree _tree)
	{
		super() ;

		this.tree = _tree ;
		this.resource = _resource ;
		this.resource.addObserver(this) ;
		this.setUserObject(resource) ;
		
		Iterator<RoleDescriptor> localIterator = resource.getPerformingRoles().iterator() ;
		while (localIterator.hasNext())
		{
			this.add(new RoleDescriptorTreeNode(localIterator.next(), tree)) ;
		}
	}

	/**
	 * Getter
	 * 
	 * @return Returns the resource.
	 */
	public HumanResource getResource ()
	{
		return this.resource ;
	}

	/**
	 * Setter
	 * 
	 * @param _resource
	 *            The resource to set.
	 */
	public void setResource (HumanResource _resource)
	{
		this.resource = _resource ;
	}

	
	/**
	 * Updating the roles and tasks defs for the resource
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update (Observable _observable, Object _object)
	{
		if (_object != null && _object instanceof RoleDescriptor)
		{
			RoleDescriptor tempRole = (RoleDescriptor)_object ;
			// If adding
			if (resource.getPerformingRoles().contains(_object))
			{
				RoleDescriptorTreeNode localNode = new RoleDescriptorTreeNode(tempRole, tree) ;
				tree.getModel().insertNodeInto(localNode, this, getChildCount()) ;
				tree.scrollPathToVisible(new TreePath(localNode.getPath()));
				
			}
			
			// Else deleting
			else
			{
				int localChildCount = getChildCount() ;
				for(int i = 0 ; i < localChildCount ; i++)
				{
					if (((RoleDescriptorTreeNode)getChildAt(i)).getRole().getId().equals(tempRole.getId()))
					{
						tree.getModel().removeNodeFromParent((RoleDescriptorTreeNode)getChildAt(i)) ;
						break ;
					}
				}
			}
		}
	}

	
}
