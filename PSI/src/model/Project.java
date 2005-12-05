
package model ;

import java.util.Date ;

import model.spem2.DeliveryProcess ;

/**
 * Project : a project common class.
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class Project
{
	/**
	 * The unique ID of the project
	 */
	private String id ;

	/**
	 * The name of the project
	 */
	private String name ;

	/**
	 * The description of the project
	 */
	private String description ;

	/**
	 * Beginning of the project
	 */
	private Date startDate ;

	/**
	 * End of the project
	 */
	private Date finishDate ;

	/**
	 * The delivery process associated to the project
	 */
	private DeliveryProcess process ;

	/**
	 * Constructor
	 * 
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _startDate
	 * @param _finishDate
	 * @param _process
	 */
	public Project (String _id, String _name, String _description, Date _startDate, Date _finishDate, DeliveryProcess _process)
	{
		this(_id, _name, _description, _startDate, _finishDate) ;
		this.process = _process ;
	}

	/**
	 * Constructor
	 * 
	 * @param _id
	 * @param _name
	 * @param _description
	 * @param _startDate
	 * @param _finishDate
	 */
	public Project (String _id, String _name, String _description, Date _startDate, Date _finishDate)
	{
		super() ;
		this.id = _id ;
		this.name = _name ;
		this.description = _description ;
		this.startDate = _startDate ;
		this.finishDate = _finishDate ;
		this.process = null ;
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
	 * @return Returns the description.
	 */
	public String getDescription ()
	{
		return this.description ;
	}

	/**
	 * Setter
	 * 
	 * @param _description
	 *            The description to set.
	 */
	public void setDescription (String _description)
	{
		this.description = _description ;
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
	 * @return Returns the finishDate.
	 */
	public Date getFinishDate ()
	{
		return this.finishDate ;
	}

	/**
	 * Setter
	 * 
	 * @param _finishDate
	 *            The finishDate to set.
	 */
	public void setFinishDate (Date _finishDate)
	{
		this.finishDate = _finishDate ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the startDate.
	 */
	public Date getStartDate ()
	{
		return this.startDate ;
	}

	/**
	 * Setter
	 * 
	 * @param _startDate
	 *            The startDate to set.
	 */
	public void setStartDate (Date _startDate)
	{
		this.startDate = _startDate ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the process.
	 */
	public DeliveryProcess getProcess ()
	{
		return this.process ;
	}

	/**
	 * Setter
	 * 
	 * @param _process
	 *            The process to set.
	 */
	public void setProcess (DeliveryProcess _process)
	{
		this.process = _process ;
	}

}
