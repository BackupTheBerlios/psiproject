
package model ;

import java.util.Date ;

/**
 * LogInformation : basic information on user's actions
 * 
 * @author Condé Mickaël K.
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
	private String result ;

	/**
	 * Constructor
	 * 
	 * @param _date
	 * @param _name
	 * @param _result
	 */
	public LogInformation (Date _date, String _name, String _result)
	{
		super() ;
		// TODO Auto-generated constructor stub
		this.date = _date ;
		this.name = _name ;
		this.result = _result ;
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
	 * @return Returns the result.
	 */
	public String getResult ()
	{
		return this.result ;
	}

	/**
	 * Setter
	 * 
	 * @param _result
	 *            The result to set.
	 */
	public void setResult (String _result)
	{
		this.result = _result ;
	}
}
