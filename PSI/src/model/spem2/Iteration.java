
package model.spem2 ;

/**
 * Iteration : a repeatable variant of an activity.
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class Iteration extends Activity
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
	public Iteration (String _id, String _name, String _description, String _authorFullName, String _authorMail)
	{
		super(_id, _name, _description, _authorFullName, _authorMail) ;
	}

}
