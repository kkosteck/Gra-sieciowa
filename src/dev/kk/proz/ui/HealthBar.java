package dev.kk.proz.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HealthBar extends UIObject{
	
	private int value = 0, maxValue= 100;
	private Color color;

	public HealthBar(float x, float y, int width, int height, int value, int maxValue, Color color) {
		super(x, y, width, height);
		this.value = value;
		this.color = color;
		this.maxValue = maxValue;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect((int) x,(int) y, width, height);
			g.setColor(color);
			float barWidth = ((float)value / (float)maxValue) * 128;
			g.fillRect((int)(x + 4), (int)(y + 4), (int) barWidth, 24);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 18));
			g.drawString("HP: "+ value + "/" + maxValue, (int)(x + 6), (int)(y + 23));	
	}

	@Override
	public void onClick() {
		
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
