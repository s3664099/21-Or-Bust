package view;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.interfaces.PlayingCard;

//This sets up the table where the hand is laid out.
//Each player has their own panel assigned to them
@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	private String playerId;
	private boolean deal = false;
	private Border border = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.RED);
	private JLabel currentCard;

	public GamePanel(String playerId) {

		this.playerId = playerId;
		setBackground(Color.GREEN);
		setLayout(new FlowLayout());

	}

	public boolean getPlayer(String currentPlayerId) {

		if (currentPlayerId == playerId) {
			return true;
		}

		return false;
	}
	
	//This method called when a hand is being dealt
	//Obtains an image of the card that relates to the card
	//And then places the image on the panel
	public void addCard(PlayingCard drawnCard, ImageIcon[] cards, boolean bust) {

		String cardString = drawnCard.getSuit() + " " + drawnCard.getValue();

		//Searches the icons for the image that relates to the specific card
		for (ImageIcon card : cards) {
			if (cardString.equals(card.getDescription())) {
				currentCard = new JLabel(card);
			}
		}
		
		//If the bust flag is set, then the border is changed to red
		if (bust) {
			currentCard.setBorder(border);
		}

		add(currentCard);

		this.revalidate();
		this.repaint();
	}

	public boolean getDeal() {
		return deal;
	}

	public void setDeal() {
		deal = true;
	}

}
