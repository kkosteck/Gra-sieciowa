package main.entities.creatures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Handler;
import main.entities.Entity;
import main.entities.towers.Tower;
import main.gfx.Assets;
import main.tiles.Tile;
import main.utilities.Utilities.Teams;

/* Simple bullet 
 */

public class BasicBullet extends Creature {

	private BufferedImage texture;
	private static final float ATTACK_SPEED = 8.0f;
	private static final int DAMAGE = 100;
	private Teams team;

	public BasicBullet(Handler handler, float x, float y, int moving, Teams team) {
		super(handler, x, y, 16, 16);

		this.team = team;

		// set hitbox and graphic properly
		if (moving == 0) {// left
			bounds.x = 0;
			bounds.y = 6;
			bounds.width = 16;
			bounds.height = 4;
			texture = Assets.basicBullet_leftRight;
			xMove = -ATTACK_SPEED;
		} else if (moving == 1) {// right
			bounds.x = 6;
			bounds.y = 0;
			bounds.width = 16;
			bounds.height = 4;
			texture = Assets.basicBullet_leftRight;
			xMove = ATTACK_SPEED;
		} else if (moving == 2) {// down
			bounds.x = 0;
			bounds.y = 6;
			bounds.width = 16;
			bounds.height = 4;
			texture = Assets.basicBullet_upDown;
			yMove = ATTACK_SPEED;
		} else if (moving == 3) {// up
			bounds.x = 6;
			bounds.y = 0;
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
		g.drawImage(texture, (int) x, (int) y, 16, 16, null);
	}

	public void checkTarget() {
		for (Entity e : handler.getMap().getEntityManager().getEntities()) {
			if (e.equals(this)) // not target myself
				continue;
			if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xMove, yMove))) {// check for collision
				if (e instanceof Player && ((Player) e).getTeam() != team) {// if it is player from another team hit him
					e.hurt(DAMAGE);
				} else if (e instanceof Tower) { //if it is tower from another team hit it
					if (((Tower) e).getTeam() != team)
						e.hurt(DAMAGE);
				} else if (!(e instanceof Player)) { //if it is something else hit it 
					e.hurt(DAMAGE);
				}
				active = false;
			}
		}
	}

	@Override
	public void moveX() { //moving override for proper collision with tiles
		if (xMove > 0) {// moving right
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;

			if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
					&& !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} else {
				active = false;
			}

		} else if (xMove < 0) {// moving left
			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;

			if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
					&& !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} else {
				active = false;
			}
		}
	}

	@Override
	public void moveY() {
		if (yMove < 0) {// moving up
			int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;

			if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
					&& !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			} else {
				active = false;
			}

		} else if (yMove > 0) {// moving down
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
			if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
					&& !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			} else {
				active = false;
			}
		}
	}

}
