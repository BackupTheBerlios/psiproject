package model;

import model.Component;
import model.Interface;
import model.spem2.ProductType;
import junit.framework.TestCase;

/**
 * InterfaceTest : TODO type description
 *
 * @author KOUCH Hassan
 * @version 1.0
 *
 */
public class InterfaceTest extends TestCase
{
	/*
	 * Attributs
	 */
		private Interface interFace;
		private Component component;
		
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp () throws Exception
	{
		super.setUp() ;
		component = new Component("component_Id","component_Name","component_Desc");
		interFace = new Interface("interFace_Id","interFace_Name");
		interFace.setDescription("interFace_Desc");
		interFace.setGivenForComponent(component);
		interFace.setRequiredForComponent(component);
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown () throws Exception
	{
		super.tearDown() ;
	}

	/*
	 * Test method for 'model.Interface.getId()'
	 */
	public void testGetId ()
	{
		assertEquals(interFace.getId(), "interFace_Id");
	}

	/*
	 * Test method for 'model.Interface.getName()'
	 */
	public void testGetName ()
	{
		assertEquals(interFace.getName(), "interface_Name");
	}

	/*
	 * Test method for 'model.Interface.getProducts()'
	 */
	public void testGetProducts ()
	{
		
	}

	/*
	 * Test method for 'model.Interface.getDescription()'
	 */
	public void testGetDescription ()
	{
		assertEquals(interFace.getDescription(), "interFace_Desc");
	}

	/*
	 * Test method for 'model.Interface.getGivenForComponent()'
	 */
	public void testGetGivenForComponent ()
	{
		assertEquals(interFace.getGivenForComponent(), component);
	}

	/*
	 * Test method for 'model.Interface.getRequiredForComponent()'
	 */
	public void testGetRequiredForComponent ()
	{
		assertEquals(interFace.getRequiredForComponent(), component);
	}

}
