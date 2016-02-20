package jbomberman.game;


import static org.junit.Assert.*;
import org.junit.*;

public class ActionTest {
	private Action action;
	private Object[] props;
	
	@Before
	public void init(){
		props = new Object[]{"Hello","Test"};
		action = new Action(ActionType.MOVEMENT,props);
	}
	
	@Test
	public void testActionType(){
		assertEquals(ActionType.MOVEMENT,action.getActionType());
	}
	
	@Test
	public void testGetProperty(){
		assertEquals("Hello",action.getProperty(0));
	}

}
