package jbomberman.game.server;
import static org.junit.Assert.*;
import jbomberman.utils.Position;

import org.junit.*;
public class CollisionTest {
	/**
	 * Test for Collision between Bomberman and Blocks (Solid/Destroyable)
	 */
	private GBomberman gman;
	private GSolidBlock sblock;
	private GDestroyableBlock gdestblock;
	private Position pos1 = new Position(0,0); // Start Position
	private Position pos2 = new Position(64,64); // Collsion = false
	@Before
	public void init() {
		gman = new GBomberman(pos1, 0);
		sblock = new GSolidBlock(pos2,1);
		gdestblock = new GDestroyableBlock(pos2,2);
	}
	
	@Test
	public void testCollisionWithBlocks() {
		// Collision False
		assertEquals(false,gman.checkCollisionWith(sblock));
		//Collisions True
		pos2.setX(63);
		pos2.setY(63);
		assertEquals(true,gman.checkCollisionWith(sblock));
		//Collision on fareaway Pos = false
		pos2.setX(1000);
		pos2.setY(1000);
		assertEquals(false,gman.checkCollisionWith(sblock));
		//Change pos of Bomberman to a Collision = true -> until 63 px before
		pos1.setX(937);
		pos1.setY(937);
		assertEquals(true,gman.checkCollisionWith(sblock));
		//Same behaviour as SolidBlock as long it is not destroyed
		pos2.setX(64);
		pos2.setY(64);
		assertEquals(false,gman.checkCollisionWith(gdestblock));
		pos1.setX(63);
		pos1.setY(63);
		assertEquals(true,gman.checkCollisionWith(gdestblock));
	}

}
