package main.gfx;

import java.awt.image.BufferedImage;

// all textures are load and stored in here

public class Assets {
	
	// defualt one segment of spritesheet resolution
	private static final int width = 32, height = 32;
	
	// players
	public static BufferedImage[] bluePlayer, redPlayer;
	// tiles
	public static BufferedImage grassTile, blueTower, redTower, redHealTile, blueHealTile, wallTile;
	// buttons
	public static BufferedImage[] startButton, exitButton, tryAgainButton, htpButton, backButton;
	// simple buttons
	public static BufferedImage redSideButton, blueSideButton;
	// instruction graphics
	public static BufferedImage howToPlay;
	
	public static BufferedImage blueBasicTower, redBasicTower;
	public static BufferedImage basicBullet_upDown, basicBullet_leftRight;
	public static BufferedImage castle;
	
	public static void init() {
		SpriteSheet bluePlayerSheet = new SpriteSheet(ImageLoader.loadImage("/textures/bluePlayer.png"));
		SpriteSheet redPlayerSheet = new SpriteSheet(ImageLoader.loadImage("/textures/redPlayer.png"));
		SpriteSheet buttons = new SpriteSheet(ImageLoader.loadImage("/textures/buttons.png"));
		SpriteSheet tiles = new SpriteSheet(ImageLoader.loadImage("/textures/tiles.png"));
		SpriteSheet bullets = new SpriteSheet(ImageLoader.loadImage("/textures/bullets.png"));
		SpriteSheet towers = new SpriteSheet(ImageLoader.loadImage("/textures/towers.png"));
		
		castle = ImageLoader.loadImage("/textures/castle.png");
		howToPlay = ImageLoader.loadImage("/textures/htp.png");
		
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
		redHealTile = tiles.crop(3*width, 0, width, height);
		blueHealTile = tiles.crop(4*width, 0, width, height);
		wallTile = tiles.crop(5*width, 0, width, height);
		
		basicBullet_upDown = bullets.crop(0, 0, width / 2, height / 2);
		basicBullet_leftRight = bullets.crop(width / 2, 0, width / 2, height / 2);
		
		startButton = new BufferedImage[2];
		startButton[0] = buttons.crop(0, 0, width * 4, height * 2);
		startButton[1] = buttons.crop(width * 4, 0, width * 4, height * 2);
		
		exitButton = new BufferedImage[2];
		exitButton[0] = buttons.crop(width * 8, 0, width * 4, height * 2);
		exitButton[1] = buttons.crop(width * 12, 0, width * 4, height * 2);
		
		htpButton = new BufferedImage[2];
		htpButton[0] = buttons.crop(width * 16, 0, width * 4, height * 2);
		htpButton[1] = buttons.crop(width * 16, height * 2, width * 4, height * 2);
		
		backButton = new BufferedImage[2];
		backButton[0] = buttons.crop(0, height * 4, width * 4, height * 2);
		backButton[1] = buttons.crop(width * 4, height * 4, width * 4, height * 2);
		
		redSideButton = buttons.crop(0, height * 2, width * 4, height * 2);
		blueSideButton = buttons.crop(width * 4, height * 2, width * 4, height * 2);
		
		tryAgainButton = new BufferedImage[2];
		tryAgainButton[0] = buttons.crop(width * 8, height * 2, width * 4, height * 2);
		tryAgainButton[1] = buttons.crop(width * 12, height * 2, width * 4, height * 2);
		
		blueBasicTower = towers.crop(0, 0, (int)( width * 1.5), (int)(height * 1.5));
		redBasicTower = towers.crop((int)( width * 1.5), 0, (int)( width * 1.5), (int)(height * 1.5));
		
		
	}
}
