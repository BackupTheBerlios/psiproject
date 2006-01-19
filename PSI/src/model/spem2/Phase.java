
package model.spem2 ;

/**
 * Phase : a non repeatable variant of an Activity
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class Phase extends Activity
{

	/**
	 * Constructor
	 * 
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _parentId
	 */
	public Phase (String _id, String _name, String _description, String _parentId)
	{
		super(_id, _name, _description, _parentId) ;
	}

}
