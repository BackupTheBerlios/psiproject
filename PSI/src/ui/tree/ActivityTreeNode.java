
package ui.tree ;

import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.tree.DefaultMutableTreeNode;

import model.spem2.Activity;
import model.spem2.BreakdownElement;
import model.spem2.TaskDescriptor;

/**
 * ActivityTreeNode : A tree representation of an activity (IEPP's work definition)
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class ActivityTreeNode extends DefaultMutableTreeNode implements Observer
{
	private static final long serialVersionUID = -6783865116938275134L ;

	private Activity activity = null ;
	
	private MainTree tree = null ;

	/**
	 * Constructor
	 * 
	 * @param _activity
	 */
	public ActivityTreeNode (Activity _activity, MainTree _tree)
	{
		super() ;

		this.tree = _tree ;
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
				this.add(new TaskDescriptorTreeNode((TaskDescriptor) localElement, tree)) ;
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

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update (Observable _arg0, Object _arg1)
	{		
		
	}

}
