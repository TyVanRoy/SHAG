package shag.server.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class ServerLog extends JPanel {
	private static final long serialVersionUID = -5810531013054563064L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final Dimension DIMENSIONS = new Dimension(WIDTH, HEIGHT);
	private Font titleFont = new Font("Times New Roman", Font.BOLD, 46);
	private Font mainFont = new Font("Times New Roman", Font.PLAIN, 14);
	private JScrollPane logScrollPane;
	private JTextArea log, playerLog;
	private JLabel titleLabel;
	private ArrayList<String> players = new ArrayList<String>();

	public ServerLog(int portNumber, int players) {
		format();
		initialLog(portNumber, players);
	}

	public void format() {
		setPreferredSize(DIMENSIONS);
		setMinimumSize(DIMENSIONS);
		setMaximumSize(DIMENSIONS);
		setLayout(null);
		setBackground(Color.BLACK);
		titleLabel = new JLabel("SHAG SERVER LOG", JLabel.CENTER);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(titleFont);
		titleLabel.setBorder(new LineBorder(Color.BLACK));
		titleLabel.setBounds(0, 0, 500, 201);
		playerLog = new JTextArea();
		playerLog.setFont(mainFont);
		playerLog.setBorder(new LineBorder(Color.WHITE));
		playerLog.setBackground(Color.BLACK);
		playerLog.setForeground(Color.WHITE);
		playerLog.setBounds(500, 1, 300, 201);
		log = new JTextArea();
		log.setFont(mainFont);
		log.setBackground(Color.BLACK);
		log.setForeground(Color.WHITE);
		log.setBorder(null);
		logScrollPane = new JScrollPane(log);
		logScrollPane.setBorder(new LineBorder(Color.WHITE));
		logScrollPane.setBounds(20, 201, WIDTH - 20, HEIGHT - 201);
		add(titleLabel);
		add(playerLog);
		add(logScrollPane);
	}

	public void initialLog(final int portNumber, final int players) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				log.append("::::::::::::::::::::::::::::::::::::"
						+ "::::::::::::::::::::::::::::::::::::"
						+ "::::::::::::::::::::::::::::::::::::"
						+ ":::::::::::::::::::::::::::::\n");
				String string = String.format(
						"New server created on port %d waiting for %d players "
								+ "::::::\n"
								+ "::::::::::::::::::::::::::::::::::::"
								+ "::::::::::::::::::::::::::::::::::::"
								+ "::::::::::::::::::::::::::::::::::::"
								+ ":::::::::::::::::::::::::::::", portNumber,
						players);
				log.append(String.format("**%s: %s\n",
						String.valueOf(new java.util.Date()), string));
			}
		});
	}

	public void log(final String string) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				log.append(String.format("**%s: %s\n",
						String.valueOf(new java.util.Date()), string));
			}
		});
	}

	public void logPlayers() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				playerLog.setText("");
				for (int i = 0; i < players.size(); i++) {
					playerLog.append(players.get(i) + " \n");
				}
			}
		});
	}

	public void addPlayer(String playerName) {
		players.add(playerName);
		logPlayers();
	}

	public void removePlayer(String playerName) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).equals(playerName)) {
				players.remove(i);
				break;
			}
		}
		logPlayers();
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
