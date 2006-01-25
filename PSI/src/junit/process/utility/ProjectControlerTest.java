package junit.process.utility;

import junit.framework.TestCase;
import java.io.File;
import process.exception.FileParseException;
import process.utility.ProjectControler;
import model.Project;


/**
 * ProjectControlerTest : Test different functions of ProjectControl
 *
 * @author Avetisyan Gohar
 * @version 1.0
 *
 */
public class ProjectControlerTest extends TestCase
{

	/*
	 * Test method for 'process.utility.ProjectControler.create(File)'
	 */
	public void testCreate () throws FileParseException
	{
		File mauvais_fich = new File("/users/iupisi/m1isi28/BE/FICH/test.xml");
		File bon_fich = new File("/users/iupisi/m1isi28/BE/FICH/open_workbench_project.xml");
		Project pr1,pr2;
		/**
		 * project imported with the good format
		 * to comment the next test for running this test
		 */
		pr1 = ProjectControler.create(bon_fich);
		/**
		 * project imported with the bad format
		 */
		pr2 = ProjectControler.create(mauvais_fich);	
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
