package io.zonk.jbomberman.game;

import io.zonk.jbomberman.network.NetworkFacade;

public class ActionDispatcher extends Thread {
	
	private NetworkFacade network;
	private ActionQueue queue;

	public ActionDispatcher(NetworkFacade network, ActionQueue queue) {
		this.network = network;
		this.queue = queue;
	}
	
	@Override
	public void run() {
		
	}

}
