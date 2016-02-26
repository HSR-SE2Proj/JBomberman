package jbomberman.game.client;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jbomberman.game.Action;
import jbomberman.game.ActionSerializer;
import jbomberman.game.ActionType;
import jbomberman.game.BombermanState;
import jbomberman.game.Party;
import jbomberman.game.Player;
import jbomberman.game.PowerUpType;
import jbomberman.network.client.ClientNetwork;
import jbomberman.network.server.ServerNetwork;
import jbomberman.utils.Position;

public class ClientGameTest {
	
	private ClientGame clientGame;
	private ServerNetwork serverNetwork;
	private ClientNetwork clientNetwork;
	private Party party;
	private Player player;
	
	@Before
	public void setUp() {
		clientNetwork = new ClientNetwork();
		clientNetwork.connect("localhost");
		serverNetwork = new ServerNetwork();
		serverNetwork.connect("localhost");
		party = new Party();
		player = new Player("Player1", 1);
		party.add(player);
		clientGame = new ClientGame(clientNetwork, party);
	}
	
	@Test
	public void testFinishGame() throws InterruptedException {
		Object[] properties = {"finishGame"};
		Action action = new Action(ActionType.LOBBY_COMMUNICATION, properties);
		serverNetwork.sendMessage(ActionSerializer.serialize(action));
		Thread.sleep(500);
		clientGame.loop();
	}
	
	@Test
	public void testFinishRound() throws InterruptedException {
		Object[] properties = {"finishRound"};
		Action action = new Action(ActionType.LOBBY_COMMUNICATION, properties);
		serverNetwork.sendMessage(ActionSerializer.serialize(action));
		Thread.sleep(500);
		clientGame.loop();
	}
	
	@Test
	public void testBomberman() throws InterruptedException {
		Object[] properties = {new Position(0,0),1};
		Action action = new Action(ActionType.CREATE_BOMBERMAN, properties);
		serverNetwork.sendMessage(ActionSerializer.serialize(action));
		Thread.sleep(500);
		clientGame.loop();
		Object[] properties2 = {1,new Position(64,64),BombermanState.DOWN};
		Action action2 = new Action(ActionType.MOVEMENT, properties2);
		serverNetwork.sendMessage(ActionSerializer.serialize(action2));
		Thread.sleep(500);
		clientGame.loop();
	}
	
	@Test
	public void testSolidBlock() throws InterruptedException {
		Object[] properties = {new Position(0,0),1};
		Action action = new Action(ActionType.CREATE_SOLIDBLOCK, properties);
		serverNetwork.sendMessage(ActionSerializer.serialize(action));
		Thread.sleep(500);
		clientGame.loop();
	}
	
	@Test
	public void testDestroyableBlock() throws InterruptedException {
		Object[] properties = {new Position(0,0),1};
		Action action = new Action(ActionType.CREATE_DESTROYABLEBLOCK, properties);
		serverNetwork.sendMessage(ActionSerializer.serialize(action));
		Thread.sleep(500);
		clientGame.loop();
	}
	
	@Test
	public void testBomb() throws InterruptedException {
		Object[] properties = {new Position(0,0),1};
		Action action = new Action(ActionType.CREATE_BOMB, properties);
		serverNetwork.sendMessage(ActionSerializer.serialize(action));
		Thread.sleep(500);
		clientGame.loop();
	}
	
	@Test
	public void testExplosion() throws InterruptedException {
		Object[] properties = {new Position(0,0),1};
		Action action = new Action(ActionType.CREATE_EXPLOSION, properties);
		serverNetwork.sendMessage(ActionSerializer.serialize(action));
		Thread.sleep(500);
		clientGame.loop();
	}
	
	@Test
	public void testPowerUp() throws InterruptedException {
		Object[] properties = {new Position(0,0),1, PowerUpType.BOMB};
		Action action = new Action(ActionType.CREATE_POWERUP, properties);
		serverNetwork.sendMessage(ActionSerializer.serialize(action));
		Thread.sleep(500);
		clientGame.loop();
	}
	
	@Test
	public void testBanner() throws InterruptedException {
		Object[] properties = {new Position(0,0),1};
		Action action = new Action(ActionType.CREATE_BANNER, properties);
		serverNetwork.sendMessage(ActionSerializer.serialize(action));
		Thread.sleep(500);
		clientGame.loop();
	}
	
	@Test
	public void testDestroy() throws InterruptedException {
		Object[] properties = {1};
		Action action = new Action(ActionType.DESTROY, properties);
		serverNetwork.sendMessage(ActionSerializer.serialize(action));
		Thread.sleep(500);
		clientGame.loop();
	}
	
	@Test
	public void testTimer() throws InterruptedException {
		Object[] properties = {900};
		Action action = new Action(ActionType.UPDATE_TIMER, properties);
		serverNetwork.sendMessage(ActionSerializer.serialize(action));
		Thread.sleep(500);
		clientGame.loop();
		assertEquals(900,clientGame.getTimer());
	}
	
	@After
	public void close() {
		clientNetwork.close();
		serverNetwork.close();
	}

}
