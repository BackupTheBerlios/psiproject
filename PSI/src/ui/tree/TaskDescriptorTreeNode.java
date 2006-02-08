
package ui.tree ;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import model.spem2.TaskDefinition;
import model.spem2.TaskDescriptor;

/**
 * TaskDescriptorTreeNode : A tree representation of a task
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class TaskDescriptorTreeNode extends DefaultMutableTreeNode implements Observer
{
	private static final long serialVersionUID = -5327466955359571281L ;

	/**
	 * The task of the node
	 */
	private TaskDescriptor task ;
	
	private DefaultTreeModel treeModel = null ;

	
	/**
	 * Constructor
	 *
	 * @param _task
	 * @param _model
	 */
	public TaskDescriptorTreeNode (TaskDescriptor _task, DefaultTreeModel _model)
	{
		super() ;

		this.treeModel = _model ;
		this.task = _task ;
		this.task.addObserver(this) ;
		this.setUserObject(task.getName()) ;
		
		Iterator<TaskDefinition> localIterator = task.getTasks().iterator() ;
		while (localIterator.hasNext())
		{
			this.add(new TaskDefinitionTreeNode(localIterator.next(), treeModel)) ;
		}
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

	
	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update (Observable _observable, Object _object)
	{
		if (_object instanceof TaskDefinition)
		{
			// Adding to the node
			if (task.getTasks().contains(_object))
			{
				treeModel.insertNodeInto(new TaskDefinitionTreeNode((TaskDefinition)_object, treeModel), this, getChildCount()) ;
			}
		}
		
	}

}
