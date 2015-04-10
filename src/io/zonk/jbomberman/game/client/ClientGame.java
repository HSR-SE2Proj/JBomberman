package io.zonk.jbomberman.game.client;

import io.zonk.jbomberman.game.ActionQueue;
import io.zonk.jbomberman.game.GameLoop;
import io.zonk.jbomberman.game.Party;
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
		
	}
}
