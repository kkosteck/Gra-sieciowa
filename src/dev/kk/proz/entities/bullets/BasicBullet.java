package dev.kk.proz.entities.bullets;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


import dev.kk.proz.Handler;
import dev.kk.proz.entities.Entity;
import dev.kk.proz.gfx.Assets;

public class BasicBullet extends Bullet {
	
	private BufferedImage texture;
	private static final float ATTACK_SPEED = 8.0f;
	private static final int DAMAGE = 100;
	private int team;

	public BasicBullet(Handler handler, float x, float y, int moving, int team) {
		super(handler, x, y, 16, 16);
		
		this.team = team;
		
		if(moving == 0) {//left
			bounds.x = 0;
			bounds.y  = 6;
			bounds.width = 16;
			bounds.height = 4;
			texture = Assets.basicBullet_leftRight;
			xMove = -ATTACK_SPEED;
		}else if(moving == 1){//right
			bounds.x = 6;
			bounds.y  = 0;
			bounds.width = 16;
			bounds.height = 4;
			texture = Assets.basicBullet_leftRight;
			xMove = ATTACK_SPEED;
		}else if(moving == 2){//down
			bounds.x = 0;
			bounds.y  = 6;
			bounds.width = 16;
			bounds.height = 4;
			texture = Assets.basicBullet_upDown;
			yMove = ATTACK_SPEED;
		}else if(moving == 3){//up
			bounds.x = 6;
			bounds.y  = 0;
			bounds.width = 16;
			bounds.height = 4;
			texture = Assets.basicBullet_upDown;
			yMove = -ATTACK_SPEED;
		}
	}

	@Override
	public void tick() {
		checkTarget();
		move();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(texture, (int)x, (int)y, 16, 16, null);
	}

	@Override
	public void die() {
		
	}
	
	public void checkTarget() {
		for(Entity e : handler.getMap().getEntityManager().getEntities()) {
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xMove, yMove))){
				if(e.getTeam() != team) 
					e.hurt(DAMAGE);
				active = false;
			}
		}
	}
	
}
