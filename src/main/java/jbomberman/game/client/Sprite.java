package jbomberman.game.client;

import java.awt.Dimension;
import java.awt.Graphics;
import jbomberman.utils.ImageManager;
import jbomberman.utils.Position;

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
