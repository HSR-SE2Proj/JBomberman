package io.zonk.jbomberman.game.client;

import java.awt.Dimension;
import java.awt.Graphics2D;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.utils.ImageManager;
import io.zonk.jbomberman.utils.Position;

public abstract class Sprite {
	
	protected Position position;
	private int id;
	protected String imgName;
	private Dimension size;
	private int layer;
	
	public Sprite(Position position, int id, String imgName, Dimension size, int layer) {
		this.position = position;
		this.id = id;
		this.imgName = imgName;
		this.size = size;
		this.layer = layer;
	}
	
	public abstract void update(Action action);
	public abstract void tick();
	
	public void draw(int[] screen) {
		int[] pixels = new int[64*64];
		ImageManager.getInstance().get(imgName).getRGB(0, 0, 64, 64, pixels, 0, 64);
		for(int y = position.getY(); y < position.getY()+64; y++) {
			if(y < 0 || y >= 832) continue;
			for(int x = position.getX(); x < position.getX()+64; x++) {
				if(x < 0 || x >= 832) continue;
				screen[x + y * 832] = pixels[(x-position.getX()) + (y - position.getY()) * 64];
			}
		}
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getLayer() {
		return layer;
	}
}
