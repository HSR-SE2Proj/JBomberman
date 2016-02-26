package jbomberman.game.server;

import org.junit.Test;
import org.mockito.Mockito;

import jbomberman.application.server.ServerController;

public class ServerControllerTest {
	@Test
	public void testFinishRound() {
		ServerController controller = Mockito.mock(ServerController.class);
		controller.finishRound();
	}
}
