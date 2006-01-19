
package ui.tree ;

import javax.swing.tree.DefaultMutableTreeNode ;

import model.spem2.TaskDescriptor ;

/**
 * TaskDescriptorTreeNode : A tree representation of a task
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class TaskDescriptorTreeNode extends DefaultMutableTreeNode
{
	private static final long serialVersionUID = -5327466955359571281L ;

	/**
	 * The task of the node
	 */
	private TaskDescriptor task ;

	/**
	 * Constructor
	 * 
	 * @param _task
	 */
	public TaskDescriptorTreeNode (TaskDescriptor _task)
	{
		super() ;

		this.task = _task ;
		this.setUserObject(task.getName()) ;
	}
	
	public TaskDescriptor getTaskDescriptor()
	{
		return this.task;
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
	 * @param _task
	 *            The task to set.
	 */
	public void setTask (TaskDescriptor _task)
	{
		this.task = _task ;
	}

}
