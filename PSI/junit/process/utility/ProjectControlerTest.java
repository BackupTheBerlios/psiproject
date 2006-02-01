package junit.process.utility;

import java.io.File;

import process.exception.FileParseException;
import process.utility.ProjectControler;

import model.Project;

import junit.framework.TestCase;

/**
 * ProjectControlerTest : Test different functions of ProjectControl
 *
 * @author Avetisyan Gohar
 * @version 1.0
 *
 */
public class ProjectControlerTest extends TestCase
{
	Project projet;
	public int testOk = 1;
	/*
	 * Test method for 'process.utility.ProjectControler.create(File)'
	 */
	public void testCreate (File fich) throws FileParseException
	{
		try{
			projet = ProjectControler.create(fich);
		}
		catch(FileParseException fpe){
			testOk = 0;	
		}
	}

	/*
	 * Test method for 'process.utility.ProjectControler.open(File)'
	 */
	public void testOpen ()
	{

	}

	/*
	 * Test method for 'process.utility.ProjectControler.save(Project, File)'
	 */
	public void testSave ()
	{

	}
}

