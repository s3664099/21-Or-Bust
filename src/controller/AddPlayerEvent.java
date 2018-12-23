package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.GameEngine;
import view.AddPlayerPopUp;
import view.GameFrame;

//The first of the controller classes.
//This class fires a popup box is an add player is requested (either from the menu
//or from the tool bar).
public class AddPlayerEvent implements ActionListener {

	private GameEngine gameEngine;
	private GameFrame frame;

	public AddPlayerEvent(GameEngine gameEngine, GameFrame frame) {
		this.gameEngine = gameEngine;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		new AddPlayerPopUp(gameEngine, frame);

	}

}
