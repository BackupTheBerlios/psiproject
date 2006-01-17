
package ui.tree ;

import java.util.Collection ;
import java.util.Iterator;

import javax.swing.tree.DefaultMutableTreeNode ;

import ui.resource.Bundle ;

import model.spem2.BreakdownElement ;
import model.spem2.DeliveryProcess ;
import model.spem2.RoleDescriptor;
import model.spem2.TaskDescriptor ;

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
		DefaultMutableTreeNode localTaskNode = new DefaultMutableTreeNode(Bundle.getText("MainFrameTreeActivities")) ;
		DefaultMutableTreeNode localRoleNode = new DefaultMutableTreeNode(Bundle.getText("MainFrameTreeRoles")) ;
		Collection <BreakdownElement> localNested = process.getNestedElements() ;
		BreakdownElement localElement ;
		Iterator<BreakdownElement> localIterator = localNested.iterator() ;
		
		while (localIterator.hasNext())
		{
			localElement = localIterator.next() ;

			if (localElement instanceof TaskDescriptor)
			{
				localTaskNode.add(new TaskDescriptorTreeNode((TaskDescriptor)localElement)) ;
			}

			else if (localElement instanceof RoleDescriptor)
			{
				localRoleNode.add(new RoleDescriptorTreeNode((RoleDescriptor)localElement)) ;
			}
		}
		
		this.add(localTaskNode) ;
		this.add(localRoleNode) ;
	}

}
