
package model.spem2 ;

/**
 * WorkBreakdownElement : special breakdown element which references work definitions
 * 
 * @author Condé Mickael K.
 * @version 1.0
 * 
 */
public interface WorkBreakdownElement extends BreakdownElement
{

	/**
	 * Gets the isRepeatable property of the Work Breakdown Element
	 * 
	 * @author Condé Mickael K.
	 * @version 1.0
	 * 
	 * @return isRepeatable property
	 */
	public boolean isRepeatable () ;

	/**
	 * Sets the isRepeatable property of the Work Breakdown Element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @param _isRepeatable
	 */
	public void setRepeatable (boolean _isRepeatable) ;

	/**
	 * Gets the isOngoing property of the Work Breakdown Element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @return isOngoing property
	 */
	public boolean isOngoing () ;

	/**
	 * Sets the isOngoing property of the Work Breakdown Element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @param _isOngoing
	 */
	public void setOngoing (boolean _isOngoing) ;

	/**
	 * Gets the isEventDriven property of the Work Breakdown Element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @return isEventDriven property
	 */
	public boolean isEventDriven () ;

	/**
	 * Sets the isEventDriven property of the Work Breakdown Element
	 * 
	 * @author Administrateur
	 * @version 1.0
	 * 
	 * @param _isEventDriven
	 */
	public void setEventDriven (boolean _isEventDriven) ;

	
}
