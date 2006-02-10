package junit;

import java.io.File;

import process.exception.FileParseException;
import junit.model.spem2.TestActivity;
//import test.TestPointPolaire;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
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
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for test");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestActivity.class);
		//suite.addTestSuite(TestPointCartesien.class);
		//$JUnit-END$
		return suite;
	}
	
}
