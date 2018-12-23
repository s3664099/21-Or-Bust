package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.ClosePopUpEvent;
import controller.NewPlayerSubmit;
import model.interfaces.GameEngine;

//This is a popup dialogue box that appears when a new player is added to the game
@SuppressWarnings("serial")
public class AddPlayerPopUp extends JFrame {

	private JTextField pointsTextField;
	private JTextField nameTextField;

	public AddPlayerPopUp(GameEngine gameEngine, GameFrame frame) {
		
		//sets the title of the popup box
		//Not that this is a subclass of the JFrame class
		super("Add Player");

		setSize(400, 150);

		setLayout(new GridLayout(3, 3));

		// Set up name and points fields
		nameTextField = new JTextField(20);
		pointsTextField = new JTextField(20);
		JLabel nameLabel = new JLabel();
		nameLabel.setText("Name: ");
		JLabel pointsLabel = new JLabel();
		pointsLabel.setText("Points");

		//When the dialogue box is closed, it disappears
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// Create Cancel Button
		// Adds an action listener to call appropriate method
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ClosePopUpEvent(this));

		// Create Submit Button
		// Adds a lister to execute the appropriate method when the button is pressed
		JButton submit = new JButton("Submit");
		submit.addActionListener(new NewPlayerSubmit(this, gameEngine, frame));

		// Set up frame
		add(nameLabel);
		
		//The text box where the name is entered
		add(nameTextField); 
		add(pointsLabel);
		
		//The text box where the points are entered
		add(pointsTextField); 
		
		//The button that sends the details to the GameEngineImpl Class to create a new player
		add(submit); 
		
		//The button to basically cancel the box and close it
		add(cancel);

		setVisible(true);

	}

	public String getNameText() {
		return nameTextField.getText();
	}

	public int getPointsText() {
		return Integer.parseInt(pointsTextField.getText());
	}

}
