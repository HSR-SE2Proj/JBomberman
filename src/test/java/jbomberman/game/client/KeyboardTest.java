package jbomberman.game.client;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;

import javax.swing.JButton;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jbomberman.game.ActionSerializer;
import jbomberman.network.client.ClientNetwork;
import jbomberman.network.server.ServerNetwork;

public class KeyboardTest {

	private Keyboard keyboard;
	private static ClientNetwork clientNetwork;
	private static ServerNetwork serverNetwork;
	@BeforeClass
	public static void setUp() {
		clientNetwork = new ClientNetwork();
		serverNetwork = new ServerNetwork();
	}
	
	@Before
	public void Connect() {
		clientNetwork.connect("localhost");
		serverNetwork.connect("localhost");
	}
	
	@Test
	public void testEnterKey() {
		keyboard = new Keyboard(0, clientNetwork);
		KeyEvent e = new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_ENTER, '\0');
		keyboard.keyPressed(e);
		assertEquals(KeyEvent.VK_ENTER,ActionSerializer.deserialize(serverNetwork.receiveMessage()).getProperty(1));
		keyboard.keyReleased(e);
		assertEquals(KeyEvent.VK_ENTER,ActionSerializer.deserialize(serverNetwork.receiveMessage()).getProperty(1));
	}
	
	@Test
	public void testWKey() {
		keyboard = new Keyboard(0, clientNetwork);
		KeyEvent e = new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_W, '\0');
		keyboard.keyPressed(e);
		assertEquals(KeyEvent.VK_W,ActionSerializer.deserialize(serverNetwork.receiveMessage()).getProperty(1));
		keyboard.keyReleased(e);
		assertEquals(KeyEvent.VK_W,ActionSerializer.deserialize(serverNetwork.receiveMessage()).getProperty(1));
	}
	
	@Test
	public void testAKey() {
		keyboard = new Keyboard(0, clientNetwork);
		KeyEvent e = new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_A, '\0');
		keyboard.keyPressed(e);
		assertEquals(KeyEvent.VK_A,ActionSerializer.deserialize(serverNetwork.receiveMessage()).getProperty(1));
		keyboard.keyReleased(e);
		assertEquals(KeyEvent.VK_A,ActionSerializer.deserialize(serverNetwork.receiveMessage()).getProperty(1));
	}
	
	@Test
	public void testSKey() {
		keyboard = new Keyboard(0, clientNetwork);
		KeyEvent e = new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_S, '\0');
		keyboard.keyPressed(e);
		assertEquals(KeyEvent.VK_S,ActionSerializer.deserialize(serverNetwork.receiveMessage()).getProperty(1));
		keyboard.keyReleased(e);
		assertEquals(KeyEvent.VK_S,ActionSerializer.deserialize(serverNetwork.receiveMessage()).getProperty(1));
	}
	
	@Test
	public void testDKey() {
		keyboard = new Keyboard(0, clientNetwork);
		KeyEvent e = new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_D, '\0');
		keyboard.keyPressed(e);
		assertEquals(KeyEvent.VK_D,ActionSerializer.deserialize(serverNetwork.receiveMessage()).getProperty(1));
		keyboard.keyReleased(e);
		assertEquals(KeyEvent.VK_D,ActionSerializer.deserialize(serverNetwork.receiveMessage()).getProperty(1));
	}
	
	@Test
	public void testInvalidKey() {
		keyboard = new Keyboard(0, clientNetwork);
		KeyEvent e = new KeyEvent(new JButton(), 0, 0, 0, KeyEvent.VK_3, '\0');
		keyboard.keyPressed(e);
		keyboard.keyReleased(e);
	}
	
	@After
	public void close() {
		clientNetwork.close();
		serverNetwork.close();
	}
	
}
