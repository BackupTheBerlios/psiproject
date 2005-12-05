
package model.spem2 ;

/**
 * MethodContentElement : these are Content Elements that represent method content package.
 * 
 * @author Cond� Micka�l K.
 * @version 1.0
 * 
 */
public interface MethodContentElement extends ContentElement
{
	/**
	 * Returns the current identifier
	 * 
	 * @author Cond� Micka�l K.
	 * @version 1.0
	 * 
	 * @return
	 */
	public String getId () ;

	/**
	 * Sets a new identifier to the element
	 * 
	 * @author Cond� Micka�l K.
	 * @version 1.0
	 * 
	 * @param _id
	 */
	public void setId (String _id) ;

	/**
	 * Returns the current name
	 * 
	 * @author Cond� Micka�l K.
	 * @version 1.0
	 * 
	 * @return
	 */
	public String getName () ;

	/**
	 * Sets a new name to the element
	 * 
	 * @author Cond� Micka�l K.
	 * @version 1.0
	 * 
	 * @param _name
	 */
	public void setName (String _name) ;

}
