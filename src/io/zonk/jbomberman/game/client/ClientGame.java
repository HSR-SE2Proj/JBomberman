package io.zonk.jbomberman.game.client;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionDispatcher;
import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.GameLoop;
import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.time.Timer;

import java.awt.Graphics2D;
import java.util.Observable;

public class ClientGame extends Observable 
	implements GameLoop {
	
	private NetworkFacade network;
	private SpriteManager manager;
	private ActionQueue queue;
	private Party party;
	
	public ClientGame(NetworkFacade network, Party party) {
		this.network = network;
		this.party = party;
		
		manager = new SpriteManager();
		
		queue = new ActionQueue();
		ActionDispatcher dispatcher = new ActionDispatcher(network, queue);
		dispatcher.start();
		
		//Timer timer = new Timer(1000/30, this);
		//timer.start();
	}

	@Override
	public void loop() {
		System.out.println("Client Game Running");
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






