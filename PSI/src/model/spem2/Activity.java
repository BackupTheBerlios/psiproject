
package model.spem2 ;

import java.util.ArrayList ;
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
	 * The actual data for this element
	 */
	private PlanningData realData ;

	/**
	 * The descriptor of the activity
	 */
	private ActivityDescriptor descriptor ;

	/**
	 * 
	 */
	private String interfaceDiagramPath ;

	/**
	 * 
	 */
	private String flowDiagramPath ;

	/**
	 * 
	 */
	private String activityDiagramPath ;

	/**
	 * An activity is composed of Breakdown Elements
	 */
	private Collection <BreakdownElement> nestedElements = null ;

	/**
	 * Constructor
	 * 
	 * @param _id
	 *            the identifier of the activity
	 * @param _name
	 *            the name of the activity
	 * @param _description
	 *            the description
	 */
	public Activity (String _id, String _name, String _description, String _parentId)
	{
		super() ;
		nestedElements = new ArrayList <BreakdownElement>() ;
		this.descriptor = new ActivityDescriptor(_id, _name, _description, _parentId) ;
	}

	/**
	 * Constructor
	 * 
	 * @param _interfaceDiagramPath
	 * @param _flowDiagramPath
	 * @param _activityDiagramPath
	 */
	public Activity (String _id, String _name, String _description, String _parentId, String _interfaceDiagramPath, String _flowDiagramPath,
			String _activityDiagramPath)
	{
		this(_id, _name, _description, _parentId) ;

		this.interfaceDiagramPath = _interfaceDiagramPath ;
		this.flowDiagramPath = _flowDiagramPath ;
		this.activityDiagramPath = _activityDiagramPath ;
	}

	/**
	 * @see model.spem2.BreakdownElement#getPrefix()
	 */
	public String getPrefix ()
	{
		return descriptor.getPrefix() ;
	}

	/**
	 * @see model.spem2.BreakdownElement#hasMultipleOccurences()
	 */
	public boolean hasMultipleOccurences ()
	{
		return descriptor.hasMultipleOccurences() ;
	}

	/**
	 * @see model.spem2.BreakdownElement#isOptional()
	 */
	public boolean isOptional ()
	{
		return descriptor.isOptional() ;
	}

	/**
	 * @see model.spem2.BreakdownElement#isPlanned()
	 */
	public boolean isPlanned ()
	{
		return descriptor.isPlanned() ;
	}

	/**
	 * @see model.spem2.BreakdownElement#setMultipleOccurences(boolean)
	 */
	public void setMultipleOccurences (boolean _hasMultipleOccurences)
	{
		descriptor.setMultipleOccurences(_hasMultipleOccurences) ;

	}

	/**
	 * @see model.spem2.BreakdownElement#setOptional(boolean)
	 */
	public void setOptional (boolean _isOptional)
	{
		descriptor.setOptional(_isOptional) ;
	}

	/**
	 * @see model.spem2.BreakdownElement#setPlanned(boolean)
	 */
	public void setPlanned (boolean _isPlanned)
	{
		descriptor.setPlanned(_isPlanned) ;

	}

	/**
	 * @see model.spem2.BreakdownElement#setPrefix(java.lang.String)
	 */
	public void setPrefix (String _prefix)
	{
		descriptor.setPrefix(_prefix) ;

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
	 * @param _planningData
	 *            The planningData to set.
	 */
	public void setPlanningData (PlanningData _planningData)
	{
		this.planningData = _planningData ;
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
	 * @return Returns the descriptor.
	 */
	public ActivityDescriptor getDescriptor ()
	{
		return this.descriptor ;
	}

	/**
	 * Setter
	 * 
	 * @param _descriptor
	 *            The descriptor to set.
	 */
	public void setDescriptor (ActivityDescriptor _descriptor)
	{
		this.descriptor = _descriptor ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the activityDiagramPath.
	 */
	public String getActivityDiagramPath ()
	{
		return this.activityDiagramPath ;
	}

	/**
	 * Setter
	 * 
	 * @param _activityDiagramPath
	 *            The activityDiagramPath to set.
	 */
	public void setActivityDiagramPath (String _activityDiagramPath)
	{
		this.activityDiagramPath = _activityDiagramPath ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the flowDiagramPath.
	 */
	public String getFlowDiagramPath ()
	{
		return this.flowDiagramPath ;
	}

	/**
	 * Setter
	 * 
	 * @param _flowDiagramPath
	 *            The flowDiagramPath to set.
	 */
	public void setFlowDiagramPath (String _flowDiagramPath)
	{
		this.flowDiagramPath = _flowDiagramPath ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the interfaceDiagramPath.
	 */
	public String getInterfaceDiagramPath ()
	{
		return this.interfaceDiagramPath ;
	}

	/**
	 * Setter
	 * 
	 * @param _interfaceDiagramPath
	 *            The interfaceDiagramPath to set.
	 */
	public void setInterfaceDiagramPath (String _interfaceDiagramPath)
	{
		this.interfaceDiagramPath = _interfaceDiagramPath ;
	}
}
