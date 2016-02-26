package jbomberman.game.server;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jbomberman.game.GameObjectType;
import jbomberman.game.Party;
import jbomberman.game.Player;
import jbomberman.network.server.ServerNetwork;

public class StandardMapTest {
	private GameObjectManager om;
	private ServerNetwork serverNetwork;
	private Party party;
	private StandardMap standmap;
	private Player player1,player2,player3,player4;
	@Before
	public void setUp() {
		om = new GameObjectManager();
		serverNetwork = new ServerNetwork();
		serverNetwork.connect("localhost");
		party = new Party();
		player1 = new Player("Player1",1);
		player2 = new Player("Player2",2);
		player3 = new Player("Player3",3);
		player4 = new Player("Player4",4);
		party.add(player1);
		party.add(player2);
		party.add(player3);
		party.add(player4);
	}
	@Test
	public void testStandardMap() {
		char[][] map = new char[][]{
			{'#','#','#','#','#','#','#','#','#','#','#','#','#'},
			{'#','1','X',' ',' ',' ',' ',' ',' ',' ','X','2','#'},
			{'#','X','#',' ','#',' ','#',' ','#',' ','#','X','#'},
			{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
			{'#',' ','#',' ','#',' ','#',' ','#',' ','#',' ','#'},
			{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
			{'#',' ','#',' ','#',' ','#',' ','#',' ','#',' ','#'},
			{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
			{'#',' ','#',' ','#',' ','#',' ','#',' ','#',' ','#'},
			{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
			{'#','X','#',' ','#',' ','#',' ','#',' ','#','X','#'},
			{'#','3','X',' ',' ',' ',' ',' ',' ',' ','X','4','#'},
			{'#','#','#','#','#','#','#','#','#','#','#','#','#'}
	};
		standmap = new StandardMap();
		assertArrayEquals(map,standmap.map);
	}
	
	@Test
	public void testInit() {
		standmap = new StandardMap();
		standmap.init(om, serverNetwork, party);
		assertEquals(73,om.getByType(GameObjectType.SOLID_BLOCK).size());
		assertEquals(4,om.getByType(GameObjectType.BOMBERMAN).size());
		//Player1 Position
		assertEquals(64,om.getById(1).getPosition().getX());
		assertEquals(64,om.getById(1).getPosition().getY());
		//Player2 Position
		assertEquals(704,om.getById(2).getPosition().getX());
		assertEquals(64,om.getById(2).getPosition().getY());
		//Player3 Position
		assertEquals(64,om.getById(3).getPosition().getX());
		assertEquals(704,om.getById(3).getPosition().getY());
		//Player4 Position
		assertEquals(704,om.getById(4).getPosition().getX());
		assertEquals(704,om.getById(4).getPosition().getY());
	}
	
	@After
	public void tearDown() {
		serverNetwork.close();
	}
}
