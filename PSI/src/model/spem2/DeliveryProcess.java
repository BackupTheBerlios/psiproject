
package model.spem2 ;

/**
 * DeliveryProcess : represents the process created, used and exported by PSI.
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * @see Process
 */
public class DeliveryProcess extends Process
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
	public DeliveryProcess (String _id, String _name, String _description, String _authorFullName, String _authorMail)
	{
		super(_id, _name, _description, _authorFullName, _authorMail) ;
	}

}
