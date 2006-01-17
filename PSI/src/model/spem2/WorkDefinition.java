
package model.spem2 ;

import java.util.Collection ;

/**
 * WorkDefinition : generalization of all definitions of work within the SPEM 2.0
 * 
 * @author Cond� Micka�l K.
 * @version 1.0
 * 
 */
public interface WorkDefinition
{
	/**
	 * Gets all Role Descriptors associated to the current element
	 * 
	 * @author Cond� Micka�l K.
	 * @version 1.0
	 * 
	 * @return the Role Descriptors associated to te Work Definition
	 */
	public Collection <RoleDescriptor> getPerformingRoles () ;

	/**
	 * Sets the roles lists with new values
	 * 
	 * @author Cond� Micka�l K.
	 * @version 1.0
	 * 
	 * @param _roleDescriptor
	 *            the new roles list
	 */
	public void setPerformingRoles (Collection <RoleDescriptor> _roleDescriptor) ;

}
