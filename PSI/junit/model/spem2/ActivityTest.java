package model.spem2;

import java.util.Collection;
import java.util.Collections;

import junit.framework.TestCase;

/**
 * ActivityTest : TODO type description
 *
 * @author m1isi28
 * @version 1.0
 *
 */
public class ActivityTest extends TestCase
{

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp () throws Exception
	{
		super.setUp() ;
		Activity activite = new Activity("0001","Activity1","This activity is the best!","parentId","$USER/interfaceDiagramPath/", "$USER/flowDiagramPath/","$USER/activityDiagramPath/");
		Collection<BreakDownElement> bde = new Collections();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown () throws Exception
	{
		super.tearDown() ;
	}

	/*
	 * Test method for 'model.spem2.Activity.getNestedElements()'
	 */
	public void testGetNestedElements ()
	{
		assertEquals(activite.nestedElements, null);
	}

	/*
	 * Test method for 'model.spem2.Activity.setNestedElements(Collection<BreakdownElement>)'
	 */
	public void testSetNestedElements ()
	{

	}

	/*
	 * Test method for 'model.spem2.Activity.getDescriptor()'
	 */
	public void testGetDescriptor ()
	{

	}

	/*
	 * Test method for 'model.spem2.Activity.setDescriptor(ActivityDescriptor)'
	 */
	public void testSetDescriptor ()
	{

	}

	/*
	 * Test method for 'model.spem2.Activity.getActivityDiagramPath()'
	 */
	public void testGetActivityDiagramPath ()
	{

	}

	/*
	 * Test method for 'model.spem2.Activity.setActivityDiagramPath(String)'
	 */
	public void testSetActivityDiagramPath ()
	{

	}

	/*
	 * Test method for 'model.spem2.Activity.getFlowDiagramPath()'
	 */
	public void testGetFlowDiagramPath ()
	{

	}

	/*
	 * Test method for 'model.spem2.Activity.setFlowDiagramPath(String)'
	 */
	public void testSetFlowDiagramPath ()
	{

	}

	/*
	 * Test method for 'model.spem2.Activity.getInterfaceDiagramPath()'
	 */
	public void testGetInterfaceDiagramPath ()
	{

	}

	/*
	 * Test method for 'model.spem2.Activity.setInterfaceDiagramPath(String)'
	 */
	public void testSetInterfaceDiagramPath ()
	{

	}

	/*
	 * Test method for 'model.spem2.Activity.getPresentationElement()'
	 */
	public void testGetPresentationElement ()
	{

	}

	/*
	 * Test method for 'model.spem2.Activity.setPresentationElement(Presentation)'
	 */
	public void testSetPresentationElement ()
	{

	}

}
