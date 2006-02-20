package model.spem2;

import java.util.Collection;
import java.util.Collections;

import model.Presentation;

import junit.framework.TestCase;

/**
 * ActivityTest : TODO type description
 *
 * @author m1isi28
 * @version 1.0
 *
 */
public class TestActivity extends TestCase
{
	Activity activite;
	Collection<BreakdownElement> collection_bde;
	ActivityDescriptor act_desc;
	Presentation presentation;
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp () throws Exception
	{
		super.setUp() ;
		activite = new Activity("0001","Activity1","This activity is the best!","parentId","$USER/interfaceDiagramPath/", "$USER/flowDiagramPath/","$USER/activityDiagramPath/");
		
		collection_bde = null;
		activite.setNestedElements(collection_bde);
		
		act_desc = new ActivityDescriptor("desc_id", "desc_name", "desc_description","desc_parentId");
		activite.setDescriptor(act_desc);
		
		presentation = new Presentation("pr_id", "pr_name", "pr_description", "pr_iconPath", "pr_contentPath", "pr_pagePath");
		activite.setPresentationElement(presentation);
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
		assertEquals(activite.getNestedElements(), null);
	}


	/*
	 * Test method for 'model.spem2.Activity.getDescriptor()'
	 */
	public void testGetDescriptor ()
	{
		assertEquals(activite.getDescriptor().getId(), "desc_id");
		assertEquals(activite.getDescriptor().getName(), "desc_name");
		assertEquals(activite.getDescriptor().getDescription(), "desc_description");
		assertEquals(activite.getDescriptor().getParentId(), "desc_parentId");
	}


	/*
	 * Test method for 'model.spem2.Activity.getActivityDiagramPath()'
	 */
	public void testGetActivityDiagramPath ()
	{
		assertEquals(activite.getActivityDiagramPath(), "$USER/activityDiagramPath/");
	}

	/*
	 * Test method for 'model.spem2.Activity.getFlowDiagramPath()'
	 */
	public void testGetFlowDiagramPath ()
	{
		assertEquals(activite.getFlowDiagramPath(),"$USER/flowDiagramPath/");
	}

	/*
	 * Test method for 'model.spem2.Activity.getInterfaceDiagramPath()'
	 */
	public void testGetInterfaceDiagramPath ()
	{
		assertEquals(activite.getInterfaceDiagramPath(), "$USER/interfaceDiagramPath/");
	}

	/*
	 * Test method for 'model.spem2.Activity.getPresentationElement()'
	 */
	public void testGetPresentationElement ()
	{
		assertEquals(activite.getPresentationElement().getId(), "pr_id");
		assertEquals(activite.getPresentationElement().getName(), "pr_name");
		assertEquals(activite.getPresentationElement().getDescription(), "pr_description");
		assertEquals(activite.getPresentationElement().getIconPath(), "pr_iconPath");
		assertEquals(activite.getPresentationElement().getContentPath(), "pr_contentPath");
		assertEquals(activite.getPresentationElement().getPagePath(), "pr_pagePath");
		
	}

}
