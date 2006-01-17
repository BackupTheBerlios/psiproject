package ui.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import model.spem2.TaskDescriptor;

/**
 * TaskDescriptorTreeNode : TODO type description
 *
 * @author Cond� Micka�l K.
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

}
