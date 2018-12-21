package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.GameEngine;
import view.GameFrame;

public class SwitchHouseEvent implements ActionListener {

	private GameFrame frame;

	public SwitchHouseEvent(GameFrame frame, GameEngine gameEngine) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		frame.setHouseView();

	}

}
