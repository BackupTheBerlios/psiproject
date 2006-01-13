
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
		super(_id, _name, _description, _authorFullName, _authorMail) ;
	}
}
