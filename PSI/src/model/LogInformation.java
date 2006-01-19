
package model ;

import java.util.Date ;

/**
 * LogInformation : basic information on user's actions
 * 
 * @author Cond? Micka?l K.
 * @version 1.0
 * 
 */
public class LogInformation
{
	/**
	 * The date when an event/action occured
	 */
	private Date date ;

	/**
	 * The name of the event
	 */
	private String name ;

	/**
	 * Success or failure
	 */
	private boolean success ;

	/**
	 * Constructor
	 * 
	 * @param _date
	 * @param _name
	 * @param _result
	 */
	public LogInformation (Date _date, String _name, boolean _success)
	{
		super() ;

		this.date = _date ;
		this.name = _name ;
		this.success = _success ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the date.
	 */
	public Date getDate ()
	{
		return this.date ;
	}

	/**
	 * Setter
	 * 
	 * @param _date
	 *            The date to set.
	 */
	public void setDate (Date _date)
	{
		this.date = _date ;
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
	 * @return Returns the success.
	 */
	public boolean isSuccess ()
	{
		return this.success ;
	}

	/**
	 * Setter
	 *
	 * @param _success The success to set.
	 */
	public void setSuccess (boolean _success)
	{
		this.success = _success ;
	}

	
}
