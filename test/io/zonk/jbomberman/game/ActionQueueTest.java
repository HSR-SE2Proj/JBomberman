package io.zonk.jbomberman.game;

import org.junit.*;

public class ActionQueueTest {
	
	private ActionQueue queue;
	private Action action1 = new Action(ActionType.LOBBY_COMMUNICATION, null);
	private Action action2 = new Action(ActionType.LOBBY_COMMUNICATION, null);
	private Action action3 = new Action(ActionType.LOBBY_COMMUNICATION, null);
	
	@Before
	public void init() {
		queue = new ActionQueue();
		queue.put(action1);
		queue.put(action2);
		queue.put(action3);
	}
	
	@Test
	public void takeTest() {
		Assert.assertTrue(action1.equals(queue.take()));
		Assert.assertTrue(action2.equals(queue.take()));
		Assert.assertTrue(action3.equals(queue.take()));
	}
	
	@Test
	public void isEmptyTest() {
		Assert.assertTrue(!queue.isEmpty());
		queue.take();
		queue.take();
		queue.take();
		Assert.assertTrue(queue.isEmpty());
	}

}
