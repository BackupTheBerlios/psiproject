
package ui.tree ;

import java.util.Collection ;
import java.util.Iterator ;

import javax.swing.tree.DefaultMutableTreeNode ;

import model.Component ;
import model.spem2.BreakdownElement ;
import model.spem2.DeliveryProcess ;

/**
 * ProcessTreeNode : tree like representation of a Delivery Process
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class ProcessTreeNode extends DefaultMutableTreeNode
{
	private static final long serialVersionUID = -7393283936901894640L ;

	private DeliveryProcess process = null ;

	/**
	 * Constructor
	 * 
	 * @param _process
	 */
	public ProcessTreeNode (DeliveryProcess _process)
	{
		super() ;

		this.process = _process ;
		this.setUserObject(process.getDescriptor().getName()) ;

		/*
		 * Adding elements to the tree
		 */
		Collection <BreakdownElement> localNested = process.getNestedElements() ;

		BreakdownElement localElement ;
		Iterator <BreakdownElement> localIterator = localNested.iterator() ;

		while (localIterator.hasNext())
		{
			localElement = localIterator.next() ;

			if (localElement instanceof Component)
			{
				this.add(new ComponentTreeNode((Component) localElement)) ;
			}
		}
	}

	
	/**
	 * Getter
	 * 
	 * @return Returns the process.
	 */
	public DeliveryProcess getProcess ()
	{
		return this.process ;
	}

	
	/**
	 * Setter
	 * 
	 * @param _process
	 *            The process to set.
	 */
	public void setProcess (DeliveryProcess _process)
	{
		this.process = _process ;
	}
}
