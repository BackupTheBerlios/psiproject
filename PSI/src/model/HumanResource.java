
package model ;

/**
 * HumanResource : a member working on a project
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class HumanResource
{
	/**
	 * The member's unique ID during a project
	 */
	private String id ;

	/**
	 * The member's first name
	 */
	private String firstName ;

	/**
	 * The member's last name
	 */
	private String lastName ;

	/**
	 * The member's mail
	 */
	private String email ;

	/**
	 * Getter
	 * 
	 * @return Returns the firstName.
	 */
	public String getFirstName ()
	{
		return this.firstName ;
	}

	/**
	 * Setter
	 * 
	 * @param _firstName
	 *            The firstName to set.
	 */
	public void setFirstName (String _firstName)
	{
		this.firstName = _firstName ;
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
	 * @return Returns the lastName.
	 */
	public String getLastName ()
	{
		return this.lastName ;
	}

	/**
	 * Setter
	 * 
	 * @param _lastName
	 *            The lastName to set.
	 */
	public void setLastName (String _lastName)
	{
		this.lastName = _lastName ;
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

}
