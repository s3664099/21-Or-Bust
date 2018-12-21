package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

//The main class that generates the inital frame for the game
@SuppressWarnings("serial")
public class GameFrame extends JFrame {

	private GameEngine gameEngine;
	private Player currentPlayer = null;
	private String playerId = "house";
	private GamePanel gamePanel;
	private GameStatusBar statusBar;
	private JToolBar toolBar;
	private GamePlayerList playerList;
	private CardImage deck = new CardImage();
	private ImageIcon[] cards;
	private Collection<GamePanel> panels = new ArrayList<GamePanel>();

	public GameFrame(GameEngine gameEngine) {

		super("21 or Bust");

		this.gameEngine = gameEngine;
		
		//Generates a default screen size based on the size of the monitor
		//Then sets the size of the dialogue box to be half that of the monitor
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setMinimumSize( new Dimension(screenSize.width/2, screenSize.height/2));

		//Sets up the dialogue box
		//A menu bar, and a toolbar are created across the top
		//A player bar is created down the size, and a status bar is placed
		//across the bottom. The initial status is 'idle'.
		//Panels are also created for each of the players, and the main centre panel
		//is also created.
		setLayout(new BorderLayout());
		createMenuBar();
		createToolBar();
		createPlayerPanels();
		createPlayerBar();
		createStatusBar("idle");

		//If the dialogue box is closed, the program terminates
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		cards = deck.createCards();

		setVisible(true);
	}

	// creates a list of panels representing each player
	// This is the panel that appears to display the hand that has been
	// dealt to the particular player. If no hand has been dealt then
	// the panel will appear blank (or at least green).
	// The following three methods also create the other parts of the main
	// Box
	private void createPlayerPanels() {
		
		//Cycles through each of the players currently in the game
		for (Player player : gameEngine.getAllPlayers()) {
			gamePanel = new GamePanel(player.getPlayerId());

			// Checks to see if the player has any money left
			if (player.getPoints() == 0)

				// if not, sets the deal value to dealt
				gamePanel.setDeal();

			panels.add(gamePanel);
		}

		// Generates a panel for the house
		gamePanel = new GamePanel("house");
		panels.add(gamePanel);
		setCurrentPlayerPanel();
	}

	private void createMenuBar() {
		setJMenuBar(new GameMenubar(this, gameEngine, currentPlayer));
	}

	private void createToolBar() {
		toolBar = new GameToolBar(this, gameEngine);
		add(toolBar, BorderLayout.NORTH);
	}

	private void createStatusBar(String action) {

		statusBar = new GameStatusBar(currentPlayer, gameEngine, action);
		add(statusBar, BorderLayout.SOUTH);
	}
	
	private void createPlayerBar() {
		playerList = new GamePlayerList(gameEngine, this);
		add(playerList, BorderLayout.WEST);
		updateFrame();
	}
	
	// creates a new panel when player added
	public void addPanel(Player player) {
		gamePanel = new GamePanel(player.getPlayerId());
		panels.add(gamePanel);

		// updates the panel with new player information
		setCurrentPlayerPanel();
		updatePlayerBar();
		updateToolBar();
		updateStatusBar(String.format("%s has joined the game", player.getPlayerName()));
		updateMenuBar();
	}

	//When the player is changed, the new player is set as the current player
	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
		playerId = player.getPlayerId();
		updateCurrentPlayer();
	}

	//This method is called if the house is selected instead of the player
	public void setHouseView() {
		currentPlayer = null;
		playerId = "house";
		updateCurrentPlayer();
	}

	// This method sets the current player panel
	// that is the hand, if any, for the select player
	// as the panel that is on display
	private void setCurrentPlayerPanel() {
		remove(gamePanel);
		gamePanel = findPanel(playerId);
		add(gamePanel, BorderLayout.CENTER);
	}

	// Searches the list of panels and grabs the one corresponding
	// to the player
	private GamePanel findPanel(String Id) {
		for (GamePanel panel : panels) {
			if (panel.getPlayer(Id)) {
				return panel;
			}
		}
		return null;
	}

	// Removes the panel from the list of panels
	// This is called when a playe is removed from the game
	public void removePanel(String Id) {
		panels.remove(findPanel(Id));
	}
	
	//The method for placing a card onto the player panel.
	public void nextCard(String Id, String playerName, PlayingCard card, boolean bust) {
		
		//Locates the corrresponding panel relating to the player
		gamePanel = findPanel(Id);
		gamePanel.addCard(card, cards, bust);
		remove(statusBar);
		
		//Updates the status bar indicating the card that has been dealt
		createStatusBar(String.format("Card dealt to %s : %s of %s", playerName, card.getValue(), card.getSuit()));

		// Checks to see if the card is a bust card
		if (bust) {
			createStatusBar(String.format("%s BUSTS...!!!!", playerName));
		}
		updateFrame();
	}
	
	// Checks to see whether the player has already dealt.
	public boolean checkDeal() {
		int numberDealt = 0;

		for (GamePanel panel : panels) {
			if (panel.getDeal()) {
				numberDealt++;
			}
		}
		System.out.println(numberDealt);

		if (numberDealt == panels.size() - 1) {
			return true;
		}

		return false;
	}

	//A new game, so all of the panels are refreshed and everything update
	public void newGame() {

		updatePanel();
		updatePlayerBar();
		updateFrame();
	}

	//This method is called when a new game is requested
	//It is required since all of the panels will be full of cards
	//So it removes all of those panels and destroys them, and creates
	//a collection of fresh panels.
	public void updatePanel() {
		remove(gamePanel);
		panels.removeAll(panels);
		createPlayerPanels();
		setCurrentPlayerPanel();
	}

	private void updateFrame() {
		this.revalidate();
		this.repaint();
	}

	public void updateStatusBar(String action) {
		remove(statusBar);
		createStatusBar(action);
	}

	public void updateMenuBar() {
		createMenuBar();
		updateFrame();
	}

	public void updateToolBar() {
		remove(toolBar);
		createToolBar();
		updateFrame();
	}

	public void updateCurrentPlayer() {
		remove(statusBar);
		remove(toolBar);
		setCurrentPlayerPanel();
		createStatusBar("idle");
		createToolBar();
		updateFrame();
	}

	public void updatePlayerBar() {
		remove(playerList);
		createPlayerBar();
	}

	public GamePanel getPanel() {
		return gamePanel;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
}
