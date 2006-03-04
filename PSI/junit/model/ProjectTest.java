package model;

import java.util.Collection;
import java.util.Date;

import model.HumanResource;
import model.Project;
import model.spem2.DeliveryProcess;
import junit.framework.TestCase;

/**
 * ProjectTest : TODO type description
 *
 * @author KOUCH Hassan
 * @version 1.0
 *
 */
public class ProjectTest extends TestCase
{
	
/*
 * Attributs
 */
	private Project projet;
	private Date StartDate;
	private Date FinishDate;
	private Date date_DP; 
	private DeliveryProcess DP;
	private Collection <HumanResource> ressources;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp () throws Exception
	{
		super.setUp() ;
		
		ressources = null;          
	
		DP = new DP("DP_Id","DP_Name","DP_Desc","DP_AuthorFullName","DP_AuthorMail");
		DP.setDate(date_DP);
		DP.setGenerationPath("DP_GenerationPath");
		
		FinishDate = new Date();
		StartDate = new Date();
		
		projet = new Project();
		projet.setDescription("projet_Desc");
		projet.setFinishDate(FinishDate);
		projet.setId("projet_Id");
		projet.setName("projet_Name");
		projet.setProcess(DP);
		projet.setStartDate(StartDate);
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown () throws Exception
	{
		super.tearDown() ;
	}

	/*
	 * Test method for 'model.Project.getId()'
	 */
	public void testGetId ()
	{
		assertEquals(projet.getId(), "projet_Id");
	}

	/*
	 * Test method for 'model.Project.getDescription()'
	 */
	public void testGetDescription ()
	{
		assertEquals(projet.getDescription(), "projet_Desc");
	}

	/*
	 * Test method for 'model.Project.getName()'
	 */
	public void testGetName ()
	{
		assertEquals(projet.getName(), "projet_Name");
	}

	/*
	 * Test method for 'model.Project.getFinishDate()'
	 */
	public void testGetFinishDate ()
	{
		assertEquals(projet.getFinishDate(), FinishDate);
	}

	/*
	 * Test method for 'model.Project.getStartDate()'
	 */
	public void testGetStartDate ()
	{
		assertEquals(projet.getStartDate(), StartDate);
	}

	/*
	 * Test method for 'model.Project.getProcess()'
	 */
	public void testGetProcess ()
	{
		assertEquals(projet.getProcess(), DP);
	}

	/*
	 * Test method for 'model.Project.getResources()'
	 */
	public void testGetResources ()
	{
		assertEquals(projet.getResources(), ressources);
	}

}
