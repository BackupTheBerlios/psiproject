import junit.framework.*;

/**
 * AllTests : TODO type description
 *
 * @author Chez Maggy
 * @version 1.0
 *
 */
public class AllTests
{

	public static Test suite ()
	{
		TestSuite suite = new TestSuite("Test for default package") ;
		//$JUnit-BEGIN$
		suite.addTest(model.spem2.AllTests.suite());
		suite.addTest(model.AllTests.suite());
		suite.addTest(process.AllTests.suite());
		//$JUnit-END$
		return suite ;
	}

}
