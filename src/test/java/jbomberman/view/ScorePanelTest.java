package jbomberman.view;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.awt.Dimension;

import org.junit.Before;
import org.junit.Test;

import jbomberman.game.Party;

public class ScorePanelTest {
	
	private ScorePanel panel;
	private Party party;
	
	@Before
	public void setUp() {
		party = new Party();
		panel = new ScorePanel(party,1);
	}
	
	@Test
	public void testlayout() {
		assertEquals(new BorderLayout().getClass(),panel.getLayout().getClass());
	}
	
	@Test
	public void testSize() {
		assertEquals(new Dimension(0,0),panel.getSize());
	}

}
