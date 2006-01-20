package process.utility;



import java.io.File;

import process.exception.FileParseException;

import model.spem2.DeliveryProcess;

import junit.framework.TestCase;

/**
 * ProcessControlerTest : TODO type description
 *
 * @author KOUCH Hassan
 * @version 1.0
 *
 */
public class ProcessControlerTest extends TestCase
{

	/*
	 * Test method for 'process.utility.ProcessControler.load(File)'
	 */
	public void testLoad () throws FileParseException
	{
		File fic = new File("/users/iupisi/l3isi21/open_workbench_project.xml");
		try
		{
			DeliveryProcess DP = ProcessControler.load(fic);
		}
		catch (FileParseException exc)
		{
		
			//exc.printStackTrace();
		}
		
		}
		
	}
