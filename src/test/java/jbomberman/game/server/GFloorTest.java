package jbomberman.game.server;

import org.junit.Test;

import jbomberman.game.ActionQueue;
import jbomberman.utils.Position;

public class GFloorTest {
	@Test
	public void testGFloor() {
		GFloor floor = new GFloor(new Position(0,0), 1);
		floor.tick(new ActionQueue(), new GameObjectManager());
	}
}
