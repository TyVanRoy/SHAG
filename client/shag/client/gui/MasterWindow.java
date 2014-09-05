package shag.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Formatter;
import java.util.Scanner;

import javax.swing.JFrame;

import shag.client.game.Game;
import shag.client.io.Connection;
import shag.client.main.Shag;

public class MasterWindow extends JFrame implements KeyListener {
	private static final long serialVersionUID = 2169213667919614627L;
	private static final int WIDTH = (int) Toolkit.getDefaultToolkit()
			.getScreenSize().getWidth();
	private static final int HEIGHT = (int) Toolkit.getDefaultToolkit()
			.getScreenSize().getHeight();
	private static final Dimension DIMENSIONS = new Dimension(WIDTH, HEIGHT);
	private GraphicsEnvironment graphicsEnvironment;
	private GraphicsDevice graphicsDevice;
	private boolean inMainMenu = false;
	private boolean inServerMenu = false;
	private boolean inSettingsMenu = false;
	private boolean inConnectionScreen = false;
	private boolean inFullScreen = false;
	private boolean inGameLobby = false;
	private boolean inGame = false;
	private MainMenu mainMenu;
	private ServerMenu serverMenu;
	private SettingsMenu settingsMenu;
	private ConnectionScreen connectionScreen;
	private GameLobby gameLobby;
	private Game game;
	private GameCanvas gameCanvas;
	private Connection connection;
	private String presetsPath = Shag.getPresets();
	private String ipAddress;
	private int portNumber;
	private String username = "Player";
	private int gameVolume = 100;
	private int musicVolume = 100;
	private int masterVolume = 100;
	private int commandTime = 0;
	private String commandCode = "";

	public MasterWindow() {
		super("Press ESC to regain fullscreen");
		initializeGraphics();
		addListeners();
		loadUserPresets();
	}

	public void initializeGraphics() {
		graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		getContentPane().setBackground(Color.BLACK);
	}

	public void addListeners() {
		addKeyListener(this);
	}

