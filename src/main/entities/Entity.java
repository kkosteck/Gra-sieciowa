package main.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.Handler;

/* all entities inherit from this class
 */

public abstract class Entity {

	public static final int DEFUALT_HEALTH = 10; // minimal health for every entity if it is not set separately
	protected float x, y; // position coordinate
	protected int width, height; // size of graphic representation
	protected Handler handler;
	protected Rectangle bounds; // size of hitbox
	protected int health;
	protected boolean active = true;

	public Entity(Handler handler, float x, float y, int width, int height) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		health = DEFUALT_HEALTH;
		bounds = new Rectangle(0, 0, width, height); // defualt hitbox is same as graphic size
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public void hurt(int amount) { // if entity gets hit
		health -= amount;
		if (health <= 0) {
			active = false;
		}
	}

	public boolean checkEntityCollisions(float xOffset, float yOffset) {
		for (Entity e : handler.getMap().getEntityManager().getEntities()) {
			if (e.equals(this)) // not checking for collision with myself
				continue;
			if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) // checking for collision
																								// with move offset
				return true;
		}
		return false;
	}

	public Rectangle getCollisionBounds(float xOffset, float yOffset) { // return hitbox
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width,
				bounds.height);
	}

	// getters and setters

	public boolean isActive() {
		return active;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
