package io.zonk.jbomberman.utils;

import io.zonk.jbomberman.game.GameLoop;

public class TimeUtil {
	public void sleepFor(int millis) {
		Object monitoredObject = new Object();
		synchronized (monitoredObject) {
			try {
				monitoredObject.notifyAll();
				monitoredObject.wait(millis);
			} catch (InterruptedException e) {
			}
		}
	}
	
	public static Thread startTimer(int millis, GameLoop loop) {
		Thread timer = new Thread(() -> {
			System.out.println("[*] Timer started");
			boolean run = true;
			while (run) {
				Object monitoredObject = new Object();
				synchronized (monitoredObject) {
					try {
						monitoredObject.notifyAll();
						monitoredObject.wait(millis);
					} catch (InterruptedException e) {
						run = false;
					}
				}
				loop.loop();
			}
		});
		timer.start();
		return timer;
	}
}
