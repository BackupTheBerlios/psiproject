package model;

import junit.framework.*;
import junit.framework.TestSuite;
import junit.awtui.*;

/**
 * AllTests : Test suite for all tests of the package junit.model
 *
 * @author KOUCH Hassan
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
		/**
		 * Model's test Cases
		 */
		suite.addTestSuite(ComponentTest.class) ;
		suite.addTestSuite(GuideTest.class);
		suite.addTestSuite(GuideTypeTest.class);
		suite.addTestSuite(HumanResourceTest.class);
		suite.addTestSuite(InterfaceTest.class);
		suite.addTestSuite(LogInformationTest.class);
		suite.addTestSuite(PresentationTest.class);
		suite.addTestSuite(ProjectTest.class);
		suite.addTestSuite(StateTest.class);
		/**
		 * Model's sub package's test suites
		 */
		suite.addTest(model.spem2.AllTests.suite());
		
		
		//$JUnit-END$
		return suite ;
	}

}
