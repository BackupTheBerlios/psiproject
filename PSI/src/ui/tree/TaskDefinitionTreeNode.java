package ui.tree;

import java.util.Observable;
import java.util.Observer;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import model.spem2.TaskDefinition;

/**
 * TaskDefinitionTreeNode : a tree representation of a task definition
 *
 * @author Conde Mickael K.
 * @version 1.0
 *
 */
public class TaskDefinitionTreeNode extends DefaultMutableTreeNode implements Observer
{
	private static final long serialVersionUID = 7553945305003458375L ;
	
	private TaskDefinition task = null ;
	
	private DefaultTreeModel treeModel = null ;

	/**
	 * Constructor
	 *
	 * @param _task
	 */
	public TaskDefinitionTreeNode (TaskDefinition _task, DefaultTreeModel _model)
	{
		super() ;

		this.treeModel = _model ;
		this.task = _task ;
		this.task.addObserver(this) ;
		this.setUserObject(task) ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the task.
	 */
	public TaskDefinition getTask ()
	{
		return this.task ;
	}

	/**
	 * Setter
	 *
	 * @param _task The task to set.
	 */
	public void setTask (TaskDefinition _task)
	{
		this.task = _task ;
	}

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update (Observable _observable, Object _object)
	{
		treeModel.reload(this) ;
		
	}
	

}
