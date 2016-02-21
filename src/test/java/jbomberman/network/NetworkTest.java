package jbomberman.network;

import static org.junit.Assert.*;

import org.junit.*;

import jbomberman.network.client.ClientNetwork;
import jbomberman.network.server.ServerNetwork;


public class NetworkTest {

	private ClientNetwork clientNetwork;
	private ServerNetwork serverNetwork;
	@Before
	public void setUp() {
		clientNetwork = new ClientNetwork();
		clientNetwork.connect("localhost");
		serverNetwork = new ServerNetwork();
		serverNetwork.connect("Localhost");
	}
	
	@Test
	public void testConnect() {
		assertTrue(clientNetwork.isOpen());
		assertTrue(serverNetwork.isOpen());
	}
	
	@Test
	public void testSendMessagefromClient() {
		byte[] message = {2,3,4};
		clientNetwork.sendMessage(message);
		assertArrayEquals(message,serverNetwork.receiveMessage());
	}
	
	@Test
	public void testSendMessagefromServer() {
		byte[] message = {2,3,4};
		serverNetwork.sendMessage(message);
		assertArrayEquals(message,clientNetwork.receiveMessage());
	}
	
	@Test
	public void testRecieveMessageTimeoutClient() {
		byte[] message = {2,3,4};
		serverNetwork.sendMessage(message);
		assertArrayEquals(message,clientNetwork.receiveMessage(10));
	}
	
	@Test
	public void testRecieveMessageTimeoutServer() {
		byte[] message = {2,3,4};
		clientNetwork.sendMessage(message);
		assertArrayEquals(message,serverNetwork.receiveMessage(10));
	}
	
	@After
	public void tearDown() {
		clientNetwork.close();
		serverNetwork.close();
	}
}
