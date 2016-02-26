package jbomberman.game.server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import jbomberman.game.ActionQueue;
import jbomberman.game.ActionType;
import jbomberman.game.DirectionType;
import jbomberman.utils.Position;

public class GDestroyableBlockTest {
	private GameObjectManager om;
	private ActionQueue queue;
	private GDestroyableBlock block;
	private GExplosion explosion;
	private Position pos;
	
	@Before
	public void setUp() {
		om = new GameObjectManager();
		queue = new ActionQueue();
		pos = new Position(0,0);
		
	}
	
	@Test
	public void testDestroyableBlock() {
		block = new GDestroyableBlock(pos, 0);
		explosion = new GExplosion(pos,1, 3, DirectionType.DOWN);
		om.add(block);
		om.add(explosion);
		block.tick(queue, om);
		assertEquals(ActionType.DESTROY,queue.take().getActionType());
	}
}
