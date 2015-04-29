package io.zonk.jbomberman.game.client;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.utils.Position;
import java.awt.Dimension;

public class SExplosion extends Sprite {

	public SExplosion(Position position, int id) {
		super(position, id, "IMG_EXPLOSION", new Dimension(64, 64), 7);
		frameCnt = 5;
	}

	@Override
	public void update(Action action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		super.tick();
	}
}
