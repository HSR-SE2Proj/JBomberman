package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.utils.Position;

public class GDestroyableBlock extends GameObject {

	public GDestroyableBlock(Position position, int id) {
		super(position, id, GameObjectType.DESTROYBALE_BLOCK);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick(ActionQueue queue, GameObjectManager manager) {
		for(GameObject object : manager.getByType(GameObjectType.EXPLOSION)) {
			if(checkCollisionWith(object)) {
				if(object instanceof GExplosion) {
					queue.put(new Action(ActionType.DESTROY, new Object[]{id}));
					//drop Powerup
					System.out.println(object.getPosition().getX() + ", " + object.getPosition().getY());
					break;
				}
			}
		}
		
	}

	@Override
	public void update(Action action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendUpdates(NetworkFacade network) {
		// TODO Auto-generated method stub
		
	}
}
