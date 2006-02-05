
package process.utility ;

import java.util.Calendar ;
import java.util.Iterator ;

import model.HumanResource ;
import model.spem2.Artifact ;
import model.spem2.RoleDescriptor ;
import model.spem2.TaskDefinition;
import model.spem2.TaskDescriptor ;
import model.spem2.WorkProductDescriptor ;
import process.exception.DuplicateElementException ;

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

	/**
	 * Adds a new artifact into a product
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _product
	 * @param _name
	 * @param _description
	 * @throws DuplicateElementException
	 */
	public static void addArtefactIntoWorkProductDescriptor (WorkProductDescriptor _product, String _name, String _description)
			throws DuplicateElementException
	{
		// Checking if this name exists
		Iterator <Artifact> localIterator = _product.getArtifacts().iterator() ;
		while (localIterator.hasNext())
		{
			if (localIterator.next().getName().equals(_name)) { throw new DuplicateElementException() ; }
		}

		// Generating new ID
		Calendar localCalendar = Calendar.getInstance() ;
		String localID = "_" + localCalendar.get(Calendar.MILLISECOND) + localCalendar.get(Calendar.DAY_OF_MONTH) + localCalendar.get(Calendar.MONTH)
				+ localCalendar.get(Calendar.YEAR) + localCalendar.get(Calendar.HOUR) + localCalendar.get(Calendar.MINUTE) + localCalendar.get(Calendar.SECOND)
				+ "_artf" ;
		Artifact localArtifact = new Artifact(localID, _name, _description, _product.getId(), _product) ;
		_product.getArtifacts().add(localArtifact) ;
		_product.setChanged() ;
		_product.notifyObservers(localArtifact) ;
	}

	/**
	 * Adds new task into an "activity"
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _task
	 * @param _name
	 * @param _description
	 * @throws DuplicateElementException
	 */
	public static void addTaskDefinitionIntoTaskDescriptor (TaskDescriptor _task, String _name, String _description) throws DuplicateElementException
	{
		Iterator <TaskDefinition> localIterator = _task.getTasks().iterator() ;
		while (localIterator.hasNext())
		{
			if (localIterator.next().getName().equals(_name)) { throw new DuplicateElementException() ; }
		}

		// Generating new ID
		Calendar localCalendar = Calendar.getInstance() ;
		String localID = "_" + localCalendar.get(Calendar.MILLISECOND) + localCalendar.get(Calendar.DAY_OF_MONTH) + localCalendar.get(Calendar.MONTH)
				+ localCalendar.get(Calendar.YEAR) + localCalendar.get(Calendar.HOUR) + localCalendar.get(Calendar.MINUTE) + localCalendar.get(Calendar.SECOND)
				+ "_artf" ;
		TaskDefinition localTask = new TaskDefinition(localID, _name, _description, _task.getId(), _task) ;
		_task.getTasks().add(localTask) ;
		_task.setChanged() ;
		_task.notifyObservers(localTask) ;
	}

	/**
	 * Updates artifact with new values
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _artifact
	 * @param _name
	 * @param _description
	 */
	public static void updateArtifactInfo(Artifact _artifact, String _name, String _description)
	{
		_artifact.setName(_name) ;
		_artifact.setDescription(_description) ;
		_artifact.setChanged() ;
		_artifact.notifyObservers() ;
		
	}
	
}
