package model.spem2;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;


/**
 * TaskDefinition : A more specific task type, defined within a project, not a process
 *
 * @author Conde Mickael K.
 * @version 1.0
 *
 */
public class TaskDefinition extends TaskDescriptor implements Transferable
{
	private TaskDescriptor task ;
	
	/**
	 * Drag and drop capability
	 */
	public static DataFlavor TASK_FLAVOR = new DataFlavor(RoleDescriptor.class, "Task") ;;
	
	private static DataFlavor[] flavors = {TASK_FLAVOR} ;
	
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
	
	/**
	 * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
	 */
	public Object getTransferData (DataFlavor _df) throws UnsupportedFlavorException, IOException
	{
		if (_df.equals(TASK_FLAVOR))
		{
			return this ;
		}
		else
		{
			throw new UnsupportedFlavorException(_df) ;
		}
	}	

	/**
	 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
	 */
	public DataFlavor[] getTransferDataFlavors ()
	{
		return flavors ;
	}
	

	/**
	 * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
	 */
	public boolean isDataFlavorSupported (DataFlavor _df)
	{
		return _df.equals(TASK_FLAVOR) ;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@ Override
	public String toString ()
	{
		return getName() ;
	}
	
}
