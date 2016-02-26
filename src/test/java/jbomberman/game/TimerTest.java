package jbomberman.game;

import org.junit.Before;
import org.junit.Test;

import jbomberman.game.client.ClientGame;
import jbomberman.network.client.ClientNetwork;

public class TimerTest {
	private ClientGame client;
	private Timer timer;
	private ClientNetwork clientNetwork;
	private Party party;
	private Player player;
	
	@Before
	public void setUp() {
		clientNetwork = new ClientNetwork();
		clientNetwork.connect("localhost");
		party = new Party();
		player = new Player("Player1",0);
		party.add(player);
		client = new ClientGame(clientNetwork,party);
		timer = new Timer(10,client);
	}
	
	@Test
	public void testTimer() {
		timer.start();
	}
}
