package io.zonk.jbomberman.view;

import io.zonk.jbomberman.application.client.ClientController;
import io.zonk.jbomberman.application.client.ClientControllerState;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class StartFrame extends JFrame implements Observer{
	private static final long serialVersionUID = -8317409060308366477L;
	private ConnectionPanel cp;
	private LobbyPanel lp;
	private ClientController cc;
	public static void main(String[] args) {
		new StartFrame(new ClientController());
	}
	
	public StartFrame(ClientController cc) {
		cp = new ConnectionPanel(cc);
		cc.addObserver(this);
		
		setTitle("JBomberman");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if(cc.getConnState() == ClientControllerState.CONNECTED) cc.disconnect();
		        System.exit(0);
		    }
		});

		add(cp);		

		setLocationRelativeTo(null);
		getRootPane().setDefaultButton(cp.getDefaultButton());

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
		String s = (String)arg1;
		if(s != null && s.equals("connChanged")){
			if (arg0 instanceof ClientController)
			cc = (ClientController) arg0;
			switch (cc.getConnState()) {
			case DISCONNECT:
				switchPanel(lp, cp);
				getRootPane().setDefaultButton(cp.getDefaultButton());
				break;
			case CONNECTED:
				lp = new LobbyPanel(cc);
				switchPanel(cp, lp);
				getRootPane().setDefaultButton(lp.getDefaultButton());
				break;
				
			case CONN_POP_FULL:
				JOptionPane.showMessageDialog(this, "Could not connect: Server full", "Connection error", JOptionPane.ERROR_MESSAGE);
				break;
			
			case CONN_POP_LOST:
				JOptionPane.showMessageDialog(this, "Connection lost", "Connection error", JOptionPane.ERROR_MESSAGE);
				break;
				
			case CONN_POP_NF:
				JOptionPane.showMessageDialog(this, "Could not connect: Server not found", "Connection error", JOptionPane.ERROR_MESSAGE);
				break;
				
			case GAME_STARTED:
				this.setVisible(false);
				break;
				
			case GAME_FINISHED:
				switchPanel(lp, cp);
				getRootPane().setDefaultButton(cp.getDefaultButton());
				this.setVisible(true);
				break;
				
			default:
				break;
			}
		}
	}
}
