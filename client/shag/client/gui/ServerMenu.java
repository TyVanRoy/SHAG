package shag.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JPanel;

import shag.client.main.Shag;

public class ServerMenu extends JPanel implements Runnable, MouseListener {
	private static final long serialVersionUID = -1465606987187469124L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final Dimension DIMENSIONS = new Dimension(WIDTH, HEIGHT);
	private static final int SELECTION_FRAME_RATE = 6;
	private static final String FONTS = Shag.getFonts();
	private static final int VERTICLE_OPTIONS_X = 550;
	private static final int MAIN_MENU_X = (((WIDTH / 2) - 284) / 2) + 15;
	private static final int JOIN_GAME_X = (WIDTH / 2 + (((WIDTH / 2) - 284) / 2)) - 15;
	private static final int IP_Y = 125;
	private static final int PORT_Y = 250;
	private static final int USERNAME_Y = 375;
	private int selectionFrameRefresher = 0;
	private Font mainFont;
	private boolean selectionVisible = true;
	private boolean running;
	private boolean joinGame = true;
	private InputField usernameField, ipField, portField;
	MasterWindow window;

	public ServerMenu(MasterWindow window) {
		this.window = window;
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
		addMouseListener(this);
		ipField = new InputField(window.getIPAddress(), 32f, window);
		ipField.setBounds(336 + 40, IP_Y - 33, (WIDTH / 2) - 40, 34);
		portField = new InputField(
				fromInt(window.getPortNumber()).equals("0") ? ""
						: fromInt(window.getPortNumber()), 32f, window);
		portField.setBounds(368 + 40, PORT_Y - 33, (WIDTH / 2) - 40, 34);
		usernameField = new InputField(window.getUsername(), 32f, window);
		usernameField
				.setBounds(272 + 40, USERNAME_Y - 33, (WIDTH / 2) - 40, 34);
		add(ipField);
		add(portField);
		add(usernameField);
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
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.setFont(mainFont.deriveFont(32f));
		g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
		g.drawString("IP ADDRESS:", 20, IP_Y);
		g.drawString("PORT NUMBER:", 20, PORT_Y);
		g.drawString("USERNAME:", 20, USERNAME_Y);
		if (joinGame) {
			if (selectionVisible) {
				g.drawString("JOIN GAME", JOIN_GAME_X, VERTICLE_OPTIONS_X);
			}
			g.drawString("MAIN MENU", MAIN_MENU_X, VERTICLE_OPTIONS_X);
		} else {
			if (selectionVisible) {
				g.drawString("MAIN MENU", MAIN_MENU_X, VERTICLE_OPTIONS_X);
			}
			g.drawString("JOIN GAME", JOIN_GAME_X, VERTICLE_OPTIONS_X);
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
		joinGame = !joinGame ? !joinGame : joinGame;
		selectionVisible = false;
	}

	public synchronized void moveSelectionLeft() {
		selectionFrameRefresher = 0;
		joinGame = joinGame ? !joinGame : joinGame;
		selectionVisible = false;
	}

	public synchronized boolean joinGameSelected() {
		return joinGame;
	}

	public String getIPAddress() {
		return ipField.getText();
	}

	public int getPortNumber() {
		return fromString(portField.getText());
	}

	public String getUsername() {
		return usernameField.getText();
	}

	public String fromInt(int i) {
		return String.valueOf(i);
	}

	public int fromString(String string) {
		return Integer.parseInt(string);
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
