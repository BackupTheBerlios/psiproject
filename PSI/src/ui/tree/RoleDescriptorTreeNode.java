
package ui.tree ;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;

import model.spem2.RoleDescriptor;

/**
 * RoleDescriptorTreeNode : A tree representation of a role
 * 
 * @author Cond? Micka?l K.
 * @version 1.0
 * 
 */
public class RoleDescriptorTreeNode extends DefaultMutableTreeNode implements ActionListener
{
	private static final long serialVersionUID = 2312503011598809409L ;

	/**
	 * The role of the node
	 */
	private RoleDescriptor role ;
	
	@SuppressWarnings("unused")
	private MainTree tree = null ;

	/**
	 * Constructor
	 * 
	 * @param _role
	 */
	public RoleDescriptorTreeNode (RoleDescriptor _role, MainTree _tree)
	{
		super() ;

		this.tree = _tree ;
		this.role = _role ;
		this.setUserObject(role) ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the role.
	 */
	public RoleDescriptor getRole ()
	{
		return this.role ;
	}

	/**
	 * Setter
	 * 
	 * @param _role
	 *            The role to set.
	 */
	public void setRole (RoleDescriptor _role)
	{
		this.role = _role ;
	}
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed (ActionEvent _e)
	{
		// TODO
		
	}
	
	class ResourceControlHandler extends TransferHandler
	{
		private static final long serialVersionUID = -2535767058105111621L ;

		/**
		 * @see javax.swing.TransferHandler#canImport(javax.swing.JComponent, java.awt.datatransfer.DataFlavor[])
		 */
		@ Override
		public boolean canImport (JComponent _comp, DataFlavor[] _transferFlavors)
		{
			// TODO Auto-generated method stub
			return super.canImport(_comp, _transferFlavors);
		}

		/**
		 * @see javax.swing.TransferHandler#createTransferable(javax.swing.JComponent)
		 */
		@ Override
		protected Transferable createTransferable (JComponent _c)
		{
			// TODO Auto-generated method stub
			return super.createTransferable(_c);
		}

		/**
		 * @see javax.swing.TransferHandler#exportDone(javax.swing.JComponent, java.awt.datatransfer.Transferable, int)
		 */
		@ Override
		protected void exportDone (JComponent _source, Transferable _data, int _action)
		{
			// TODO Auto-generated method stub
			super.exportDone(_source, _data, _action);
		}
		
	}
}
