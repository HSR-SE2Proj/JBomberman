package io.zonk.jbomberman.network.server;

import java.io.IOException;




import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class ServerReceiver {
	
	private static final String QUEUE_NAME = "Client_To_Server";
	
	private Channel channel;
	private QueueingConsumer consumer;
	
	public ServerReceiver(Connection connection) throws IOException {
		channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
	
	public byte[] receive() throws ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		return delivery.getBody();
	}
	
	public void close() throws IOException {
		channel.close();
	}

}
