package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.utils.Position;

public class GFloor extends GameObject {

	public GFloor(Position position, int id) {
		super(position, id, GameObjectType.FLOOR);
	}

	@Override
	public void tick(ActionQueue queue, GameObjectManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Action action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendUpdates(NetworkFacade network) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkCollisionWith(GameObject object) {
		// TODO Auto-generated method stub
		return false;
	}

}
