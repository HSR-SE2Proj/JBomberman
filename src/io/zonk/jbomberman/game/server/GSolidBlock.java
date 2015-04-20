package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.utils.Position;

public class GSolidBlock extends GameObject {

	public GSolidBlock(Position position, int id) {
		super(position, id, GameObjectType.SOLID_BLOCK);
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
	public boolean checkCollisionWith(GameObject object) {
		// TODO Auto-generated method stub
		return false;
	}

}
