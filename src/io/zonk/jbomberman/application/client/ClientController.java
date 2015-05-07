package io.zonk.jbomberman.application.client;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.game.Player;
import io.zonk.jbomberman.game.client.ClientGame;
import io.zonk.jbomberman.game.client.Keyboard;
import io.zonk.jbomberman.network.client.ClientNetwork;
import io.zonk.jbomberman.time.Timer;
import io.zonk.jbomberman.utils.ActionSerializer;
import io.zonk.jbomberman.view.ClientControllerState;
import io.zonk.jbomberman.view.GameCanvas;

import java.util.HashMap;
import java.util.Observable;

public class ClientController extends Observable {
	private String server;
	private Thread t;
	private ClientControllerState controllerState = ClientControllerState.CONNECT;
	
	private static final int CONNECT_TIMEOUT = 5000;
	private int countdown = 0;
	
	HashMap<Integer, Boolean> states;
	
	// Player this instance is associated  with
	int playerId = 0;
	
	private ClientNetwork network;
	private Party party;
	
	public ClientController() {
		this.network = new ClientNetwork();
		this.party = new Party();
	}
	
	//Achtung GUI-Thread
	public void startGame() {
 		ClientGame game = new ClientGame(network, party);
 		Timer timer = new Timer(1000/60, game);
 		Keyboard keyboard = new Keyboard(playerId, network);
		new GameCanvas(game, keyboard);
 		timer.start();
 		
 		controllerState = ClientControllerState.GAME_STARTED;
		setChanged();
		notifyObservers("connChanged");
	}
	
	public void finishGame() {
 		controllerState = ClientControllerState.GAME_FINISHED;
		setChanged();
		notifyObservers("connChanged");
	}
	
	public void connectToServer(String hostname) {
		this.server = hostname;
		network.connect(hostname);
		if(network.isOpen()) {
			Object[] prop = {"connect"};
			send(prop);

			byte[] recMsg = network.receiveMessage(CONNECT_TIMEOUT);
			if(recMsg != null) {
				while(recMsg.length < 1) {
					recMsg = network.receiveMessage();
				}

				Action returnAction = ActionSerializer.deserialize(recMsg);
				if(((String)returnAction.getProperty(0)).equals("lobbyList")) {
					states = (HashMap<Integer, Boolean>)returnAction.getProperty(1);
					playerId = (Integer)returnAction.getProperty(2);
					
					controllerState = ClientControllerState.LOBBY;
					setChanged();
					notifyObservers("connChanged");

					startReceiving();
				} else if(((String)returnAction.getProperty(0)).equals("serverFull")) {
					controllerState = ClientControllerState.CONN_POP_FULL;
					setChanged();
					notifyObservers("connChanged");
				}
			} else {
				controllerState = ClientControllerState.CONN_POP_NF;
				setChanged();
				notifyObservers("connChanged");
			}
		}
	}
	
	public void msgReceived(byte[] recMsg) {
		Action returnAction = ActionSerializer.deserialize(recMsg);
		String s = ((String)returnAction.getProperty(0));

		if(s != null){
			switch (s) {
			case "updateStates":
			case "lobbyList":
				states = (HashMap<Integer, Boolean>)returnAction.getProperty(1);

				setChanged();
				notifyObservers();
				break;
	
			case "countUpdate":
				countdown = (Integer)returnAction.getProperty(1);
				setChanged();
				notifyObservers();
				break;
	
			case "finish":
				
				break;
	
			case "startGame":
				String[][] sParty = (String[][])returnAction.getProperty(1);
				for(String[] p : sParty) {
					if(p[0] != null && p[1] != null) party.add(new Player(p[0], Integer.parseInt(p[1])));
				}
				startGame();
				break;
	
			default:
				break;
			}
			
			if(!s.equals("startGame")) startReceiving();
		} else {
			startReceiving();
		}
	}
	
	private void startReceiving() {
		t = new Thread(() -> {
				msgReceived(network.receiveMessage());
		});
		t.start();
	}
	
	private void send(Object[] prop) {
		Action connAction = new Action(ActionType.LOBBY_COMMUNICATION, prop);
		network.sendMessage(ActionSerializer.serialize(connAction));
	}

	public void disconnect() {
		Object[] prop = {"disconnect", playerId};
		send(prop);
		network.close();
		controllerState = ClientControllerState.CONNECT;
		setChanged();
		notifyObservers("connChanged");
	}
	
	public void setReady(boolean b) {
		Object[] prop = {"updateState", playerId, b};
		send(prop);
	}
	
	public String getServer() {
		return server;
	}
	
	public ClientControllerState getConnState() {
		return controllerState;
	}
	
	public HashMap<Integer, Boolean> getStates() {
		return states;
	}

	public int getPlayerId() {
		return playerId;
	}
	
	public int getCountdown() {
		return countdown;
	}
}
