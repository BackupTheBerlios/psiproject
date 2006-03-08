package model;

import model.HumanResource;
import junit.framework.TestCase;

/**
 * HumanResourceTest : TODO type description
 *
 * @author KOUCH Hassan
 * @version 1.0
 *
 */
public class HumanResourceTest extends TestCase
{

	/*
	 * Attributs
	 */
	
	private HumanResource humanResource;
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp () throws Exception
	{
		super.setUp() ;
		
		humanResource = new HumanResource("humanResource_Id","humanResource_FullName"); 
		humanResource.setEmail("humanResource_Email");
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown () throws Exception
	{
		super.tearDown() ;
	}

	/*
	 * Test method for 'model.HumanResource.getFullName()'
	 */
	public void testGetFullName ()
	{
		assertEquals(humanResource.getFullName(), "humanResource_FullName");
	}

	/*
	 * Test method for 'model.HumanResource.getId()'
	 */
	public void testGetId ()
	{
		assertEquals(humanResource.getId(), "humanResource_Id");
	}

	/*
	 * Test method for 'model.HumanResource.getEmail()'
	 */
	public void testGetEmail ()
	{
		assertEquals(humanResource.getEmail(), "humanResource_Email");
	}

	/*
	 * Test method for 'model.HumanResource.getPerformingRoles()'
	 */
	public void testGetPerformingRoles ()
	{

	}

	/*
	 * Test method for 'model.HumanResource.getTransferData(DataFlavor)'
	 */
	public void testGetTransferData ()
	{

	}

	/*
	 * Test method for 'model.HumanResource.getTransferDataFlavors()'
	 */
	public void testGetTransferDataFlavors ()
	{

	}

	/*
	 * Test method for 'model.HumanResource.isDataFlavorSupported(DataFlavor)'
	 */
	public void testIsDataFlavorSupported ()
	{

	}

	/*
	 * Test method for 'model.HumanResource.getPerformingTasks()'
	 */
	public void testGetPerformingTasks ()
	{

	}

}
