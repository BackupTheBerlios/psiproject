
package ui.tree ;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.tree.DefaultMutableTreeNode;

import model.Component;
import model.spem2.Activity;
import model.spem2.BreakdownElement;
import model.spem2.RoleDescriptor;
import model.spem2.WorkProductDescriptor;
import ui.resource.Bundle;
import ui.resource.LocaleController;
import ui.resource.LocaleListener;

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
	
	private MainTree tree = null ;
	
	/**
	 * The locale controller for the language.
	 */
	private LocaleController controllerLocale = null;
	
	private DefaultMutableTreeNode WDNode = null ;
	
	private DefaultMutableTreeNode ProductsNode = null ; 
	
	DefaultMutableTreeNode RoleNode = null ;

	
	/**
	 * Constructor
	 *
	 * @param _component
	 * @param _model
	 */
	public ComponentTreeNode (Component _component, MainTree _tree)
	{
		super() ;
		
		this.controllerLocale = LocaleController.getInstance();
	    this.controllerLocale.addLocaleListener(new LocaleListener(){
	    	public void localeChanged() {
	    		updateText();
	        }
	    });

		this.tree = _tree ;
		this.component = _component ;
		this.setUserObject(component.getDescriptor().getName()) ;

		WDNode = new DefaultMutableTreeNode(Bundle.getText("MainTreeNodeWorkDefinitions")) ;
		RoleNode = new DefaultMutableTreeNode(Bundle.getText("MainTreeNodeRoles")) ;
		ProductsNode = new DefaultMutableTreeNode(Bundle.getText("MainTreeNodeWorkProducts")) ;
		
		Collection <BreakdownElement> localNested = component.getNestedElements() ;
		BreakdownElement localElement ;
		Iterator <BreakdownElement> localIterator = localNested.iterator() ;

		while (localIterator.hasNext())
		{
			localElement = localIterator.next() ;

			if (localElement instanceof RoleDescriptor)
			{
				RoleNode.add(new RoleDescriptorTreeNode((RoleDescriptor) localElement, tree)) ;
			}
			
			else if (localElement instanceof Activity)
			{
				WDNode.add(new ActivityTreeNode((Activity) localElement, tree)) ;
			}
			
			else if (localElement instanceof WorkProductDescriptor)
			{
				ProductsNode.add(new WorkProductDescriptorTreeNode((WorkProductDescriptor) localElement, tree)) ;
			}
		}

		this.add(WDNode) ;
		this.add(RoleNode) ;
		this.add(ProductsNode) ;
	}
	
	private void updateText()
	{
		WDNode.setUserObject(Bundle.getText("MainTreeNodeWorkDefinitions")) ;
		RoleNode.setUserObject(Bundle.getText("MainTreeNodeRoles")) ;
		ProductsNode.setUserObject(Bundle.getText("MainTreeNodeWorkProducts")) ;
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
