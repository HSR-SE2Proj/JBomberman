package io.zonk.jbomberman.application.client;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionSerializer;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.game.Timer;
import io.zonk.jbomberman.game.client.ClientGame;
import io.zonk.jbomberman.game.client.Keyboard;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.network.client.ClientNetwork;
import io.zonk.jbomberman.utils.RandomUtil;
import io.zonk.jbomberman.utils.TimeUtil;
import io.zonk.jbomberman.view.GameCanvas;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class ClientController extends Observable implements Observer  {
	private String server;
	private ClientControllerState controllerState = ClientControllerState.DISCONNECT;

	public static final int CLIENT_LOOPTIMER = 1000/60;
	private static final int CONNECT_TIMEOUT = 5000;
	private int countdown = 0;
	private int round = 1;
	private HashMap<Integer, Boolean> states;
	
	// Player this instance is associated  with
	private int playerId = 0;
	
	private NetworkFacade network;
	private Timer timer;
	private GameCanvas gCanvas;
	
	public ClientController() {
		this.network = new ClientNetwork();
	}
	
	/**
	 * Erstellt eine GameFrame, welches die ClientGame 
	 * Klasse instanziert und somit das Spiel startet.
	 */
	public void startGame(Party party) {		
 		ClientGame game = new ClientGame(network, party);
 		game.addObserver(this);
 		
 		
 		
 		Keyboard keyboard = new Keyboard(playerId, network);
 		gCanvas = new GameCanvas(game, keyboard, party, round);
 		gCanvas.requestFocus();
 		timer = new Timer(CLIENT_LOOPTIMER, game);
 		timer.start();
 		controllerState = ClientControllerState.GAME_STARTED;
		setChanged();
		notifyObservers("connChanged");
	}
	/**
	 * Informiert den LobbyFrame darüber, 
	 * dass das Spiel beendet wurde.
	 */
	public void finishGame() {
		timer.run = false;
		this.round = 1;
		gCanvas.dispose();
 		controllerState = ClientControllerState.GAME_FINISHED;
		setChanged();
		notifyObservers("connChanged");
		Object[] prop = {"finished"};
		send(prop);
	}
	
	public void finishRound() {
		gCanvas.render();
		timer.run = false;
		new TimeUtil().sleepFor(2000);
		gCanvas.dispose();

		Action returnAction = ActionSerializer.deserialize(network.receiveMessage());
		while(returnAction != null && returnAction.getActionType() != ActionType.LOBBY_COMMUNICATION) {
			returnAction = ActionSerializer.deserialize(network.receiveMessage());
		}
		if(returnAction != null && ((String)returnAction.getProperty(0)).equals("startGame")) {
			round = (Integer)returnAction.getProperty(2);
			startGame((Party) returnAction.getProperty(1));
		}
	}
	
	/**
	 * Versucht bis zu 5 mal sich mit dem Server zu verbinden
	 * @param rand ConnectId
	 * @return
	 */
	private Action receiveLobby(int rand) {
		byte[] recMsg = network.receiveMessage(CONNECT_TIMEOUT);
		Action returnAction = ActionSerializer.deserialize(recMsg);
		int i = 0;
		while(returnAction == null || (((String)returnAction.getProperty(0)).equals("lobbyList") && 
				((Integer)returnAction.getProperty(3)) != rand && i < 5)) {
	        rand = RandomUtil.getRandomInt(1000);
			Object[] prop = {"connect", rand};
			send(prop);
			recMsg = network.receiveMessage(CONNECT_TIMEOUT);
			returnAction = ActionSerializer.deserialize(recMsg);
			i++;
		}
		return returnAction;
	}
	
	/**
	 * Verbindet den Client mit einem RabbitMQ Broker.
	 * @param hostname FQDN des Servers
	 */
	
	public void connectToServer(String hostname) {
		this.server = hostname;
		network.connect(hostname);
		if(network.isOpen()) {
	        int randomInt = RandomUtil.getRandomInt(1000);
			Object[] prop = {"connect", randomInt};
			send(prop);

			Action returnAction = receiveLobby(randomInt);
			if(returnAction != null) {

				if(((String)returnAction.getProperty(0)).equals("lobbyList")) {
					states = (HashMap<Integer, Boolean>)returnAction.getProperty(1);
					playerId = (Integer)returnAction.getProperty(2);
					
					controllerState = ClientControllerState.CONNECTED;
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
				startGame((Party)returnAction.getProperty(1));
				break;
	
			case "aliveCheck":
				Object[] prop = {"alive", playerId};
				send(prop);
				send(prop);
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
		new Thread(() -> {
				msgReceived(network.receiveMessage());
		}).start();
	}
	
	private void send(Object[] prop) {
		Action connAction = new Action(ActionType.LOBBY_COMMUNICATION, prop);
		network.sendMessage(ActionSerializer.serialize(connAction));
	}
	/**
	 * Schliesst die Verbindung mit dem RabbitMQ Broker
	 */
	public void disconnect() {
		Object[] prop = {"disconnect", playerId};
		send(prop);
		network.close();
		controllerState = ClientControllerState.DISCONNECT;
		setChanged();
		notifyObservers("connChanged");
	}
	
	/**
	 * Setzt den Spieler auf bereit.
	 * @param b Boolean
	 */
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
		if(arg != null && ((String)arg).equals("roundFinished")) finishRound();
		if(arg != null && ((String)arg).equals("gameFinished")) finishGame();
	}

	
}
