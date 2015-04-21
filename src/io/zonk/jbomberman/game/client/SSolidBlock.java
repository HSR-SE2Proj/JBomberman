package io.zonk.jbomberman.game.client;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.utils.ImageManager;
import io.zonk.jbomberman.utils.Position;

import java.awt.Dimension;

public class SSolidBlock extends Sprite {
	private String imageName;
	private Position position;
	private int width, height;
	public SSolidBlock(Position position, int id, Dimension size) {
		super(position, id, "IMG_SOLIDBLOCK", size, 2);
		//this.imageName = "SolidBlock";
	}

	@Override
	public void update(Action action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	
	public void draw(int[] screen) {
		int[] pixels = new int[64*64];
		ImageManager.getInstance().get(imageName).getRGB(0, 0, 64, 64, pixels, 0, 64);
		for(int y = position.getY(); y < position.getY()+height; y++) {
			if(y < 0 || y >= 832) continue;
			for(int x = position.getX(); x < position.getX()+width; x++) {
				if(x < 0 || x >= 832) continue;
				screen[x + y * 832] = pixels[(x-position.getX()) + (y - position.getY()) * width];
			}
		}
	}

}
