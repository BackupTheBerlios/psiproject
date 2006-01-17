
package model.spem2 ;

/**
 * BreakdownElement : represents Process Element nested in a breakdown structure. For a description
 * of breakdown structure please refer to spem 2.0 reference manual.
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * @see ProcessElement
 * 
 */
public interface BreakdownElement extends ProcessElement
{
	/**
	 * Gets the default prefix that can be set by the project manager to id Breakdown Elements
	 * within a process
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @return the prefix of the current element
	 */
	public String getPrefix () ;

	/**
	 * Sets the default prefix that can be set by the project manager to id Breakdown Elements
	 * within a process
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @param _prefix
	 */
	public void setPrefix (String _prefix) ;

	/**
	 * Returns the isPlanned property of the Breakdown Element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @return isPlanned property
	 */
	public boolean isPlanned () ;

	/**
	 * Sets the isPlanned property of the Breakdown Element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @param _isPlanned
	 */
	public void setPlanned (boolean _isPlanned) ;

	/**
	 * Returns the hasMultipleOccurences property of the Breakdown Element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @return hasMultipleOccurences property
	 */
	public boolean hasMultipleOccurences () ;

	/**
	 * Sets the hasMultipleOccurences property of the Breakdown Element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @param _hasMultipleOccurences
	 */
	public void setMultipleOccurences (boolean _hasMultipleOccurences) ;

	/**
	 * Returns the isOptional property of the Breakdown Element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @return isOptional property
	 */
	public boolean isOptional () ;

	/**
	 * Sets the isOptional property of the Breakdown Element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @param _isOptional
	 */
	public void setOptional (boolean _isOptional) ;
	
	/**
	 * Gets the Planning Data associated to the Breakdown Element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @return the Planning Data associated to current element
	 */
	public PlanningData getPlanningData () ;

	/**
	 * Sets the Planning Data associated to the Breakdown Element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @param _planningData
	 */
	public void setPlanningData (PlanningData _planningData) ;
	
	/**
	 * Gets the real Planning Data associated to the Breakdown Element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @return the Planning Data associated to current element
	 */
	public PlanningData getRealData () ;

	/**
	 * Sets the real Planning Data associated to the Breakdown Element
	 * 
	 * @author Condé Mickaël K.
	 * @version 1.0
	 * 
	 * @param _planningData
	 */
	public void setRealData (PlanningData _realData) ;

}
