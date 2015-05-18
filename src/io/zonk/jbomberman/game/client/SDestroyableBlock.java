package io.zonk.jbomberman.game.client;

import io.zonk.jbomberman.utils.Position;
import java.awt.Dimension;

public class SDestroyableBlock extends Sprite {
	
	public SDestroyableBlock(Position position, int id) {
		super(position, id, "IMG_DESTROYABLEBLOCK", new Dimension(64, 64), 3);
	}

	@Override
	public void tick() {}
}
