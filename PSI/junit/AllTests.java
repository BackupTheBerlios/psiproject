package junit;

import java.io.File;

import process.exception.FileParseException;

import junit.framework.TestCase;
import junit.process.utility.ProjectControlerTest;

/**
 * AllTests : TODO type description
 *
 * @author Chez Maggy
 * @version 1.0
 *
 */
public class AllTests extends TestCase
{
	int main() throws FileParseException
	{
		
		/**
		 * Test of Project create member
		 */
		File mauvais_fich = new File("TestFiles/test.xml");
		File bon_fich = new File("TestFiles/open_workbench_project.xml");
		File inexist_fich = new File("TestFiles/toto.xml");
		ProjectControlerTest pct1 = null,pct2 = null,pct3 = null;
		/**
		 * project imported with the good format file
		 * to comment the next test for running this test
		 */
		//pct1.testCreate(bon_fich);
		//assertTrue(pct1.testOk == 1);
		/**
		 * project imported with the bad format file
		 */
		pct2.testCreate(mauvais_fich);
		assertTrue(pct2.testOk == 10);
		/**
		 * project imported with file don't exist
		 */
		pct3.testCreate(inexist_fich);
		assertTrue(pct3.testOk == 1);
		
		return 0;
	}
	
}
