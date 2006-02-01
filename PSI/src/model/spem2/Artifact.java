package model.spem2;

/**
 * Artifact : a definition of a product within a project
 *
 * @author Conde Mickael K.
 * @version 1.0
 *
 */
public class Artifact extends WorkProductDescriptor
{
	/**
	 * 
	 */
	private WorkProductDescriptor product ;
	
	/**
	 * Constructor
	 *
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _parentId
	 * @param _product
	 */
	public Artifact (String _id, String _name, String _description, String _parentId, WorkProductDescriptor _product)
	{
		super(_id, _name, _description, _parentId) ;

		this.product = _product ;
	}

	/**
	 * Constructor
	 *
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _parentId
	 */
	public Artifact (String _id, String _name, String _description, String _parentId)
	{
		super(_id, _name, _description, _parentId) ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the product.
	 */
	public WorkProductDescriptor getProduct ()
	{
		return this.product ;
	}

	/**
	 * Setter
	 *
	 * @param _product The product to set.
	 */
	public void setProduct (WorkProductDescriptor _product)
	{
		this.product = _product ;
	}

}
