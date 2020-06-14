package main.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

// simple button, which player can click
// it cant change texture when player is hovering over it

public class UISimpleButton extends UIObject{
	
	private BufferedImage image;
	private ClickListener clicker;

	public UISimpleButton(float x, float y, int width, int height, BufferedImage image, ClickListener clicker) {
		super(x, y, width, height);
		this.image = image;
		this.clicker = clicker;
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, (int) x, (int) y, width, height, null);
	}

	@Override
	public void onClick() {
		clicker.onClick();
	}
	
}

