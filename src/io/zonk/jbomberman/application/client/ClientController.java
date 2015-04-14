package io.zonk.jbomberman.application.client;

import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.game.Player;
import io.zonk.jbomberman.network.NetworkFacade;

import java.util.Observable;

public class ClientController extends Observable {
	private String server;
	private int connectionState = 0; //0 => Connect; 1 => Lobby
	
	private NetworkFacade network;
	private Party party;
	
	public ClientController(NetworkFacade network, Party party) {
		this.network = network;
		this.party = party;
	}
	
	public void startGame() {
		
	}
	
	public void finishGame() {
		
	}
	
	public void addPlayer(Player player) {
		
	}
	
	public void removePlayer(Player player) {
		
	}
	
	public void connectToServer(String hostname) {
		this.server = server;
		connectionState = 1;	
		setChanged();
		notifyObservers(connectionState);
	}
	
	public void disconnect() {
		connectionState = 0;
		setChanged();
		notifyObservers(connectionState);
	}
	
	public void setReady(boolean b) {
		
	}
	
	public String getServer() {
		return server;
	}
}
