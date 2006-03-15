
package ui.tree ;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import model.spem2.TaskDefinition;
import model.spem2.TaskDescriptor;
import process.GlobalController;

/**
 * TaskDescriptorTreeNode : A tree representation of a task
 * 
 * @author Cond? Micka?l K.
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
	
	private MainTree tree = null ;

	
	/**
	 * Constructor
	 *
	 * @param _task
	 * @param _model
	 */
	public TaskDescriptorTreeNode (TaskDescriptor _task, MainTree _tree)
	{
		super() ;

		this.tree = _tree ;
		this.task = _task ;
		this.task.addObserver(this) ;
		this.setUserObject(task.getName()) ;
		
		Iterator<TaskDefinition> localIterator = task.getTasks().iterator() ;
		TaskDefinition localTask ;
		
		while (localIterator.hasNext())
		{
			localTask = localIterator.next() ;
			if (GlobalController.currentIteration.getTasks().contains(localTask))
			{
				this.add(new TaskDefinitionTreeNode(localTask, tree)) ;
			}
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
			if (task.getTasks().contains(_object) && GlobalController.currentIteration.getTasks().contains(_object))
			{
				TaskDefinitionTreeNode localNode = new TaskDefinitionTreeNode((TaskDefinition)_object, tree) ;
				tree.getModel().insertNodeInto(localNode, this, getChildCount()) ;
				tree.scrollPathToVisible(new TreePath(localNode.getPath()));
			}
			
			else
			{
				int localChildCount = getChildCount() ;
				for(int i = 0 ; i < localChildCount ; i++)
				{
					if (((TaskDefinitionTreeNode)getChildAt(i)).getTask().getId().equals(((TaskDefinition)_object).getId()))
					{
						tree.getModel().removeNodeFromParent((TaskDefinitionTreeNode)getChildAt(i)) ;
						break ;
					}
				}
			}
		}
		
	}

}
