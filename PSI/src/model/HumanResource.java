
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
	 * The member's full name
	 */
	private String fullName ;

	/**
	 * The member's mail
	 */
	private String email ;

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

}
