package shag.client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import shag.client.game.BattleShip;
import shag.client.game.Game;

public class GameCanvas extends Canvas implements Runnable {
	private static final long serialVersionUID = -5536259926678068734L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final Dimension DIMENSIONS = new Dimension(WIDTH, HEIGHT);
	private Game game;
	private MasterWindow window;
	private BufferStrategy buffer;
	private boolean rendering = false;
	private BattleShip testShip = new BattleShip(100, 100);

	public GameCanvas(Game game, MasterWindow window) {
		this.game = game;
		this.window = window;
	}
	
	public MasterWindow getWindow(){
		return window;
	}
	
	public void startRendering(){
		new Thread(this).start();
	}

	@Override
	public void run() {
		rendering = true;
		while (rendering) {
			try {
				Thread.sleep(1000 / Game.FRAME_RATE);
				render();
			} catch (Exception e) {
				rendering = false;
				e.printStackTrace();
			}

		}
	}

	private void render() {
		if (buffer == null) {
			createBufferStrategy(2);
			buffer = getBufferStrategy();
		}

		Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		paintShips(g);

		g.dispose();
		buffer.show();
		Toolkit.getDefaultToolkit().sync();
	}

	private void paintShips(Graphics2D g) {
		g.setColor(Color.white);
		g.draw(testShip.getShape());
	}

	public Game getGame() {
		return game;
	}

}
