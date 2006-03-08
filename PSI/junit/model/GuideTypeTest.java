package model;

import model.GuideType;
import junit.framework.TestCase;

/**
 * GuideTypeTest : TODO type description
 *
 * @author KOUCH Hassan
 * @version 1.0
 *
 */
public class GuideTypeTest extends TestCase
{
	/*
	 * Attributs
	 */
	private GuideType guideType; 
	
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp () throws Exception
	{
		super.setUp() ;
		guideType = new GuideType("guideType_Id","guideType_Name");
		guideType.setDescription("");
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown () throws Exception
	{
		super.tearDown() ;
	}

	/*
	 * Test method for 'model.GuideType.getId()'
	 */
	public void testGetId ()
	{
		assertEquals(guideType.getId(), "guideType_Id");
	}

	/*
	 * Test method for 'model.GuideType.getName()'
	 */
	public void testGetName ()
	{
		assertEquals(guideType.getName(), "guideType_Name");
	}

	/*
	 * Test method for 'model.GuideType.getDescription()'
	 */
	public void testGetDescription ()
	{
		assertEquals(guideType.getDescription(), "");
	}

}
