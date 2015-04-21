package io.zonk.jbomberman.utils;

public class Position {
	
	private int x, y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Position() {
		this(0, 0);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void incrementY(int delta) {
		y+=delta;
	}
	
	public void incrementX(int delta) {
		x+=delta;
	}
	
	public void decrementY(int delta) {
		y-=delta;
	}
	
	public void decrementX(int delta) {
		x-=delta;
	}
}
