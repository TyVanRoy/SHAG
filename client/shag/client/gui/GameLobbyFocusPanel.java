package shag.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import javax.swing.JPanel;

import shag.client.main.Shag;

public class GameLobbyFocusPanel extends JPanel{
	private static final long serialVersionUID = -4757088856673857462L;
	private static final int WIDTH = 940;
	private static final int HEIGHT = 700;
	private static final Dimension DIMENSIONS = new Dimension(WIDTH, HEIGHT);
	private GameLobby gameLobby;
	private boolean staticComponentsPainted = false;
	private boolean inTeamSelector;
	private Font mainFont;

	public GameLobbyFocusPanel(GameLobby gameLobby){
		this.gameLobby = gameLobby;
		inTeamSelector = true;
		loadImagesAndFonts();
	}

	public void loadImagesAndFonts(){
		try{
			mainFont = Font.createFont(Font.TRUETYPE_FONT,
					new File(Shag.getFonts() + "PressStart2P.ttf"));
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(!staticComponentsPainted){
			paintStaticComponents(g);
			staticComponentsPainted = true;
		}
		if(inTeamSelector){
			paintTeamSelector(g);
			staticComponentsPainted = true;
		}
	}

	public void paintTeamSelector(Graphics g){
		g.setColor(Color.BLUE);
		g.fillRect(11, 11, WIDTH - 22, (HEIGHT - 22) / 2);
		g.setColor(Color.YELLOW);
		g.fillRect(11, HEIGHT / 2, WIDTH - 22, (HEIGHT - 22) / 2);
		g.setFont(mainFont.deriveFont(36f));
		g.setColor(Color.RED);
		g.drawString("BLUE TEAM", 30 ,66);
		g.drawString("GOLD TEAM", 30, HEIGHT - 30);
	}

	public void paintStaticComponents(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH - 1, HEIGHT - 1);
		g.setColor(Color.RED);
		g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
		g.setColor(Color.BLACK);
		g.drawRect(10, 10, WIDTH - 21, HEIGHT - 21);
	}

	public GameLobby getGameLobby(){
		return gameLobby;
	}

	public int getWidth(){
		return WIDTH;
	}

	public int getHeight(){
		return HEIGHT;
	}

	public Dimension getDimensions(){
		return DIMENSIONS;
	}

}
