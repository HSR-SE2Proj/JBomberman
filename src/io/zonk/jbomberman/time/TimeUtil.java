package io.zonk.jbomberman.time;

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
}
