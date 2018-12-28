package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.interfaces.GameEngine;
import model.interfaces.Player;

//This class generates the player list that runs down the left side of the screen
@SuppressWarnings("serial")
public class GamePlayerList extends JPanel {

	private Border border = BorderFactory.createLineBorder(Color.BLACK);
	private Border borderIn = BorderFactory.createLineBorder(Color.RED, 5);

	public GamePlayerList(GameEngine gameEngine, GameFrame frame) {
		updatePlayers(gameEngine, frame);
	}

	public void updatePlayers(GameEngine gameEngine, GameFrame frame) {
		// Gets a collection of players
		Collection<Player> players = gameEngine.getAllPlayers();

		setLayout(new GridLayout(players.size(), 1));

		// Creates a box for each of the players
		for (Player player : players) {
			PlayerPanel panel = new PlayerPanel(player);
			panel.setBorder(border);
			
			//Creates a mouse listener
			//An inner class that changes the current player when the box clicked
			//This can probably be done better by firing the switch player event
			//TODO: change to to firet the Switch Player Event
			panel.addMouseListener(new MouseAdapter() {
				
				//Event listener to change the player when clicked
				public void mouseClicked(MouseEvent e)
				{
					frame.setCurrentPlayer(player);
				}
				
				//Highlights the box when the mouse enters
				public void mouseEntered (MouseEvent e)
				{
					panel.setBorder(borderIn);
					panel.setBackground(Color.YELLOW);
				}
				
				//Returns the box to the unhighlighted state when the mouse leaves.
				public void mouseExited (MouseEvent e)
				{
					panel.setBorder(border);
					panel.setBackground(Color.LIGHT_GRAY);
				}
				
			});
			
			add(panel);

		}
	}

}
