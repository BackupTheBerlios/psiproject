package ui.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import model.spem2.RoleDescriptor;

/**
 * RoleDescriptorTreeNode : TODO type description
 *
 * @author Condé Mickaël K.
 * @version 1.0
 *
 */
public class RoleDescriptorTreeNode extends DefaultMutableTreeNode
{
	private static final long serialVersionUID = 2312503011598809409L ;
	
	/**
	 * The role of the node
	 */
	private RoleDescriptor role ;

	/**
	 * Constructor
	 *
	 * @param _role
	 */
	public RoleDescriptorTreeNode (RoleDescriptor _role)
	{
		super() ;

		this.role = _role ;
		this.setUserObject(role.getName()) ;
	}

}
