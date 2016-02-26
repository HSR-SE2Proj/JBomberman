package jbomberman.game.server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import jbomberman.game.Action;
import jbomberman.game.ActionQueue;
import jbomberman.game.ActionType;
import jbomberman.game.DirectionType;
import jbomberman.utils.Position;

public class GExplosionTest {
	
	private GExplosion explosion;
	private Position pos;
	private ActionQueue queue;
	private GameObjectManager om;
	
	@Before
	public void setUp() {
		pos = new Position(0,0);
		queue = new ActionQueue();
		om = new GameObjectManager();
	}
	
	@Test
	public void testExplosionDown() {
		explosion = new GExplosion(pos, 0, 2, DirectionType.DOWN);
		explosion.tick(queue, om);
		Action action = queue.take();
		assertEquals(ActionType.CREATE_EXPLOSION, action.getActionType());
		Position changed = (Position) action.getProperty(0);
		assertEquals(0,changed.getX());
		assertEquals(64,changed.getY());
	}
	
	@Test
	public void testExplosionLeft() {
		explosion = new GExplosion(pos, 0, 2, DirectionType.LEFT);
		explosion.tick(queue, om);
		Action action = queue.take();
		assertEquals(ActionType.CREATE_EXPLOSION, action.getActionType());
		Position changed = (Position) action.getProperty(0);
		assertEquals(-64,changed.getX());
		assertEquals(0,changed.getY());
	}
	
	@Test
	public void testExplosionRight() {
		explosion = new GExplosion(pos, 0, 2, DirectionType.RIGHT);
		explosion.tick(queue, om);
		Action action = queue.take();
		assertEquals(ActionType.CREATE_EXPLOSION, action.getActionType());
		Position changed = (Position) action.getProperty(0);
		assertEquals(64,changed.getX());
		assertEquals(0,changed.getY());
	}
	
	@Test
	public void testExplosionUp() {
		explosion = new GExplosion(pos, 0, 2, DirectionType.UP);
		explosion.tick(queue, om);
		Action action = queue.take();
		assertEquals(ActionType.CREATE_EXPLOSION, action.getActionType());
		Position changed = (Position) action.getProperty(0);
		assertEquals(0,changed.getX());
		assertEquals(-64,changed.getY());
	}
	
	@Test
	public void testPower() {
		explosion = new GExplosion(pos, 0, 2, DirectionType.UP);
		explosion.setPower(4);
		assertEquals(4,explosion.getPower());
	}
	
	@Test
	public void testGetDirectiontype() {
		explosion = new GExplosion(pos, 0, 2, DirectionType.NO_DIRECTION);
		assertEquals(DirectionType.NO_DIRECTION,explosion.getDirectionType());
	}
}
