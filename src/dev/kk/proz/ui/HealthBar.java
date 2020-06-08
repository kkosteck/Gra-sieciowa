package dev.kk.proz.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.kk.proz.entities.creatures.Player;
import dev.kk.proz.utilities.Utilities.Teams;

public class HealthBar extends UIObject{
	
	private int value = 0;
	private Teams team = Teams.NONE;

	public HealthBar(float x, float y, int width, int height, int value, Teams team) {
		super(x, y, width, height);
		this.value = value;
		this.team = team;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		if(team == Teams.RED) {
			g.setColor(Color.BLACK);
			g.fillRect(16, 675, 136, 32);
			g.setColor(Color.RED);
			float barWidth = ((float)value / (float)Player.MAX_HEALTH) * 128;
			g.fillRect(20, 679, (int) barWidth, 24);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 18));
			g.drawString("HP:     "+ value + "/" + Player.MAX_HEALTH, 22, 698);
		}else {
			g.setColor(Color.BLACK);
			g.fillRect(1128, 675, 136, 32);
			g.setColor(Color.BLUE);
			float barWidth = ((float)value / (float)Player.MAX_HEALTH) * 128;
			g.fillRect(1132, 679, (int) barWidth, 24);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 18));
			g.drawString("HP:     "+ value + "/" + Player.MAX_HEALTH, 1134, 698);
			
		}
	}

	@Override
	public void onClick() {
		
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
