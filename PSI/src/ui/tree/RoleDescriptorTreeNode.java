
package ui.tree ;

import javax.swing.tree.DefaultMutableTreeNode;

import model.spem2.RoleDescriptor;

/**
 * RoleDescriptorTreeNode : A tree representation of a role
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
	
	@SuppressWarnings("unused")
	private MainTree tree = null ;

	/**
	 * Constructor
	 * 
	 * @param _role
	 */
	public RoleDescriptorTreeNode (RoleDescriptor _role, MainTree _tree)
	{
		super() ;

		this.tree = _tree ;
		this.role = _role ;
		this.setUserObject(role) ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the role.
	 */
	public RoleDescriptor getRole ()
	{
		return this.role ;
	}

	/**
	 * Setter
	 * 
	 * @param _role
	 *            The role to set.
	 */
	public void setRole (RoleDescriptor _role)
	{
		this.role = _role ;
	}

	
}
