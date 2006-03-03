package model.spem2;

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
		TestSuite suite = new TestSuite("Test for model.spem2") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ActivityTest.class) ;
		suite.addTestSuite(ActivityDescriptorTest.class);
		suite.addTestSuite(ArtifactTest.class);
		suite.addTestSuite(ProcessTest.class);
		suite.addTestSuite(PlanningDataTest.class);
		suite.addTestSuite(ProductTypeTest.class);
		//suite.addTestSuite(DeliveryProcessTest.class);
		//suite.addTestSuite(IterationTest.class);
		//suite.addTestSuite(PhaseTest.class);
		
		//$JUnit-END$
		return suite ;
	}

}
