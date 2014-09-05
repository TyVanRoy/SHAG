package shag.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTextField;

import shag.client.main.Shag;

public class SettingsMenu extends JPanel implements Runnable, ActionListener, MouseListener {
	private static final long serialVersionUID = 1864133480062687632L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final int SELECTION_FRAME_RATE = 6;
	private static final Dimension DIMENSIONS = new Dimension(WIDTH, HEIGHT);
	private static final String FONTS = Shag.getFonts();
	private static final int VERTICLE_OPTIONS_LEVEL = 550;
	private static final int ALT_X = (((WIDTH / 2) - 284) / 2) + 15;
	private static final int APPLY_X = (WIDTH / 2 + (((WIDTH / 2) - 160) / 2)) - 15;
	private static final int GAME_VOLUME_Y = 100;
	private static final int MUSIC_VOLUME_Y = 200;
	private static final int MASTER_VOLUME_Y = 300;
	public static final int MAIN_MENU = 0;
	public static final int GAME_LOBBY = 1;
	private int selectionFrameRefresher = 0;
	private int applied = 0;
	private boolean running;
	private boolean selectionVisible = true;
	private boolean apply = true;
	private MasterWindow window;
	private Font mainFont;
	private InputField gameVolumeField, musicVolumeField, masterVolumeField;
	private int gameVolume, musicVolume, masterVolume;
	private String alt;

	public SettingsMenu(int alt, MasterWindow window) {
		switch (alt) {
		case MAIN_MENU:
			this.alt = "MAIN MENU";
			break;
		case GAME_LOBBY:
			this.alt = "GAME LOBBY";
			break;
		}
		this.window = window;
		gameVolume = window.getGameVolume();
		musicVolume = window.getMusicVolume();
		masterVolume = window.getMasterVolume();
		format();
		new Thread(this).start();
	}

	public String getAlt() {
		return alt;
	}

	public void format() {
		loadImagesAndFonts();
		setPreferredSize(DIMENSIONS);
		setMinimumSize(DIMENSIONS);
		setMaximumSize(DIMENSIONS);
		setLayout(null);
		setBackground(Color.BLACK);
		addMouseListener(this);
		gameVolumeField = new InputField(fromInt(window.getGameVolume()), 24F,
				window);
		gameVolumeField.setHorizontalAlignment(JTextField.RIGHT);
		gameVolumeField.setBounds(433, GAME_VOLUME_Y - 25, 72, 26);
		gameVolumeField.addActionListener(this);
		musicVolumeField = new InputField(fromInt(window.getMusicVolume()),
				24F, window);
		musicVolumeField.setHorizontalAlignment(JTextField.RIGHT);
		musicVolumeField.setBounds(433, MUSIC_VOLUME_Y - 25, 72, 26);
		musicVolumeField.addActionListener(this);
		masterVolumeField = new InputField(fromInt(window.getMasterVolume()),
				24F, window);
		masterVolumeField.setHorizontalAlignment(JTextField.RIGHT);
		masterVolumeField.setBounds(433, MASTER_VOLUME_Y - 25, 72, 26);
		masterVolumeField.addActionListener(this);
		add(gameVolumeField);
		add(musicVolumeField);
		add(masterVolumeField);
	}

	public void loadImagesAndFonts() {
		try {
			mainFont = Font.createFont(Font.TRUETYPE_FONT, new File(FONTS
					+ "PressStart2P.ttf"));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void run() {
		try {
			running = true;
			while (running) {
				evaluateFrameRate();
				repaint();
				Thread.sleep(100);
			}
		} catch (Exception exception) {
			running = false;
			exception.printStackTrace();
		}
	}

	public void evaluateFrameRate() {
		selectionFrameRefresher++;
		if (selectionFrameRefresher % SELECTION_FRAME_RATE == 0) {
			selectionVisible = !selectionVisible;
		}
		if (applied != 0) {
			applied++;
		}
		if (applied > 5) {
			applied = 0;
			gameVolumeField.setForeground(Color.WHITE);
			musicVolumeField.setForeground(Color.WHITE);
			masterVolumeField.setForeground(Color.WHITE);
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(applied != 0 ? Color.GREEN : Color.WHITE);
		if (applied != 0) {
			gameVolumeField.setForeground(Color.GREEN);
			musicVolumeField.setForeground(Color.GREEN);
			masterVolumeField.setForeground(Color.GREEN);
		}
		g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
		g.setFont(mainFont.deriveFont(24f));
		g.drawString("GAME VOLUME:   /100", WIDTH - 453 - 200, GAME_VOLUME_Y);
		g.drawString("MUSIC VOLUME:   /100", WIDTH - 477 - 200, MUSIC_VOLUME_Y);
		g.drawString("MASTER VOLUME:   /100", WIDTH - 501 - 200,
				MASTER_VOLUME_Y);
		g.setFont(mainFont.deriveFont(32f));
		if (apply) {
			if (selectionVisible) {
				g.drawString("APPLY", APPLY_X, VERTICLE_OPTIONS_LEVEL);
			}
			g.drawString(alt, ALT_X, VERTICLE_OPTIONS_LEVEL);
		} else {
			if (selectionVisible) {
				g.drawString(alt, ALT_X, VERTICLE_OPTIONS_LEVEL);
			}
			g.drawString("APPLY", APPLY_X, VERTICLE_OPTIONS_LEVEL);
		}
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

	public synchronized void moveSelectionRight() {
		selectionFrameRefresher = 0;
		apply = !apply ? !apply : apply;
		selectionVisible = false;
	}

	public synchronized void moveSelectionLeft() {
		selectionFrameRefresher = 0;
		apply = apply ? !apply : apply;
		selectionVisible = false;
	}

	public synchronized boolean applySelected() {
		return apply;
	}

	public String fromInt(int i) {
		return String.valueOf(i);
	}

	public int fromString(String string) {
		return Integer.parseInt(string);
	}

	public void actionPerformed(ActionEvent e) {
		JTextField textField = (JTextField) e.getSource();
		try {
			int i = fromString(textField.getText());
			if (i > 100) {
				textField.setText("100");
			} else if (i < 0) {
				textField.setText("0");
			}
		} catch (Exception exception) {
			if (textField.getText().equals("")) {
				textField.setText("0");
			} else {
				textField.setText("100");
			}
		}
		if (textField == gameVolumeField) {
			gameVolume = fromString(textField.getText());
		} else if (textField == musicVolumeField) {
			musicVolume = fromString(textField.getText());
		} else if (textField == masterVolumeField) {
			masterVolume = fromString(textField.getText());
		}
		window.restoreFocus();
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

	public synchronized void apply() {
		applied = 1;
	}

	public void mouseClicked(MouseEvent e) {	
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
