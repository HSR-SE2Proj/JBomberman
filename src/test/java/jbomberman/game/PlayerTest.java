package jbomberman.game;

import static org.junit.Assert.*;
import org.junit.*;
public class PlayerTest {
	private Player player;
	
	@Before
	public void init() {
		player = new Player("Test",0);
	}
	
	@Test
	public void testGetID() {
		assertEquals(0,player.getId());
	}
	
	@Test
	public void testGetName() {
		assertEquals("Test",player.getName());
	}
	
	@Test
	public void testSetID() {
		player.setId(2);
		assertEquals(2,player.getId());
	}
	
	@Test
	public void testSetName() {
		player.setName("Hello");
		assertEquals("Hello",player.getName());
	}
	
}
