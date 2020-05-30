package dev.kk.proz.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 100, height = 100;
	
	public static BufferedImage sadFace, happyFace;
	
	public static void init() {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/spriteSheet.png"));
		
		happyFace = sheet.crop(0, 0, width, height);
		sadFace = sheet.crop(width, 0, width, height);
		
	}
}
