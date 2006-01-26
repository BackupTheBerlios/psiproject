
package model.spem2 ;

import java.util.ArrayList ;
import java.util.Collection ;
import java.util.Observable;

import model.Presentation;

/**
 * Activity : the first non abstract class/interface. Represents a basic process activity.
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class Activity extends Observable implements WorkBreakdownElement, WorkDefinition
{
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
	 * 
	 */
	private Presentation presentationElement ;

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
}
