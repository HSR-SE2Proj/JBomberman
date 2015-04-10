package io.zonk.jbomberman.game.server;

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
		
	}
}
