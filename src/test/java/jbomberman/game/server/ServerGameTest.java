package jbomberman.game.server;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jbomberman.game.Action;
import jbomberman.game.ActionSerializer;
import jbomberman.game.ActionType;
import jbomberman.game.DirectionType;
import jbomberman.game.Party;
import jbomberman.game.Player;
import jbomberman.game.PowerUpType;
import jbomberman.network.client.ClientNetwork;
import jbomberman.network.server.ServerNetwork;
import jbomberman.utils.Position;

public class ServerGameTest {
	private ServerGame serverGame;
	private ServerNetwork serverNetwork;
	private ClientNetwork clientNetwork;
	private Party party;
	private Player player1,player2;
	
	@Before
	public void setUp() {
		clientNetwork = new ClientNetwork();
		clientNetwork.connect("localhost");
		serverNetwork = new ServerNetwork();
		serverNetwork.connect("localhost");
		party = new Party();
		player1 = new Player("Player1", 1);
		player2 = new Player("Player2", 2);
		party.add(player1);
		party.add(player2);
		serverGame = new ServerGame(serverNetwork, party);
	}
	
	@Test
	public void testPlayerInput() throws InterruptedException {
		Object[] properties = {0, KeyEvent.VK_A, true};
		Action action = new Action(ActionType.PLAYER_INPUT, properties);
		clientNetwork.sendMessage(ActionSerializer.serialize(action));
		Thread.sleep(500);
		serverGame.loop();
	}
	
	@Test
	public void testCreateBomb() throws InterruptedException {
		Object[] properties = {new Position(0,0),2,3,1};
		Action action = new Action(ActionType.CREATE_BOMB, properties);
		clientNetwork.sendMessage(ActionSerializer.serialize(action));
		Thread.sleep(500);
		serverGame.loop();
	}
	
	@Test
	public void testCreatePowerUp() throws InterruptedException {
		Object[] properties = {new Position(0,0),2,PowerUpType.BOMB};
		Action action = new Action(ActionType.CREATE_POWERUP, properties);
		clientNetwork.sendMessage(ActionSerializer.serialize(action));
		Thread.sleep(500);
		serverGame.loop();
	}
	
	@Test
	public void testCreateExplosion() throws InterruptedException {
		Object[] properties = {new Position(0,0),2,2,DirectionType.DOWN};
		Action action = new Action(ActionType.CREATE_EXPLOSION, properties);
		clientNetwork.sendMessage(ActionSerializer.serialize(action));
		Thread.sleep(500);
		serverGame.loop();
	}
	
	@Test
	public void testDestroy() throws InterruptedException {
		Object[] properties = {1};
		Action action = new Action(ActionType.DESTROY, properties);
		clientNetwork.sendMessage(ActionSerializer.serialize(action));
		Thread.sleep(500);
		serverGame.loop();
	}
	
	@Test
	public void testGetWinner() {
		assertEquals(0,serverGame.getWinner().getId());
	}
	
	@Test
	public void testSetInitMap() {
		serverGame.setInitMap(true);
	}
	
	@After
	public void close() {
		clientNetwork.close();
		serverNetwork.close();
	}
	
}
