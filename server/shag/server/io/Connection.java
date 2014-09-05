package shag.server.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import shag.server.game.Player;

public class Connection implements Runnable {
	public static final String CHAT_CODE = "000";
	private String username;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private boolean listening = false;
	private Player player;

	public Connection(Socket socket) {
		this.socket = socket;
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();
			input = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		recieveUsername();
		startListening();
	}
	
	public void setPlayer(Player player){
		this.player = player;
	}

	public void recieveUsername() {
		try {
			username = (String) input.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startListening() {
		new Thread(this).start();
	}
	
	public void send(final String message) {
		new Thread(new Runnable(){
			public void run(){
				try {
					output.writeObject(message);
					output.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void processMessage(final String message) {
		new Thread(new Runnable() {
			public void run() {
				if(message.startsWith(CHAT_CODE)){
					player.chat(message.substring(3));
				}
			}
		}).start();
	}

	public String getUsername() {
		return username;
	}

	public Socket getSocket() {
		return socket;
	}

	public ObjectInputStream getInput() {
		return input;
	}

	public ObjectOutputStream getOutput() {
		return output;
	}

	public void run() {
		listening = true;
		while (listening) {
			try {
				String message = (String) input.readObject();
				processMessage(message);
			} catch (EOFException e) {
				e.printStackTrace();
				listening = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean listening() {
		return listening;
	}
}
