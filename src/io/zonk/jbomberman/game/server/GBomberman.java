package io.zonk.jbomberman.game.server;

import java.awt.event.KeyEvent;
import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.utils.ActionSerializer;
import io.zonk.jbomberman.utils.IDGenerator;
import io.zonk.jbomberman.utils.Position;

public class GBomberman extends GameObject {
	
	private boolean w, a, s, d, enter;
	private int speed = 8;
	private boolean updatePosition;
	
	private int bombPower = 1;
	private int bombs = 1;
	
	public GBomberman(Position position, int id) {
		super(position, id, GameObjectType.BOMBERMAN);
	}
	
	public void tick(GameObjectManager manager, ActionQueue queue) {
		
		
		
		
		
		if(w) {
			Position oldPosition = position.clone();
			position.decrementY(speed);
			for(GameObject object : manager.getByType(GameObjectType.SOLID_BLOCK, GameObjectType.DESTROYBALE_BLOCK)) {
				if(checkCollisionWith(object)) {
					position = oldPosition;
				}
			}
			updatePosition = true;
		}
		if(a) {
			Position oldPosition = position.clone();
			position.decrementX(speed);
			for(GameObject object : manager.getByType(GameObjectType.SOLID_BLOCK, GameObjectType.DESTROYBALE_BLOCK)) {
				if(checkCollisionWith(object)) {
					position = oldPosition;
				}
			}
			updatePosition = true;
		}
		if(s) {
			Position oldPosition = position.clone();
			position.incrementY(speed);
			for(GameObject object : manager.getByType(GameObjectType.SOLID_BLOCK, GameObjectType.DESTROYBALE_BLOCK)) {
				if(checkCollisionWith(object)) {
					position = oldPosition;
				}
			}
			updatePosition = true;
		}
		if(d) {
			Position oldPosition = position.clone();
			position.incrementX(speed);
			for(GameObject object : manager.getByType(GameObjectType.SOLID_BLOCK, GameObjectType.DESTROYBALE_BLOCK)) {
				if(checkCollisionWith(object)) {
					position = oldPosition;
				}
			}
			updatePosition = true;
		}
		if(enter) {
			if(bombs > 0) {
				Position pos = new Position(position.getX()/64*64, position.getY()/64*64);
				Action action = new Action(ActionType.CREATE_BOMB, new Object[]{pos, IDGenerator.getId(), bombPower, id});
				queue.put(action);
				bombs--;
			}
		}
	}

	@Override
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
			if(key == KeyEvent.VK_ENTER)
				enter = pressed;
			break;
			
			
			
		default:
			break;
		}
		
	}

	@Override
	public void sendUpdates(NetworkFacade network) {
		if(updatePosition) {
			Action action = new Action(ActionType.MOVEMENT, new Object[]{id, position});
			network.sendMessage(ActionSerializer.serialize(action));
			updatePosition = false;
		}
		
	}

	@Override
	public void tick(ActionQueue queue, GameObjectManager manager) {
		// TODO Auto-generated method stub
		
	}
	
	public void addBomb() {
		bombs++;
	}

}
