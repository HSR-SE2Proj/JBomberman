package io.zonk.jbomberman.network.client;

import java.io.IOException;




import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class ClientReceiver {
	
	private static final String EXCHANGE_NAME = "Bomberman_Exchange";
	
	private Channel channel;
	private String queueName;
	private QueueingConsumer consumer;
	
	public ClientReceiver(Connection connection) throws IOException {
		channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
		queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, "");
		
		consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);
	}
	
	public byte[] receive() throws ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		return delivery.getBody();
	}
	
	public void close() throws IOException {
		channel.basicCancel(consumer.getConsumerTag());
		channel.close();
	}

}
