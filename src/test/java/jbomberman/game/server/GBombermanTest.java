package jbomberman.game.server;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;


import org.junit.Before;
import org.junit.Test;

import jbomberman.game.Action;
import jbomberman.game.ActionQueue;
import jbomberman.game.ActionType;
import jbomberman.game.BombermanState;
import jbomberman.utils.Position;

public class GBombermanTest {
	private GBomberman gbomberman;
	private GSolidBlock solid;
	private GameObjectManager om;
	private Position pos;
	private ActionQueue queue;
	private Action action;
	@Before
	public void setUp() {
		pos = new Position(64,64);
		queue = new ActionQueue();
		gbomberman = new GBomberman(pos, 0);
		solid = new GSolidBlock(new Position(0,0),1);
		om = new GameObjectManager();
		om.add(gbomberman);
		om.add(solid);
	}
	
	@Test
	public void testWKey() {
		gbomberman.setPosition(pos);
		action = new Action(ActionType.PLAYER_INPUT, new Object[]{0, KeyEvent.VK_W, true});
		gbomberman.update(action);
		gbomberman.tick(om, queue);
		assertEquals(BombermanState.UP,gbomberman.getState());
		assertEquals(60,gbomberman.getPosition().getY());
	}
	
	@Test
	public void testAKey() {
		gbomberman.setPosition(pos);
		action = new Action(ActionType.PLAYER_INPUT, new Object[]{0, KeyEvent.VK_A, true});
		gbomberman.update(action);
		gbomberman.tick(om, queue);
		assertEquals(BombermanState.LEFT,gbomberman.getState());
	}
	
	@Test
	public void testSKey() {
		gbomberman.setPosition(pos);
		action = new Action(ActionType.PLAYER_INPUT, new Object[]{0, KeyEvent.VK_S, true});
		gbomberman.update(action);
		gbomberman.tick(om, queue);
		assertEquals(BombermanState.DOWN,gbomberman.getState());
		assertEquals(68,gbomberman.getPosition().getY());
	}
	
	@Test
	public void testDKey() {
		gbomberman.setPosition(pos);
		action = new Action(ActionType.PLAYER_INPUT, new Object[]{0, KeyEvent.VK_D, true});
		gbomberman.update(action);
		gbomberman.tick(om, queue);
		assertEquals(BombermanState.RIGHT,gbomberman.getState());
		assertEquals(68,gbomberman.getPosition().getX());
	}
	
	@Test
	public void testEnterKey() {
		gbomberman.setPosition(pos);
		action = new Action(ActionType.PLAYER_INPUT, new Object[]{0, KeyEvent.VK_ENTER, true});
		gbomberman.update(action);
		gbomberman.tick(om, queue);
		assertEquals(BombermanState.IDLE_DOWN,gbomberman.getState());
		assertEquals(64,gbomberman.getPosition().getX());
		assertEquals(ActionType.CREATE_BOMB,queue.take().getActionType());
	}
	
}
