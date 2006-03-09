
package model.spem2 ;

import java.util.ArrayList ;
import java.util.Collection ;

/**
 * Iteration : a repeatable variant of an activity.
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class Iteration extends Activity
{
	
	Collection <TaskDefinition> tasks ;

	/**
	 * Constructor
	 * 
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _parentId
	 */
	public Iteration (String _id, String _name, String _description, String _parentId)
	{
		super(_id, _name, _description, _parentId) ;
		tasks = new ArrayList <TaskDefinition>() ;
	}
	
	/**
	 * Constructor
	 *
	 * @param _id
	 * @param _number
	 */
	public Iteration (String _id, int _number)
	{
		super(_id, ""+_number, "", "") ;
		tasks = new ArrayList <TaskDefinition>() ;
		
	}
	
	/**
	 * Getter
	 *
	 * @return Returns the tasks.
	 */
	public Collection <TaskDefinition> getTasks ()
	{
		return this.tasks ;
	}

	/**
	 * Setter
	 *
	 * @param _tasks The tasks to set.
	 */
	public void setTasks (Collection <TaskDefinition> _tasks)
	{
		this.tasks = _tasks ;
	}

}
