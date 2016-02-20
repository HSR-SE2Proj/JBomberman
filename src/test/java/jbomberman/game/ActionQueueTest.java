package jbomberman.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import jbomberman.utils.Position;

public class ActionQueueTest {

	private ActionQueue queue;
	@Before
	public void setUp() {
		queue = new ActionQueue();
	}
	
	@Test
	public void testPutQueue() {
		Object[] properties = {new Position(23,23)};
		Action action = new Action(ActionType.LOBBY_COMMUNICATION, properties);
		queue.put(action);
		assertEquals(false,queue.isEmpty());
	}
	
	@Test
	public void testTakeQueue() {
		Object[] properties = {new Position(23,23)};
		Action action = new Action(ActionType.LOBBY_COMMUNICATION, properties);
		queue.put(action);
		Action action2 = queue.take();
		assertEquals(ActionType.LOBBY_COMMUNICATION,action2.getActionType());
		assertEquals(true,queue.isEmpty());
	}
	
}
