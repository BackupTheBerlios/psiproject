package process;

import process.Preferences;
import junit.framework.TestCase;

/**
 * PreferencesTest : TODO type description
 *
 * @author KOUCH Hassan
 * @version 1.0
 *
 */
public class PreferencesTest extends TestCase
{
/*
 * Attributs
 */
	private Preferences preference;
	private int preference_Height;
	private int preference_LogHeight;
	private int preference_TreeWidth;
	private int preference_Width;
	private int preference_XPosition;
	private int preference_YPosition;
	private boolean preference_LoadHelp;
	private boolean preference_LoadLastProject;
	
	/*
	 * @see TestCase#setUp()
	 */
	
	public boolean boolRandom ()
	{
		boolean bool;
		double local_integer = Math.random();
		if (local_integer <= 0.5)
		{
			bool = true;
		}else {
			bool = false;
		}
		return bool;
	}
	
	protected void setUp () throws Exception
	{
		super.setUp() ;
		
		preference_Width = (int)(Math.random()*10);
		preference_XPosition = (int)(Math.random()*10);
		preference_YPosition = (int)(Math.random()*10);
		preference_TreeWidth = (int)(Math.random()*10);
		preference_LogHeight = (int)(Math.random()*10);
		preference_Height = (int)(Math.random()*10);
		
		preference_LoadLastProject = boolRandom();
		preference_LoadHelp = boolRandom();
		
		preference = Preferences.getInstance();
		preference.setExportDirectory("preference_ExportDirectory");
		preference.setHeight(preference_Height);
		preference.setLastProject("preference_LastProject");
		preference.setLoadHelp(preference_LoadHelp);
		preference.setLoadLastProject(preference_LoadLastProject);
		preference.setLocale("preference_Locale");
		preference.setLogHeight(preference_LogHeight);
		preference.setLookAndFeel("preference_LookAndFeel");
		preference.setTreeWidth(preference_TreeWidth);
		preference.setWidth(preference_Width);
		preference.setWorkDirectory("preference_WorkDirectory");
		preference.setXPosition(preference_XPosition);
		preference.setYPosition(preference_YPosition);
		
		
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown () throws Exception
	{
		super.tearDown() ;
	}

	/*
	 * Test method for 'process.Preferences.save()'
	 */
	public void testSave ()
	{

	}

	/*
	 * Test method for 'process.Preferences.getHeight()'
	 */
	public void testGetHeight ()
	{
		assertEquals(preference.getHeight(), preference_Height);
	}

	/*
	 * Test method for 'process.Preferences.getLastProject()'
	 */
	public void testGetLastProject ()
	{
		assertEquals(preference.getLastProject(), "preference_LastProject");
	}

	/*
	 * Test method for 'process.Preferences.getTreeWidth()'
	 */
	public void testGetTreeWidth ()
	{
		assertEquals(preference.getTreeWidth(), preference_TreeWidth);
	}

	/*
	 * Test method for 'process.Preferences.getLocale()'
	 */
	public void testGetLocale ()
	{
		assertEquals(preference.getLocale(), "preference_Locale");
	}

	/*
	 * Test method for 'process.Preferences.getLogHeight()'
	 */
	public void testGetLogHeight ()
	{
		assertEquals(preference.getLogHeight(), preference_LogHeight);
	}

	/*
	 * Test method for 'process.Preferences.getWidth()'
	 */
	public void testGetWidth ()
	{
		assertEquals(preference.getWidth(), preference_Width);
	}

	/*
	 * Test method for 'process.Preferences.getXPosition()'
	 */
	public void testGetXPosition ()
	{
		assertEquals(preference.getXPosition(), preference_XPosition);
	}

	/*
	 * Test method for 'process.Preferences.getYPosition()'
	 */
	public void testGetYPosition ()
	{
		assertEquals(preference.getYPosition(), preference_YPosition);
	}

	/*
	 * Test method for 'process.Preferences.getInstance()'
	 */
	public void testGetInstance ()
	{
		assertEquals(Preferences.getInstance(), preference);
	}

	/*
	 * Test method for 'process.Preferences.getExportDirectory()'
	 */
	public void testGetExportDirectory ()
	{
		assertEquals(preference.getExportDirectory(), "preference_ExportDirectory");
	}

	/*
	 * Test method for 'process.Preferences.getLoadHelp()'
	 */
	public void testGetLoadHelp ()
	{
		assertEquals(preference.getLoadHelp(), preference_LoadHelp);
	}

	/*
	 * Test method for 'process.Preferences.getLoadLastProject()'
	 */
	public void testGetLoadLastProject ()
	{
		assertEquals(preference.getLoadLastProject(), preference_LoadLastProject);
	}

	/*
	 * Test method for 'process.Preferences.getLookAndFeel()'
	 */
	public void testGetLookAndFeel ()
	{
		assertEquals(preference.getLookAndFeel(), "preference_LookAndFeel");
	}

	/*
	 * Test method for 'process.Preferences.getWorkDirectory()'
	 */
	public void testGetWorkDirectory ()
	{
		assertEquals(preference.getWorkDirectory(), "preference_WorkDirectory");
	}

}
