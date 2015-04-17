package io.zonk.jbomberman.game.client;

import java.awt.Dimension;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.utils.Position;

public abstract class Sprite {
	
	private Position position;
	private int id;
	private String imgName;
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
