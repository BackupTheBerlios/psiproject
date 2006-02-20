package model.spem2;

import junit.framework.TestCase;

/**
 * ArtifactTest : Test case for the src.model.spem2.Artifact.java
 *
 * @author Avetisyan Gohar
 * @version 1.0
 *
 */
public class ArtifactTest extends TestCase
{
	/*
	 * Attributes
	 */
	private Artifact artifact;
	private WorkProductDescriptor product;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp () throws Exception
	{
		super.setUp() ;
		artifact = new Artifact("artifact_id", "artifact_name", "artifact_description", "artifact_parentId");
		product = new WorkProductDescriptor("product_id", "product_name", "product_description", "product_parentId");
		artifact.setProduct(product);
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown () throws Exception
	{
		super.tearDown() ;
	}

	/*
	 * Test method for 'model.spem2.Artifact.getProduct()'
	 */
	public void testGetProduct ()
	{
		assertEquals(artifact.getProduct().getId(), "product_id");
		assertEquals(artifact.getProduct().getName(), "product_name");
		assertEquals(artifact.getProduct().getDescription(), "product_description");
		assertEquals(artifact.getProduct().getParentId(), "product_parentId");
	}

	/*
	 * Test method for 'model.spem2.Artifact.getTransferData(DataFlavor)'
	 */
	public void testGetTransferData ()
	{

	}

	/*
	 * Test method for 'model.spem2.Artifact.getTransferDataFlavors()'
	 */
	public void testGetTransferDataFlavors ()
	{

	}

	/*
	 * Test method for 'model.spem2.Artifact.isDataFlavorSupported(DataFlavor)'
	 */
	public void testIsDataFlavorSupported ()
	{

	}

	/*
	 * Test method for 'model.spem2.Artifact.toString()'
	 */
	public void testToString ()
	{

	}

	/*
	 * Test method for 'model.spem2.WorkProductDescriptor.setChanged()'
	 */
	public void testSetChanged ()
	{

	}

	/*
	 * Test method for 'model.spem2.WorkProductDescriptor.getDescription()'
	 */
	public void testGetDescription ()
	{

	}

	/*
	 * Test method for 'model.spem2.WorkProductDescriptor.getId()'
	 */
	public void testGetId ()
	{

	}

	/*
	 * Test method for 'model.spem2.WorkProductDescriptor.getName()'
	 */
	public void testGetName ()
	{

	}

	/*
	 * Test method for 'model.spem2.WorkProductDescriptor.getParentId()'
	 */
	public void testGetParentId ()
	{

	}

	/*
	 * Test method for 'model.spem2.WorkProductDescriptor.getProducingTasks()'
	 */
	public void testGetProducingTasks ()
	{

	}

	/*
	 * Test method for 'model.spem2.WorkProductDescriptor.getResponsible()'
	 */
	public void testGetResponsible ()
	{

	}

	/*
	 * Test method for 'model.spem2.WorkProductDescriptor.getUsingTasks()'
	 */
	public void testGetUsingTasks ()
	{

	}

	/*
	 * Test method for 'model.spem2.WorkProductDescriptor.getPresentation()'
	 */
	public void testGetPresentation ()
	{

	}

	/*
	 * Test method for 'model.spem2.WorkProductDescriptor.getInterfaces()'
	 */
	public void testGetInterfaces ()
	{

	}

	/*
	 * Test method for 'model.spem2.WorkProductDescriptor.getPresentationElement()'
	 */
	public void testGetPresentationElement ()
	{

	}

	/*
	 * Test method for 'model.spem2.WorkProductDescriptor.getProductType()'
	 */
	public void testGetProductType ()
	{

	}

	/*
	 * Test method for 'model.spem2.WorkProductDescriptor.getArtifacts()'
	 */
	public void testGetArtifacts ()
	{

	}

}
