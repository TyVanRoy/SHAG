package shag.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import shag.client.main.Shag;

public class MainMenu extends JPanel implements Runnable, MouseListener {
	public static final long serialVersionUID = 2053433290886154023L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final Dimension DIMENSIONS = new Dimension(WIDTH, HEIGHT);
	public static final int TITLE_FRAME_RATE = 1;
	public static final int SELECTION_FRAME_RATE = 6;
	public static final String FONTS = Shag.getFonts();
	private Image[] titleFrames = new Image[24];
	private int titleFrameRefresher = 0;
	private int selectionFrameRefresher = 0;
	private int titleFrame = 0;
	private int titleHeight, titleYFillSpace;
	private Font mainFont;
	private boolean play = true;
	private boolean selectionVisible = true;
	private boolean running = false;
	private boolean animating = false;
	private static String titleFramePath = Shag.getTitleFrames();
	private MasterWindow window;
	private CommandBox commandBox;

	public MainMenu(MasterWindow window) {
		this.window = window;
		format();
		new Thread(this).start();
	}

	public void format() {
		loadImagesAndFonts();
		setPreferredSize(DIMENSIONS);
		setMinimumSize(DIMENSIONS);
		setMaximumSize(DIMENSIONS);
		setBackground(Color.BLACK);
		addMouseListener(this);
	}

	public void loadImagesAndFonts() {
		try {
			for (int i = 0; i < titleFrames.length; i++) {
				titleFrames[i] = ImageIO.read(new File(titleFramePath
						+ String.format("main_title_f%d.png", i + 1)));
			}
			titleHeight = titleFrames[0].getHeight(null);
			titleYFillSpace = titleHeight - titleHeight;
			mainFont = Font.createFont(Font.TRUETYPE_FONT, new File(FONTS
					+ "PressStart2P.ttf"));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void run() {
		running = true;
		animating = true;
		try {
			while (running) {
				while (animating) {
					evaluateFrameRate();
					evaluateCommandTime();
					repaint();
					Thread.sleep(100);
				}
				Thread.sleep(10);
			}
		} catch (Exception exception) {
			running = false;
			exception.printStackTrace();
		}
	}

	public void evaluateFrameRate() {
		selectionFrameRefresher++;
		titleFrameRefresher++;
		if (titleFrame < titleFrames.length - 1) {
			if (titleFrameRefresher % TITLE_FRAME_RATE == 0) {
				titleFrame++;
			}
		}
		if (selectionFrameRefresher % SELECTION_FRAME_RATE == 0) {
			selectionVisible = !selectionVisible;
		}
	}

	public void evaluateCommandTime() {
		if (window.getCommandTime() != 0) {
			window.setCommandTime(window.getCommandTime() - 1);
		}
	}
	
	public void initializeCommandBox() {
		commandBox = new CommandBox(window);
		commandBox.setBounds((WIDTH / 2) - 200, (HEIGHT / 2) + 200, 400, 40);
		add(commandBox);
		commandBox.requestFocus();
	}

	public void deleteCommandBox() {
		remove(commandBox);
		commandBox = null;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(titleFrames[titleFrame], (WIDTH - titleFrames[titleFrame].getWidth(null)) / 2, titleYFillSpace,
				null);
		g.setColor(Color.white);
		if (play) {
			if (selectionVisible) {
				g.setFont(mainFont.deriveFont(24f));
				g.drawString("PLAY", (WIDTH / 2) - 45, 300);
			}
			g.setFont(mainFont.deriveFont(12f));
			g.drawString("SETTINGS", (WIDTH / 2) - 45, 330);
		} else {
			if (selectionVisible) {
				g.setFont(mainFont.deriveFont(12f));
				g.drawString("SETTINGS", (WIDTH / 2) - 45, 330);
			}
			g.setFont(mainFont.deriveFont(24f));
			g.drawString("PLAY", (WIDTH / 2) - 45, 300);
		}
	}

	public int toInt(boolean b) {
		return b ? 1 : 0;
	}

	public synchronized void moveSelectionUp() {
		selectionFrameRefresher = 0;
		play = !play ? !play : play;
		selectionVisible = false;
	}

	public synchronized void moveSelectionDown() {
		selectionFrameRefresher = 0;
		play = play ? !play : play;
		selectionVisible = false;
	}

	public synchronized boolean playSelected() {
		return play;
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