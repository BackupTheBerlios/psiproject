
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
}
