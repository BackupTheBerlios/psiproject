
package model.spem2 ;

import java.util.Observable;

/**
 * ActivityDescriptor : textual definition of an activity
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class ActivityDescriptor extends Observable implements Descriptor
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
	 * The parent ID of the element : Component
	 * 
	 * @see Component
	 */
	private String parentId ;	

	/**
	 * Constructor
	 * 
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _parentId
	 */
	public ActivityDescriptor (String _id, String _name, String _description, String _parentId)
	{
		super() ;

		this.id = _id ;
		this.name = _name ;
		this.description = _description ;
		this.parentId = _parentId ;
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
}
