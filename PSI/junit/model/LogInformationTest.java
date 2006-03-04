package model;

import java.util.Date;

import model.LogInformation;
import junit.framework.TestCase;

/**
 * LogInformationTest : TODO type description
 *
 * @author KOUCH Hassan
 * @version 1.0
 *
 */
public class LogInformationTest extends TestCase
{
/*
 * Attributs
 */
	private LogInformation logInfo;
	private Date date;
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp () throws Exception
	{
		super.setUp() ;
		date = new Date();
		logInfo = new LogInformation("logInfo_Name");
		logInfo.setDate(date);
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown () throws Exception
	{
		super.tearDown() ;
	}

	/*
	 * Test method for 'model.LogInformation.getDate()'
	 */
	public void testGetDate ()
	{
		assertEqual(logInfo.getDate(), date);
	}

	/*
	 * Test method for 'model.LogInformation.getName()'
	 */
	public void testGetName ()
	{
		assertEquals(logInfo.getName(), "logInfo_Name");
	}

}
