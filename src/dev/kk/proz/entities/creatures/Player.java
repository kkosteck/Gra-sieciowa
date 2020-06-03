package dev.kk.proz.entities.creatures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.kk.proz.Handler;
import dev.kk.proz.entities.bullets.BasicBullet;
import dev.kk.proz.gfx.Assets;
import dev.kk.proz.maps.Map;
import dev.kk.proz.states.State;
import dev.kk.proz.utilities.Utilities.Teams;

public class Player extends Creature {

	private BufferedImage moveUp = Assets.redPlayer[0];
	private BufferedImage moveDown = Assets.redPlayer[1];
	private BufferedImage moveLeft = Assets.redPlayer[2];
	private BufferedImage moveRight = Assets.redPlayer[3];
	private BufferedImage lastWay = Assets.redPlayer[0];
	private Teams team = Teams.NONE; 
	private String username = null;
	
	private long lastAttackTimer, attackCooldown = 250, attackTimer = attackCooldown;
	
	public Player(Handler handler, float x, float y, String username) {
		super(handler, x, y, DEFUALT_CREATURE_WIDTH, DEFUALT_CREATURE_HEIGHT);
		bounds.x = 10;
		bounds.y  = 7;
		bounds.width = 12;
		bounds.height = 18;
		this.username = username;
	}

	@Override
	public void tick() {
		getInput();
		move();
		checkAttacks();
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

	private void checkAttacks() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCooldown)
			return;
		
		if(handler.getKeyManager().attackTrigger) {
			if(getCurrentWay() == moveDown) {
				handler.getEntityManager().addEntity(new BasicBullet(handler, x + DEFUALT_CREATURE_WIDTH / 2, y + DEFUALT_CREATURE_HEIGHT, 2, team));
			}else if(getCurrentWay() == moveUp) {
				handler.getEntityManager().addEntity(new BasicBullet(handler, x + DEFUALT_CREATURE_WIDTH / 2, y, 3, team));
			}else if(getCurrentWay() == moveLeft) {
				handler.getEntityManager().addEntity(new BasicBullet(handler, x, y + DEFUALT_CREATURE_HEIGHT / 2, 0, team));
			}else if(getCurrentWay() == moveRight) {
				handler.getEntityManager().addEntity(new BasicBullet(handler, x + DEFUALT_CREATURE_WIDTH, y + DEFUALT_CREATURE_HEIGHT / 2, 1, team));
			}
		}
		attackTimer = 0;
	}
	
	public String getUsername() {
		return username;
	}

	@Override
	public void die() {
			
	}
	public void respawn(Teams team) {
		if(team == Teams.RED) {
			this.setX(256);
			this.setY(344);
			moveUp = Assets.redPlayer[0];
			moveDown = Assets.redPlayer[1];
			moveLeft = Assets.redPlayer[2];
			moveRight = Assets.redPlayer[3];
			lastWay = moveRight;
			team = Teams.RED;
		}else if(team == Teams.BLUE) {
			this.setX(1024);
			this.setY(344);
			moveUp = Assets.bluePlayer[0];
			moveDown = Assets.bluePlayer[1];
			moveLeft = Assets.bluePlayer[2];
			moveRight = Assets.bluePlayer[3];
			lastWay = moveLeft;
			team = Teams.BLUE;
		}
		
	}
}
