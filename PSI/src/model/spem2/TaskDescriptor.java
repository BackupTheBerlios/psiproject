
package model.spem2 ;

import java.util.ArrayList ;
import java.util.Collection ;
import java.util.Observable ;

import model.Presentation;

/**
 * TaskDescriptor : a task within a project
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class TaskDescriptor extends Observable implements Descriptor
{
	/**
	 * The unique identifier of the element
	 */
	private String id ;

	/**
	 * The name of the element
	 */
	private String name ;

	/**
	 * The description of the element
	 */
	private String description ;

	/**
	 * The parent ID of the element
	 * 
	 * @see
	 */
	private String parentId ;

	/**
	 * The estimation associated to this element
	 */
	private PlanningData planningData ;

	/**
	 * The real work actualy performed
	 */
	private PlanningData realData ;
	
	/**
	 * 
	 */
	private Presentation presentationElement ;
	
	/**
	 * Main performers of the task
	 */
	private Collection <RoleDescriptor> primaryPerformers ;

	/**
	 * Additional performers of the task
	 */
	private Collection <WorkProductDescriptor> inputProducts ;

	/**
	 * Assisting performers of the task
	 */
	private Collection <WorkProductDescriptor> outputProducts ;
	
	/**
	 * Proper tasks
	 */
	private Collection <TaskDefinition> tasks ;

	/**
	 * Constructor
	 * 
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _parentId
	 */
	public TaskDescriptor (String _id, String _name, String _description, String _parentId)
	{
		super() ;

		this.id = _id ;
		this.name = _name ;
		this.description = _description ;
		this.parentId = _parentId ;

		this.primaryPerformers = new ArrayList <RoleDescriptor>() ;
		this.inputProducts = new ArrayList <WorkProductDescriptor>() ;
		this.outputProducts = new ArrayList <WorkProductDescriptor>() ;
		this.tasks = new ArrayList <TaskDefinition>() ;

		this.planningData = new PlanningData() ;
		this.realData = new PlanningData() ;
	}

	
	/**
	 * Getter
	 * 
	 * @return Returns the planningData.
	 */
	public PlanningData getPlanningData ()
	{
		return this.planningData ;
	}

	/**
	 * Setter
	 * 
	 * @param _planningData
	 *            The planningData to set.
	 */
	public void setPlanningData (PlanningData _planningData)
	{
		this.planningData = _planningData ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the realData.
	 */
	public PlanningData getRealData ()
	{
		return this.realData ;
	}

	/**
	 * Setter
	 * 
	 * @param _realData
	 *            The realData to set.
	 */
	public void setRealData (PlanningData _realData)
	{
		this.realData = _realData ;
	}	

	/**
	 * Getter
	 * 
	 * @return Returns the description.
	 */
	public String getDescription ()
	{
		return this.description ;
	}

	/**
	 * Setter
	 * 
	 * @param _description
	 *            The description to set.
	 */
	public void setDescription (String _description)
	{
		this.description = _description ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the id.
	 */
	public String getId ()
	{
		return this.id ;
	}

	/**
	 * Setter
	 * 
	 * @param _id
	 *            The id to set.
	 */
	public void setId (String _id)
	{
		this.id = _id ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the name.
	 */
	public String getName ()
	{
		return this.name ;
	}

	/**
	 * Setter
	 * 
	 * @param _name
	 *            The name to set.
	 */
	public void setName (String _name)
	{
		this.name = _name ;
	}

	
	/**
	 * Getter
	 * 
	 * @return Returns the primaryPerformers.
	 */
	public Collection <RoleDescriptor> getPrimaryPerformers ()
	{
		return this.primaryPerformers ;
	}

	/**
	 * Setter
	 * 
	 * @param _primaryPerformers
	 *            The primaryPerformers to set.
	 */
	public void setPrimaryPerformers (Collection <RoleDescriptor> _primaryPerformers)
	{
		this.primaryPerformers = _primaryPerformers ;
	}
	
	/**
	 * Getter
	 * 
	 * @return Returns the parentId.
	 */
	public String getParentId ()
	{
		return this.parentId ;
	}

	/**
	 * Setter
	 * 
	 * @param _parentId
	 *            The parentId to set.
	 */
	public void setParentId (String _parentId)
	{
		this.parentId = _parentId ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the inputProducts.
	 */
	public Collection <WorkProductDescriptor> getInputProducts ()
	{
		return this.inputProducts ;
	}

	/**
	 * Setter
	 *
	 * @param _inputProducts The inputProducts to set.
	 */
	public void setInputProducts (Collection <WorkProductDescriptor> _inputProducts)
	{
		this.inputProducts = _inputProducts ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the outputProducts.
	 */
	public Collection <WorkProductDescriptor> getOutputProducts ()
	{
		return this.outputProducts ;
	}

	/**
	 * Setter
	 *
	 * @param _outputProducts The outputProducts to set.
	 */
	public void setOutputProducts (Collection <WorkProductDescriptor> _outputProducts)
	{
		this.outputProducts = _outputProducts ;
	}


	/**
	 * Getter
	 *
	 * @return Returns the presentationElement.
	 */
	public Presentation getPresentationElement ()
	{
		return this.presentationElement ;
	}


	/**
	 * Setter
	 *
	 * @param _presentationElement The presentationElement to set.
	 */
	public void setPresentationElement (Presentation _presentationElement)
	{
		this.presentationElement = _presentationElement ;
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


	
	/**
	 * @see java.util.Observable#setChanged()
	 */
	@ Override
	public synchronized void setChanged ()
	{
		super.setChanged() ;
	}

}
