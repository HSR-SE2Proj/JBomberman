package io.zonk.jbomberman.game.server;

import java.awt.event.KeyEvent;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.utils.ActionSerializer;
import io.zonk.jbomberman.utils.Position;

public class GBomberman extends GameObject {
	
	private boolean w, a, s, d, enter;
	private int speed = 4;
	private boolean updatePosition;

	public GBomberman(Position position, int id) {
		super(position, id, GameObjectType.BOMBERMAN);
	}

	@Override
	public void tick() {
		//Update Position
		if(w) {
			position.decrementY(speed);
			updatePosition = true;
		}
		if(a) {
			position.decrementX(speed);
			updatePosition = true;
		}
		if(s) {
			position.incrementY(speed);
			updatePosition = true;
		}
		if(d) {
			position.incrementX(speed);
			updatePosition = true;
		}
		if(enter) {
			//place bomb
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
	public boolean checkCollisionWith(GameObject object) {
		// TODO Auto-generated method stub
		return false;
	}

}
