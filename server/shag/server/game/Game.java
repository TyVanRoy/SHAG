package shag.server.game;

import java.util.ArrayList;
import shag.server.io.Connection;
import shag.server.io.Server;

public class Game implements Runnable {
	private ArrayList<Player> players = new ArrayList<Player>();
	private Server server;
	private boolean listeningForExits = false;

	public Game(Server server) {
		this.server = server;
		listenForExits();
	}

	public void listenForExits() {
		new Thread(this).start();
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void run() {
		listeningForExits = true;
		while (listeningForExits) {
			try {
				for (int i = 0; i < players.size(); i++) {
					if (!players.get(i).getConnection().listening()) {
						server.removePlayer(players.get(i).getUsername());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void chat(String username, String message) {
		server.log(username + "-- " + message);
		sendToAll("000" + username + "-- " + message);
	}

	public void sendToAll(String message) {
		for (int i = 0; i < players.size(); i++) {
			players.get(i).send(message);
		}
	}
	
	public void sendPlayerData(Player player){
		for(int i = 0; i < players.size() - 1; i++){
			player.send("010" + players.get(i).getUsername());
		}
	}

	public void addPlayer(Connection connection) {
		players.add(new Player(this, connection, connection.getUsername(), 0, 0));
		sendPlayerData(players.get(players.size() - 1));
		sendToAll("010" + connection.getUsername());
		sendToAll("000" + connection.getUsername() + " has joined the game!");
	}

	public void removePlayer(String username) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getUsername().equals(username)) {
				players.remove(i);
				sendToAll("011" + players.get(i).getUsername());
				break;
			}
		}
	}

	public void joinTeam(Player player, boolean goldTeam) {
		player.joinTeam(goldTeam);
	}

	public Server getServer() {
		return server;
	}
}
