package model;

import junit.framework.*;
import junit.framework.TestSuite;
import junit.awtui.*;

/**
 * AllTests : Test suite for all tests of the package junit.model.spem2
 *
 * @author Avetisyan Gohar
 * @version 1.0
 *
 */
public class AllTests
{
	public static void main(String[] args){
		TestRunner.run(AllTests.class);
	}
	
	public static Test suite ()
	{
		TestSuite suite = new TestSuite("Test for model") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(Component.class) ;
		
		//suite.addTestSuite(ActivityDescriptorTest.class);
		//suite.addTestSuite(ArtifactTest.class);
		
		//$JUnit-END$
		return suite ;
	}

}
