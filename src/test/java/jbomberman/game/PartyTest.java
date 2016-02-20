package jbomberman.game;

import static org.junit.Assert.*;

import org.junit.*;

public class PartyTest {
	private Party party;
	private Player player;
	private Player player2;
	private Player player3;
	@Before
	public void init() {
		party = new Party();
		player = new Player("Test",0);
		player2 = new Player("Test",2);
		player3 = new Player("Test",3);
		party.add(player);
		party.add(player2);
	}
	
	@Test
	public void testAddPlayers(){
		assertEquals(2,party.getPlayers().size());
		party.add(player3);
		assertEquals(3,party.getPlayers().size());
	}
	
	@Test
	public void testGetPlayer() {
		assertEquals(party.getPlayers().get(0),party.get(0));
	}
	
	@Test
	public void testRemovePlayer() {
		assertEquals(2,party.getPlayers().size());
		party.remove(player);
		assertEquals(1,party.getPlayers().size());
	}
	
	@Test
	public void testGetPlayers() {
		assertEquals(2,party.getPlayers().size());
	}
	
	@Test
	public void testGetOverallWinner() {
		party.get(2).addScore();
		assertEquals(player2,party.getOverallWinner());
	}
	
	@Test
	public void testGetOverallWinnerSameScore() {
		assertEquals(5,party.getOverallWinner().getId());
		assertEquals("Temp",party.getOverallWinner().getName());
	}
	
}
