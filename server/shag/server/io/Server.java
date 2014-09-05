package shag.server.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import shag.server.game.Game;
import shag.server.game.Player;
import shag.server.gui.ServerLog;

public class Server {
	private ServerLog log;
	private int portNumber, playerCount;
	private boolean listeningForPlayers = false;
	ServerSocket serverSocket;
	Game game;

	public Server(int playerCount, int portNumber, ServerLog serverLog) {
		this.playerCount = playerCount;
		this.portNumber = portNumber;
		log = serverLog;
		bindServerSocket();
		game = new Game(this);
		startListeningForPlayers(this.playerCount);
	}

	public void bindServerSocket() {
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			log("***FATAL ERROR ATTEMPTING TO BIND THE SERVERSOCKET TO PORT "
					+ portNumber + "***");
			e.printStackTrace();
		}
	}

	public void startListeningForPlayers(final int finalPlayerCount) {
		listeningForPlayers = true;
		new Thread(new Runnable() {
			public void run() {
				int playerCount = 0;
				while (listeningForPlayers) {
					try {
						Socket socket = serverSocket.accept();
						Connection connection = new Connection(socket);
						addPlayer(connection);
						if (playerCount >= finalPlayerCount) {
							listeningForPlayers = false;
						}
					} catch (NullPointerException exception) {

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public void addPlayer(Connection connection) {
		String username = connection.getUsername();
		game.addPlayer(connection);
		log.addPlayer(username);
		log(username + " has joined the game!");
	}

	public void removePlayer(String username) {
		game.removePlayer(username);
		log.removePlayer(username);
		log(username + " has left the game!");
	}

	public void joinTeam(Player player, boolean goldTeam) {
		game.joinTeam(player, goldTeam);
	}

	public void log(String string) {
		log.log(string);
	}
}
