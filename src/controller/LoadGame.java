package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import loader.model.GameLoaderException;
import loader.model.GameLoaderText;
import loader.model.interfaces.GameLoader;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameFrame;

//The method that is executed when the load game menu item is selection
public class LoadGame implements ActionListener {
	
	private GameEngine gameEngine;
	private GameFrame frame;
	private String path = "saveGame.txt";
	
	public LoadGame(GameEngine gameEngine, GameFrame frame)
	{
		this.gameEngine = gameEngine;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		//Activates the game loader class
		GameLoader loadGame = new GameLoaderText();
		
		//Creates an empty list of players
		Collection<Player> players = new ArrayList<Player>();
		
		//Loads the file containing the player data
		try {
			players = loadGame.loadAllPlayers(path);
		} catch (GameLoaderException e) {
			System.out.println(e);
			return;
		}
		
		//Adds the players to the game
		for (Player player: players)
		{
			gameEngine.addPlayer(player);
			frame.addPanel(player);
		}
		
	}

}
