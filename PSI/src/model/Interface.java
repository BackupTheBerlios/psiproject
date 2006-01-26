package model;

import java.util.ArrayList;
import java.util.Collection;

import model.spem2.Descriptor;
import model.spem2.WorkProductDescriptor;

/**
 * Interface : the interface type related to products
 *
 * @author Conde Mickael K.
 * @version 1.0
 *
 */
public class Interface implements Descriptor
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
	 * The required component
	 */
	private Component requiredForComponent ;
	
	/**
	 * The given component
	 */
	private Component givenForComponent ;
	
	/**
	 * The products associated
	 */
	private Collection <WorkProductDescriptor> products ;

	/**
	 * Constructor
	 *
	 * @param _id
	 * @param _name
	 */
	public Interface (String _id, String _name)
	{
		super() ;
		
		this.id = _id ;
		this.name = _name ;
		
		products = new ArrayList <WorkProductDescriptor>() ;
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
	 * @return Returns the products.
	 */
	public Collection <WorkProductDescriptor> getProducts ()
	{
		return this.products ;
	}

	/**
	 * Setter
	 *
	 * @param _products The products to set.
	 */
	public void setProducts (Collection <WorkProductDescriptor> _products)
	{
		this.products = _products ;
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
	 * @return Returns the givenComponent.
	 */
	public Component getGivenForComponent ()
	{
		return this.givenForComponent ;
	}

	
	/**
	 * Setter
	 *
	 * @param _givenComponent The givenComponent to set.
	 */
	public void setGivenForComponent (Component _givenComponent)
	{
		this.givenForComponent = _givenComponent ;
	}

	
	/**
	 * Getter
	 *
	 * @return Returns the requiredComponent.
	 */
	public Component getRequiredForComponent ()
	{
		return this.requiredForComponent ;
	}

	
	/**
	 * Setter
	 *
	 * @param _requiredComponent The requiredComponent to set.
	 */
	public void setRequiredForComponent (Component _requiredComponent)
	{
		this.requiredForComponent = _requiredComponent ;
	}
}
