
package ui.tree ;

import javax.swing.tree.DefaultMutableTreeNode;

import model.HumanResource;

/**
 * ResourceTreeNode : tree like representation of a Human Resource
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class ResourceTreeNode extends DefaultMutableTreeNode
{
	private static final long serialVersionUID = -2206326976916871308L ;

	private HumanResource resource = null ;

	/**
	 * Constructor
	 * 
	 * @param _resource
	 */
	public ResourceTreeNode (HumanResource _resource)
	{
		super() ;

		this.resource = _resource ;
		this.setUserObject(resource) ;
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

	
}
