package model.spem2;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Artifact : a definition of a product within a project
 *
 * @author Conde Mickael K.
 * @version 1.0
 *
 */
public class Artifact extends WorkProductDescriptor implements Transferable
{
	/**
	 * 
	 */
	private WorkProductDescriptor product ;
	
	/**
	 * Drag and drop capability
	 */
	public static DataFlavor ARTIFACT_FLAVOR = new DataFlavor(RoleDescriptor.class, "Artifact") ;;
	
	private static DataFlavor[] flavors = {ARTIFACT_FLAVOR} ;
	
	/**
	 * Constructor
	 *
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _parentId
	 * @param _product
	 */
	public Artifact (String _id, String _name, String _description, String _parentId, WorkProductDescriptor _product)
	{
		super(_id, _name, _description, _parentId) ;

		this.product = _product ;
	}

	/**
	 * Constructor
	 *
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _parentId
	 */
	public Artifact (String _id, String _name, String _description, String _parentId)
	{
		super(_id, _name, _description, _parentId) ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the product.
	 */
	public WorkProductDescriptor getProduct ()
	{
		return this.product ;
	}

	/**
	 * Setter
	 *
	 * @param _product The product to set.
	 */
	public void setProduct (WorkProductDescriptor _product)
	{
		this.product = _product ;
	}

	/**
	 * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
	 */
	public Object getTransferData (DataFlavor _df) throws UnsupportedFlavorException, IOException
	{
		if (_df.equals(ARTIFACT_FLAVOR))
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
		return _df.equals(ARTIFACT_FLAVOR) ;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@ Override
	public String toString ()
	{
		return getName() ;
	}
}
