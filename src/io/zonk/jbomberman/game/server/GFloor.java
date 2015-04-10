package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.utils.Position;

public class GFloor extends GameObject {

	public GFloor(Position position, int id) {
		super(position, id, GameObjectType.FLOOR);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Action action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendUpdates() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkCollisionWidth(GameObject object) {
		// TODO Auto-generated method stub
		return false;
	}

}
