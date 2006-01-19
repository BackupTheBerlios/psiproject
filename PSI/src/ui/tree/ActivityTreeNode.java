
package ui.tree ;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.tree.DefaultMutableTreeNode ;

import model.spem2.Activity ;
import model.spem2.BreakdownElement;
import model.spem2.TaskDescriptor;

/**
 * ActivityTreeNode : A tree representation of an activity (IEPP's work definition)
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class ActivityTreeNode extends DefaultMutableTreeNode
{
	private static final long serialVersionUID = -6783865116938275134L ;

	private Activity activity = null ;

	/**
	 * Constructor
	 * 
	 * @param _activity
	 */
	public ActivityTreeNode (Activity _activity)
	{
		super() ;

		this.activity = _activity ;
		this.setUserObject(activity.getDescriptor().getName()) ;
		
		// Displaying associated tasks
		Collection <BreakdownElement> localNested = activity.getNestedElements() ;
		BreakdownElement localElement ;
		Iterator <BreakdownElement> localIterator = localNested.iterator() ;

		while (localIterator.hasNext())
		{
			localElement = localIterator.next() ;

			if (localElement instanceof TaskDescriptor)
			{
				this.add(new TaskDescriptorTreeNode((TaskDescriptor) localElement)) ;
			}
		}
	}

	/**
	 * Getter
	 * 
	 * @return Returns the activity.
	 */
	public Activity getActivity ()
	{
		return this.activity ;
	}

	/**
	 * Setter
	 * 
	 * @param _activity
	 *            The activity to set.
	 */
	public void setActivity (Activity _activity)
	{
		this.activity = _activity ;
	}

}
