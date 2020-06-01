package dev.kk.proz.entities.creatures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.kk.proz.Handler;
import dev.kk.proz.gfx.Assets;
import dev.kk.proz.states.State;

public class Player extends Creature {
	
	private BufferedImage moveDown = Assets.redPlayer_down;
	private BufferedImage moveUp = Assets.redPlayer_up;
	private BufferedImage moveLeft = Assets.redPlayer_left;
	private BufferedImage moveRight = Assets.redPlayer_right;
	private BufferedImage lastWay = Assets.redPlayer_down;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFUALT_CREATURE_WIDTH, Creature.DEFUALT_CREATURE_HEIGHT);
		
		bounds.x = 10;
		bounds.y  = 7;
		bounds.width = 12;
		bounds.height = 18;
	}

	@Override
	public void tick() {
		if(State.getSide() == 1) {
			this.setX(256);
			this.setY(344);
			moveDown = Assets.redPlayer_down;
			moveUp = Assets.redPlayer_up;
			moveLeft = Assets.redPlayer_left;
			moveRight = Assets.redPlayer_right;
			lastWay = moveRight;
			State.setSide(0);
		}else if(State.getSide() == 2) {
			this.setX(1024);
			this.setY(344);
			moveDown = Assets.bluePlayer_down;
			moveUp = Assets.bluePlayer_up;
			moveLeft = Assets.bluePlayer_left;
			moveRight = Assets.bluePlayer_right;
			lastWay = moveLeft;
			State.setSide(0);
		}
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
		g.drawImage(getCurrentWay(), (int)x, (int)y, width, height, null);
		
	}
	
	private BufferedImage getCurrentWay() {
		if(xMove < 0) {
			lastWay = moveLeft;
			return moveLeft;
		}
		else if(xMove > 0) {
			lastWay = moveRight;
			return moveRight;
		}
		else if(yMove > 0) {
			lastWay = moveDown;
			return moveDown;
		}
		else if (yMove < 0) {
			lastWay = moveUp;
			return moveUp;
		}
		else
			return lastWay;
	}

	@Override
	public void die() {
		
		
	}
}
