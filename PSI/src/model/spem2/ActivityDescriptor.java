package model.spem2;

/**
 * ActivityDescriptor : textual definition of an activity
 *
 * @author Condé Mickaël K.
 * @version 1.0
 *
 */
public class ActivityDescriptor implements Descriptor
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
	private PlanningData planningData = null ;

	/**
	 * The real work actualy performed
	 */
	private PlanningData realData = null ;

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
	 * Constructor
	 *
	 * @param _id
	 * @param _name
	 * @param _description
	 */
	public ActivityDescriptor (String _id, String _name, String _description)
	{
		super() ;

		this.id = _id ;
		this.name = _name ;
		this.description = _description ;
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
	 * @param _description The description to set.
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
	 * @param _id The id to set.
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
	 * @param _name The name to set.
	 */
	public void setName (String _name)
	{
		this.name = _name ;
	}
}
