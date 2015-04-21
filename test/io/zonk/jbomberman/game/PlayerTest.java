package io.zonk.jbomberman.game;

import static org.junit.Assert.*;

import org.junit.*;
public class PlayerTest {
	private Player player;
	@Before
	public void init() {
		player = new Player("Test",0);
	}
	
	@Test
	public void testID() {
		assertEquals(0,player.getId());
	}
	
	@Test
	public void testName() {
		assertEquals("Test",player.getName());
	}
}
