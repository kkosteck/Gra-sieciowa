package dev.kk.proz.entities.creatures;

import dev.kk.proz.Handler;
import dev.kk.proz.entities.Entity;

public abstract class Creature extends Entity{

	public static final int DEFUALT_HEALTH = 10;
	public static final float DEFUALT_SPEED = 10.0f;
	public static final int DEFUALT_CREATURE_WIDTH = 100, DEFUALT_CREATURE_HEIGHT = 100;
	
	protected int health;
	protected float speed;
	protected float xMove, yMove;
	
	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		health = DEFUALT_HEALTH;
		speed = DEFUALT_SPEED;
	}

	public float getxMove() {
		return xMove;
	}
	
	public void move() {//temporary collision bounds
		if((x + xMove) <= 1180 && (x + xMove) >= 0)
			x += xMove;
		if((y + yMove) <= 620 && (y + yMove) >= 0)
			y += yMove;
	}
	

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
}
