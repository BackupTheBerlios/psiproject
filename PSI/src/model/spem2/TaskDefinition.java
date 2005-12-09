
package model.spem2 ;

import java.util.Collection ;

/**
 * TaskDefinition : defines a work being performed by roles
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class TaskDefinition implements MethodContentElement, WorkDefinition
{
	/**
	 * The IEPP identifier of the definition
	 */
	private String id ;

	/**
	 * The name of the definition
	 */
	private String name ;

	/**
	 * The description of the definition
	 */
	private String description ;
	
	/**
	 * Primary performers of one task
	 */
	private Collection <RoleDefinition> primaryPerformers ;

	/**
	 * Additional performers of one task
	 */
	private Collection <RoleDefinition> additionalPerformers ;

	/**
	 * Assisting persons for one task
	 */
	private Collection <RoleDefinition> assistants ;

	/**
	 * Getter
	 * 
	 * @return Returns the additionalPerformers.
	 */
	public Collection <RoleDefinition> getAdditionalPerformers ()
	{
		return this.additionalPerformers ;
	}

	/**
	 * Setter
	 * 
	 * @param _additionalPerformers
	 *            The additionalPerformers to set.
	 */
	public void setAdditionalPerformers (Collection <RoleDefinition> _additionalPerformers)
	{
		this.additionalPerformers = _additionalPerformers ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the assistants.
	 */
	public Collection <RoleDefinition> getAssistants ()
	{
		return this.assistants ;
	}

	/**
	 * Setter
	 * 
	 * @param _assistants
	 *            The assistants to set.
	 */
	public void setAssistants (Collection <RoleDefinition> _assistants)
	{
		this.assistants = _assistants ;
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
	 * @return Returns the primaryPerformers.
	 */
	public Collection <RoleDefinition> getPrimaryPerformers ()
	{
		return this.primaryPerformers ;
	}

	/**
	 * Setter
	 * 
	 * @param _primaryPerformers
	 *            The primaryPerformers to set.
	 */
	public void setPrimaryPerformers (Collection <RoleDefinition> _primaryPerformers)
	{
		this.primaryPerformers = _primaryPerformers ;
	}

	
	/**
	 * @see model.spem2.WorkDefinition#getPerformingRoles()
	 */
	public Collection <RoleDefinition> getPerformingRoles ()
	{
		return null ;
	}

	/**
	 * @see model.spem2.WorkDefinition#setPerformingRoles(java.util.Collection)
	 */
	public void setPerformingRoles (Collection <RoleDefinition> _roleDescriptor)
	{
	}
}
