package dev.kk.proz.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

public class HealthBar extends UIObject{
	
	private int value = 0, maxValue= 100;
	private Color color;
	private boolean toLeft;

	public HealthBar(float x, float y, int width, int height, int value, int maxValue, Color color, boolean toLeft) {
		super(x, y, width, height);
		this.value = value;
		this.color = color;
		this.maxValue = maxValue;
		this.toLeft = toLeft;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect((int) x,(int) y, width, height);
			g.setColor(color);
			if(toLeft) {
				float barWidth = ((float)value / (float)maxValue) * (width - 8);
				g.fillRect((int)(x + 4), (int)(y + 4), (int) barWidth, 24);
				g.setColor(Color.WHITE);
				drawCenteredString(g,"HP: "+ value + "/" + maxValue,new Rectangle((int) x,(int) y, width, height),new Font("Arial", Font.BOLD, 18));
			}else {
				float barWidth = ((float)value / (float)maxValue) * (width - 8);
				g.fillRect((int)(x + 4 +(width - 8 - barWidth)), (int)(y + 4), (int) barWidth, 24);
				g.setColor(Color.WHITE);
				drawCenteredString(g,"HP: "+ value + "/" + maxValue,new Rectangle((int) x,(int) y, width, height),new Font("Arial", Font.BOLD, 18));
			}
	}

	public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
	    FontMetrics metrics = g.getFontMetrics(font);
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    g.setFont(font);
	    g.drawString(text, x, y);
	}

	@Override
	public void onClick() {
		
	}

	public void setValue(int value) {
		this.value = value;
	}
}
