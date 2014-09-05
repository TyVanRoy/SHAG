package shag.client.main;

import shag.client.gui.MasterWindow;

public class Shag {
	private static MasterWindow window;
	private static String resources;
	private static String images;
	private static String titleFrames;
	private static String fonts;
	private static String presets;
	
	public static void main(String[] args) {
		start();
	}

	public static void start() {
		configurePaths();
		window = new MasterWindow();
		window.launch();
	}

	public static void configurePaths() {
		String pathFinder;
		
		if (System.getProperty("os.name").equals("Mac OS X")) {
			pathFinder = "//";
		}else{
			pathFinder = "\\";
		}
		resources = "resources" + pathFinder;
		images = resources + "images" + pathFinder;
		titleFrames = images + "titleframes" + pathFinder;
		fonts = resources + "fonts" + pathFinder;
		presets = resources + "presets" + pathFinder;
	}

	public static MasterWindow getScreen() {
		return window;
	}

	public static String getImages() {
		return images;
	}

	public static String getTitleFrames() {
		return titleFrames;
	}

	public static String getFonts() {
		return fonts;
	}

	public static String getPresets() {
		return presets;
	}
}
