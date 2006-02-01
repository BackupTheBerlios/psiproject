
package process.utility ;

import model.HumanResource ;
import model.spem2.RoleDescriptor ;

/**
 * BreakdownElementsControler : utility class to create associations and more between model elements
 * Also uses the observer pattern by notifying elements
 * 
 * @author Conde Mickael K.
 * @version 1.0
 * 
 */
public class BreakdownElementsControler
{
	/**
	 * Links roles and human resources : by editing the proper collections
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _role
	 * @param _resource
	 */
	public static void linkRoleDescriptorAndHumanResource (RoleDescriptor _role, HumanResource _resource)
	{
		if (!_role.getPerformers().contains(_resource))
		{
			_role.getPerformers().add(_resource) ;
			_role.setChanged() ;
			_role.notifyObservers(_resource) ;
			// Back linking
			_resource.getPerformingRoles().add(_role) ;
			_resource.setChanged() ;
			_resource.notifyObservers(_role) ;
		}
	}

}
