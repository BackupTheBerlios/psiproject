package junit.process.utility;

import java.io.File;

import process.exception.FileParseException;
import process.exception.FileSaveException;
import process.utility.ProjectControler;

import model.Project;

import junit.framework.TestCase;

/**
 * ProjectControlerTest : Test different functions of ProjectControl
 *
 * @author Avetisyan Gohar & KOUCH Hassan
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
		File ficPasBon = new File("/users/iupisi/l3isi21/Test.xml");
		File ficBon = new File("/users/iupisi/l3isi21/open_workbench_project.xml");
	
		Project pr1 = ProjectControler.create(ficBon);
		Project pr2 = ProjectControler.create(ficPasBon);	
	
	 }	/*	try{
			projet = ProjectControler.create(fich);
		}
		catch(FileParseException fpe){
			testOk = 0;	
		}
	}*/

	/*
	 * Test method for 'process.utility.ProjectControler.open(File)'
	 */
	public void testOpen ()
	{
		File ficPasBon = new File("/users/iupisi/l3isi21/Test.xml");
		File ficBon = new File("/users/iupisi/l3isi21/open_workbench_project.xml");
	
		Project pr1 = ProjectControler.open(ficBon);
		Project pr2 = ProjectControler.open(ficPasBon);
	
	}

	/*
	 * Test method for 'process.utility.ProjectControler.save(Project, File)'
	 */
	public void testSave ()
	{
		
	File ficPasBon = new File("/users/iupisi/l3isi21/Test.xml");
	File ficBon = new File("/users/iupisi/l3isi21/open_workbench_project.xml");

	Project pr1 = null;
	Project pr2 = null;
	
	try
	{
		ProjectControler.save(pr1,ficBon);
	}
	catch (FileSaveException exc)
	{
		// TODO Auto-generated catch block
		exc.printStackTrace();
	}
	try
	{
		ProjectControler.save(pr2,ficPasBon);
	}
	catch (FileSaveException exc)
	{
		int d
		
		// TODO Auto-generated catch block
		exc.printStackTrace();
	}
	
}

	
}

