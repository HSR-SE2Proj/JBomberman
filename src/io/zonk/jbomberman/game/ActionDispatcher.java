package io.zonk.jbomberman.game;

import io.zonk.jbomberman.network.NetworkFacade;
import io.zonk.jbomberman.utils.ActionSerializer;

public class ActionDispatcher extends Thread {
	
	private NetworkFacade network;
	private ActionQueue queue;

	public ActionDispatcher(NetworkFacade network, ActionQueue queue) {
		this.network = network;
		this.queue = queue;
		this.setDaemon(true);
	}
	
	@Override
	public void run() {
		while(true) {
			queue.put(ActionSerializer.deserialize(network.receiveMessage()));
		}
	}

}
