package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.NewPlaceBet;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class PlaceBetPopup extends JFrame {

	private JTextField bet;

	public PlaceBetPopup(GameFrame frame, Player player) {

		super("Place Bet");

		setSize(400, 150);

		setLayout(new GridLayout(2, 2));

		bet = new JTextField(20);

		JLabel nameLabel = new JLabel();
		nameLabel.setText("Amount to Bet: ");

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		//Creates the submit button
		JButton submit = new JButton("Place Bet");
		submit.addActionListener(new NewPlaceBet(frame, this));
		
		//Sets the submit button as the default button
		//So that when the 'enter' key is pressed it also activates
		this.getRootPane().setDefaultButton(submit);

		add(nameLabel);
		add(bet);
		add(submit);

		setVisible(true);
	}

	public int getBetAmount() {
		return Integer.parseInt(bet.getText());
	}

}
