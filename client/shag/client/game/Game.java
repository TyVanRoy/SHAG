package shag.client.game;

import shag.client.gui.MasterWindow;

public class Game implements Runnable {
	public static final int FRAME_RATE = 60;
	private MasterWindow window;

	public Game(MasterWindow window) {
		this.window = window;
	}
	
	public MasterWindow getWindow(){
		return window;
	}

	@Override
	public void run() {

	}

}
