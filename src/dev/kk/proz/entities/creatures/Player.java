package dev.kk.proz.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;

import dev.kk.proz.Game;
import dev.kk.proz.Handler;
import dev.kk.proz.gfx.Assets;

public class Player extends Creature {
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFUALT_CREATURE_WIDTH, Creature.DEFUALT_CREATURE_HEIGHT);
		
		bounds.x = 12;
		bounds.y  = 4;
		bounds.width = 20;
		bounds.height = 24;
	}

	@Override
	public void tick() {
		getInput();
		move();
		
	}
	
	private void getInput() {
		xMove = 0;
		yMove = 0;

		if(handler.getKeyManager().up)
			yMove = -speed;
		if(handler.getKeyManager().down)
			yMove = speed;
		if(handler.getKeyManager().left)
			xMove = -speed;
		if(handler.getKeyManager().right)
			xMove = speed;
	}
	

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.bluePlayer, (int)x, (int)y, width, height, null);
		
	}
	
}
