
package ui.tree ;

import javax.swing.tree.DefaultMutableTreeNode ;

import model.spem2.RoleDescriptor ;

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
