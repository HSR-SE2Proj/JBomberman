package io.zonk.jbomberman.game;

import java.io.Serializable;
import java.util.HashMap;

public class Party implements Serializable{
	private static final long serialVersionUID = 1417598965327204906L;
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
		
	public Player getOverallWinner() {
		int tempscore = 0;
		Player temp = new Player("Temp",5);
		for(Player player : getPlayers().values()) {
			if (player.getScore() > tempscore) {
				tempscore = player.getScore();
				temp = player;
			}
		}
		return temp;
	}
	
	public HashMap<Integer, Player> getPlayers() {
		return playerList;
	}
}
