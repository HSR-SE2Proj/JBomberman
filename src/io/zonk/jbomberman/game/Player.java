package io.zonk.jbomberman.game;

import io.zonk.jbomberman.game.server.GBomberman;

public class Player {
	
	private String name;
	private int id;
	private GBomberman bman;
	
	public Player(String name, int id, GBomberman bman) {
		this.name = name;
		this.id = id;
		this.bman = bman;
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
}
