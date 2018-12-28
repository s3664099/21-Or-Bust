package view;

import java.awt.Color;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.AddPlayerEvent;
import controller.LoadGame;
import controller.NewGame;
import controller.SaveGame;
import controller.SwitchHouseEvent;
import controller.QuitGameEvent;
import controller.RemovePlayerEvent;
import controller.SwitchPlayerEvent;
import model.interfaces.GameEngine;
import model.interfaces.Player;

//This class creates the menu bar that sits at the top of the main window.
//A space for the menu bar always exists, no matter what layout you end up using.
@SuppressWarnings("serial")
public class GameMenubar extends JMenuBar {

	//The four menus are defined here.
	private JMenu gameMenu = new JMenu("Game");
	private JMenu playerMenu = new JMenu("Player");
	private JMenu switchPlayer = new JMenu("Switch Player");
	private JMenu removePlayer = new JMenu("Remove Player");
	
	//The items in the four menus are defined here.
	private JMenuItem newGame = new JMenuItem("New Game");
	private JMenuItem newPlayer = new JMenuItem("Add Player");
	private JMenuItem quitGame = new JMenuItem("Quit");
	private JMenuItem saveGame = new JMenuItem("Save Game");
	private JMenuItem loadGame = new JMenuItem("Load Game");
	private JMenuItem players;

	public GameMenubar(GameFrame frame, GameEngine gameEngine, Player currentPlayer) {
		setBackground(Color.LIGHT_GRAY);

		add(gameMenu);
		add(playerMenu);

		// Player Menu Options
		// creates a submenu for switching and removing players
		for (Player player : gameEngine.getAllPlayers()) {
			
			//This section creates a menu item for each of the players
			//And places them into the switch player submenu
			players = new JMenuItem(player.getPlayerName());
			players.addActionListener(new SwitchPlayerEvent(frame, player));
			switchPlayer.add(players);

			//This does the same except this is for the remove player submenu
			players = new JMenuItem(player.getPlayerName());
			players.addActionListener(new RemovePlayerEvent(gameEngine, frame, player));
			removePlayer.add(players);
		}

		//Since you should also be able to change to the house, an option to switch
		//to the house is added to the switch player submenu.
		//Note, even though it would be great at times, you cannot remove the house
		//from the game.
		players = new JMenuItem("House");
		players.addActionListener(new SwitchHouseEvent(frame, gameEngine));
		switchPlayer.add(players);
		
		//This option is to add a new player to the game
		newPlayer.addActionListener(new AddPlayerEvent(gameEngine, frame));

		// Game Menu Options
		newGame.addActionListener(new NewGame(frame));
		saveGame.addActionListener(new SaveGame(gameEngine));
		loadGame.addActionListener(new LoadGame(gameEngine, frame));
		quitGame.addActionListener(new QuitGameEvent());

		// Adds menu to menu bar
		gameMenu.add(newGame);
		gameMenu.add(saveGame);
		gameMenu.add(loadGame);
		playerMenu.add(newPlayer);
		playerMenu.add(switchPlayer);
		playerMenu.add(removePlayer);
		gameMenu.add(quitGame);
	}

}
