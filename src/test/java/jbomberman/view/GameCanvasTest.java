package jbomberman.view;

import static org.junit.Assert.*;

import java.awt.Dimension;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jbomberman.game.Party;
import jbomberman.game.Player;
import jbomberman.game.client.ClientGame;
import jbomberman.game.client.Keyboard;
import jbomberman.network.client.ClientNetwork;

public class GameCanvasTest {
	
	private GameCanvas canvas;
	private ClientGame game;
	private Keyboard keyboard;
	private Party party;
	private Player player1,player2;
	private ClientNetwork clientNetwork;
	
	@Before
	public void setUp() {
		clientNetwork = new ClientNetwork();
		clientNetwork.connect("localhost");
		party = new Party();
		player1 = new Player("Player1",1);
		player2 = new Player("Player2",2);
		party.add(player1);
		party.add(player2);
		game = new ClientGame(clientNetwork, party);
		keyboard = new Keyboard(0, clientNetwork);
		canvas = new GameCanvas(game,keyboard,party,1);
	}
	
	@Test
	public void testCanvas() {
		assertEquals(new Dimension(640,640),canvas.getPreferredSize());
	}
	
	@Test
	public void testDispose() {
		canvas.dispose();
	}
	
	@Test
	public void testRender() {
		canvas.render();
	}
	
	@Test
	public void testUpdateTimer() {
		canvas.update(null, "updateTimer");
	}
	
	@Test
	public void testUpdate() {
		canvas.update(null, "somethingElse");
	}
	
	@After
	public void tearDown() {
		clientNetwork.close();
	}
	

}
