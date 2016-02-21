package jbomberman.view;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.border.EmptyBorder;

import org.junit.Before;
import org.junit.Test;

import jbomberman.application.client.ClientController;

public class ConnectionPanelTest {
	
	private ConnectionPanel panel;
	private ClientController client;
	@Before
	public void setUp() {
		client = new ClientController();
		panel = new ConnectionPanel(client);
	}
	
	@Test
	public void testLayout() {
		assertEquals(new GridBagLayout().getClass(),panel.getLayout().getClass());
	}
	
	@Test
	public void testBorder() {
		assertEquals(new EmptyBorder(10, 20, 20, 20).getBorderInsets(),
				panel.getBorder().getBorderInsets(panel));
	}
	
	@Test
	public void testBounds() {
		assertEquals(new Dimension(0,0),panel.getBounds().getSize());
	}
	
	@Test
	public void testDefaultButton() {
		assertEquals("Connect",panel.getDefaultButton().getText());
	}
	
}
