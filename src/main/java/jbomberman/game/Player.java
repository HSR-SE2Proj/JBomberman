package jbomberman.game;

import java.io.Serializable;

public class Player implements Serializable{
	private static final long serialVersionUID = 5032439795920907379L;
	private String name;
	private int id;
	private int score;
	
	public Player(String name, int id) {
		this.name = name;
		this.id = id;
		this.score = 0;
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
	
	public void addScore() {
		this.score++;
	}
	
	public int getScore() {
		return this.score;
	}
}
