package jbomberman.game.client;

import jbomberman.utils.Position;
import java.awt.Dimension;

public class SFloor extends Sprite {

	public SFloor(Position position, int id) {
		super(position, id, "IMG_FLOOR", new Dimension(64, 64), 1);
	}

	@Override
	public void tick() {}
}