	public void loadUserPresets() {
		try {
			File presets = new File(presetsPath + "presets.txt");
			Scanner scanner = new Scanner(presets);
			ipAddress = scanner.nextLine();
			portNumber = fromString(scanner.nextLine());
			username = scanner.nextLine();
			scanner.close();
		} catch (Exception exception) {
			try {
				new Formatter(presetsPath + "presets.txt").format("").close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void launch() {
		mainMenu = new MainMenu(this);
		mainMenu.setBounds((WIDTH - MainMenu.WIDTH) / 2,
				(HEIGHT - MainMenu.HEIGHT) / 2, MainMenu.WIDTH,
				MainMenu.HEIGHT);
		add(mainMenu);
		inMainMenu = true;
		enterFullScreen();
		repaint();
	}

	public void enterFullScreen() {
		graphicsDevice.setFullScreenWindow(this);
		setVisible(false);
		setVisible(true);
		requestFocus();
		validate();
		inFullScreen = true;
	}

	public void exitFullScreen() {
		graphicsDevice.setFullScreenWindow(null);
		setLocationRelativeTo(null);
		inFullScreen = false;
	}

	public void exit() {
		graphicsDevice.setFullScreenWindow(null);
		setVisible(false);
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public Dimension getDimensions() {
		return DIMENSIONS;
	}
	
	public Game getGame(){
		return game;
	}
	
	public GameCanvas getGameCanvas(){
		return gameCanvas;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		final int KEY = e.getKeyCode();
		
		if(inGame){
			return;
		}
		
		switch (KEY) {
		case KeyEvent.VK_ESCAPE:
			if (inFullScreen) {
				exitFullScreen();
			} else {
				enterFullScreen();
			}
			break;
		case KeyEvent.VK_UP:
			if (inMainMenu) {
				mainMenu.moveSelectionUp();
			}
			break;
		case KeyEvent.VK_DOWN:
			if (inMainMenu) {
				mainMenu.moveSelectionDown();
			}
			break;
		case KeyEvent.VK_LEFT:
			if (inServerMenu) {
				serverMenu.moveSelectionLeft();
			} else if (inSettingsMenu) {
				settingsMenu.moveSelectionLeft();
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (inServerMenu) {
				serverMenu.moveSelectionRight();
			} else if (inSettingsMenu) {
				settingsMenu.moveSelectionRight();
			}
			break;
		case KeyEvent.VK_ENTER:
			enter();
			break;
		case KeyEvent.VK_C:
			commandCode = "c";
			commandTime = 5;
			break;
		case KeyEvent.VK_O:
			if (commandTime != 0 && commandCode.equals("c")) {
				commandCode = "co";
				commandTime = 5;
			}
			break;
		case KeyEvent.VK_M:
			if (commandTime != 0 && commandCode.equals("co")) {
				commandCode = "";
				initializeCommandBox();
			}
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void initializeCommandBox() {
		mainMenu.initializeCommandBox();
	}

	public void enterCommand(String command) {
		mainMenu.deleteCommandBox();
		switch (command.trim().toLowerCase().replace(" ", "").replace("!", "")
				.replace(",", "").replace(".", "")) {
		case "gamelobby":
			initializeGameLobbyTest();
			break;
		case "gametest":
			initializeGameTest();
			break;
		}
	}

	public void initializeGameLobbyTest() {
		remove(mainMenu);
		gameLobby = new GameLobby(this);
		gameLobby.setBounds((WIDTH - gameLobby.getWidth()) / 2,
				(HEIGHT - gameLobby.getHeight()) / 2, gameLobby.getWidth(),
				gameLobby.getHeight());
		add(gameLobby);
		inGameLobby = true;
		mainMenu = null;
		inMainMenu = false;
		repaint();
	}
	
	public void initializeGameTest(){
		remove(mainMenu);
		game = new Game(this);
		gameCanvas = new GameCanvas(game, this);
		gameCanvas.setBounds((WIDTH - GameCanvas.WIDTH) / 2,
				(HEIGHT - GameCanvas.HEIGHT) / 2, GameCanvas.WIDTH,
				GameCanvas.HEIGHT);
		add(gameCanvas);
		inGame = true;
		gameCanvas.startRendering();
		mainMenu = null;
		inMainMenu = false;
		repaint();
	}

	public void setCommandTime(int time) {
		commandTime = time;
	}

	public int getCommandTime() {
		return commandTime;
	}

	public void enter() {
		if (inMainMenu) {
			if (mainMenu.playSelected()) {
				remove(mainMenu);
				serverMenu = new ServerMenu(this);
				serverMenu.setBounds((WIDTH - serverMenu.getWidth()) / 2,
						(HEIGHT - serverMenu.getHeight()) / 2,
						serverMenu.getWidth(), serverMenu.getHeight());
				add(serverMenu);
				inServerMenu = true;
				mainMenu = null;
				inMainMenu = false;
			} else if (!mainMenu.playSelected()) {
				enterSettingsMenu(SettingsMenu.MAIN_MENU);
			}
			repaint();
		} else if (inServerMenu) {
			remove(serverMenu);
			if (serverMenu.joinGameSelected()) {
				ipAddress = serverMenu.getIPAddress();
				portNumber = serverMenu.getPortNumber();
				username = serverMenu.getUsername();
				connectionScreen = new ConnectionScreen();
				connectionScreen.setBounds(
						(WIDTH - connectionScreen.getWidth()) / 2,
						(HEIGHT - connectionScreen.getHeight()) / 2,
						connectionScreen.getWidth(),
						connectionScreen.getHeight());
				add(connectionScreen);
				inConnectionScreen = true;
				repaint();
				establishConnection(this);
			} else if (!serverMenu.joinGameSelected()) {
				mainMenu = new MainMenu(this);
				mainMenu.setBounds((WIDTH - MainMenu.WIDTH) / 2,
						(HEIGHT - MainMenu.HEIGHT) / 2,
						MainMenu.WIDTH, MainMenu.HEIGHT);
				add(mainMenu);
				inMainMenu = true;
			}
			serverMenu = null;
			inServerMenu = false;
			repaint();
		} else if (inSettingsMenu) {
			if (settingsMenu.applySelected()) {
				setGameVolume(settingsMenu.getGameVolume());
				setMusicVolume(settingsMenu.getMusicVolume());
				setMasterVolume(settingsMenu.getMasterVolume());
				settingsMenu.apply();
			} else if (!settingsMenu.applySelected()) {
				remove(settingsMenu);
				if (settingsMenu.getAlt().equals("MAIN MENU")) {
					add(mainMenu);
					inMainMenu = true;
				} else if (settingsMenu.getAlt().equals("GAME LOBBY")) {
					add(gameLobby);
					inGameLobby = true;
				}
				settingsMenu = null;
				inSettingsMenu = false;
				repaint();
			}
		} else if (inConnectionScreen) {
			if (connectionScreen.getConnectionType() != 1) {
				remove(connectionScreen);
				if (connectionScreen.getConnectionType() == 0) {
					serverMenu = new ServerMenu(this);
					serverMenu.setBounds((WIDTH - serverMenu.getWidth()) / 2,
							(HEIGHT - serverMenu.getHeight()) / 2,
							serverMenu.getWidth(), serverMenu.getHeight());
					add(serverMenu);
					inServerMenu = true;
				} else if (connectionScreen.getConnectionType() == 2) {
					gameLobby = new GameLobby(this);
					gameLobby.setBounds((WIDTH - gameLobby.getWidth()) / 2,
							(HEIGHT - gameLobby.getHeight()) / 2,
							gameLobby.getWidth(), gameLobby.getHeight());
					add(gameLobby);
					inGameLobby = true;
				}
				connectionScreen = null;
				inConnectionScreen = false;
				repaint();
			}
		} else if (inGameLobby) {

		}
	}

	public void enterSettingsMenu(int alt) {
		settingsMenu = new SettingsMenu(alt, this);
		settingsMenu.setBounds((WIDTH - settingsMenu.getWidth()) / 2,
				(HEIGHT - settingsMenu.getHeight()) / 2,
				settingsMenu.getWidth(), settingsMenu.getHeight());
		switch (alt) {
		case 0:
			remove(mainMenu);
			inMainMenu = false;
			break;
		case 1:
			remove(gameLobby);
			inGameLobby = false;
			break;
		}
		add(settingsMenu);
		inSettingsMenu = true;
		repaint();
		restoreFocus();
	}

	public synchronized void leaveGame() {
		remove(gameLobby);
		serverMenu = new ServerMenu(this);
		serverMenu.setBounds((WIDTH - serverMenu.getWidth()) / 2,
				(HEIGHT - serverMenu.getHeight()) / 2, serverMenu.getWidth(),
				serverMenu.getHeight());
		add(serverMenu);
		inServerMenu = true;
		gameLobby = null;
		inGameLobby = false;
		repaint();
		restoreFocus();
		try {
			connection.close();
		} catch (NullPointerException e) {
		}
		connection = null;
	}

	public void establishConnection(final MasterWindow masterWindow) {
		new Thread(new Runnable() {
			public void run() {
				connection = new Connection(ipAddress, portNumber, username,
						masterWindow);
				connection.listen();
			}
		}).start();
	}

	public synchronized void failConnection() {
		writeUserPresets();
		if (inConnectionScreen) {
			connection = null;
			connectionScreen.setConnection(false);
			repaint();
		}
	}

	public synchronized void achieveConnection() {
		writeUserPresets();
		if (inConnectionScreen) {
			connectionScreen.setConnection(true);
			repaint();
		}
	}

	public void writeUserPresets() {
		try {
			new Formatter(presetsPath + "presets.txt").format(
					ipAddress + "\n" + fromInt(portNumber) + "\n" + username)
					.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreFocus() {
		requestFocus();
		validate();
	}

	public String getIPAddress() {
		return ipAddress;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public String getUsername() {
		return username;
	}

	public synchronized void setIPAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public synchronized void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	public synchronized void setUsername(String username) {
		this.username = username;
	}

	public int getGameVolume() {
		return gameVolume;
	}

	public int getMusicVolume() {
		return musicVolume;
	}

	public int getMasterVolume() {
		return masterVolume;
	}

	public synchronized void setGameVolume(int gameVolume) {
		this.gameVolume = gameVolume;
	}

	public synchronized void setMusicVolume(int musicVolume) {
		this.musicVolume = musicVolume;
	}

	public synchronized void setMasterVolume(int masterVolume) {
		this.masterVolume = masterVolume;
	}

	public void sendMessage(String message) {
		connection.send(message);
	}

	public void gameLobbyChat(String message) {
		gameLobby.log(message);
	}

	public void addPlayer(String username) {
		gameLobby.addPlayer(username);
	}

	public void removePlayer(String username) {
		gameLobby.removePlayer(username);
	}

	public String fromInt(int i) {
		return String.valueOf(i);
	}

	public int fromString(String string) {
		return Integer.parseInt(string);
	}
}
