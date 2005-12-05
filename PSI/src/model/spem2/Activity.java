
package model.spem2 ;


import java.util.Collection;

/**
 * Activity : the first non abstract class/interface. Represents a basic process activity.
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class Activity implements WorkBreakdownElement, WorkDefinition
{
	/**
	 * A list of activity descriptors linked to the activity
	 */
	private Collection<ActivityDescriptor> activities ;
	
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
	 * Is the element repeatable or can be executed more than once in one project or not. For
	 * example, an iteration which is a WorkBreakdownElement is repeatable whereas a phase is not.
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
	 * An activity is composed of Breakdown Elements
	 */
	private Collection<BreakdownElement> nestedElements ;

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
	 * @return Returns the activities.
	 */
	public Collection <ActivityDescriptor> getActivities ()
	{
		return this.activities ;
	}
	

	/**
	 * Setter
	 *
	 * @param _activities The activities to set.
	 */
	public void setActivities (Collection <ActivityDescriptor> _activities)
	{
		this.activities = _activities ;
	}
	

	/**
	 * Getter
	 *
	 * @return Returns the nestedElements.
	 */
	public Collection <BreakdownElement> getNestedElements ()
	{
		return this.nestedElements ;
	}
	

	/**
	 * Setter
	 *
	 * @param _nestedElements The nestedElements to set.
	 */
	public void setNestedElements (Collection <BreakdownElement> _nestedElements)
	{
		this.nestedElements = _nestedElements ;
	}

	
	/**
	 * @see model.spem2.WorkDefinition#getPerformingRoles()
	 */
	public Collection <RoleDescriptor> getPerformingRoles ()
	{
		return null ;
	}

	
	/**
	 * @see model.spem2.WorkDefinition#setPerformingRoles(java.util.Collection)
	 */
	public void setPerformingRoles (Collection <RoleDescriptor> _roleDescriptor)
	{
		// TODO Auto-generated method stub
		
	}
	
}
