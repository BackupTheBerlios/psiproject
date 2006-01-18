
package model.spem2 ;

import java.util.Date ;

/**
 * PlanningData : a planification element attached which can be attached to a
 * Breakdown Element
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * @see BreakdownElement
 * 
 */
public class PlanningData implements ProcessElement
{
	/**
	 * Absolute starting date for one Breakdown Element
	 */
	private Date startDate ;

	/**
	 * Absolute ending date for one Breakdown Element
	 */
	private Date finishDate ;

	/**
	 * Defines the ranking of a Breakdown Element relative to other Breakdown
	 * Elements in the same Activity
	 */
	private int rank ;

	/**
	 * Duration of planification. Note that the default unit is an hour.
	 */
	private float duration ;

	/**
	 * Constructor
	 * 
	 * @param _startDate
	 * @param _finishDate
	 * @param _rank
	 * @param _duration
	 */
	public PlanningData (Date _startDate, Date _finishDate, int _rank, float _duration)
	{
		super() ;
		this.startDate = _startDate ;
		this.finishDate = _finishDate ;
		this.rank = _rank ;
		this.duration = _duration ;
	}
	
	public PlanningData()
	{
		super() ;
		this.startDate = new Date();
		this.finishDate = new Date();
		this.rank = 0;
		this.duration = 0;		
	}

	/**
	 * Getter
	 * 
	 * @return Returns the duration.
	 */
	public float getDuration ()
	{
		return this.duration ;
	}

	/**
	 * Setter
	 * 
	 * @param _duration
	 *            The duration to set.
	 */
	public void setDuration (float _duration)
	{
		this.duration = _duration ;
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
	 * @return Returns the rank.
	 */
	public int getRank ()
	{
		return this.rank ;
	}

	/**
	 * Setter
	 * 
	 * @param _rank
	 *            The rank to set.
	 */
	public void setRank (int _rank)
	{
		this.rank = _rank ;
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

}
