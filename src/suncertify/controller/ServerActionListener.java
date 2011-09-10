package suncertify.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ServerActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (!(e.getSource() instanceof JButton)) {
			return;
		}
		
		final JButton eventButton = (JButton) e.getSource();
		
		
	}

}
