
package ui.tree ;

import javax.swing.tree.DefaultMutableTreeNode ;

import ui.resource.Bundle ;

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
		this.setUserObject(process.getName()) ;

		/*
		 * Adding roles to the tree
		 */
		this.add(new DefaultMutableTreeNode(Bundle.getText("MainFrameTreeRoles"))) ;

		/*
		 * Adding activities to the tree
		 */
		this.add(new DefaultMutableTreeNode(Bundle.getText("MainFrameTreeActivities"))) ;
	}

}
