package view;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.interfaces.Player;

@SuppressWarnings("serial")
public class PlayerPanel extends JPanel {

	PlayerPanel(Player player) {
		setLayout(new GridLayout(4, 2));

		add(new JLabel("Player: "));
		add(new JLabel(player.getPlayerName()));
		add(new JLabel("Kitty: "));
		add(new JLabel(String.format("$%s.00", player.getPoints())));
		add(new JLabel("Bet:"));
		add(new JLabel(String.format("$%s.00", player.getBet())));
		add(new JLabel("Result:"));
		add(new JLabel(String.format("%s", player.getResult())));
	}

}
