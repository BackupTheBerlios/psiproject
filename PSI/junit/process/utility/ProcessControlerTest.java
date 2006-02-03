package junit.process.utility;

import junit.framework.TestCase;



import java.io.File;

import process.exception.FileParseException;
import process.utility.ProcessControler;

import model.spem2.DeliveryProcess;

import junit.framework.TestCase;
import com.sun
./**
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
		
		/*
		 * Test with a valid file.
		 */
		
		File ficBon = new File("/users/iupisi/l3isi21/open_workbench_project.xml");
		try
		{
			DeliveryProcess DP = ProcessControler.load(ficBon);
		}
		catch (FileParseException exc)
		{
		
			//exc.printStackTrace();
		}
	
		/*
		 * Test with a invalid file.
		 */
		
		File ficPasBon = new File("/users/iupisi/l3isi21/Test.xml");
		try
		{
			DeliveryProcess DP = ProcessControler.load(ficPasBon);
		}
		catch (FileParseException exc)
		{
		
			//exc.printStackTrace();
		}
		
	}
		
}
