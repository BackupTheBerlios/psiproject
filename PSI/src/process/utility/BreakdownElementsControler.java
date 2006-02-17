
package process.utility ;

import java.util.Calendar ;
import java.util.Date;
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
	 * Links roles and human resources : by editing the proper collections
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _role
	 * @param _resource
	 */
	public static void unlinkRoleDescriptorAndHumanResource (RoleDescriptor _role, HumanResource _resource)
	{
		if (_role.getPerformers().contains(_resource))
		{
			_role.getPerformers().remove(_resource) ;
			_role.setChanged() ;
			_role.notifyObservers(_resource) ;
			// Back linking
			_resource.getPerformingRoles().remove(_role) ;
			_resource.setChanged() ;
			_resource.notifyObservers(_role) ;
		}
	}
	
	/**
	 * Links a task to a resource
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _task
	 * @param _resource
	 */
	public static void linkTaskDefinitionAndHumanResource (TaskDefinition _task, HumanResource _resource)
	{
		if (!_resource.getPerformingTasks().contains(_task))
		{
			_resource.getPerformingTasks().add(_task) ;
			_resource.setChanged() ;
			_resource.notifyObservers(_task) ;
		}
	}
	
	/**
	 * Unlinks a task with a resource
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _task
	 * @param _resource
	 */
	public static void unlinkTaskDefinitionAndHumanResource (TaskDefinition _task, HumanResource _resource)
	{
		if (_resource.getPerformingTasks().contains(_task))
		{
			_resource.getPerformingTasks().remove(_task) ;
			_resource.setChanged() ;
			_resource.notifyObservers(_task) ;
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
	
	/**
	 * Makes a link between an artifact and task definitions (input or output dependencies)
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _artifact
	 * @param _tasks
	 * @param _in
	 */
	public static void linkArtifactToTaskDefinitions(Artifact _artifact, Object[] _tasks, boolean _in)
	{
		for (int i = 0 ; i < _tasks.length ; i++)
		{
			if (_in)
			{
				((TaskDefinition)_tasks[i]).getInputProducts().add(_artifact) ;
				_artifact.getUsingTasks().add((TaskDefinition)_tasks[i]) ;
			}
			else
			{
				((TaskDefinition)_tasks[i]).getOutputProducts().add(_artifact) ;
				_artifact.getProducingTasks().add((TaskDefinition)_tasks[i]) ;
			}
			((TaskDefinition)_tasks[i]).setChanged() ;
			((TaskDefinition)_tasks[i]).notifyObservers(_artifact) ;
			_artifact.setChanged() ;
			_artifact.notifyObservers(_tasks[i]) ;
		}		
	}
	
	/**
	 * Unlinks previously linked artifact and tasks
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _artifact
	 * @param _tasks
	 * @param _in
	 */
	public static void unlinkArtifactAndTaskDefinitions(Artifact _artifact, Object[] _tasks, boolean _in)
	{
		for (int i = 0 ; i < _tasks.length ; i++)
		{
			if (_in)
			{
				((TaskDefinition)_tasks[i]).getInputProducts().remove(_artifact) ;
				_artifact.getUsingTasks().remove((TaskDefinition)_tasks[i]) ;
			}
			else
			{
				((TaskDefinition)_tasks[i]).getOutputProducts().remove(_artifact) ;
				_artifact.getProducingTasks().remove((TaskDefinition)_tasks[i]) ;
			}
			((TaskDefinition)_tasks[i]).setChanged() ;
			((TaskDefinition)_tasks[i]).notifyObservers(_artifact) ;
			_artifact.setChanged() ;
			_artifact.notifyObservers(_tasks[i]) ;
		}		
	}
	
	/**
	 * Makes a link between a task and artifacts (input or output dependencies)
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _task
	 * @param _artifacts
	 * @param _in
	 */
	public static void linkTaskDefinitionToArtifacts(TaskDefinition _task, Object[] _artifacts, boolean _in)
	{
		for (int i = 0 ; i < _artifacts.length ; i++)
		{
			if (_in)
			{
				((Artifact)_artifacts[i]).getUsingTasks().add(_task) ;
				_task.getInputProducts().add((Artifact)_artifacts[i]) ;
			}
			else
			{
				((Artifact)_artifacts[i]).getProducingTasks().add(_task) ;
				_task.getOutputProducts().add((Artifact)_artifacts[i]) ;
			}
			((Artifact)_artifacts[i]).setChanged() ;
			((Artifact)_artifacts[i]).notifyObservers(_task) ;
			_task.setChanged() ;
			_task.notifyObservers(_artifacts[i]) ;
		}		
	}
	
	/**
	 * Unlinks previously linked task and artifacts
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _task
	 * @param _artifacts
	 * @param _in
	 */
	public static void unlinkTaskDefinitionAndArtifacts(TaskDefinition _task, Object[] _artifacts, boolean _in)
	{
		for (int i = 0 ; i < _artifacts.length ; i++)
		{
			if (_in)
			{
				((Artifact)_artifacts[i]).getUsingTasks().remove(_task) ;
				_task.getInputProducts().remove((Artifact)_artifacts[i]) ;
			}
			else
			{
				((Artifact)_artifacts[i]).getProducingTasks().remove(_task) ;
				_task.getOutputProducts().remove((Artifact)_artifacts[i]) ;
			}
			((Artifact)_artifacts[i]).setChanged() ;
			((Artifact)_artifacts[i]).notifyObservers(_task) ;
			_task.setChanged() ;
			_task.notifyObservers(_artifacts[i]) ;
		}		
	}
	
	/**
	 * Sets a date for a task definition
	 * 
	 * @param _task
	 * @param _date
	 * @param _start
	 * @param _prevision
	 */
	public static void setDateForTaskDefinition(TaskDefinition _task, Date _date, boolean _start, boolean _prevision)
	{
		if (_prevision)
		{			
			if (_start)
			{
				_task.getPlanningData().setStartDate(_date) ;
			}
			
			else
			{
				_task.getPlanningData().setFinishDate(_date) ;
			}
		}
		
		else
		{			
			if (_start)
			{
				_task.getRealData().setStartDate(_date) ;
			}
			
			else
			{
				_task.getRealData().setFinishDate(_date) ;
			}
		}
		
		_task.setChanged() ;
		_task.notifyObservers() ;
	}
	
	/**
	 * Sets the duration of a task
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _task
	 * @param _duration
	 * @param _prevision
	 */
	public static void setDurationForTaskDefinition(TaskDefinition _task, float _duration, boolean _prevision)
	{
		if (_prevision)
		{
			_task.getPlanningData().setDuration(_duration) ;
		}
		
		else
		{
			_task.getRealData().setDuration(_duration) ;
		}
		
		_task.setChanged() ;
		_task.notifyObservers() ;
		
	}
}
