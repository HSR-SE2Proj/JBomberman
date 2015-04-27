package io.zonk.jbomberman.game;

import static org.junit.Assert.*;
import io.zonk.jbomberman.game.server.GBomberman;
import org.junit.*;
public class PlayerTest {
	private Player player;
	private GBomberman gb;
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
	
	@Test
	public void testGetBomberman() {
		assertEquals(0,player.getBomberman().getId());
		assertEquals(GameObjectType.BOMBERMAN,player.getBomberman().getType());
		//anpassen, wenn Position mitgegeben wird
		//assertEquals(null,player.getBomberman().getPosition());
	}
	
	@Test
	public void testSetBomberman() {
		gb = new GBomberman(null, 1);
		player.setBomberman(gb);
		assertEquals(1,player.getBomberman().getId());
		assertEquals(null,player.getBomberman().getPosition());
		assertEquals(GameObjectType.BOMBERMAN,player.getBomberman().getType());
	}
}
