package dev.kk.proz.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 32, height = 32;
	
	public static BufferedImage[] bluePlayer, redPlayer;
	
	
	public static BufferedImage grassTile, blueTower, redTower, blueCastle, redCastle, wallTile;
	public static BufferedImage[] startButton, exitButton;
	public static BufferedImage redSideButton, blueSideButton;
	
	public static BufferedImage basicTower;
	public static BufferedImage basicBullet_upDown, basicBullet_leftRight;
	
	public static void init() {
		SpriteSheet bluePlayerSheet = new SpriteSheet(ImageLoader.loadImage("/textures/bluePlayer.png"));
		SpriteSheet redPlayerSheet = new SpriteSheet(ImageLoader.loadImage("/textures/redPlayer.png"));
		SpriteSheet buttons = new SpriteSheet(ImageLoader.loadImage("/textures/buttons.png"));
		SpriteSheet tiles = new SpriteSheet(ImageLoader.loadImage("/textures/tiles.png"));
		SpriteSheet bullets = new SpriteSheet(ImageLoader.loadImage("/textures/bullets.png"));
		
		bluePlayer = new BufferedImage[4];
		bluePlayer[3] = bluePlayerSheet.crop(0, 0, width, height);
		bluePlayer[0] = bluePlayerSheet.crop(width, 0, width, height);
		bluePlayer[2] = bluePlayerSheet.crop(2*width, 0, width, height);
		bluePlayer[1] = bluePlayerSheet.crop(3*width, 0, width, height);
		
		redPlayer = new BufferedImage[4];
		redPlayer[3] = redPlayerSheet.crop(0, 0, width, height);
		redPlayer[0] = redPlayerSheet.crop(width, 0, width, height);
		redPlayer[2] = redPlayerSheet.crop(2*width, 0, width, height);
		redPlayer[1] = redPlayerSheet.crop(3*width, 0, width, height);
		
		
		grassTile = tiles.crop(0, 0, width, height);
		blueTower = tiles.crop(width, 0, width, height);
		redTower = tiles.crop(2*width, 0, width, height);
		redCastle = tiles.crop(3*width, 0, width, height);
		blueCastle = tiles.crop(4*width, 0, width, height);
		wallTile = tiles.crop(5*width, 0, width, height);
		
		basicBullet_upDown = bullets.crop(0, 0, width / 2, height / 2);
		basicBullet_leftRight = bullets.crop(width / 2, 0, width / 2, height / 2);
		
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
