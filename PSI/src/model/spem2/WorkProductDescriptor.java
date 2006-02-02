
package model.spem2 ;

import java.util.ArrayList ;
import java.util.Collection ;
import java.util.Observable;

import model.Interface ;
import model.Presentation ;

/**
 * WorkProductDescriptor : a product within a project
 * 
 * @author Conde Mickael K.
 * @version 1.0
 * 
 */
public class WorkProductDescriptor extends Observable implements Descriptor
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
	 * 
	 */
	private Collection <Interface> interfaces ;
	
	/**
	 * 
	 */
	private ProductType productType ;

	/**
	 * The main tasks of the role
	 */
	private Collection <TaskDescriptor> usingTasks ;

	/**
	 * The additional tasks of the role
	 */
	private Collection <TaskDescriptor> producingTasks ;
	
	/**
	 * 
	 */
	private Collection <Artifact> artifacts ;

	/**
	 * The assisting tasks of the role
	 */
	private RoleDescriptor responsible = null ;

	/**
	 * 
	 */
	private Presentation presentation = null ;

	/**
	 * Constructor
	 * 
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _parentId
	 */
	public WorkProductDescriptor (String _id, String _name, String _description, String _parentId)
	{
		super() ;

		this.id = _id ;
		this.name = _name ;
		this.description = _description ;
		this.parentId = _parentId ;

		this.usingTasks = new ArrayList <TaskDescriptor>() ;
		this.producingTasks = new ArrayList <TaskDescriptor>() ;
		this.interfaces = new ArrayList <Interface>() ;
		this.artifacts = new ArrayList <Artifact>() ;
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
	 * @return Returns the producingTasks.
	 */
	public Collection <TaskDescriptor> getProducingTasks ()
	{
		return this.producingTasks ;
	}

	/**
	 * Setter
	 * 
	 * @param _producingTasks
	 *            The producingTasks to set.
	 */
	public void setProducingTasks (Collection <TaskDescriptor> _producingTasks)
	{
		this.producingTasks = _producingTasks ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the responsible.
	 */
	public RoleDescriptor getResponsible ()
	{
		return this.responsible ;
	}

	/**
	 * Setter
	 * 
	 * @param _responsible
	 *            The responsible to set.
	 */
	public void setResponsible (RoleDescriptor _responsible)
	{
		this.responsible = _responsible ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the usingTasks.
	 */
	public Collection <TaskDescriptor> getUsingTasks ()
	{
		return this.usingTasks ;
	}

	/**
	 * Setter
	 * 
	 * @param _usingTasks
	 *            The usingTasks to set.
	 */
	public void setUsingTasks (Collection <TaskDescriptor> _usingTasks)
	{
		this.usingTasks = _usingTasks ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the presentation.
	 */
	public Presentation getPresentation ()
	{
		return this.presentation ;
	}

	/**
	 * Setter
	 * 
	 * @param _presentation
	 *            The presentation to set.
	 */
	public void setPresentation (Presentation _presentation)
	{
		this.presentation = _presentation ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the interfaces.
	 */
	public Collection <Interface> getInterfaces ()
	{
		return this.interfaces ;
	}

	/**
	 * Setter
	 * 
	 * @param _interfaces
	 *            The interfaces to set.
	 */
	public void setInterfaces (Collection <Interface> _interfaces)
	{
		this.interfaces = _interfaces ;
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
	 * Getter
	 *
	 * @return Returns the productType.
	 */
	public ProductType getProductType ()
	{
		return this.productType ;
	}

	/**
	 * Setter
	 *
	 * @param _productType The productType to set.
	 */
	public void setProductType (ProductType _productType)
	{
		this.productType = _productType ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the artifacts.
	 */
	public Collection <Artifact> getArtifacts ()
	{
		return this.artifacts ;
	}

	/**
	 * Setter
	 *
	 * @param _artifacts The artifacts to set.
	 */
	public void setArtifacts (Collection <Artifact> _artifacts)
	{
		this.artifacts = _artifacts ;
	}

	
	/**
	 * @see java.util.Observable#setChanged()
	 */
	@ Override
	public synchronized void setChanged ()
	{
		super.setChanged() ;
	}

}
