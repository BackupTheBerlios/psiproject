
package model ;

import java.awt.datatransfer.DataFlavor ;
import java.awt.datatransfer.Transferable ;
import java.awt.datatransfer.UnsupportedFlavorException ;
import java.io.IOException ;
import java.util.ArrayList ;
import java.util.Collection ;

import model.spem2.RoleDescriptor ;
import model.spem2.TaskDescriptor;

/**
 * HumanResource : a member working on a project
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class HumanResource implements Transferable
{
	/**
	 * The member's unique ID during a project
	 */
	private String id ;

	/**
	 * The member's full name
	 */
	private String fullName ;

	/**
	 * The member's mail
	 */
	private String email ;

	/**
	 * Drag and drop capability
	 */
	public static DataFlavor RESOURCE_FLAVOR = new DataFlavor(HumanResource.class, "Human Resource") ;;
	
	private static DataFlavor[] flavors = {RESOURCE_FLAVOR} ;

	private Collection <RoleDescriptor> performingRoles ;
	
	private Collection <TaskDescriptor> performingTasks ;

	/**
	 * Constructor
	 * 
	 * @param _id
	 * @param _fullName
	 */
	public HumanResource (String _id, String _fullName)
	{
		super() ;

		this.id = _id ;
		this.fullName = _fullName ;

		performingRoles = new ArrayList <RoleDescriptor>() ;
		performingTasks = new ArrayList <TaskDescriptor>() ;
	}

	/**
	 * Constructor
	 * 
	 * @param _id
	 * @param _fullName
	 * @param _email
	 */
	public HumanResource (String _id, String _fullName, String _email)
	{
		this(_id, _fullName) ;
		this.email = _email ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the fullName.
	 */
	public String getFullName ()
	{
		return this.fullName ;
	}

	/**
	 * Setter
	 * 
	 * @param _firstName
	 *            The fullName to set.
	 */
	public void setFullName (String _fullName)
	{
		this.fullName = _fullName ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the id.
	 */
	public String getId ()
	{
		return this.id ;
	}

	/**
	 * Setter
	 * 
	 * @param _id
	 *            The id to set.
	 */
	public void setId (String _id)
	{
		this.id = _id ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the email.
	 */
	public String getEmail ()
	{
		return this.email ;
	}

	/**
	 * Setter
	 * 
	 * @param _email
	 *            The email to set.
	 */
	public void setEmail (String _email)
	{
		this.email = _email ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the performingRoles.
	 */
	public Collection <RoleDescriptor> getPerformingRoles ()
	{
		return this.performingRoles ;
	}

	/**
	 * Setter
	 * 
	 * @param _performingRoles
	 *            The performingRoles to set.
	 */
	public void setPerformingRoles (Collection <RoleDescriptor> _performingRoles)
	{
		this.performingRoles = _performingRoles ;
	}

	/**
	 * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
	 */
	public Object getTransferData (DataFlavor _df) throws UnsupportedFlavorException, IOException
	{
		if (_df.equals(RESOURCE_FLAVOR))
		{
			return this ;
		}
		else
		{
			throw new UnsupportedFlavorException(_df) ;
		}

	}

	/**
	 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
	 */
	public DataFlavor[] getTransferDataFlavors ()
	{
		return flavors ;
	}

	/**
	 * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
	 */
	public boolean isDataFlavorSupported (DataFlavor _df)
	{
		return _df.equals(RESOURCE_FLAVOR) ;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@ Override
	public String toString ()
	{
		return fullName ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the performingTasks.
	 */
	public Collection <TaskDescriptor> getPerformingTasks ()
	{
		return this.performingTasks ;
	}

	/**
	 * Setter
	 *
	 * @param _performingTasks The performingTasks to set.
	 */
	public void setPerformingTasks (Collection <TaskDescriptor> _performingTasks)
	{
		this.performingTasks = _performingTasks ;
	}
	
	

}
