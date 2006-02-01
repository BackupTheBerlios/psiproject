package model.spem2;


/**
 * TaskDefinition : A more specific task type, defined within a project, not a process
 *
 * @author Conde Mickael K.
 * @version 1.0
 *
 */
public class TaskDefinition extends TaskDescriptor
{
	private TaskDescriptor task ;
	
	/**
	 * Constructor
	 *
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _parentId
	 * @param _task
	 */
	public TaskDefinition (String _id, String _name, String _description, String _parentId, TaskDescriptor _task)
	{
		super(_id, _name, _description, _parentId) ;

		this.task = _task ;
	}

	/**
	 * Constructor
	 *
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _parentId
	 */
	public TaskDefinition (String _id, String _name, String _description, String _parentId)
	{
		super(_id, _name, _description, _parentId) ;
		
	}

	/**
	 * Getter
	 *
	 * @return Returns the task.
	 */
	public TaskDescriptor getTask ()
	{
		return this.task ;
	}

	/**
	 * Setter
	 *
	 * @param _task The task to set.
	 */
	public void setTask (TaskDescriptor _task)
	{
		this.task = _task ;
	}
	
}
