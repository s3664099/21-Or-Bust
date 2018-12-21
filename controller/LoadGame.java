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

//This is the load game controller
//It does not work. This will need to be reviewed down the track
//TODO: Get this working
public class LoadGame implements ActionListener {
	
	private GameEngine gameEngine;
	private String path = "saveGame.txt";
	
	public LoadGame(GameEngine gameEngine)
	{
		this.gameEngine = gameEngine;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		GameLoader loadGame = new GameLoaderText();
		
		Collection<Player> players = new ArrayList<Player>();
		
		try {
			players = loadGame.loadAllPlayers(path);
		} catch (GameLoaderException e) {
			
		}
		
		for (Player player: players)
		{
			gameEngine.addPlayer(player);
		}

	}

}
