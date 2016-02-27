package jbomberman.game.server;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;

import org.junit.Before;
import org.junit.Test;

import jbomberman.game.Action;
import jbomberman.game.ActionQueue;
import jbomberman.game.ActionType;
import jbomberman.game.BombermanState;
import jbomberman.game.DirectionType;
import jbomberman.game.GameObjectType;
import jbomberman.game.PowerUpType;
import jbomberman.network.server.ServerNetwork;
import jbomberman.utils.Position;

public class GBombermanTest {
	private GBomberman gbomberman;
	private GExplosion explosion;
	private GPowerUp powerup;
	private GSolidBlock solid;
	private GBomb bomb;
	private GameObjectManager om;
	private Position pos;
	private ActionQueue queue;
	private Action action;
	private ServerNetwork serverNetwork;
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
	
	@Test
	public void testExplosion() {
		gbomberman.setPosition(pos);
		bomb = new GBomb(pos, 2, 3, 2);
		om.add(bomb);
		for (int i = 0; i < 91;i++){
			bomb.tick(queue, om);
		}
		assertEquals(ActionType.DESTROY,queue.take().getActionType());
		assertEquals(ActionType.CREATE_EXPLOSION,queue.take().getActionType());
		assertEquals(ActionType.CREATE_EXPLOSION,queue.take().getActionType());
		assertEquals(ActionType.CREATE_EXPLOSION,queue.take().getActionType());
		assertEquals(ActionType.CREATE_EXPLOSION,queue.take().getActionType());
		assertEquals(ActionType.CREATE_EXPLOSION,queue.take().getActionType());
		//Create Explosion Manualy
		explosion = new GExplosion(pos,3,4,DirectionType.DOWN);
		om.add(explosion);
		gbomberman.tick(om, queue);
		assertEquals(BombermanState.DEAD,gbomberman.getState());
		assertEquals(ActionType.DESTROY,queue.take().getActionType());
	}
	
	@Test
	public void testSetFullPowerUps() {
		gbomberman.setFullPowerups();
		gbomberman.setPosition(pos);
		action = new Action(ActionType.PLAYER_INPUT, new Object[]{0, KeyEvent.VK_D, true});
		gbomberman.update(action);
		gbomberman.tick(om, queue);
		assertEquals(BombermanState.RIGHT,gbomberman.getState());
		assertEquals(72,gbomberman.getPosition().getX());
	}
	
	@Test
	public void testIdleState() {
		serverNetwork = new ServerNetwork();
		serverNetwork.connect("localhost");
		action = new Action(ActionType.PLAYER_INPUT, new Object[]{0, KeyEvent.VK_D, true});
		gbomberman.update(action);
		gbomberman.tick(om, queue);
		action = new Action(ActionType.PLAYER_INPUT, new Object[]{0, KeyEvent.VK_D, false});
		gbomberman.update(action);
		gbomberman.tick(om, queue);
		gbomberman.sendUpdates(serverNetwork);
		gbomberman.tick(om, queue);
		assertEquals(BombermanState.IDLE_RIGHT,gbomberman.getState());
		action = new Action(ActionType.PLAYER_INPUT, new Object[]{0, KeyEvent.VK_W, true});
		gbomberman.update(action);
		gbomberman.tick(om, queue);
		action = new Action(ActionType.PLAYER_INPUT, new Object[]{0, KeyEvent.VK_W, false});
		gbomberman.update(action);
		gbomberman.tick(om, queue);
		gbomberman.sendUpdates(serverNetwork);
		gbomberman.tick(om, queue);
		assertEquals(BombermanState.IDLE_UP,gbomberman.getState());
		action = new Action(ActionType.PLAYER_INPUT, new Object[]{0, KeyEvent.VK_S, true});
		gbomberman.update(action);
		gbomberman.tick(om, queue);
		action = new Action(ActionType.PLAYER_INPUT, new Object[]{0, KeyEvent.VK_S, false});
		gbomberman.update(action);
		gbomberman.tick(om, queue);
		gbomberman.sendUpdates(serverNetwork);
		gbomberman.tick(om, queue);
		assertEquals(BombermanState.IDLE_DOWN,gbomberman.getState());
		action = new Action(ActionType.PLAYER_INPUT, new Object[]{0, KeyEvent.VK_A, true});
		gbomberman.update(action);
		gbomberman.tick(om, queue);
		action = new Action(ActionType.PLAYER_INPUT, new Object[]{0, KeyEvent.VK_A, false});
		gbomberman.update(action);
		gbomberman.tick(om, queue);
		gbomberman.sendUpdates(serverNetwork);
		gbomberman.tick(om, queue);
		assertEquals(BombermanState.IDLE_LEFT,gbomberman.getState());
		serverNetwork.close();
	}
	
	@Test
	public void testPowerUp() {
		//Bomb
		powerup = new GPowerUp(pos, 2, PowerUpType.BOMB);
		om.add(powerup);
		gbomberman.tick(om, queue);
		om.remove(2);
		assertEquals(ActionType.DESTROY,queue.take().getActionType());
		//Power
		powerup = new GPowerUp(pos, 3, PowerUpType.POWER);
		om.add(powerup);
		gbomberman.tick(om, queue);
		action = new Action(ActionType.PLAYER_INPUT, new Object[]{0, KeyEvent.VK_ENTER, true});
		gbomberman.update(action);
		om.remove(3);
		gbomberman.tick(om, queue);
		assertEquals(ActionType.DESTROY,queue.take().getActionType());
		assertEquals(1,queue.take().getProperty(2));
		//Speed
		powerup = new GPowerUp(pos, 4, PowerUpType.SPEED);
		om.add(powerup);
		gbomberman.tick(om, queue);
		om.remove(4);
		assertEquals(ActionType.DESTROY,queue.take().getActionType());
		action = new Action(ActionType.PLAYER_INPUT, new Object[]{0, KeyEvent.VK_D, true});
		gbomberman.update(action);
		gbomberman.tick(om, queue);
		assertEquals(BombermanState.RIGHT,gbomberman.getState());
		assertEquals(72,gbomberman.getPosition().getX());
	}
	
	@Test
	public void testSetType() {
		gbomberman.setType(GameObjectType.BOMB);
		assertEquals(GameObjectType.BOMB,gbomberman.getType());
	}
	
	@Test
	public void testSetId() {
		gbomberman.setId(1);
		assertEquals(1,gbomberman.getId());
	}
	
	@Test
	public void testAddBomb() {
		gbomberman.addBomb();
	}
	
}
