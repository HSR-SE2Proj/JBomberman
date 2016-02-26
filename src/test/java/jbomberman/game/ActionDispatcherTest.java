package jbomberman.game;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jbomberman.network.client.ClientNetwork;
import jbomberman.network.server.ServerNetwork;
import jbomberman.utils.Position;

public class ActionDispatcherTest {
	private ActionDispatcher dispatcher;
	private ServerNetwork serverNetwork;
	private ClientNetwork clientNetwork;
	private ActionQueue queue;
	
	@Before
	public void setUp() {
		serverNetwork = new ServerNetwork();
		serverNetwork.connect("localhost");
		clientNetwork = new ClientNetwork();
		clientNetwork.connect("localhost");
		queue = new ActionQueue();
		dispatcher = new ActionDispatcher(serverNetwork, queue);
	}
	
	@Test
	public void testDispatcher() throws InterruptedException {
		Object[] properties = {new Position(23,23)};
		Action action = new Action(ActionType.LOBBY_COMMUNICATION, properties);
		clientNetwork.sendMessage(ActionSerializer.serialize(action));
		dispatcher.start();
		Position pos = (Position) queue.take().getProperty(0);
		assertEquals(23,pos.getX());
		assertEquals(23,pos.getY());
	}
	
	@After
	public void tearDown() {
		clientNetwork.close();
		serverNetwork.close();
	}

}
