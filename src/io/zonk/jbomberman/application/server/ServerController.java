package io.zonk.jbomberman.application.server;

import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.game.server.ServerGame;
import io.zonk.jbomberman.network.NetworkFacade;
import java.util.Observable;
import java.util.Observer;

public class ServerController implements Observer {

	//state???
	private ServerGame game;
	private NetworkFacade network;
	private Party party;
	
	public ServerController(ServerGame game, NetworkFacade network, Party party) {
		this.game = game;
		this.network = network;
		this.party = party;
		game.addObserver(this);
	}
	
	public void startGame(Party party) {
		
	}
	
	public void finishGame() {
		
	}
	
	public void waitForPlayers() {
		
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}
}
