package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import loader.model.GameLoaderException;
import loader.model.GameLoaderText;
import loader.model.interfaces.GameLoader;
import model.interfaces.GameEngine;
import model.interfaces.Player;


//Event that is fire when the save game menu option is selected
public class SaveGame implements ActionListener {

	private GameEngine gameEngine;
	private String path = "saveGame.txt";

	public SaveGame(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		//Create a collection of players, and places all current players
		//into the collection
		Collection<Player> players = gameEngine.getAllPlayers();

		//Activates the save game class
		GameLoader saveGame = new GameLoaderText();

		//Saves all of the players in the game to a text file
		try {
			saveGame.saveAllPlayers(path, players);
		} catch (GameLoaderException e1) {

		}

	}
}
