package shag.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import javax.swing.JPanel;

import shag.client.main.Shag;

public class ConnectionScreen extends JPanel implements Runnable {
	private static final long serialVersionUID = -439572074840500745L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final Dimension DIMENSIONS = new Dimension(WIDTH, HEIGHT);
	private static final int SELECTION_FRAME_RATE = 6;
	private static final String FONTS = Shag.getFonts();
	private static final int FREE_SPACE = 0;
	private int selectionFrameRefresher = 6;
	private Font mainFont;
	private boolean selectionVisible = true;
	private boolean running;
	private int connection = 1;

	public ConnectionScreen() {
		format();
		new Thread(this).start();
	}

	public void format() {
		loadImagesAndFonts();
		setPreferredSize(DIMENSIONS);
		setMinimumSize(DIMENSIONS);
		setMaximumSize(DIMENSIONS);
		setLayout(null);
		setBackground(Color.BLACK);
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
		running = true;
		try {
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
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(mainFont.deriveFont(64f));
		switch (connection) {
		case 0:
			g.setColor(Color.RED);
			g.drawString("CONNECTION", (WIDTH - 632) / 2, ((HEIGHT / 2) - 10)
					- FREE_SPACE);
			g.drawString("FAILED", (WIDTH - 376) / 2,
					(((HEIGHT / 2) + 56) + 10) - FREE_SPACE);
			g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
			if (selectionVisible) {
				g.setFont(mainFont.deriveFont(32f));
				g.setColor(Color.WHITE);
				g.drawString("SERVER MENU", (WIDTH - 348) / 2, 500);
			}
			break;
		case 1:
			g.setColor(Color.BLUE);
			g.drawString("CONNECTING", (WIDTH - 632) / 2,
					((HEIGHT / 2) + (56 / 2)) - FREE_SPACE);
			g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
			break;
		case 2:
			g.setColor(Color.GREEN);
			g.drawString("CONNECTED", (WIDTH - 568) / 2,
					((HEIGHT / 2) + (56 / 2)) - FREE_SPACE);
			g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
			if (selectionVisible) {
				g.setFont(mainFont.deriveFont(32f));
				g.setColor(Color.WHITE);
				g.drawString("ENTER THE GAME", (WIDTH - 444) / 2, 500);
			}
			break;
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

	public void setConnection(boolean connected) {
		connection = connected ? 2 : 0;
	}

	public int getConnectionType() {
		return connection;
	}
}
