package io.zonk.jbomberman.game.client;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.GameLoop;
import io.zonk.jbomberman.game.Party;

import java.awt.Graphics2D;
import java.util.Observable;

public class ClientGame extends Observable 
	implements GameLoop {
	
	private SpriteManager manager;
	private ActionQueue queue;
	private Party party;
	
	public ClientGame(SpriteManager manager, ActionQueue queue, Party party) {
		this.manager = manager;
		this.queue = queue;
		this.party = party;
	}

	@Override
	public void loop() {
		
		while(true) { //game running?
			
			//Handle all available Actions
			while(!queue.isEmpty()) {
				Action action = queue.take();
				switch(action.getActionType()) {
				default:
					break;
				}
				
			}
			
			//Tick all Sprites
			for(Sprite sprite : manager.getAll()) {
				sprite.tick();
			}
			
			setChanged();
			notifyObservers();
			
		}
	}
	
	public void drawAll(Graphics2D g2d) {
		//draw Sprites layer by layer
	}
}






