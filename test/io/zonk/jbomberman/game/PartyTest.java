package io.zonk.jbomberman.game;

import static org.junit.Assert.*;

import org.junit.*;

public class PartyTest {
	private Party party;
	private Player player;
	@Before
	public void init() {
		party = new Party();
		player = new Player("Test",0);
		party.add(player);
	}
	
	@Test
	public void testAddPlayers(){
		assertEquals(1,party.getPlayers().size());
		party.add(player);
		assertEquals(2,party.getPlayers().size());
	}
	
	@Test
	public void testGetPlayer() {
		assertEquals(party.getPlayers().get(0),party.get(0));
	}
	
	@Test
	public void testRemovePlayer() {
		assertEquals(1,party.getPlayers().size());
		party.remove(player);
		assertEquals(0,party.getPlayers().size());
	}
	
	@Test
	public void testGetPlayers() {
		assertEquals(1,party.getPlayers().size());
	}
	
}