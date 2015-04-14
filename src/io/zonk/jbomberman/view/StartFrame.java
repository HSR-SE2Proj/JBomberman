package io.zonk.jbomberman.view;

import io.zonk.jbomberman.application.client.ClientController;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class StartFrame extends JFrame implements Observer{
	private ConnectionPanel cp;
	private LobbyPanel lp;
	
	public static void main(String[] args) {
		new StartFrame(new ClientController(null, null));
	}
	
	public StartFrame(ClientController cc) {
		cp = new ConnectionPanel(cc);
		lp = new LobbyPanel(cc);
		cc.addObserver(this);
		
		setTitle("JBomberman");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		add(cp);		

		setLocationRelativeTo(null);

		pack();
		setResizable(false);
		setVisible(true);
	}
	
	private void switchPanel(JPanel from, JPanel to) {
		remove(from);
		add(to);
		repaint();
		pack();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		switch ((int)arg1) {
		case 0:
			switchPanel(lp, cp);
			break;
		case 1:
			switchPanel(cp, lp);
			break;

		default:
			break;
		}
	}
}
