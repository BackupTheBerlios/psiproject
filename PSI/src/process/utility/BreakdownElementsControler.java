
package process.utility ;

import java.util.Calendar ;
import java.util.Date;
import java.util.Iterator ;

import model.HumanResource ;
import model.Project;
import model.spem2.Artifact ;
import model.spem2.Iteration;
import model.spem2.RoleDescriptor ;
import model.spem2.TaskDefinition;
import model.spem2.TaskDescriptor ;
import model.spem2.WorkProductDescriptor ;
import process.GlobalController;
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
			
			GlobalController.projectChanged = true ;
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
			
			GlobalController.projectChanged = true ;
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
			
			GlobalController.projectChanged = true ;
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
			
			GlobalController.projectChanged = true ;
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
	public static void addArtifactIntoWorkProductDescriptor (WorkProductDescriptor _product, String _name, String _description)
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
		
		GlobalController.projectChanged = true ;
	}
	
	/**
	 * Delete an artifact and cleaning all references
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _artifact
	 */
	public static void deleteArtifact(Artifact _artifact)
	{
		// Suppressing from tasks
		Iterator <TaskDescriptor> localTaskIterator = _artifact.getUsingTasks().iterator() ;
		TaskDefinition localTask ;
		while (localTaskIterator.hasNext())
		{
			localTask = (TaskDefinition)localTaskIterator.next() ;
			localTask.getInputProducts().remove(_artifact) ;
			localTask.setChanged() ;
			localTask.notifyObservers(_artifact) ;
		}
		
		localTaskIterator = _artifact.getProducingTasks().iterator() ;
		while (localTaskIterator.hasNext())
		{
			localTask = (TaskDefinition)localTaskIterator.next() ;
			localTask.getOutputProducts().remove(_artifact) ;
			localTask.setChanged() ;
			localTask.notifyObservers(_artifact) ;
		}
		
		// Suppressing from product
		_artifact.getProduct().getArtifacts().remove(_artifact) ;
		_artifact.getProduct().setChanged() ;
		_artifact.getProduct().notifyObservers(_artifact) ;
		
		GlobalController.projectChanged = true ;
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
				+ "_tskd" ;
		TaskDefinition localTask = new TaskDefinition(localID, _name, _description, _task.getId(), _task) ;
		_task.getTasks().add(localTask) ;
		_task.setChanged() ;
		_task.notifyObservers(localTask) ;
		
		GlobalController.projectChanged = true ;
		GlobalController.currentIteration.getTasks().add(localTask) ;
	}
	
	/**
	 * Delete a task from curent iteration andcleaning all references
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _task
	 */
	public static void deleteTaskDefinition(TaskDefinition _task, Project _proj)
	{
		// Suppressing from tasks
		Iterator <WorkProductDescriptor> localArtifactIterator = _task.getInputProducts().iterator() ;
		Artifact localArtifact ;
		while (localArtifactIterator.hasNext())
		{
			localArtifact = (Artifact)localArtifactIterator.next() ;
			localArtifact.getUsingTasks().remove(_task) ;
			localArtifact.setChanged() ;
			localArtifact.notifyObservers(_task) ;
		}
		
		localArtifactIterator = _task.getOutputProducts().iterator() ;
		while (localArtifactIterator.hasNext())
		{
			localArtifact = (Artifact)localArtifactIterator.next() ;
			localArtifact.getProducingTasks().remove(_task) ;
			localArtifact.setChanged() ;
			localArtifact.notifyObservers(_task) ;
		}
		
		// Suppressing from resources
		
		// Suppressing from product
		_task.getTask().getTasks().remove(_task) ;
		_task.getTask().setChanged() ;
		_task.getTask().notifyObservers(_task) ;
		
		GlobalController.projectChanged = true ;
	}
	
	/**
	 * Adds a new iteration
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _proj
	 */
	public static void addIterationIntoProject(Project _proj)
	{
		Calendar localCalendar = Calendar.getInstance() ;
		String localID = "_" + localCalendar.get(Calendar.MILLISECOND) + localCalendar.get(Calendar.DAY_OF_MONTH) + localCalendar.get(Calendar.MONTH)
				+ localCalendar.get(Calendar.YEAR) + localCalendar.get(Calendar.HOUR) + localCalendar.get(Calendar.MINUTE) + localCalendar.get(Calendar.SECOND)
				+ "_iter" ;
		
		Iteration localIteration = new Iteration(localID, _proj.getIterations().size() + 1) ;
		
		// Copying references to tasks
		if (GlobalController.currentIteration != null)
		{
			Iterator<TaskDefinition> localTaskIteration = GlobalController.currentIteration.getTasks().iterator() ;
			
			while (localTaskIteration.hasNext())
			{
				localIteration.getTasks().add(localTaskIteration.next()) ;
			}
		}

		_proj.getIterations().add(localIteration) ;
		_proj.setChanged() ;
		_proj.notifyObservers(localIteration) ;
		
		GlobalController.projectChanged = true ;
		GlobalController.currentIteration = localIteration ;
		
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
		
		GlobalController.projectChanged = true ;
		
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
			
			GlobalController.projectChanged = true ;
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
			
			GlobalController.projectChanged = true ;
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
			
			GlobalController.projectChanged = true ;
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
			
			GlobalController.projectChanged = true ;
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
		
		GlobalController.projectChanged = true ;
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
		
		GlobalController.projectChanged = true ;
		
	}
}
