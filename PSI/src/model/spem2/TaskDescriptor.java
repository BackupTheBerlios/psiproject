
package model.spem2 ;

import java.util.Collection ;

/**
 * TaskDescriptor : a task within a project
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class TaskDescriptor implements Descriptor
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
	 * The estimation associated to this element
	 */
	private PlanningData planningData ;

	/**
	 * The real work actualy performed
	 */
	private PlanningData realData ;

	/**
	 * Is the element planed or not
	 */
	private boolean isPlanned ;

	/**
	 * Has the element multiple occurences or not
	 */
	private boolean hasMultipleOccurences ;

	/**
	 * Is the element optional or not
	 */
	private boolean isOptional ;

	/**
	 * Is the element synchronized with source or not
	 */
	private boolean isSynchronizedWithSource ;

	/**
	 * The prefix of the element
	 */
	private String prefix ;

	/**
	 * Main performers of the task
	 */
	private Collection <RoleDescriptor> primaryPerformers ;

	/**
	 * Additional performers of the task
	 */
	private Collection <RoleDescriptor> additionalPerformers ;

	/**
	 * Assisting performers of the task
	 */
	private Collection <RoleDescriptor> assistingPerformers ;

	/**
	 * Constructor
	 *
	 * @param _id
	 * @param _name
	 * @param _description
	 */
	public TaskDescriptor (String _id, String _name, String _description)
	{
		super() ;

		this.id = _id ;
		this.name = _name ;
		this.description = _description ;
		this.planningData = new PlanningData();
		this.realData = new PlanningData();
	}

	/**
	 * Getter
	 * 
	 * @return Returns the hasMultipleOccurences.
	 */
	public boolean hasMultipleOccurences ()
	{
		return this.hasMultipleOccurences ;
	}

	/**
	 * Setter
	 * 
	 * @param _hasMultipleOccurences
	 *            The hasMultipleOccurences to set.
	 */
	public void setMultipleOccurences (boolean _hasMultipleOccurences)
	{
		this.hasMultipleOccurences = _hasMultipleOccurences ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the isOptional.
	 */
	public boolean isOptional ()
	{
		return this.isOptional ;
	}

	/**
	 * Setter
	 * 
	 * @param _isOptional
	 *            The isOptional to set.
	 */
	public void setOptional (boolean _isOptional)
	{
		this.isOptional = _isOptional ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the isPlanned.
	 */
	public boolean isPlanned ()
	{
		return this.isPlanned ;
	}

	/**
	 * Setter
	 * 
	 * @param _isPlanned
	 *            The isPlanned to set.
	 */
	public void setPlanned (boolean _isPlanned)
	{
		this.isPlanned = _isPlanned ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the isSynchronizedWithSource.
	 */
	public boolean isSynchronizedWithSource ()
	{
		return this.isSynchronizedWithSource ;
	}

	/**
	 * Setter
	 * 
	 * @param _isSynchronizedWithSource
	 *            The isSynchronizedWithSource to set.
	 */
	public void setSynchronizedWithSource (boolean _isSynchronizedWithSource)
	{
		this.isSynchronizedWithSource = _isSynchronizedWithSource ;
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
	 * @return Returns the prefix.
	 */
	public String getPrefix ()
	{
		return this.prefix ;
	}

	/**
	 * Setter
	 * 
	 * @param _prefix
	 *            The prefix to set.
	 */
	public void setPrefix (String _prefix)
	{
		this.prefix = _prefix ;
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
	 * @return Returns the additionalPerformers.
	 */
	public Collection <RoleDescriptor> getAdditionalPerformers ()
	{
		return this.additionalPerformers ;
	}

	/**
	 * Setter
	 * 
	 * @param _additionalPerformers
	 *            The additionalPerformers to set.
	 */
	public void setAdditionalPerformers (Collection <RoleDescriptor> _additionalPerformers)
	{
		this.additionalPerformers = _additionalPerformers ;
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
	 * @return Returns the assistingPerformers.
	 */
	public Collection <RoleDescriptor> getAssistingPerformers ()
	{
		return this.assistingPerformers ;
	}

	/**
	 * Setter
	 * 
	 * @param _assistingPerformers
	 *            The assistingPerformers to set.
	 */
	public void setAssistingPerformers (Collection <RoleDescriptor> _assistingPerformers)
	{
		this.assistingPerformers = _assistingPerformers ;
	}

}
