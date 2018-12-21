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
	
	@Override
	public Collection<Player> loadAllPlayers(String path) throws GameLoaderException {

		Collection<Player> players = new ArrayList<Player>();
				
		try(Scanner input = new Scanner(new File(path)).useDelimiter("\t")) {
			
			while(input.hasNextLine())
			{
				
				String id = input.next();
				System.out.println("ID:"+id);
				String name = input.next();
				System.out.println("Name:"+name);
				int points = input.nextInt();
				System.out.println("Points:"+points);
				
				Player player = new SimplePlayer(id, name, points);
				
				System.out.println(player.toString());
				
				players.add(player);
								
			}
			
			return players;
			
		} catch (Exception e) {
			throw new GameLoaderException("Unable to load file "+path);
		}
				

	}

	@Override
	public void saveAllPlayers(String path, Collection<Player> players) throws GameLoaderException
	{
		try (PrintWriter pw = new PrintWriter(new FileWriter(path)))
		{
			for (Player player:players)
			{
				pw.println(""+player.getPlayerId()+"\t"+player.getPlayerName()+"\t"
						+player.getPoints()+"\t");
			}
						
		} catch (IOException e) {
			throw new GameLoaderException("Unable to create file");
		}
				
	}

	@Override
	public void appendPlayer(String path, Player player) throws GameLoaderException {
		// TODO Auto-generated method stub
		
	}

}
