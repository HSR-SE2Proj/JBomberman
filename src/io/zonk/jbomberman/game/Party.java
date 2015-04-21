package io.zonk.jbomberman.game;

import java.util.ArrayList;

public class Party {
	private ArrayList<Player> playerList = new ArrayList<>();
	
	public void add(Player player) {
		playerList.add(player);
	}
	
	public Player remove(Player player){
		return (playerList.remove(player)) ? player : null;
	}
	
	public Player get(int id) {
		return playerList.get(id-1);
	}
	
	public ArrayList<Player> getPlayers() {
		return playerList;
	}
}
