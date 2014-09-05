package shag.server.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import shag.server.io.Server;

public class MasterServerWindow extends JFrame {
	private static final long serialVersionUID = -1287144940841934423L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 622;
	private static final Dimension DIMENSIONS = new Dimension(WIDTH, HEIGHT);
	private SetupMenu setupMenu;
	Server server;
	ServerLog serverLog;

	public MasterServerWindow() {
		super("SHAG Server");
		format();
		setVisible(true);
	}

	public void format() {
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setupMenu = new SetupMenu(this);
		add(setupMenu, BorderLayout.CENTER);
	}

	public void initiateNewServer(int portNumber, int players) {
		serverLog = new ServerLog(portNumber, players);
		server = new Server(players, portNumber, serverLog);
		remove(setupMenu);
		add(serverLog, BorderLayout.CENTER);
		setupMenu = null;
		repaint();
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
}
