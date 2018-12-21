package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import loader.model.GameLoaderException;
import loader.model.GameLoaderText;
import loader.model.interfaces.GameLoader;
import model.interfaces.GameEngine;
import model.interfaces.Player;


//Not sure if this actually works.
//Not part of the assignment, but I should try to get it running.
//At least by clicking on the player panels I can dispense with that stupid combo-box
//TODO: Check to see if this works
public class SaveGame implements ActionListener {

	private GameEngine gameEngine;
	private String path = "saveGame.txt";

	public SaveGame(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Collection<Player> players = gameEngine.getAllPlayers();

		GameLoader saveGame = new GameLoaderText();

		try {
			saveGame.saveAllPlayers(path, players);
		} catch (GameLoaderException e1) {

		}

	}
}
