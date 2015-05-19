package io.zonk.jbomberman.game;

import java.util.HashMap;

public class Party {
	private HashMap<Integer, Player> playerList = new HashMap<>();
	
	public void add(Player player) {
		playerList.put(player.getId(), player);
	}
	
	public Player remove(Player player){
		return playerList.remove(player.getId());
	}
	
	public Player get(int id) {
		return playerList.get(id);
	}
	
	public Player getWinner() {
		for (Player player : getPlayers().values()){
			if(player.getBomberman().getState() != BombermanState.DEAD) {
				return player;
			}
		}
		return null;
	}
	
	public HashMap<Integer, Player> getPlayers() {
		return playerList;
	}
}
