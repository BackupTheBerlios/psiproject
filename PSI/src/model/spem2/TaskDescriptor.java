
package model.spem2 ;

/**
 * TaskDescriptor : a Task Descriptor represents a proxy for a Task in the context of one specific
 * Activity.
 * 
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class TaskDescriptor implements Descriptor, WorkBreakdownElement
{
	/**
	 * A common prefix which can be set by the project manager to id elements.
	 */
	private String prefix ;

	/**
	 * Is the element planned within a project or not.
	 */
	private boolean isPlanned ;

	/**
	 * Has the element multiple occurences within a project or not
	 */
	private boolean hasMultipleOccurences ;

	/**
	 * Is the element optional for a project or not.
	 */
	private boolean isOptional ;

	/**
	 * Is the element repeatable or can be executed more than once in a project or not. For example,
	 * an iteration which is a WorkBreakdownElement is repeatable whereas a phase is not.
	 */
	private boolean isRepeatable ;

	/**
	 * Is the element ongoing or not.
	 */
	private boolean isOngoing ;

	/**
	 * Is the element event driven or not.
	 */
	private boolean isEventDriven ;

	/**
	 * The planification data for this element
	 * 
	 * @see PlanningData
	 */
	private PlanningData planningData ;

	/**
	 * Is the element synchronized with its source or not.
	 */
	private boolean isSynchronizedWithSource ;

	/**
	 * The definition of the Task
	 */
	private TaskDefinition definition ;

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
	 * @return Returns the isEventDriven.
	 */
	public boolean isEventDriven ()
	{
		return this.isEventDriven ;
	}

	/**
	 * Setter
	 * 
	 * @param _isEventDriven
	 *            The isEventDriven to set.
	 */
	public void setEventDriven (boolean _isEventDriven)
	{
		this.isEventDriven = _isEventDriven ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the isOngoing.
	 */
	public boolean isOngoing ()
	{
		return this.isOngoing ;
	}

	/**
	 * Setter
	 * 
	 * @param _isOngoing
	 *            The isOngoing to set.
	 */
	public void setOngoing (boolean _isOngoing)
	{
		this.isOngoing = _isOngoing ;
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
	 * @return Returns the isRepeatable.
	 */
	public boolean isRepeatable ()
	{
		return this.isRepeatable ;
	}

	/**
	 * Setter
	 * 
	 * @param _isRepeatable
	 *            The isRepeatable to set.
	 */
	public void setRepeatable (boolean _isRepeatable)
	{
		this.isRepeatable = _isRepeatable ;
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
	 * @return Returns the definition.
	 */
	public TaskDefinition getDefinition ()
	{
		return this.definition ;
	}
	

	/**
	 * Setter
	 * 
	 * @param _definition
	 *            The definition to set.
	 */
	public void setDefinition (TaskDefinition _definition)
	{
		this.definition = _definition ;
	}

}
