
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
	 * 
	 */
	private String date ;

	/**
	 * 
	 */
	private String generationPath ;

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
	 * Constructor
	 * 
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _authorFullName
	 * @param _authorMail
	 * @param _date
	 * @param _generationPath
	 */
	public Process (String _id, String _name, String _description, String _authorFullName, String _authorMail, String _date, String _generationPath)
	{
		this(_id, _name, _description, _authorFullName, _authorMail) ;

		this.date = _date ;
		this.generationPath = _generationPath ;
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
	 * @param _authorFullName
	 *            The authorFullName to set.
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
	 * @param _authorMail
	 *            The authorMail to set.
	 */
	public void setAuthorMail (String _authorMail)
	{
		this.authorMail = _authorMail ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the date.
	 */
	public String getDate ()
	{
		return this.date ;
	}

	/**
	 * Setter
	 * 
	 * @param _date
	 *            The date to set.
	 */
	public void setDate (String _date)
	{
		this.date = _date ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the generationPath.
	 */
	public String getGenerationPath ()
	{
		return this.generationPath ;
	}

	/**
	 * Setter
	 * 
	 * @param _generationPath
	 *            The generationPath to set.
	 */
	public void setGenerationPath (String _generationPath)
	{
		this.generationPath = _generationPath ;
	}

}
