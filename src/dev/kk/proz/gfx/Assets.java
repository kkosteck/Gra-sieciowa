package dev.kk.proz.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 32, height = 32;
	
	public static BufferedImage redPlayer, bluePlayer, map;
	public static BufferedImage grassTile, blueTower, redTower, blueCastle, redCastle, wallTile;
	public static BufferedImage[] startButton, exitButton;
	
	public static void init() {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/players.png"));
		SpriteSheet tiles = new SpriteSheet(ImageLoader.loadImage("/textures/tiles.png"));

		bluePlayer = sheet.crop(0, 0, width, height);
		redPlayer = sheet.crop(width, 0, width, height);
		
		grassTile = tiles.crop(0, 0, width, height);
		blueTower = tiles.crop(width, 0, width, height);
		redTower = tiles.crop(2*width, 0, width, height);
		redCastle = tiles.crop(3*width, 0, width, height);
		blueCastle = tiles.crop(4*width, 0, width, height);
		wallTile = tiles.crop(5*width, 0, width, height);
		
		startButton = new BufferedImage[2];
		startButton[0] = ImageLoader.loadImage("/textures/startButtonOff.png");
		startButton[1] = ImageLoader.loadImage("/textures/startButtonOn.png");
		
		exitButton = new BufferedImage[2];
		exitButton[0] = ImageLoader.loadImage("/textures/exitButtonOff.png");
		exitButton[1] = ImageLoader.loadImage("/textures/exitButtonOn.png");
		
		map = ImageLoader.loadImage("/textures/map.png");
	}
}
