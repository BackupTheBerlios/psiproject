
package model.spem2 ;

/**
 * Process : a base class for a process, which is a specialised activity
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * @see Activity
 * 
 */
public class Process extends Activity
{

	/**
	 * 
	 */
	private String authorFullName ;

	/**
	 * 
	 */
	private String authorMail ;

	/**
	 * Constructor
	 *
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _authorFullName
	 * @param _authorMail
	 */
	public Process (String _id, String _name, String _description, String _authorFullName, String _authorMail)
	{
		super(_id, _name, _description, "") ;

		this.authorFullName = _authorFullName ;
		this.authorMail = _authorMail ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the authorFullName.
	 */
	public String getAuthorFullName ()
	{
		return this.authorFullName ;
	}

	/**
	 * Setter
	 *
	 * @param _authorFullName The authorFullName to set.
	 */
	public void setAuthorFullName (String _authorFullName)
	{
		this.authorFullName = _authorFullName ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the authorMail.
	 */
	public String getAuthorMail ()
	{
		return this.authorMail ;
	}

	/**
	 * Setter
	 *
	 * @param _authorMail The authorMail to set.
	 */
	public void setAuthorMail (String _authorMail)
	{
		this.authorMail = _authorMail ;	}

	
}
