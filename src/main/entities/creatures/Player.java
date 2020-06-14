package main.entities.creatures;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Handler;
import main.entities.Entity;
import main.entities.towers.Castle;
import main.gfx.Assets;
import main.input.KeyManager;
import main.net.packets.Packet02Move;
import main.net.packets.Packet03Attack;
import main.tiles.BlueHealingTile;
import main.tiles.RedHealingTile;
import main.tiles.Tile;
import main.utilities.Utilities.Teams;

public class Player extends Creature {

	// assign proper movement textures
	private BufferedImage moveUp = Assets.redPlayer[0];
	private BufferedImage moveDown = Assets.redPlayer[1];
	private BufferedImage moveLeft = Assets.redPlayer[2];
	private BufferedImage moveRight = Assets.redPlayer[3];
	private BufferedImage lastWay = Assets.redPlayer[0];
	public static final int MAX_HEALTH = 500;
	private Teams team = Teams.NONE;
	private String username = null;
	private boolean healing = false, living = true;
	private int healAmount = 10;

	// timers for cooldowns
	private long lastAttackTimer, attackCooldown = 250, attackTimer = attackCooldown;
	private long lastHealTimer, healCooldown = 500, healTimer = healCooldown;
	private long lastDeathTimer, deathCooldown = 10000, deathTimer = 0;

	// keyboard input manager
	private KeyManager keyManager;

	public Player(Handler handler, KeyManager keyManager, float x, float y, String username, Teams team) {
		super(handler, x, y, DEFUALT_CREATURE_WIDTH, DEFUALT_CREATURE_HEIGHT);
		health = MAX_HEALTH;
		// size of hitbox
		bounds.x = 10;
		bounds.y = 7;
		bounds.width = 12;
		bounds.height = 18;

		this.username = username;
		this.keyManager = keyManager;
		this.team = team;
		health = MAX_HEALTH;
		// assign proper team's textures
		if (team == Teams.RED) {
			moveUp = Assets.redPlayer[0];
			moveDown = Assets.redPlayer[1];
			moveLeft = Assets.redPlayer[2];
			moveRight = Assets.redPlayer[3];
			lastWay = moveRight;
		} else if (team == Teams.BLUE) {
			moveUp = Assets.bluePlayer[0];
			moveDown = Assets.bluePlayer[1];
			moveLeft = Assets.bluePlayer[2];
			moveRight = Assets.bluePlayer[3];
			lastWay = moveLeft;
		}
	}

	@Override
	public void tick() {
		if (living) { // checking for being alive
			move();
			if (keyManager != null) {
				keyManager.tick();
				getInput();
				checkAttacks();
				// send movement packet
				Packet02Move packet = new Packet02Move(getUsername(), xMove, yMove, x, y);
				packet.writeData(handler.getSocketClient());
			}
			checkHealing(healAmount); // check if player stands on healing tile
			lastDeathTimer = System.currentTimeMillis(); // set death timer
		} else { // if player is dead check for respawning
			respawn();
		}
	}

	@Override
	public void render(Graphics g) {
		if (living) { // render only if player is alive
			g.drawImage(getCurrentWay(), (int) x, (int) y, width, height, null);
			if (healing == true) {
				g.setFont(new Font("Arial", Font.PLAIN, 12));
				g.setColor(Color.PINK);
				g.drawString("+" + healAmount, (int) x, (int) y);
			}
		}
	}

