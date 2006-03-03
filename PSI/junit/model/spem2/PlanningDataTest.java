package model.spem2;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

/**
 * PlanningDataTest : TODO type description
 *
 * @author m1isi28
 * @version 1.0
 *
 */
public class PlanningDataTest extends TestCase
{
	/**
	 * Attributs 
	 */
	PlanningData planningDataVide;
	PlanningData planningData;
	Date startDateVide;
	Date finishDateVide;
	Date startDate;
	Date finishDate;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp () throws Exception
	{
		super.setUp() ;
		startDateVide = new Date();
		finishDateVide = new Date();
		startDate = new Date(102,11,5);
		finishDate = new Date(106,2,6);
		planningDataVide = new PlanningData();
		planningData = new PlanningData(startDate,finishDate, 15, 16);
		
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown () throws Exception
	{
		super.tearDown() ;
	}

	/*
	 * Test method for 'model.spem2.PlanningData.getDuration()'
	 */
	public void testGetDuration ()
	{
		assertEquals(planningDataVide.getDuration(), (float)0);
		assertEquals(planningData.getDuration(), (float)16);
	}

	/*
	 * Test method for 'model.spem2.PlanningData.getFinishDate()'
	 */
	public void testGetFinishDate ()
	{
		assertEquals(planningDataVide.getFinishDate(), finishDateVide);
		assertEquals(planningData.getFinishDate(),finishDate);
	}

	/*
	 * Test method for 'model.spem2.PlanningData.getRank()'
	 */
	public void testGetRank ()
	{
		assertEquals(planningDataVide.getRank(), 0);
		assertEquals(planningData.getRank(), 15);
	}

	/*
	 * Test method for 'model.spem2.PlanningData.getStartDate()'
	 */
	public void testGetStartDate ()
	{
		assertEquals(planningDataVide.getStartDate(),startDateVide);
		assertEquals(planningData.getStartDate(), startDate);
	}

}
