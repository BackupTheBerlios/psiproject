package model;

import java.util.ArrayList ;
import java.util.Collection;

import model.spem2.Descriptor;

/**
 * Presentation : A presentation element
 *
 * @author Conde Mickael K.
 * @version 1.0
 *
 */
public class Presentation implements Descriptor
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
	 * 
	 */
	private String iconPath ;
	
	/**
	 * 
	 */
	private String contentPath ;
	
	/**
	 * 
	 */
	private String pagePath ;
	
	/**
	 * 
	 */
	private Collection<Guide> guides ;

	/**
	 * Constructor
	 *
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _iconPath
	 * @param _contentPath
	 * @param _pagePath
	 */
	public Presentation (String _id, String _name, String _description, String _iconPath, String _contentPath, String _pagePath)
	{
		super() ;

		this.id = _id ;
		this.name = _name ;
		this.description = _description ;
		this.iconPath = _iconPath ;
		this.contentPath = _contentPath ;
		this.pagePath = _pagePath ;
		
		guides = new ArrayList <Guide>() ;
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

	/**
	 * Getter
	 *
	 * @return Returns the contentPath.
	 */
	public String getContentPath ()
	{
		return this.contentPath ;
	}

	/**
	 * Setter
	 *
	 * @param _contentPath The contentPath to set.
	 */
	public void setContentPath (String _contentPath)
	{
		this.contentPath = _contentPath ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the guides.
	 */
	public Collection <Guide> getGuides ()
	{
		return this.guides ;
	}

	/**
	 * Setter
	 *
	 * @param _guides The guides to set.
	 */
	public void setGuides (Collection <Guide> _guides)
	{
		this.guides = _guides ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the iconPath.
	 */
	public String getIconPath ()
	{
		return this.iconPath ;
	}

	/**
	 * Setter
	 *
	 * @param _iconPath The iconPath to set.
	 */
	public void setIconPath (String _iconPath)
	{
		this.iconPath = _iconPath ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the pagePath.
	 */
	public String getPagePath ()
	{
		return this.pagePath ;
	}

	/**
	 * Setter
	 *
	 * @param _pagePath The pagePath to set.
	 */
	public void setPagePath (String _pagePath)
	{
		this.pagePath = _pagePath ;
	}
	
	
}
