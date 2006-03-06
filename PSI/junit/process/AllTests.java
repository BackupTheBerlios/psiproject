package model;

import junit.framework.*;
import junit.framework.TestSuite;
import junit.awtui.*;

/**
 * AllTests : Test suite for all tests of the package junit.process
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
		TestSuite suite = new TestSuite("Test for process") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(PreferencesTest.class) ;
		
		//$JUnit-END$
		return suite ;
	}

}
