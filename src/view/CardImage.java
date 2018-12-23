package view;

import java.io.File;

import javax.swing.ImageIcon;

import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;

public class CardImage {

	//This is basically an ImageIcon that will display a picture of the card
	//A collection of images is created based on the playing cards
	private ImageIcon[] card = new ImageIcon[PlayingCard.DECK_SIZE];
	
	public ImageIcon[] createCards() {
		int i = 0;
		
		// populates the deck with a unique set of cards
		// Cycles through each of the four suits, and for each of the four suits
		// Cycles through the card values
		for (Suit suit : Suit.values()) {
			for (Value value : Value.values()) {
				
				//The images are cycled through in the directory and are located based
				//On the card's suit and value. Each of the images are defined by the
				//suit and value
				card[i] = new ImageIcon("Card_Images" + File.separator + value + "_" + suit + ".jpg",
						suit + " " + value);
				i++;
			}
		}

		return card;
	}

}
