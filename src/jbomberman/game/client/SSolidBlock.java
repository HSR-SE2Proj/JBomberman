package jbomberman.game.client;

import jbomberman.utils.Position;
import java.awt.Dimension;

public class SSolidBlock extends Sprite {
	
	public SSolidBlock(Position position, int id) {
		super(position, id, "IMG_SOLIDBLOCK", new Dimension(64, 64), 2);
	}

	@Override
	public void tick() {}
}
