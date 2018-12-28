package loader.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import loader.model.interfaces.GameLoader;
import model.SimplePlayer;
import model.interfaces.Player;

public class GameLoaderText implements GameLoader {
	
	//Method for loading data stored in a text file
	@Override
	public Collection<Player> loadAllPlayers(String path) throws GameLoaderException {

		//creates a collection of players
		Collection<Player> players = new ArrayList<Player>();
				
		//Begins to load the file
		try(Scanner input = new Scanner(new File(path))) {
			
			//makes a note that a tab space is the delimiter between data
			input.useDelimiter("\t");
			
			while(input.hasNext())
			{
				
				//Loads and stores the data
				String id = input.next();
								
				//Checks to see if the word 'stop' has been loaded
				if (id.contains("stop"))
				{
					//Breaks out of the method if that is the case
					return players;
				}
				
				String name = input.next();
				int points = input.nextInt();
				
				//Creates a new player from the data
				Player player = new SimplePlayer(id, name, points);
				
				//Places the new player into the collection
				players.add(player);				
			}
						
			//Returns the collection of players to place them into the game
			return players;
			
		} catch (Exception e) {
			
			//Throws an exception is there is a problem loading the file
			throw new GameLoaderException("Unable to load file "+path);
		}
				

	}

	//Method to save the current status of all the playes
	@Override
	public void saveAllPlayers(String path, Collection<Player> players) throws GameLoaderException
	{
		//Opens a new file
		try (PrintWriter pw = new PrintWriter(new FileWriter(path)))
		{
			//cycles through each of the players and saves the player data
			for (Player player:players)
			{
				pw.println(player.getPlayerId()+"\t"+player.getPlayerName()+"\t"
						+player.getPoints()+"\t");
			}
			
			//Flag inserted into the file to indicate that there is nothing more to load
			pw.print("stop");
						
		} catch (IOException e) {
			throw new GameLoaderException("Unable to create file");
		}
				
	}

	@Override
	public void appendPlayer(String path, Player player) throws GameLoaderException {
		// TODO create an append player method where a save file already exists
		
	}

}
