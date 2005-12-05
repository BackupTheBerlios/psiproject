
package model.spem2 ;

import java.util.Collection ;

/**
 * RoleDefinition : set of related skills, competences and responsabilities.
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class RoleDefinition implements MethodContentElement
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
	 * A list of roles linked to the definition, in the current version of apes should be alwas null ?
	 */
	private Collection <RoleDescriptor> roles ;

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
	 * @param _description The description to set.
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
	 * @param _id The id to set.
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
	 * @param _name The name to set.
	 */
	public void setName (String _name)
	{
		this.name = _name ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the roles.
	 */
	public Collection <RoleDescriptor> getRoles ()
	{
		return this.roles ;
	}

	/**
	 * Setter
	 *
	 * @param _roles The roles to set.
	 */
	public void setRoles (Collection <RoleDescriptor> _roles)
	{
		this.roles = _roles ;
	}
}
