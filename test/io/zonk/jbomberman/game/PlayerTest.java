package io.zonk.jbomberman.game;

import static org.junit.Assert.*;
import io.zonk.jbomberman.game.server.GBomberman;
import org.junit.*;
import io.zonk.jbomberman.utils.Position;
public class PlayerTest {
	private Player player;
	private GBomberman gb;
	private Position pos;
	@Before
	public void init() {
		player = new Player("Test",0);
		pos = new Position(1,2);
		gb = new GBomberman(pos, 1);
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
	public void testGetSetBomberman() {
		player.setBomberman(gb);
		assertEquals(1,player.getBomberman().getId());
		assertEquals(pos.getX(),player.getBomberman().getPosition().getX());
		assertEquals(pos.getY(),player.getBomberman().getPosition().getY());
		assertEquals(GameObjectType.BOMBERMAN,player.getBomberman().getType());
	}
}
