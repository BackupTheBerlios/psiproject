
package ui.tree ;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.tree.DefaultMutableTreeNode;

import model.spem2.Artifact;
import model.spem2.WorkProductDescriptor;

/**
 * WorkProductDescriptorTreeNode : a tree representation of a product
 * 
 * @author Conde Mickael K.
 * @version 1.0
 * 
 */
public class WorkProductDescriptorTreeNode extends DefaultMutableTreeNode implements Observer
{
	private static final long serialVersionUID = 2218171819807912138L ;

	private WorkProductDescriptor product = null ;

	/**
	 * Constructor
	 * 
	 * @param _product
	 */
	public WorkProductDescriptorTreeNode (WorkProductDescriptor _product)
	{
		super() ;

		this.product = _product ;
		this.setUserObject(product.getName()) ;
		this.product.addObserver(this) ;

		// Adding artifacts to this node
		Iterator <Artifact> localIterator = product.getArtifacts().iterator() ;
		while (localIterator.hasNext())
		{
			this.add(new ArtifactTreeNode(localIterator.next())) ;
		}

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
	 * @param _product
	 *            The product to set.
	 */
	public void setProduct (WorkProductDescriptor _product)
	{
		this.product = _product ;
	}

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update (Observable _observable, Object _object)
	{
		if (_object instanceof Artifact)
		{
			if (product.getArtifacts().contains(_object))
			{
				add(new ArtifactTreeNode((Artifact) _object)) ;
			}
		}
	}

}
