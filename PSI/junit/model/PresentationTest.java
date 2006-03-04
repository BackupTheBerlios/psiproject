package model;

import java.util.Collection;

import model.Guide;
import model.Presentation;
import junit.framework.TestCase;

/**
 * PresentationTest : TODO type description
 *
 * @author KOUCH Hassan
 * @version 1.0
 *
 */
public class PresentationTest extends TestCase
{

	/*
	 * Attributs
	 */
	
	private Presentation presentation;
	private Collection<Guide> guide;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp () throws Exception
	{
		super.setUp() ;
		guide = null;
		presentation = new Presentation("presentation_Id","presentation_Name","presentation_Desc","presentation_IconPath","presentation_ContentPath","presentation_PagePath");
		presentation.setGuides(guide);
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown () throws Exception
	{
		super.tearDown() ;
	}

	/*
	 * Test method for 'model.Presentation.getDescription()'
	 */
	public void testGetDescription ()
	{
		assertEquals(presentation.getDescription(), "presentation_Desc");
	}

	/*
	 * Test method for 'model.Presentation.getId()'
	 */
	public void testGetId ()
	{
		assertEquals(presentation.getId(), "presentation_Id");
	}

	/*
	 * Test method for 'model.Presentation.getName()'
	 */
	public void testGetName ()
	{
		assertEquals(presentation.getName(), "presentation_Name");
	}

	/*
	 * Test method for 'model.Presentation.getContentPath()'
	 */
	public void testGetContentPath ()
	{
		assertEquals(presentation.getContentPath(), "presentation_ContentPath" );
	}

	/*
	 * Test method for 'model.Presentation.getGuides()'
	 */
	public void testGetGuides ()
	{
		assertEquals(presentation.getGuides(), guide);
	}

	/*
	 * Test method for 'model.Presentation.getIconPath()'
	 */
	public void testGetIconPath ()
	{
		assertEquals(presentation.getContentPath(), "presentation_IconPath" );
	}

	/*
	 * Test method for 'model.Presentation.getPagePath()'
	 */
	public void testGetPagePath ()
	{
		assertEquals(presentation.getContentPath(), "presentation_PagePath" );
	}

}
