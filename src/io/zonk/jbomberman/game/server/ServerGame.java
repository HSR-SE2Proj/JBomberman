package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionDispatcher;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.GameLoop;
import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.game.Player;
import io.zonk.jbomberman.network.NetworkFacade;

import java.util.Observable;

public class ServerGame extends Observable implements GameLoop {

	private NetworkFacade network;
	private ActionQueue queue;
	private GameObjectManager manager;
	private Party party;
	
	public ServerGame(NetworkFacade network, Party party) {
		this.network = network;
		this.party = party;
		
		for(Player player : party.getPlayers()) {
			
		}
		
		queue = new ActionQueue();
		ActionDispatcher dispatcher = new ActionDispatcher(network, queue);
		dispatcher.start();
	}

	@Override
	public void loop() {
		
		while(true) { //game running?
			
			//handle all available Actions
			while(!queue.isEmpty()) {
				Action action = queue.take();
				switch(action.getActionType()) {
				
				/*
				 * 
				 */
				case PLAYER_INPUT:
					int id = (int) action.getProperty(0);
					party.get(id).getBomberman().update(action);
					break;
					
					
				default:
					break;
				
				}
			}
			
			//Tick all Objects
			for(GameObject object : manager.getAll()) {
				object.tick();
			}
			
			//send Updates
			
		}
		
	}
}
