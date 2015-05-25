package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.game.PowerUpType;
import io.zonk.jbomberman.utils.IDGenerator;
import io.zonk.jbomberman.utils.Position;
import io.zonk.jbomberman.utils.RandomUtil;

public class GDestroyableBlock extends GameObject {

	public GDestroyableBlock(Position position, int id) {
		super(position, id, GameObjectType.DESTROYBALE_BLOCK);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick(ActionQueue queue, GameObjectManager manager) {
		for(GameObject object : manager.getByType(GameObjectType.EXPLOSION)) {
			if(checkCollisionWith(object)) {
				
				queue.put(new Action(ActionType.DESTROY, new Object[]{id}));
				
				if(RandomUtil.probability(30)) {
					
					PowerUpType type;
					
					if(RandomUtil.probability(33))
						type = PowerUpType.BOMB;
					else if(RandomUtil.probability(50))
						type = PowerUpType.POWER;
					else
						type = PowerUpType.SPEED;
					
					queue.put(new Action(ActionType.CREATE_POWERUP, new Object[]{position, IDGenerator.getId(), type}));
					System.out.println("POWERUP");
				}
				
				
				
				System.out.println(object.getPosition().getX() + ", " + object.getPosition().getY());
				break;
				
			}
		}
	}
}
