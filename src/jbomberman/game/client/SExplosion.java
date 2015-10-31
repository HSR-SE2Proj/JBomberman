package jbomberman.game.client;

import jbomberman.utils.Position;
import java.awt.Dimension;

public class SExplosion extends Sprite {

	public SExplosion(Position position, int id) {
		super(position, id, "IMG_EXPLOSION", new Dimension(64, 64), 7);
		frameCnt = 5;
	}

	@Override
	public void tick() {
		super.tick();
	}
}
