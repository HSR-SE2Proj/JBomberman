package io.zonk.jbomberman.application.client;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.game.Player;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.network.client.ClientNetwork;
import io.zonk.jbomberman.utils.ActionSerializer;

import java.util.Hashtable;
import java.util.Observable;
import java.util.Random;

public class ClientController extends Observable {
	private String server;
	private int connectionState = 0; //0 => Connect; 1 => Lobby
	
	String[][] states = {};
	int mePlayer = 0;
	
	private NetworkFacade network;
	private Party party;
	
	public ClientController() {
		this.network = new ClientNetwork();
		this.party = null;
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
		this.server = hostname;
		network.connect(hostname);
		if(network.isOpen()) {
		    Random randomGenerator = new Random();
			Object[] prop = {"Connect", randomGenerator.nextInt(1000) + 1000};
			Action connAction = new Action(ActionType.LOBBY_COMMUNICATION, prop);
			
			network.sendMessage(ActionSerializer.serialize(connAction));

			byte[] recMsg = network.receiveMessage();
			while(recMsg.length < 1) {
				recMsg = network.receiveMessage();
			}
			Action returnAction = ActionSerializer.deserialize(recMsg);
			if(((String)returnAction.getProperty(0)).equals("LobbyList")) {
				states = (String[][])returnAction.getProperty(1);
				mePlayer = (Integer)returnAction.getProperty(2);
				
				connectionState = 1;	
				setChanged();
				notifyObservers(connectionState);
			}
		}
	}

	public void disconnect() {
		network.close();
		connectionState = 0;
		setChanged();
		notifyObservers();
	}
	
	public void setReady(boolean b) {
	}
	
	public String getServer() {
		return server;
	}
	
	public int getConnState() {
		return connectionState;
	}
	
	public String[][] getStates() {
		return states;
	}

	public int getMePlayer() {
		return mePlayer;
	}
}
