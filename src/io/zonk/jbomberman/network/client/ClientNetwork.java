package io.zonk.jbomberman.network.client;

import io.zonk.jbomberman.network.NetworkFacade;

import java.io.IOException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;

public class ClientNetwork implements NetworkFacade {

	private static final int CONNECT_TIMEOUT = 5000;
	private Connection connection;
	private ClientSender sender;
	private ClientReceiver receiver;
	
	private byte[] recMsg;

	@Override
	public void connect(String hostname) {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(hostname);
		
		try {
			connection = factory.newConnection();
			sender = new ClientSender(connection);
			receiver = new ClientReceiver(connection);
		} catch (IOException e) {
			System.err.println("Error: Could not connect");
			e.printStackTrace();
			assert false;
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
			recMsg = null;
			Thread t = new Thread(() -> {
				try {
					recMsg = receiver.receive();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			});

			Object monitoredObject = new Object();
			synchronized (monitoredObject) {
				try {
					monitoredObject.notifyAll();
					monitoredObject.wait(CONNECT_TIMEOUT);
				} catch (InterruptedException e) {
				}
			}
			t.interrupt();
			return recMsg;
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
