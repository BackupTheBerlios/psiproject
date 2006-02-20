package model.spem2;

import junit.framework.TestCase;

/**
 * ActivityDescriptorTest : Test case of model.spam2.ActivityDecrptor.java
 *
 * @author Avetisyan Gohar
 * @version 1.0
 *
 */
public class ActivityDescriptorTest extends TestCase
{
	/*
	 * Attributes
	 */
	private ActivityDescriptor activity_descriptor;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp () throws Exception
	{
		super.setUp() ;
		activity_descriptor = new ActivityDescriptor("act_desc_id", "act_desc_name", "act_desc_description", "act_desc_parentId");
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown () throws Exception
	{
		super.tearDown() ;
	}

	/*
	 * Test method for 'model.spem2.ActivityDescriptor.getDescription()'
	 */
	public void testGetDescription ()
	{
		assertEquals(activity_descriptor.getDescription(), "act_desc_description");
	}

	/*
	 * Test method for 'model.spem2.ActivityDescriptor.getId()'
	 */
	public void testGetId ()
	{
		assertEquals(activity_descriptor.getId(), "act_desc_id");
	}

	/*
	 * Test method for 'model.spem2.ActivityDescriptor.getName()'
	 */
	public void testGetName ()
	{
		assertEquals(activity_descriptor.getName(), "act_desc_name");
	}

	/*
	 * Test method for 'model.spem2.ActivityDescriptor.getParentId()'
	 */
	public void testGetParentId ()
	{
		assertEquals(activity_descriptor.getParentId(), "act_desc_parentId");
	}

}
