package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.utils.Position;

public class GSolidBlock extends GameObject {

	public GSolidBlock(Position position, int id) {
		super(position, id, GameObjectType.SOLID_BLOCK);
	}

	@Override
	public void tick(ActionQueue queue) {
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
		return true;
	}

}
