package dev.kk.proz.entities.creatures;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.kk.proz.Handler;
import dev.kk.proz.gfx.Assets;
import dev.kk.proz.input.KeyManager;
import dev.kk.proz.net.packets.Packet02Move;
import dev.kk.proz.net.packets.Packet03Attack;
import dev.kk.proz.tiles.BlueHealingTile;
import dev.kk.proz.tiles.RedHealingTile;
import dev.kk.proz.tiles.Tile;
import dev.kk.proz.utilities.Utilities.Teams;

public class Player extends Creature {

	private BufferedImage moveUp = Assets.redPlayer[0];
	private BufferedImage moveDown = Assets.redPlayer[1];
	private BufferedImage moveLeft = Assets.redPlayer[2];
	private BufferedImage moveRight = Assets.redPlayer[3];
	private BufferedImage lastWay = Assets.redPlayer[0];
	public static final int MAX_HEALTH = 500;
	private Teams team = Teams.NONE; 
	private String username = null;
	private boolean healing = false;
	private int healAmount = 10;
	
	private long lastAttackTimer, attackCooldown = 250, attackTimer = attackCooldown;
	private long lastHealTimer, healCooldown = 500, healTimer = healCooldown;
	private KeyManager keyManager;
	
	public Player(Handler handler, KeyManager keyManager, float x, float y, String username, Teams team) {
		super(handler, x, y, DEFUALT_CREATURE_WIDTH, DEFUALT_CREATURE_HEIGHT);
		health = MAX_HEALTH;
		bounds.x = 10;
		bounds.y  = 7;
		bounds.width = 12;
		bounds.height = 18;
		this.username = username;
		this.keyManager = keyManager;
		this.team = team;
		health = MAX_HEALTH;
		respawn(this.team);
	}

	@Override
	public void tick() {
		move();
		if(keyManager != null) {
			keyManager.tick();
			getInput();
			checkAttacks();
			Packet02Move packet = new Packet02Move(getUsername(), xMove , yMove, x, y);
			packet.writeData(handler.getSocketClient());
		}
		checkHealing(healAmount);
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentWay(), (int)x, (int)y, width, height, null);
		if(healing == true) {
			g.setFont(new Font("Arial", Font.PLAIN, 12));
			g.setColor(Color.PINK);
			g.drawString("+"+healAmount, (int) x, (int) y);
		}
	}
	
	private void checkAttacks() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCooldown)
			return;
		if(keyManager.attackTrigger) {
			int way = 0;
			float xBullet = 0, yBullet = 0;
			if(getCurrentWay() == moveDown) {
				way = 2;
				xBullet = x + DEFUALT_CREATURE_WIDTH / 2;
				yBullet = y + DEFUALT_CREATURE_HEIGHT;
			}else if(getCurrentWay() == moveUp) {
				way = 3;
				xBullet = x + DEFUALT_CREATURE_WIDTH / 2;
				yBullet = y;
			}else if(getCurrentWay() == moveLeft) {
				way = 0;
				xBullet = x;
				yBullet = y + DEFUALT_CREATURE_HEIGHT / 2;
			}else if(getCurrentWay() == moveRight) {
				way = 1;
				xBullet = x + DEFUALT_CREATURE_WIDTH;
				yBullet = y + DEFUALT_CREATURE_HEIGHT / 2;
			}
			//handler.getEntityManager().addEntity(new BasicBullet(handler, xBullet, yBullet, way, team));
			Packet03Attack packet = new Packet03Attack(xBullet, yBullet, team, way);
			packet.writeData(handler.getSocketClient());
		}
		attackTimer = 0;
	}
	
	private void checkHealing(int amount) {
		healTimer += System.currentTimeMillis() - lastHealTimer;
		lastHealTimer = System.currentTimeMillis();
		if(healTimer < healCooldown)
			return;
		if(health + amount >= MAX_HEALTH) {
			healing = false;
			health = MAX_HEALTH;
			return;
		}
		if(handler.getMap().getTile((int) (x / Tile.TILEWIDTH), (int) (y / Tile.TILEHEIGHT)) instanceof RedHealingTile && team == Teams.RED) {
			healing = true;
			health +=amount;
		} else if(handler.getMap().getTile((int) (x / Tile.TILEWIDTH), (int) (y / Tile.TILEHEIGHT)) instanceof BlueHealingTile && team == Teams.BLUE) {
			healing = true;
			health +=amount;
		}else
			healing = false;
		healTimer = 0;
	}
	
	public void respawn(Teams team) {
		if(team == Teams.RED) {
			moveUp = Assets.redPlayer[0];
			moveDown = Assets.redPlayer[1];
			moveLeft = Assets.redPlayer[2];
			moveRight = Assets.redPlayer[3];
			lastWay = moveRight;
		}else if(team == Teams.BLUE) {
			moveUp = Assets.bluePlayer[0];
			moveDown = Assets.bluePlayer[1];
			moveLeft = Assets.bluePlayer[2];
			moveRight = Assets.bluePlayer[3];
			lastWay = moveLeft;
		}
		
	}

	@Override
	public void die() {
		
	}
	@Override
	public void hurt(int amount) {
		health -=amount;
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

	private void getInput() {
		xMove = 0;
		yMove = 0;
	
		if(keyManager.up)
			yMove = -speed;
		if(keyManager.down)
			yMove = speed;
		if(keyManager.left)
			xMove = -speed;
		if(keyManager.right)
			xMove = speed;
	}

	public String getUsername() {
		return username;
	}

	public Teams getTeam() {
		return team;
	}

	public void setTeam(Teams team) {
		this.team = team;
	}
	
}
