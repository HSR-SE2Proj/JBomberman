package io.zonk.jbomberman.application.server;

import io.zonk.jbomberman.game.Action;
import io.zonk.jbomberman.game.ActionType;
import io.zonk.jbomberman.game.Party;
import io.zonk.jbomberman.game.Player;
import io.zonk.jbomberman.game.server.ServerGame;
import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.network.server.ServerNetwork;
import io.zonk.jbomberman.time.TimeUtil;
import io.zonk.jbomberman.time.Timer;
import io.zonk.jbomberman.utils.ActionSerializer;
import io.zonk.jbomberman.utils.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class ServerController implements Observer {
	private static final int SERVER_LOOPTIMER = 1000/30;
	private static final int READY_THRESHOLD = 2;
	private static final int COUNTDOWN_TIME = 10;
	private static final int HEARTBEAT_TIMEOUT = 100;
	private int countdown = 10;
	private int readyCount = 0;
	
	private ServerGame game;
	private NetworkFacade network;
	private Party party;
	private Timer timer;
	private int round = 1;
	public static void main(String[] args) {
		new ServerController();
	}
	
	public ServerController() {
		this.network = new ServerNetwork();
		network.connect("localhost");
		this.party = new Party();
		
		waitForPlayers();
	}
	/**
	 * Erstellt die nötigen Klassen und startet das Spiel
	 * @param party Party mit allen Spielern
	 */
	public void startGame(Party party) {
		Object[] start = {"startGame", party, round};
		sendLobbyUpdate(start);
		game = new ServerGame(network, party);
		game.addObserver(this);

		timer = new Timer(SERVER_LOOPTIMER, game);
		timer.start();
	}

	/**
	 * Entkoppelt die Spielrelevanten Klassen und geht in 
	 * die waitForPlayers Methode zurück.\\
	 */
	public void finishGame() {
		timer.run = false;
		Object[] finish = {"finishGame"};
		sendLobbyUpdate(finish);
		party = new Party();
		waitForPlayers();
	}
	
	
	public void finishRound() {
		displayBanner();
		if (round > 2) {
			new TimeUtil().sleepFor(3000);
			finishGame();
		} else {
			Object[] finish = {"finishRound"};
			sendLobbyUpdate(finish);
			new TimeUtil().sleepFor(2000);
			timer.run = false;
			++round;
			Object[] start = {};
			sendLobbyUpdate(start);
			startGame(party);
		}
	}

	public void displayBanner() {
		Action action = new Action(ActionType.CREATE_BANNER,
				new Object[] {new Position(256,320),party.getWinner().getId()});
		network.sendMessage(ActionSerializer.serialize(action));
	}
	/**
	 * Der Server befindet sich nach dem Starten in 
	 * dieser Methode und wartet bis mehr als ein Spieler 
	 * verbunden und bereit ist. Danach startet er das Spiel.
	 */
	public void waitForPlayers() {
		boolean countStarted = false;
		readyCount = 0;
		countdown = 10;
		HashMap<Integer, Boolean> playerStates = new HashMap<>();
		while (readyCount < READY_THRESHOLD || countdown > 0) {
			Action receivedAction = ActionSerializer.deserialize(network.receiveMessage());
			if(receivedAction.getActionType().equals(ActionType.LOBBY_COMMUNICATION)) {
				switch ((String)receivedAction.getProperty(0)) {
				case "connect":
					int pid = getNextId(playerStates);
					playerStates.put(pid, false);
					if(pid == 0) {
						Object[] prop = {"serverFull"};
						sendLobbyUpdate(prop);
						break;
					}

					Object[] prop = {"lobbyList", playerStates, pid, (Integer)receivedAction.getProperty(1)};
					sendLobbyUpdate(prop);

					Object[] c = {"countUpdate", countdown};
					sendLobbyUpdate(c);
					break;
					
				case "updateState":
					int playerId = (Integer)receivedAction.getProperty(1);
					Boolean playerState = (Boolean)receivedAction.getProperty(2);
					
					playerStates.put(playerId, playerState);
					updateStates(playerStates);
					if(playerState) {
						Player p = new Player("Player" + playerId, playerId);
						party.add(p);
						readyCount++;
						if(readyCount >= READY_THRESHOLD && !countStarted) {
							countStarted = true;
							startTimer();
						}
					} else {
						party.remove(party.get(playerId));
						readyCount--;
					}
					break;
					
				case "disconnect":
					readyCount--;
					int id = (Integer)receivedAction.getProperty(1);
					if(playerStates.get(id)) party.remove(party.get(id));
					playerStates.remove(id);
					updateStates(playerStates);
 					break;

				default:
					break;
				}
			}
		}
	}

	private void startTimer() {
		new Thread(() -> {
			Object monitoredObject = new Object();
			synchronized (monitoredObject) {
				try {
					while(countdown > 0) {
						monitoredObject.notifyAll();
						monitoredObject.wait(1000);
						if(readyCount >= READY_THRESHOLD) {
							countdown--;
						} else {
							countdown = COUNTDOWN_TIME;
						}
						Object[] c2 = {"countUpdate", countdown};
						sendLobbyUpdate(c2);
					}
				} catch (InterruptedException e) {
				}
			}
			checkPlayersAlive();
			startGame(party);
		}).start();
	}

	private void sendLobbyUpdate(Object[] prop) {
		Action replyAction = new Action(ActionType.LOBBY_COMMUNICATION, prop);
		network.sendMessage(ActionSerializer.serialize(replyAction));
	}
	
	private void updateStates(HashMap<Integer, Boolean> playerStates) {
		Object[] plStates = {"updateStates", playerStates};
		sendLobbyUpdate(plStates);
 	}

	private int getNextId(HashMap<Integer, Boolean> playerStates) {
		for(int i = 1; i <= 4; i++) {
			if(!playerStates.containsKey(i)) return i;
		}
		return 0;
	}
	
	private void checkPlayersAlive() {
		Object[] prop = {"aliveCheck"};
		sendLobbyUpdate(prop);
		
		ArrayList<Integer> pids = new ArrayList<>();
		for(int pid : party.getPlayers().keySet()) {
			pids.add(pid);
		}
		
		//Size * 2 because of redundancy
		for(int i = 0; i < party.getPlayers().size() * 2; i++) {
			Action heartbeat = ActionSerializer.deserialize(network.receiveMessage(HEARTBEAT_TIMEOUT));
			if(heartbeat != null && heartbeat.getActionType().equals(ActionType.LOBBY_COMMUNICATION) && heartbeat.getProperty(0).equals("alive")) {
				pids.remove((Integer)heartbeat.getProperty(1));
			}
		}
		
		if(!pids.isEmpty()) {
			for(int pid : pids) {
				party.remove(party.get(pid));
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(((String)arg).equals("exitRound")) finishRound();
	}

	
}
