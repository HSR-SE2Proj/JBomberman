package jbomberman.network.server;

import jbomberman.network.NetworkFacade;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;

public class ServerNetwork implements NetworkFacade {
	
	private Connection connection;
	private ServerSender sender;
	private ServerReceiver receiver;
	
	private Thread currThread;
	private byte[] recMsg;

	@Override
	public void connect(String hostname) {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(hostname);
		
		try {
			connection = factory.newConnection();
			sender = new ServerSender(connection);
			receiver = new ServerReceiver(connection);
		} catch (IOException e) {
			System.err.println("Error: Could not connect");
			e.printStackTrace();
			assert false;
		} catch (TimeoutException e) {
			System.err.println("Error: Connection Timeout");
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() {
		try {
			sender.close();
			receiver.close();
			connection.close();
		} catch (IOException e) {
			System.err.println("Error: Could not close down connection");
			e.printStackTrace();
			assert false;
		} catch (TimeoutException e) {
			System.err.println("Error: Connection Timeout");
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(byte[] message) {
		try {
			sender.send(message);
		} catch (IOException e) {
			System.err.println("Error: Could not send message");
			e.printStackTrace();
			assert false;
		}
	}

	@Override
	public byte[] receiveMessage() {
		try {
			return receiver.receive();
		} catch (ShutdownSignalException | ConsumerCancelledException
				| InterruptedException e) {
			System.err.println("Error: Could not receive message");
			e.printStackTrace();
			assert false;
			return null;
		}
	}

	public byte[] receiveMessage(int timeout) {
		try {
			recMsg = null;

			currThread = Thread.currentThread();
			new Thread(() -> {
				Object monitoredObject = new Object();
				synchronized (monitoredObject) {
					try {
						monitoredObject.notifyAll();
						monitoredObject.wait(timeout);
						if (recMsg == null) currThread.interrupt();
					} catch (InterruptedException e) {
					}
				}
			}).start();
			
			try {
				recMsg = receiver.receive();
			} catch (InterruptedException e) {
			}

			return (recMsg != null) ? recMsg.clone(): null;
		} catch (ShutdownSignalException | ConsumerCancelledException e) {
			System.err.println("Error: Could not receive message");
			e.printStackTrace();
			assert false;
			return null;
		}
	}

	@Override
	public boolean isOpen() {
		return connection.isOpen();
	}

}
