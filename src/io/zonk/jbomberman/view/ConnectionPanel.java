package io.zonk.jbomberman.view;

import io.zonk.jbomberman.application.client.ClientController;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class ConnectionPanel extends JPanel{
	private JTextField serverAddress;

	public ConnectionPanel(ClientController cc) {
		
		setBorder(new EmptyBorder(10, 20, 20, 20));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblServAddr = new JLabel("Connect to Server:");
		GridBagConstraints gbc_lblServAddr = new GridBagConstraints();
		gbc_lblServAddr.anchor = GridBagConstraints.WEST;
		gbc_lblServAddr.insets = new Insets(0, 0, 5, 5);
		gbc_lblServAddr.gridx = 1;
		gbc_lblServAddr.gridy = 1;
		add(lblServAddr, gbc_lblServAddr);
		
		serverAddress = new JTextField();
		GridBagConstraints gbc_serverAddress = new GridBagConstraints();
		gbc_serverAddress.insets = new Insets(0, 0, 0, 5);
		gbc_serverAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_serverAddress.gridx = 1;
		gbc_serverAddress.gridy = 2;
		add(serverAddress, gbc_serverAddress);
		serverAddress.setColumns(19);
		
		JButton btnConnect = new JButton("Connect");
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.insets = new Insets(0, 10, 0, 0);
		gbc_btnConnect.gridx = 2;
		gbc_btnConnect.gridy = 2;
		add(btnConnect, gbc_btnConnect);
		
		
		btnConnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cc.connectToServer(serverAddress.getText());
			}
		});
	}
}
