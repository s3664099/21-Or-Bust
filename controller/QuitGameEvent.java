package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuitGameEvent implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {

		System.exit(0);
	}

}
