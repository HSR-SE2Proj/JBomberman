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
import java.util.Observer;
import java.util.Random;

public class ClientController extends Observable implements Observer  {
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
	private Timer timer;
	private GameCanvas gCanvas;
	
	public ClientController() {
		this.network = new ClientNetwork();
		this.party = new Party();
	}
	
	//Achtung GUI-Thread
	public void startGame() {
 		ClientGame game = new ClientGame(network, party);
 		game.addObserver(this);
 		timer = new Timer(1000/60, game);
 		Keyboard keyboard = new Keyboard(playerId, network);
 		gCanvas = new GameCanvas(game, keyboard);
 		timer.start();
 		
 		controllerState = ClientControllerState.GAME_STARTED;
		setChanged();
		notifyObservers("connChanged");
	}
	
	public void finishGame() {
		timer.run = false;
		gCanvas.dispose();
		Object monitoredObject = new Object();
		synchronized (monitoredObject) {
			try {
				monitoredObject.notifyAll();
				monitoredObject.wait(500);
			} catch (InterruptedException e) {
			}
		}
 		controllerState = ClientControllerState.GAME_FINISHED;
		setChanged();
		notifyObservers("connChanged");
		party = new Party();
		Object[] prop = {"finished"};
		send(prop);
	}
	
	private Action receiveLobby(int rand) {
		byte[] recMsg = network.receiveMessage(CONNECT_TIMEOUT);
		Action returnAction = ActionSerializer.deserialize(recMsg);
		int i = 0;
		while(returnAction == null || (((String)returnAction.getProperty(0)).equals("lobbyList") && 
				((Integer)returnAction.getProperty(3)) != rand && i < 5)) {
			Random randomGenerator = new Random();
	        rand = randomGenerator.nextInt(1000);
			Object[] prop = {"connect", rand};
			send(prop);
			recMsg = network.receiveMessage(CONNECT_TIMEOUT);
			returnAction = ActionSerializer.deserialize(recMsg);
			i++;
		}
		return returnAction;
	}
	
	public void connectToServer(String hostname) {
		this.server = hostname;
		network.connect(hostname);
		if(network.isOpen()) {
			Random randomGenerator = new Random();
	        int randomInt = randomGenerator.nextInt(1000);
			Object[] prop = {"connect", randomInt};
			send(prop);

			Action returnAction = receiveLobby(randomInt);
			if(returnAction != null) {

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

	@Override
	public void update(Observable o, Object arg) {
		if(arg != null && ((String)arg).equals("finishGame")) finishGame();
	}
}
