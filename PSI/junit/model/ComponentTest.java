package model;

import java.util.Collection;

import model.Component;
import model.Presentation;
import model.spem2.ActivityDescriptor;
import model.spem2.BreakdownElement;
import junit.framework.TestCase;

/**
 * ComponentTest : TODO type description
 *
 * @author l3isi21
 * @version 1.0
 *
 */
public class ComponentTest extends TestCase
{
	/*
	 * Attributs
	 */
	
	
	private Component component;
	private ActivityDescriptor activityDescriptor;
	private Interface iface;
	private Collection<BreakdownElement> collection;
	private Collection<String> collection_string;
	private Presentation presentation;
	
	/*
	 * @see TestCase#setUp()
	 */
	
	protected void setUp () throws Exception
	{
		super.setUp() ;
		presentation = new Presentation("presentation_Id","presentation_Name","presentation_Desc","presentation_IconPath","presentation_ContentPath","presentation_PagePath");
		collection = null;
		iface = new Interface("iface_Id","iface_Name");
		activityDescriptor = new ActivityDescriptor("activity_Id","activity_Name","activity_Desc","activity_ParentId");
		component = new Component("component_Id","component_Name","component_Desc");
		component.setActivityDiagramPath("component_ActivityDiagramPath");
		component.setAuthorFullName("component_AuthorFullName");
		component.setAuthorMail("component_AuthorMail");
		component.setDate("component_Date");
		component.setDescriptor(activityDescriptor);
		component.setFlowDiagramPath("component_FlowDiagramPath");
		component.setGenerationOrder("component_GenerationOrder");
		component.setGivenInterface(iface);
		component.setInterfaceDiagramPath("component_InterfaceDiagramPath");
		component.setNestedElements(collection);
		component.setPresentationElement(presentation);
		component.setRequiredInterface(iface);
		component.setResponsabilityDiagramPaths(collection_string);
		component.setVersion("component_Version");
	
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown () throws Exception
	{
		super.tearDown() ;
	}

	/*
	 * Test method for 'model.Component.getDate()'
	 */
	public void testGetDate ()
	{
			assertEquals(component.getDate(), "component_Date");
	}

	/*
	 * Test method for 'model.Component.getVersion()'
	 */
	public void testGetVersion ()
	{
		assertEquals(component.getVersion(), "component_Version");
	}

	/*
	 * Test method for 'model.Component.getGenerationOrder()'
	 */
	public void testGetGenerationOrder ()
	{
		assertEquals(component.getGenerationOrder(), "component_GenerationOrder");
	}

	/*
	 * Test method for 'model.Component.getResponsabilityDiagramPaths()'
	 */
	public void testGetResponsabilityDiagramPaths ()
	{
		assertEquals(component.getResponsabilityDiagramPaths(), "component_ResponsabilityDiagramPaths");
	}

	/*
	 * Test method for 'model.Component.getAuthorFullName()'
	 */
	public void testGetAuthorFullName ()
	{
		assertEquals(component.getAuthorFullName(), "component_AuthorFullName");
	}

	/*
	 * Test method for 'model.Component.getAuthorMail()'
	 */
	public void testGetAuthorMail ()
	{
		assertEquals(component.getAuthorMail(), "component_AuthorMail");
	}

	/*
	 * Test method for 'model.Component.getGivenInterface()'
	 */
	public void testGetGivenInterface ()
	{
		assertEquals(component.getGivenInterface(), iface);
	}

	/*
	 * Test method for 'model.Component.getRequiredInterface()'
	 */
	public void testGetRequiredInterface ()
	{
		assertEquals(component.getRequiredInterface(), iface);
	}

}
