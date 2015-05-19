package io.zonk.jbomberman.game.server;

import java.util.Random;
import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.GameObjectType;
import io.zonk.jbomberman.game.PowerUpType;
import io.zonk.jbomberman.utils.IDGenerator;
import io.zonk.jbomberman.utils.Position;

public class GDestroyableBlock extends GameObject {
	
	private Random rnd = new Random();

	public GDestroyableBlock(Position position, int id) {
		super(position, id, GameObjectType.DESTROYBALE_BLOCK);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick(ActionQueue queue, GameObjectManager manager) {
		for(GameObject object : manager.getByType(GameObjectType.EXPLOSION)) {
			if(checkCollisionWith(object)) {
				
				queue.put(new Action(ActionType.DESTROY, new Object[]{id}));
				
				if(rnd.nextInt(100) > 70) {
					
					PowerUpType type;
					
					int rint = rnd.nextInt(120);
					if(rint > 80)
						type = PowerUpType.BOMB;
					else if(rint > 40)
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
