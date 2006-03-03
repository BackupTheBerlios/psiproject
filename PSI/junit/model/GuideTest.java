package model;

import java.io.File;

import model.Guide;
import model.GuideType;
import model.Presentation;
import junit.framework.TestCase;

/**
 * GuideTest : TODO type description
 *
 * @author KOUCH Hassan
 * @version 1.0
 *
 */
public class GuideTest extends TestCase
{
/*
 * Attributs
 */
	private Guide guide;
	private GuideType guideType;
	private Presentation presentation;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp () throws Exception
	{
		super.setUp() ;
		presentation = new Presentation("presentation_Id","presentation_Name","presentation_Desc","presentation_IconPath","presentation_ContentPath","presentation_PagePath");
		guideType = new GuideType("guideType_Id","guideType_Name");
		guide = new Guide("guide_Id","guide_Name");
		guide.setDescription("guide_Desc");
		guide.setType(guideType);
		guide.setPresentationElement(presentation);
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown () throws Exception
	{
		super.tearDown() ;
	}

	/*
	 * Test method for 'model.Guide.getId()'
	 */
	public void testGetId ()
	{
		assertEquals(guide.getId(), "guide_Id");
	}

	/*
	 * Test method for 'model.Guide.getName()'
	 */
	public void testGetName ()
	{
		assertEquals(guide.getName(), "guide_Name");	
	}

	/*
	 * Test method for 'model.Guide.getDescription()'
	 */
	public void testGetDescription ()
	{
		assertEquals(guide.getDescription(), "guide_Desc");
	}

	/*
	 * Test method for 'model.Guide.getType()'
	 */
	public void testGetType ()
	{
		assertEquals(guide.getType(), guideType);
	}

	/*
	 * Test method for 'model.Guide.getPresentationElement()'
	 */
	public void testGetPresentationElement ()
	{
		assertEquals(guide.getPresentationElement(), presentation);
	}

}
