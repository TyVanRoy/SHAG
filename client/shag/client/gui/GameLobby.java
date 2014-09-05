package shag.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import shag.client.main.Shag;

public class GameLobby extends JPanel implements MouseListener {
	private static final long serialVersionUID = -2222392005872503377L;
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 800;
	private static final Dimension DIMENSIONS = new Dimension(WIDTH, HEIGHT);
	private static final Dimension BUTTON_DIMENSIONS = new Dimension(150, 125);
	private static final String FONTS = Shag.getFonts();
	private Font mainFont;
	private boolean staticComponentsPainted = false;
	private JLabel settingsLabel, leaveGameLabel;
	private MasterWindow window;
	private JTextField messageField;
	private JTextArea usernameField;
	private JTextArea chatField;
	private JScrollPane chatScrollPane;
	private GameLobbyFocusPanel focusPanel;
	private ArrayList<String> players = new ArrayList<String>();

	public GameLobby(MasterWindow window) {
		this.window = window;
		format();
	}

	public void format() {
		loadImagesAndFonts();
		setPreferredSize(DIMENSIONS);
		setMinimumSize(DIMENSIONS);
		setMaximumSize(DIMENSIONS);
		setLayout(null);
		setBackground(Color.BLACK);
		addMouseListener(this);
		formatLabels();
		formatFields();
		formatFocusElements();
	}

	public void formatLabels() {
		settingsLabel = new JLabel("SETTINGS", JLabel.CENTER);
		leaveGameLabel = new JLabel("LEAVE GAME", JLabel.CENTER);
		settingsLabel.addMouseListener(this);
		leaveGameLabel.addMouseListener(this);
		settingsLabel.setBorder(new LineBorder(Color.WHITE));
		leaveGameLabel.setBorder(new LineBorder(Color.RED));
		settingsLabel.setForeground(Color.WHITE);
		leaveGameLabel.setForeground(Color.RED);
		settingsLabel.setFont(mainFont.deriveFont(12f));
		leaveGameLabel.setFont(mainFont.deriveFont(12f));
		setBounds(settingsLabel, BUTTON_DIMENSIONS, WIDTH
				- (int) BUTTON_DIMENSIONS.getWidth(), 674);
		setBounds(leaveGameLabel, BUTTON_DIMENSIONS, WIDTH
				- (int) (BUTTON_DIMENSIONS.getWidth() * 2), 674);
		add(settingsLabel);
		add(leaveGameLabel);
	}

	public void formatFields() {
		messageField = new MessageField(window);
		usernameField = new JTextArea();
		chatField = new JTextArea();
		chatScrollPane = new JScrollPane(chatField);
		chatField.setEditable(false);
		usernameField.setBorder(new LineBorder(Color.RED));
		chatField.setBorder(null);
		chatScrollPane.setBorder(new LineBorder(Color.WHITE));
		usernameField.setBackground(Color.WHITE);
		chatField.setBackground(Color.BLACK);
		chatScrollPane.setBackground(Color.BLACK);
		usernameField.setForeground(Color.BLACK);
		chatField.setForeground(Color.WHITE);
		chatScrollPane.setForeground(Color.WHITE);
		usernameField.setFont(mainFont.deriveFont(12f));
		chatField.setFont(mainFont.deriveFont(8f));
		messageField.setBounds(WIDTH - 300, 625, 300, 25);
		usernameField.setBounds(WIDTH - 300, 25, 300, 125);
		chatScrollPane.setBounds(WIDTH - 300, 175, 300, 450);
		logPlayers();
		add(messageField);
		add(usernameField);
		add(chatScrollPane);
	}
	
	public void formatFocusElements(){
		focusPanel = new GameLobbyFocusPanel(this);
		focusPanel.setBounds(20,80,focusPanel.getWidth(),focusPanel.getHeight());
		add(focusPanel);
	}

	public void setBounds(JComponent component, Dimension dimension, int x,
			int y) {
		component.setBounds(x, y, (int) dimension.getWidth(),
				(int) dimension.getHeight());
	}

	public void loadImagesAndFonts() {
		try {
			mainFont = Font.createFont(Font.TRUETYPE_FONT, new File(FONTS
					+ "PressStart2P.ttf"));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!staticComponentsPainted) {
			paintStaticComponents(g);
		}
	}

	public void paintStaticComponents(Graphics g) {
		g.setFont(mainFont.deriveFont(64f));
		g.setColor(Color.BLUE);
		g.drawString("GAME", 20, 70);
		g.setColor(Color.YELLOW);
		g.drawString("LOBBY", 300, 70);
	}

	public void addPlayer(String username) {
		players.add(username);
		logPlayers();
	}

	public void removePlayer(String username) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).equals(username)) {
				players.remove(i);
				log(username + " has left the game!");
				break;
			}
		}
		logPlayers();
	}

	public void logPlayers() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				usernameField.setText("");
				for (int i = 0; i < players.size(); i++) {
					usernameField.append(" " + players.get(i) + "\n");
				}
			}
		});
	}

	public void log(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatField.append(String.format("%s\n", message));
			}
		});
	}

	public void send(String message) {
		window.sendMessage(message);
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

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == settingsLabel) {
			window.enterSettingsMenu(SettingsMenu.GAME_LOBBY);
		} else if (e.getSource() == leaveGameLabel) {
			window.leaveGame();
		}
		window.restoreFocus();
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
