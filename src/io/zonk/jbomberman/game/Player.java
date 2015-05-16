package io.zonk.jbomberman.game;

import io.zonk.jbomberman.game.server.GBomberman;

public class Player {
	
	private String name;
	private int id;
	private GBomberman bman;
	private int score;
	
	public Player(String name, int id) {
		this.name = name;
		this.id = id;
		this.score = 0;
		//bman = new GBomberman(null, id); //Position noch irgendwie setzten
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public GBomberman getBomberman() {
		return bman;
	}
	
	public void setBomberman(GBomberman bman) {
		this.bman = bman;
	}
	
	public void addScore() {
		this.score++;
	}
	
	public int getScore() {
		return this.score;
	}
}
