package io.zonk.jbomberman.game.server;

import java.awt.event.KeyEvent;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.ActionSerializer;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.BombermanState;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.utils.IDGenerator;
import io.zonk.jbomberman.utils.Position;

public class GBomberman extends GameObject {
	
	private boolean w, a, s, d, enter;
	private int speed = 4;
	private boolean updatePosition;
	private BombermanState state = BombermanState.IDLE_DOWN;
	
	private int bombPower = 0;
	private int bombs = 1, maxBombs = 1;
	private int bombCooldown = 30*3;
	private int speedCooldown = 0;
	
	private boolean canPlace = true;
	
	public GBomberman(Position position, int id) {
		super(position, id, GameObjectType.BOMBERMAN);
	}
	
	public void tick(GameObjectManager manager, ActionQueue queue) {
		
		/*
		 * CHECK COLLISION WITH EXPLOSIONS
		 */
		for(GameObject object: manager.getByType(GameObjectType.EXPLOSION)) {
			if(checkCollisionWith(object)) {
				state = BombermanState.DEAD;
				
				queue.put(new Action(ActionType.DESTROY, new Object[]{id}));
				return;
			}
		}
		
		/*
		 * PICK UP POWERUP
		 */
		for(GameObject object: manager.getByType(GameObjectType.POWER_UP)) {
			if(checkCollisionWith(object)) {
				switch( ((GPowerUp)object).getPowerUpType() ) {
				case BOMB:
					maxBombs++;
					break;
				case POWER:
					bombPower++;
					break;
				case SPEED:
					speedCooldown = 5*30;
					position.incrementX(position.getX()%8);
					position.incrementY(position.getY()%8);
					break;
				default:
					break;
				}
				
				queue.put(new Action(ActionType.DESTROY, new Object[]{object.getId()}));
				
				break;
			}
		}
		
		/*
		 * SPEED COOLDOWN
		 */
		
		if(speedCooldown > 0) {
			speedCooldown--;
			speed = 8;
			if(speedCooldown == 0) {
				position.incrementX(position.getX()%4);
				position.incrementY(position.getY()%4);
				speed = 4;
			}
		}
		
		
		/*
		 * BOMB COOLDOWN
		 */
		if(--bombCooldown <= 0) {
			if(bombs < maxBombs) {
				bombs++;
			}
			bombCooldown = 30*3;
		}
		
		/*
		 * UPDATE POSITION
		 */
		if(w) {
			Position oldPosition = position.clonePos();
			position.decrementY(speed);
			for(GameObject object : manager.getByType(GameObjectType.SOLID_BLOCK, GameObjectType.DESTROYBALE_BLOCK)) {
				if(checkCollisionWith(object)) {
					position = oldPosition;
				} else {
					state = BombermanState.UP;
					updatePosition = true;
				}
			}
			
		}
		if(a) {
			Position oldPosition = position.clonePos();
			position.decrementX(speed);
			for(GameObject object : manager.getByType(GameObjectType.SOLID_BLOCK, GameObjectType.DESTROYBALE_BLOCK)) {
				if(checkCollisionWith(object)) {
					position = oldPosition;
				} else {
					state = BombermanState.LEFT;
					updatePosition = true;
				}
			}
			
		}
		if(s) {
			Position oldPosition = position.clonePos();
			position.incrementY(speed);
			for(GameObject object : manager.getByType(GameObjectType.SOLID_BLOCK, GameObjectType.DESTROYBALE_BLOCK)) {
				if(checkCollisionWith(object)) {
					position = oldPosition;
				} else {
					state = BombermanState.DOWN;
					updatePosition = true;
				}
			}
			
		}
		if(d) {
			Position oldPosition = position.clonePos();
			position.incrementX(speed);
			for(GameObject object : manager.getByType(GameObjectType.SOLID_BLOCK, GameObjectType.DESTROYBALE_BLOCK)) {
				if(checkCollisionWith(object)) {
					position = oldPosition;
				} else {
					state = BombermanState.RIGHT;
					updatePosition = true;
				}
			}
			
		}
		if(enter) {
			if(bombs > 0 && canPlace) {
				boolean collision = false;
				for(GameObject object: manager.getByType(GameObjectType.BOMB)) {
					if(checkCollisionWith(object)) {
						collision = true;
						break;
					}
				}
				if(!collision) {
					Position pos = new Position((position.getX()+32)/64*64, (position.getY()+32)/64*64);
					Action action = new Action(ActionType.CREATE_BOMB, new Object[]{pos, IDGenerator.getId(), bombPower, id});
					queue.put(action);
					bombs--;
					canPlace = false;
					System.out.println("placed");
				}
			}
		}
		
		if(updatePosition == false) {
			switch(state) {
			case DOWN:
				state = BombermanState.IDLE_DOWN;
				updatePosition = true;
				break;
			case LEFT:
				state = BombermanState.IDLE_LEFT;
				updatePosition = true;
				break;
			case RIGHT:
				state = BombermanState.IDLE_RIGHT;
				updatePosition = true;
				break;
			case UP:
				state = BombermanState.IDLE_UP;
				updatePosition = true;
				break;
			default:
				break;
			
			}
		}
	}

	
	public void update(Action action) {
		
		switch(action.getActionType()) {
		
		case PLAYER_INPUT:
			
			int key = (int)action.getProperty(1);
			System.out.println("input: " + key);
			boolean pressed = (boolean)action.getProperty(2);
			if(key == KeyEvent.VK_W)
				w = pressed;
			if(key == KeyEvent.VK_A)
				a = pressed;
			if(key == KeyEvent.VK_S)
				s = pressed;
			if(key == KeyEvent.VK_D)
				d = pressed;
			if(key == KeyEvent.VK_ENTER) {
				enter = pressed;
				if(pressed == false)
					canPlace = true;
			}	
			break;
		default:
			break;
		}
		
	}

	
	public void sendUpdates(NetworkFacade network) {
		if(updatePosition) {
			Action action = new Action(ActionType.MOVEMENT, new Object[]{id, position, state});
			network.sendMessage(ActionSerializer.serialize(action));
			updatePosition = false;
		}
		
	}

	@Override
	public void tick(ActionQueue queue, GameObjectManager manager) {}
	
	public BombermanState getState() {
		return state;
	}
	
	public void addBomb() {
		bombs++;
	}
	
	public void setFullPowerups() {
		bombPower = 3;
		maxBombs = 4;
		speed = 8;
		position.incrementX(position.getX()%8);
		position.incrementY(position.getY()%8);
	}

}
