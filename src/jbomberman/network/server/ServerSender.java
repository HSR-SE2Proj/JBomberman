package jbomberman.network.server;

import java.io.IOException;



import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class ServerSender {
	
	private static final String EXCHANGE_NAME = "Bomberman_Exchange";
	private Channel channel;
	
	public ServerSender(Connection connection) throws IOException {
		channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
	}
	
	public void send(byte[] message) throws IOException {
		channel.basicPublish(EXCHANGE_NAME, "", null, message);
	}
	
	public void close() throws IOException {
		channel.close();
	}

}
