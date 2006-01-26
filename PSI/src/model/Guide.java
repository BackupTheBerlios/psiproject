package model;

import model.spem2.Descriptor;

/**
 * Guide : A guide to something
 *
 * @author Conde Mickael K.
 * @version 1.0
 *
 */
public class Guide implements Descriptor
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
	 * The type of the element
	 */
	private GuideType type ;
	
	/**
	 * 
	 */
	private Presentation presentationElement ;

	/**
	 * Constructor
	 *
	 * @param _id
	 * @param _name
	 */
	public Guide (String _id, String _name)
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
		return "" ;
	}

	/**
	 * @see model.spem2.Descriptor#setDescription(java.lang.String)
	 */
	public void setDescription (String _description)
	{		
		
	}

	/**
	 * Getter
	 *
	 * @return Returns the type.
	 */
	public GuideType getType ()
	{
		return this.type ;
	}

	/**
	 * Setter
	 *
	 * @param _type The type to set.
	 */
	public void setType (GuideType _type)
	{
		this.type = _type ;
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