	private void checkAttacks() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if (attackTimer < attackCooldown)
			return;
		if (keyManager.attackTrigger) { // if attack was triggered
			int way = 0;
			float xBullet = 0, yBullet = 0;
			// set proper starting point of bullet
			if (getCurrentWay() == moveDown) {
				way = 2;
				xBullet = x + DEFUALT_CREATURE_WIDTH / 2;
				yBullet = y + DEFUALT_CREATURE_HEIGHT;
			} else if (getCurrentWay() == moveUp) {
				way = 3;
				xBullet = x + DEFUALT_CREATURE_WIDTH / 2;
				yBullet = y;
			} else if (getCurrentWay() == moveLeft) {
				way = 0;
				xBullet = x;
				yBullet = y + DEFUALT_CREATURE_HEIGHT / 2;
			} else if (getCurrentWay() == moveRight) {
				way = 1;
				xBullet = x + DEFUALT_CREATURE_WIDTH;
				yBullet = y + DEFUALT_CREATURE_HEIGHT / 2;
			}
			// send packet to tell others players that this player sent a bullet
			Packet03Attack packet = new Packet03Attack(xBullet, yBullet, team, way);
			packet.writeData(handler.getSocketClient());
		}
		attackTimer = 0;
	}

	private void checkHealing(int amount) {
		healTimer += System.currentTimeMillis() - lastHealTimer;
		lastHealTimer = System.currentTimeMillis();
		if (healTimer < healCooldown)
			return;
		if (health + amount >= MAX_HEALTH) {
			healing = false;
			health = MAX_HEALTH;
			return;
		}
		// check if player stands inside healing tile
		if (handler.getMap().getTile((int) (x / Tile.TILEWIDTH), (int) (y / Tile.TILEHEIGHT)) instanceof RedHealingTile
				&& team == Teams.RED) {
			healing = true;
			health += amount;
		} else if (handler.getMap().getTile((int) (x / Tile.TILEWIDTH),
				(int) (y / Tile.TILEHEIGHT)) instanceof BlueHealingTile && team == Teams.BLUE) {
			healing = true;
			health += amount;
		} else
			healing = false;
		healTimer = 0;
	}

	public void respawn() {
		deathTimer += System.currentTimeMillis() - lastDeathTimer;
		lastDeathTimer = System.currentTimeMillis();
		if (deathTimer < deathCooldown)
			return;
		// proper respawning stats
		health = MAX_HEALTH;
		if (team == Teams.RED) {
			x = 256;
			y = 344;
			moveUp = Assets.redPlayer[0];
			moveDown = Assets.redPlayer[1];
			moveLeft = Assets.redPlayer[2];
			moveRight = Assets.redPlayer[3];
			lastWay = moveRight;
		} else if (team == Teams.BLUE) {
			x = 1024;
			y = 344;
			moveUp = Assets.bluePlayer[0];
			moveDown = Assets.bluePlayer[1];
			moveLeft = Assets.bluePlayer[2];
			moveRight = Assets.bluePlayer[3];
			lastWay = moveLeft;
		}
		deathTimer = 0;
		living = true;
	}

	@Override // override because player is waiting for respawn when died, not disappearing
	public void hurt(int amount) {
		health -= amount;
		if (health <= 0)
			living = false;
	}

	@Override // override for not collision with teammates and own castle
	public boolean checkEntityCollisions(float xOffset, float yOffset) {
		for (Entity e : handler.getMap().getEntityManager().getEntities()) {
			if (e.equals(this)) // not checking for collision with myself
				continue;
			if (e instanceof Player && ((Player) e).getTeam() == team) // not collision with teammates
				continue;
			if (e instanceof Castle && ((Castle) e).getTeam() == team) // not collision with own castle
				continue;
			if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) // checking for collision
				return true;
		}
		return false;
	}

	private BufferedImage getCurrentWay() { // check when player texture should point
		if (xMove < 0) {
			lastWay = moveLeft;
			return moveLeft;
		} else if (xMove > 0) {
			lastWay = moveRight;
			return moveRight;
		} else if (yMove > 0) {
			lastWay = moveDown;
			return moveDown;
		} else if (yMove < 0) {
			lastWay = moveUp;
			return moveUp;
		} else
			return lastWay;
	}

	private void getInput() { // check for proper movement values
		xMove = 0;
		yMove = 0;

		if (keyManager.up)
			yMove = -speed;
		if (keyManager.down)
			yMove = speed;
		if (keyManager.left)
			xMove = -speed;
		if (keyManager.right)
			xMove = speed;
	}

	// getters and setters

	public String getUsername() {
		return username;
	}

	public Teams getTeam() {
		return team;
	}

	public void setTeam(Teams team) {
		this.team = team;
	}

	public boolean isLiving() {
		return living;
	}

	public void setLiving(boolean living) {
		this.living = living;
	}

	public long getDeathTimer() {
		return deathTimer;
	}

}
