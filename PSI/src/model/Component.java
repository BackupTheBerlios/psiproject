
package model ;

import java.util.Collection ;

import model.spem2.Activity ;

/**
 * Component : A component generated by IEPP
 * 
 * @author Administrateur
 * @version 1.0
 * 
 */
public class Component extends Activity
{

	/**
	 * The current version of the component
	 */
	private String version ;

	/**
	 * The string representation of publication date String format is used to avoid formating
	 * problems
	 */
	private String date ;

	/**
	 * The full name of the author
	 */
	private String authorFullName ;

	/**
	 * Author's contact
	 */
	private String authorMail ;

	/**
	 * 
	 */
	private String generationOrder ;

	/**
	 * 
	 */
	private Collection <String> responsabilityDiagramPaths ;

	/**
	 * Constructor
	 * 
	 * @param _id
	 * @param _name
	 * @param _description
	 */
	public Component (String _id, String _name, String _description)
	{
		super(_id, _name, _description, "") ;
	}

	/**
	 * Constructor
	 * 
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _parentId
	 * @param _interfaceDiagramPath
	 * @param _flowDiagramPath
	 * @param _activityDiagramPath
	 * @param _version
	 * @param _date
	 * @param _authorFullName
	 * @param _authorMail
	 * @param _generationOrder
	 * @param _responsabilityDiagramPaths
	 */
	public Component (String _id, String _name, String _description, String _parentId, String _interfaceDiagramPath, String _flowDiagramPath,
			String _activityDiagramPath, String _version, String _date, String _authorFullName, String _authorMail, String _generationOrder,
			Collection <String> _responsabilityDiagramPaths)
	{
		super(_id, _name, _description, _parentId, _interfaceDiagramPath, _flowDiagramPath, _activityDiagramPath) ;

		this.version = _version ;
		this.date = _date ;
		this.authorFullName = _authorFullName ;
		this.authorMail = _authorMail ;
		this.generationOrder = _generationOrder ;
		this.responsabilityDiagramPaths = _responsabilityDiagramPaths ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the date.
	 */
	public String getDate ()
	{
		return this.date ;
	}

	/**
	 * Setter
	 * 
	 * @param _date
	 *            The date to set.
	 */
	public void setDate (String _date)
	{
		this.date = _date ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the version.
	 */
	public String getVersion ()
	{
		return this.version ;
	}

	/**
	 * Setter
	 * 
	 * @param _version
	 *            The version to set.
	 */
	public void setVersion (String _version)
	{
		this.version = _version ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the generationOrder.
	 */
	public String getGenerationOrder ()
	{
		return this.generationOrder ;
	}

	/**
	 * Setter
	 * 
	 * @param _generationOrder
	 *            The generationOrder to set.
	 */
	public void setGenerationOrder (String _generationOrder)
	{
		this.generationOrder = _generationOrder ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the responsabilityDiagramPaths.
	 */
	public Collection <String> getResponsabilityDiagramPaths ()
	{
		return this.responsabilityDiagramPaths ;
	}

	/**
	 * Setter
	 * 
	 * @param _responsabilityDiagramPaths
	 *            The responsabilityDiagramPaths to set.
	 */
	public void setResponsabilityDiagramPaths (Collection <String> _responsabilityDiagramPaths)
	{
		this.responsabilityDiagramPaths = _responsabilityDiagramPaths ;
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

}
