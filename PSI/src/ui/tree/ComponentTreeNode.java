
package ui.tree ;

import java.util.Collection ;
import java.util.Iterator ;

import javax.swing.tree.DefaultMutableTreeNode ;
import javax.swing.tree.DefaultTreeModel;

import ui.resource.Bundle ;

import model.Component ;
import model.spem2.Activity;
import model.spem2.BreakdownElement ;
import model.spem2.RoleDescriptor ;
import model.spem2.WorkProductDescriptor;

/**
 * ComponentTreeNode : A tree representation of a component
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class ComponentTreeNode extends DefaultMutableTreeNode
{

	private static final long serialVersionUID = 4453822314602802032L ;

	private Component component ;
	
	private DefaultTreeModel treeModel = null ;

	
	/**
	 * Constructor
	 *
	 * @param _component
	 * @param _model
	 */
	public ComponentTreeNode (Component _component, DefaultTreeModel _model)
	{
		super() ;

		this.treeModel = _model ;
		this.component = _component ;
		this.setUserObject(component.getDescriptor().getName()) ;

		DefaultMutableTreeNode localWDNode = new DefaultMutableTreeNode(Bundle.getText("MainTreeNodeWorkDefinitions")) ;
		DefaultMutableTreeNode localRoleNode = new DefaultMutableTreeNode(Bundle.getText("MainTreeNodeRoles")) ;
		DefaultMutableTreeNode localProductsNode = new DefaultMutableTreeNode(Bundle.getText("MainTreeNodeWorkProducts")) ;
		
		Collection <BreakdownElement> localNested = component.getNestedElements() ;
		BreakdownElement localElement ;
		Iterator <BreakdownElement> localIterator = localNested.iterator() ;

		while (localIterator.hasNext())
		{
			localElement = localIterator.next() ;

			if (localElement instanceof RoleDescriptor)
			{
				localRoleNode.add(new RoleDescriptorTreeNode((RoleDescriptor) localElement, treeModel)) ;
			}
			
			else if (localElement instanceof Activity)
			{
				localWDNode.add(new ActivityTreeNode((Activity) localElement, treeModel)) ;
			}
			
			else if (localElement instanceof WorkProductDescriptor)
			{
				localProductsNode.add(new WorkProductDescriptorTreeNode((WorkProductDescriptor) localElement, treeModel)) ;
			}
		}

		this.add(localWDNode) ;
		this.add(localRoleNode) ;
		this.add(localProductsNode) ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the component.
	 */
	public Component getComponent ()
	{
		return this.component ;
	}

	/**
	 * Setter
	 * 
	 * @param _component
	 *            The component to set.
	 */
	public void setComponent (Component _component)
	{
		this.component = _component ;
	}

}
