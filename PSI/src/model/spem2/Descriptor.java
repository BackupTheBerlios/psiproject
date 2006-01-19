
package model.spem2 ;

/**
 * Descriptor : a common descriptor which can describe an element and can be synchronized with it.
 * In the SPEM 2.0 metamodel, a Descriptor is represented by a class. For example let's say that we
 * need to produce Use case diagram in Phase A and update it in phase B. For this purpose you create
 * two Descriptors (Work Product Descriptors) for one Definition (Work Product Definition). The
 * Descriptors can be "Complete UC" and "First version UC" and the Definition "UC Diagram".
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public interface Descriptor extends BreakdownElement
{

	/**
	 * Gets the isSynchronizedWithSource property of the Descriptor
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @return the isSynchronizedWithSource property
	 */
	public boolean isSynchronizedWithSource () ;

	/**
	 * Sets the isSynchronizedWithSource property associated to the Descriptor
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @param _isSynchronizedWithSource
	 */
	public void setSynchronizedWithSource (boolean _isSynchronizedWithSource) ;

	/**
	 * Returns the identifier of the current element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @return
	 */
	public String getId () ;

	/**
	 * Sets the identifier with a new value
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @param _id
	 */
	public void setId (String _id) ;

	/**
	 * Gets the name of the current element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @return
	 */
	public String getName () ;

	/**
	 * Sets the name with a new value
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @param _name
	 */
	public void setName (String _name) ;

	/**
	 * Gets the description of the current element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @return
	 */
	public String getDescription () ;

	/**
	 * Sets the description with a new value
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @param _description
	 */
	public void setDescription (String _description) ;
	
	/**
	 * Gets the parent ID of the current element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @return
	 */
	public String getParentId () ;

	/**
	 * Sets the parent ID with a new value
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @param _description
	 */
	public void setParentId (String _parentId) ;

}
