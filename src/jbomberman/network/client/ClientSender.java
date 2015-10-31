package jbomberman.network.client;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class ClientSender {
	
	private static final String QUEUE_NAME = "Client_To_Server";
	
	private Channel channel;
	
	public ClientSender(Connection connection) throws IOException {
		channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	}
	
	public void send(byte[] message) throws IOException {
		channel.basicPublish("", QUEUE_NAME, null, message);
	}
	
	public void close() throws IOException {
		channel.close();
	}

}
