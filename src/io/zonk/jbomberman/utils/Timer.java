package io.zonk.jbomberman.utils;

import io.zonk.jbomberman.game.GameLoop;

public class Timer extends Thread {

	private GameLoop loop;
	private int millis;
	
	public boolean run = true;

	public Timer(int millis, GameLoop loop) {
		this.loop = loop;
		this.millis = millis;
	}

	@Override
	public void run() {
		System.out.println("[*] Timer started");
		while (run) {
			Object monitoredObject = new Object();
			synchronized (monitoredObject) {
				try {
					monitoredObject.notifyAll();
					monitoredObject.wait(millis);
				} catch (InterruptedException e) {
				}
			}
			loop.loop();
		}
	}
}
