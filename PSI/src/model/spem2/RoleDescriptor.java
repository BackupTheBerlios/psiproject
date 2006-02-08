
package model.spem2 ;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList ;
import java.util.Collection ;
import java.util.Observable;

import model.HumanResource ;
import model.Presentation;

/**
 * RoleDescriptor : a role within a project
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class RoleDescriptor extends Observable implements Descriptor, Transferable
{
	/**
	 * The unique identifier of the element
	 */
	private String id ;

	/**
	 * The name of the element
	 */
	private String name ;

	/**
	 * The description of the element
	 */
	private String description ;

	/**
	 * The parent identifier : Component
	 * 
	 * @see Component
	 */
	private String parentId ;
	
	/**
	 * 
	 */
	private Presentation presentationElement ;

	/**
	 * The actual performers
	 */
	private Collection <HumanResource> performers ;

	/**
	 * The main tasks of the role
	 */
	private Collection <TaskDescriptor> primaryTasks ;

	/**
	 * The additional tasks of the role
	 */
	private Collection <WorkProductDescriptor> products ;
	
	/**
	 * Drag and drop capability
	 */
	public static DataFlavor ROLE_FLAVOR = new DataFlavor(RoleDescriptor.class, "Role") ;;
	
	private static DataFlavor[] flavors = {ROLE_FLAVOR} ;

	/**
	 * Constructor
	 * 
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _parentId
	 */
	public RoleDescriptor (String _id, String _name, String _description, String _parentId)
	{
		super() ;

		this.id = _id ;
		this.name = _name ;
		this.description = _description ;
		this.parentId = _parentId ;

		primaryTasks = new ArrayList <TaskDescriptor>() ;
		products = new ArrayList <WorkProductDescriptor>() ;
		performers = new ArrayList <HumanResource>() ;
	}
	
	/**
	 * Getter
	 * 
	 * @return Returns the description.
	 */
	public String getDescription ()
	{
		return this.description ;
	}

	/**
	 * Setter
	 * 
	 * @param _description
	 *            The description to set.
	 */
	public void setDescription (String _description)
	{
		this.description = _description ;
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
	 * @return Returns the name.
	 */
	public String getName ()
	{
		return this.name ;
	}

	/**
	 * Setter
	 * 
	 * @param _name
	 *            The name to set.
	 */
	public void setName (String _name)
	{
		this.name = _name ;
	}

	
	/**
	 * Getter
	 *
	 * @return Returns the products.
	 */
	public Collection <WorkProductDescriptor> getProducts ()
	{
		return this.products ;
	}

	/**
	 * Setter
	 *
	 * @param _products The products to set.
	 */
	public void setProducts (Collection <WorkProductDescriptor> _products)
	{
		this.products = _products ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the primaryTasks.
	 */
	public Collection <TaskDescriptor> getPrimaryTasks ()
	{
		return this.primaryTasks ;
	}

	/**
	 * Setter
	 * 
	 * @param _primaryTasks
	 *            The primaryTasks to set.
	 */
	public void setPrimaryTasks (Collection <TaskDescriptor> _primaryTasks)
	{
		this.primaryTasks = _primaryTasks ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the parentId.
	 */
	public String getParentId ()
	{
		return this.parentId ;
	}

	/**
	 * Setter
	 * 
	 * @param _parentId
	 *            The parentId to set.
	 */
	public void setParentId (String _parentId)
	{
		this.parentId = _parentId ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the performers.
	 */
	public Collection <HumanResource> getPerformers ()
	{
		return this.performers ;
	}

	/**
	 * Setter
	 *
	 * @param _performers The performers to set.
	 */
	public void setPerformers (Collection <HumanResource> _performers)
	{
		this.performers = _performers ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the presentationElement.
	 */
	public Presentation getPresentationElement ()
	{
		return this.presentationElement ;
	}

	/**
	 * Setter
	 *
	 * @param _presentationElement The presentationElement to set.
	 */
	public void setPresentationElement (Presentation _presentationElement)
	{
		this.presentationElement = _presentationElement ;
	}

	
	/**
	 * @see java.util.Observable#setChanged()
	 */
	@ Override
	public synchronized void setChanged ()
	{
		super.setChanged() ;
	}

	
	/**
	 * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
	 */
	public Object getTransferData (DataFlavor _df) throws UnsupportedFlavorException, IOException
	{
		if (_df.equals(ROLE_FLAVOR))
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
		return _df.equals(ROLE_FLAVOR) ;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@ Override
	public String toString ()
	{
		return name ;
	}

	
	
}
