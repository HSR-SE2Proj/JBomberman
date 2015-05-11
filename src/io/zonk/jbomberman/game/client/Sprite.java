package io.zonk.jbomberman.game.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.utils.ImageManager;
import io.zonk.jbomberman.utils.Position;

public abstract class Sprite {
	
	protected Position position;
	private int id;
	protected String imgName;
	protected Dimension size;
	private int layer;
	
	private int cnt;
	protected int frameCnt = 1, frameStep = 5, animState = 0, frameNr;
	
	public Sprite(Position position, int id, String imgName, Dimension size, int layer) {
		this.position = position;
		this.id = id;
		this.imgName = imgName;
		this.size = size;
		this.layer = layer;
	}
	/**
	 * Interpretiert die Action und führt die nötigen Aktualisierungsschritte durch.
	 * @param action 
	 */
	public abstract void update(Action action);
	/**
	 * Aktualisiert das Sprite. Wird hauptsächlich für Animationen benötigt
	 */
	public void tick() {
		if(frameStep != 0) {
			if(frameStep == cnt) {
				cnt = 0;
				frameNr++;
				if(frameNr > frameCnt -1) {
					frameNr = 0;
				}
			}
			cnt++;
		}
	}
	/**
	 * Zeichnet sich selbst auf den screen. Diese Methode wird vom 
	 * GameCanvas aufgerufen und liefert seine Graphics mit auf dem 
	 * gezeichnet werden kann.
	 * @param g Graphics
	 */
	public void draw(Graphics g) {
		g.drawImage(ImageManager.getInstance().get(imgName).getSubimage(frameNr*size.width, animState*size.height, size.width, size.height), position.getX(), position.getY(), null);
	}
	
	public void draw(int[] screen) {
		
		
		
		int[] pixels = new int[size.width*size.height];
		ImageManager.getInstance().get(imgName).getRGB(0, 0, size.width, size.height, pixels, 0, size.width);
		for(int y = position.getY(); y < position.getY()+size.height; y++) {
			if(y < 0 || y >= 832) continue;
			for(int x = position.getX(); x < position.getX()+size.width; x++) {
				if(x < 0 || x >= 832) continue;
				//if(pixels[(x-position.getX()) + (y - position.getY()) * size.width])
					screen[x + y * 832] = pixels[(x-position.getX()) + (y - position.getY()) * size.width];
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
