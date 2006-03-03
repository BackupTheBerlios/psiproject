package model.spem2;

import junit.framework.TestCase;

/**
 * ProductTypeTest : TODO type description
 *
 * @author m1isi28
 * @version 1.0
 *
 */
public class ProductTypeTest extends TestCase
{
	/**
	 * Attributs
	 */
	ProductType productType;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp () throws Exception
	{
		super.setUp() ;
		productType = new ProductType("productType_id","productType_name");
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown () throws Exception
	{
		super.tearDown() ;
	}

	/*
	 * Test method for 'model.spem2.ProductType.getId()'
	 */
	public void testGetId ()
	{
		assertEquals(productType.getId(), "productType_id");
	}

	/*
	 * Test method for 'model.spem2.ProductType.getName()'
	 */
	public void testGetName ()
	{
		assertEquals(productType.getName(), "productType_name");
	}

	/*
	 * Test method for 'model.spem2.ProductType.getDescription()'
	 */
	public void testGetDescription ()
	{

	}

}
