package model;

import model.spem2.Descriptor;

/**
 * State : TODO type description
 *
 * @author Administrateur
 * @version 1.0
 *
 */
public class State implements Descriptor
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
	 * Constructor
	 *
	 * @param _id
	 * @param _name
	 */
	public State (String _id, String _name)
	{
		super() ;

		this.id = _id ;
		this.name = _name ;
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

	/**
	 * @see model.spem2.Descriptor#getDescription()
	 */
	public String getDescription ()
	{
		return null ;
	}

	/**
	 * @see model.spem2.Descriptor#setDescription(java.lang.String)
	 */
	public void setDescription (String _description)
	{
		
	}

}
