package dev.kk.proz.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 32, height = 32;
	
	public static BufferedImage bluePlayer_up, bluePlayer_down, bluePlayer_left, bluePlayer_right;
	public static BufferedImage redPlayer_up, redPlayer_down, redPlayer_left, redPlayer_right;
	
	
	public static BufferedImage grassTile, blueTower, redTower, blueCastle, redCastle, wallTile;
	public static BufferedImage[] startButton, exitButton;
	public static BufferedImage redSideButton, blueSideButton;
	
	public static BufferedImage basicTower;
	
	public static void init() {
		SpriteSheet bluePlayer = new SpriteSheet(ImageLoader.loadImage("/textures/bluePlayer.png"));
		SpriteSheet redPlayer = new SpriteSheet(ImageLoader.loadImage("/textures/redPlayer.png"));
		SpriteSheet buttons = new SpriteSheet(ImageLoader.loadImage("/textures/buttons.png"));
		SpriteSheet tiles = new SpriteSheet(ImageLoader.loadImage("/textures/tiles.png"));
		
		bluePlayer_right = bluePlayer.crop(0, 0, width, height);
		bluePlayer_up = bluePlayer.crop(width, 0, width, height);
		bluePlayer_left = bluePlayer.crop(2*width, 0, width, height);
		bluePlayer_down = bluePlayer.crop(3*width, 0, width, height);
		
		redPlayer_right = redPlayer.crop(0, 0, width, height);
		redPlayer_up = redPlayer.crop(width, 0, width, height);
		redPlayer_left = redPlayer.crop(2*width, 0, width, height);
		redPlayer_down = redPlayer.crop(3*width, 0, width, height);
		
		
		grassTile = tiles.crop(0, 0, width, height);
		blueTower = tiles.crop(width, 0, width, height);
		redTower = tiles.crop(2*width, 0, width, height);
		redCastle = tiles.crop(3*width, 0, width, height);
		blueCastle = tiles.crop(4*width, 0, width, height);
		wallTile = tiles.crop(5*width, 0, width, height);
		
		
		
		startButton = new BufferedImage[2];
		startButton[0] = buttons.crop(0, 0, width * 4, height * 2);
		startButton[1] = buttons.crop(width * 4, 0, width * 4, height * 2);
		
		exitButton = new BufferedImage[2];
		exitButton[0] = buttons.crop(width * 8, 0, width * 4, height * 2);
		exitButton[1] = buttons.crop(width * 12, 0, width * 4, height * 2);
		
		redSideButton = buttons.crop(0, height * 2, width * 4, height * 2);
		blueSideButton = buttons.crop(width * 4, height * 2, width * 4, height * 2);
		
		basicTower = ImageLoader.loadImage("/textures/tower.png");
	}
}
