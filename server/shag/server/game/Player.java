package shag.server.game;

import shag.server.io.Connection;

public class Player extends Sprite {
	private static final int WIDTH = 100;
	private static final int HEIGHT = 100;
	private static final int MOVEMENT_UNIT = 5;
	private boolean goldTeam;
	private int type;
	private String username;
	private Connection connection;
	private Game game;

	public Player(Game game, Connection connection, String username, int x,
			int y) {
		super(x, y, WIDTH, HEIGHT);
		this.game = game;
		this.connection = connection;
		this.username = username;
		connection.setPlayer(this);
	}

	public void chat(String message) {
		game.chat(username, message);
	}

	public void send(String message) {
		connection.send(message);
	}

	public Connection getConnection() {
		return connection;
	}

	public String getUsername() {
		return username;
	}

	public int getType() {
		return type;
	}

	public boolean getTeam() {
		return goldTeam;
	}

	public void joinTeam(boolean goldTeam) {
		this.goldTeam = goldTeam;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void moveUp() {
		setY(getY() - MOVEMENT_UNIT);
	}

	public void moveDown() {
		setY(getY() + MOVEMENT_UNIT);
	}

	public void moveLeft() {
		setY(getY() - MOVEMENT_UNIT);
	}

	public void moveRight() {
		setY(getY() + MOVEMENT_UNIT);
	}
}
