
package ui.tree ;

import javax.swing.tree.DefaultMutableTreeNode ;

/**
 * HelpTreeNode : TODO type description
 * 
 * @author Evi
 * @version 1.0
 * 
 */
public class HelpTreeNode extends DefaultMutableTreeNode
{
	private static final long serialVersionUID = 1L ;

	private int ID = 0 ;

	public HelpTreeNode (String _name, int _id)
	{
		super(_name) ;
		this.ID = _id ;
	}

	public int getID ()
	{
		return this.ID ;
	}

}
