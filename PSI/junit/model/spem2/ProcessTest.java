package model.spem2;

import junit.framework.TestCase;

/**
 * ProcessTest : TODO type description
 *
 * @author m1isi28
 * @version 1.0
 *
 */
public class ProcessTest extends TestCase
{
	/**
	 * Atrributes
	 */
	private Process process;
	
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp () throws Exception
	{
		super.setUp() ;
		process = new Process("process_id", "process_name", "process_description", "process_authorFullName", "process_authorMail");
		process.setDate("process_date");
		process.setGenerationPath("process_generationPath");
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown () throws Exception
	{
		super.tearDown() ;
	}

	/*
	 * Test method for 'model.spem2.Process.getAuthorFullName()'
	 */
	public void testGetAuthorFullName ()
	{
		assertEquals(process.getAuthorFullName(), "process_authorFullName");
	}

	/*
	 * Test method for 'model.spem2.Process.getAuthorMail()'
	 */
	public void testGetAuthorMail ()
	{
		assertEquals(process.getAuthorMail(), "process_authorMail");
	}

	/*
	 * Test method for 'model.spem2.Process.getDate()'
	 */
	public void testGetDate ()
	{
		assertEquals(process.getDate(), "process_date");
	}

	/*
	 * Test method for 'model.spem2.Process.getGenerationPath()'
	 */
	public void testGetGenerationPath ()
	{
		assertEquals(process.getGenerationPath(), "process_generationPath");
	}

}
