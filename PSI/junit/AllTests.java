import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * AllTests : TODO type description
 *
 * @author l3isi21
 * @version 1.0
 *
 */
public class AllTests
{

	public static Test suite ()
	{
		TestSuite suite = new TestSuite("Test for default package") ;
		//$JUnit-BEGIN$
		suite.addTest(model.AllTests.suite());
		suite.addTest(process.AllTests.suite());
		//$JUnit-END$
		return suite ;
	}

}
