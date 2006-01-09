
package model.spem2 ;

import java.util.Collection ;

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
	private PlanningData realData;

	/**
	 * 
	 */
	private String id ;

	/**
	 * 
	 */
	private String name ;

	/**
	 * 
	 */
	private String description ;

	/**
	 * 
	 */
	private String authorFullName ;

	/**
	 * 
	 */
	private String authorMail ;

	/**
	 * Performers of an activity
	 */
	private Collection <RoleDefinition> performingRoles = null ;

	/**
	 * An activity is composed of Breakdown Elements
	 */
	private Collection <BreakdownElement> nestedElements = null ;

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
	
	public PlanningData getRealData ()
	{
		return this.realData ;
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
	
	public void setRealData (PlanningData _realData)
	{
		this.realData = _realData;
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
	 * @return Returns the nestedElements.
	 */
	public Collection <BreakdownElement> getNestedElements ()
	{
		return this.nestedElements ;
	}

	/**
	 * Setter
	 * 
	 * @param _nestedElements
	 *            The nestedElements to set.
	 */
	public void setNestedElements (Collection <BreakdownElement> _nestedElements)
	{
		this.nestedElements = _nestedElements ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the authorFullName.
	 */
	public String getAuthorFullName ()
	{
		return this.authorFullName ;
	}

	/**
	 * Setter
	 * 
	 * @param _authorFullName
	 *            The authorFullName to set.
	 */
	public void setAuthorFullName (String _authorFullName)
	{
		this.authorFullName = _authorFullName ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the authorMail.
	 */
	public String getAuthorMail ()
	{
		return this.authorMail ;
	}

	/**
	 * Setter
	 * 
	 * @param _authorMail
	 *            The authorMail to set.
	 */
	public void setAuthorMail (String _authorMail)
	{
		this.authorMail = _authorMail ;
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
	 * @return Returns the hasMultipleOccurences.
	 */
	public boolean isHasMultipleOccurences ()
	{
		return this.hasMultipleOccurences ;
	}

	/**
	 * Setter
	 * 
	 * @param _hasMultipleOccurences
	 *            The hasMultipleOccurences to set.
	 */
	public void setHasMultipleOccurences (boolean _hasMultipleOccurences)
	{
		this.hasMultipleOccurences = _hasMultipleOccurences ;
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
	 * @return Returns the performingRoles.
	 */
	public Collection <RoleDefinition> getPerformingRoles ()
	{
		return this.performingRoles ;
	}

	/**
	 * Setter
	 * 
	 * @param _performingRoles
	 *            The performingRoles to set.
	 */
	public void setPerformingRoles (Collection <RoleDefinition> _performingRoles)
	{
		this.performingRoles = _performingRoles ;
	}

}
