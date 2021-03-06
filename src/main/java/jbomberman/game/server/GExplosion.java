package jbomberman.game.server;

import jbomberman.game.Action;
import jbomberman.game.ActionQueue;
import jbomberman.game.ActionType;
import jbomberman.game.DirectionType;
import jbomberman.game.GameObjectType;
import jbomberman.utils.IDGenerator;
import jbomberman.utils.Position;

public class GExplosion extends GameObject {
	
	private int power;
	private DirectionType direction;
	private int timeToDie = 30 * 1;

	public GExplosion(Position position, int id, int power, DirectionType direction) {
		super(position, id, GameObjectType.EXPLOSION);
		this.power = power;
		this.direction = direction;
	}

	@Override
	public void tick(ActionQueue queue, GameObjectManager manager) {
		
		if(power > 0 && direction != DirectionType.NO_DIRECTION) {
			Position pos = position.clonePos();
			switch(direction) {
			case DOWN:
				pos.incrementY(64);
				System.out.println("explo tick");
				break;
			case LEFT:
				pos.decrementX(64);
				break;
			case RIGHT:
				pos.incrementX(64);
				break;
			case UP:
				pos.decrementY(64);
				break;
			default:
				break;
			
			}
			
			queue.put(new Action(ActionType.CREATE_EXPLOSION, new Object[]{pos, IDGenerator.getId(), power-1, direction}));
			power = 0;
		}
		
		if(timeToDie < 0) {
			queue.put(new Action(ActionType.DESTROY, new Object[]{id}));
		} else {
			timeToDie--;
		}
		
		
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	
	public int getPower() {
		return power;
	}
	
	public DirectionType getDirectionType() {
		return direction;
	}
}
