package model;

import model.State;
import junit.framework.TestCase;

/**
 * StateTest : TODO type description
 *
 * @author KOUCH Hassan
 * @version 1.0
 *
 */
public class StateTest extends TestCase
{
	/*
	 * Attributs
	 */
	private State state;
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp () throws Exception
	{
		super.setUp() ;
		state = new State("state_Id","state_Name");
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown () throws Exception
	{
		super.tearDown() ;
	}

	/*
	 * Test method for 'model.State.getId()'
	 */
	public void testGetId ()
	{
		assertEquals(state.getId(), "state_Id");
	}

	/*
	 * Test method for 'model.State.getName()'
	 */
	public void testGetName ()
	{
		assertEquals(state.getName(), "state_Name");
	}

	/*
	 * Test method for 'model.State.getDescription()'
	 */
	public void testGetDescription ()
	{
		assertEquals(state.getDescription(), null);
	}

}
