package io.zonk.jbomberman.game.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.GameLoop;
import io.zonk.jbomberman.game.Party;

import java.util.Observable;

public class ServerGame extends Observable implements GameLoop {

	private ActionQueue queue;
	private GameObjectManager manager;
	private Party party;
	
	public ServerGame(ActionQueue queue, GameObjectManager manager, Party party) {
		this.queue = queue;
		this.manager = manager;
		this.party = party;
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
