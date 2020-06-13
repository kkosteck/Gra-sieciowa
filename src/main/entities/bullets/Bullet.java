package main.entities.bullets;

import main.Handler;
import main.entities.Entity;
import main.tiles.Tile;

public abstract class Bullet extends Entity {
	
	public static final float DEFUALT_SPEED = 3.0f;
	public static final int DEFUALT_BULLET_WIDTH = 16, DEFUALT_BULLET_HEIGHT = 16;
	
	protected float speed;
	protected float xMove, yMove;
	
	public Bullet(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);

		speed = DEFUALT_SPEED;
	}
	
	public void move() {
		if(!checkEntityCollisions(xMove, 0f))
			moveX();
		if(!checkEntityCollisions(0f, yMove))
			moveY();
	}
	
	public void moveX() {
		if(xMove > 0) {//moving right
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
			
			if(!collisionWithTile(tx, (int)(y + bounds.y)/ Tile.TILEHEIGHT) && 
					!collisionWithTile(tx, (int) (y+ bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			}else {
				active = false;
			}
			
		}else if(xMove < 0) {//moving left
			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
			
			if(!collisionWithTile(tx, (int)(y + bounds.y)/ Tile.TILEHEIGHT) && 
					!collisionWithTile(tx, (int) (y+ bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			}else {
				active = false;
			}
		}
	}
	
	public void moveY() {
		if(yMove < 0) {//moving up
			int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;
			
			if(!collisionWithTile((int)(x + bounds.x)/ Tile.TILEWIDTH, ty) && 
					!collisionWithTile((int) (x+ bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			}else {
				active = false;
			}
			
		}else if(yMove > 0) {//moving down
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
			if(!collisionWithTile((int)(x + bounds.x)/ Tile.TILEWIDTH, ty) && 
					!collisionWithTile((int) (x+ bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			}else {
				active = false;
			}
		}
	}

	protected boolean collisionWithTile(int x, int y) {
		return handler.getMap().getTile(x, y).isSolid();
	}
	
	
	

	public float getxMove() {
		return xMove;
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

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
}
