package shag.client.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import shag.client.gui.MasterWindow;

public class Connection implements Runnable {
	public static final String NEW_USER_CODE = "010";
	public static final String EXITING_USER_CODE = "011";
	public static final String CHAT_CODE = "000";
	private String ipAddress;
	private int portNumber;
	private String username;
	private MasterWindow window;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private boolean establishing = false;
	private boolean listening = false;

	public Connection(String ipAddress, int portNumber, String username,
			MasterWindow window) {
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
		this.username = username;
		this.window = window;
		establishConnection();
	}

	public void run() {
		listening = true;
		while (listening) {
			try {
				final String message = (String) input.readObject();
				if (message != null) {
					processMessage(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
				listening = false;
			}
		}
	}

	public void listen() {
		new Thread(this).start();
	}

	public void processMessage(final String message) {
		new Thread(new Runnable() {
			public void run() {
				if (message.startsWith(NEW_USER_CODE)) {
					window.addPlayer(message.substring(3));
				} else if (message.startsWith(EXITING_USER_CODE)) {
					window.removePlayer(message.substring(3));
				} else if (message.startsWith(CHAT_CODE)) {
					window.gameLobbyChat(message.substring(3));
				}
			}
		}).start();
	}

	public void send(final String message) {
		new Thread(new Runnable() {
			public void run() {
				try {
					output.writeObject(message);
					output.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void establishConnection() {
		establishing = true;
		try {
			startTimer();
			socket = new Socket(InetAddress.getByName(ipAddress), portNumber);
			establishStreams();
			output.writeObject(username);
			output.flush();
			achieveConnection();
			return;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Connection Failed!");
		failConnection();
		establishing = false;
	}

	public void establishStreams() {
		try {
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			failConnection();
		}
		establishing = false;
	}

	public void close() {
		try {
			output.close();
			input.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startTimer() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(4000);
					if (establishing) {
						failConnection();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void failConnection() {
		window.failConnection();
	}

	public void achieveConnection() {
		window.achieveConnection();
	}
}
