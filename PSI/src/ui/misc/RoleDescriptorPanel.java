package ui.misc;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import model.spem2.RoleDescriptor;

/**
 * RoleDescriptorPanel : RoleDescriptor panel view
 *
 * @author Conde Mickael K.
 * @version 1.0
 *
 */
public class RoleDescriptorPanel extends JPanel
{
	private static final long serialVersionUID = 7889691041843745114L ;
	
	private RoleDescriptor roleDescriptor ;

	/**
	 * Constructor
	 *
	 * @param _descriptor
	 */
	public RoleDescriptorPanel (RoleDescriptor _descriptor)
	{
		this.roleDescriptor = _descriptor ;
		
		initialize() ;
		
	}
	
	/**
	 * Panel initialisation
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	private void initialize()
	{
		this.setLayout(new BorderLayout()) ;
		
		
	}

	/**
	 * Getter
	 *
	 * @return Returns the roleDescriptor.
	 */
	public RoleDescriptor getRoleDescriptor ()
	{
		return this.roleDescriptor ;
	}

	/**
	 * Setter
	 *
	 * @param _roleDescriptor The roleDescriptor to set.
	 */
	public void setRoleDescriptor (RoleDescriptor _roleDescriptor)
	{
		this.roleDescriptor = _roleDescriptor ;
	}
	
	

}
