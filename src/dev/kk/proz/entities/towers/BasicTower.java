package dev.kk.proz.entities.towers;

import java.awt.Graphics;

import dev.kk.proz.Handler;
import dev.kk.proz.gfx.Assets;
import dev.kk.proz.tiles.Tile;
import dev.kk.proz.utilities.Utilities.Teams;

public class BasicTower extends Tower {
	
	private Teams team;

	public BasicTower(Handler handler, float x, float y) {
		super(handler, x, y, 3 * Tile.TILEWIDTH, 3 * Tile.TILEHEIGHT);
		
		bounds.x = 2;
		bounds.y  = 2;
		bounds.width = 44;
		bounds.height = 44; 
		health = 100;
		this.team = DEFUALT_TEAM;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.basicTower, (int) x, (int) y, 3 * Tile.TILEWIDTH, 3 * Tile.TILEHEIGHT, null);
	}

	@Override
	public void die() {
		 
	}

	public Teams getTeam() {
		return team;
	}

	public void setTeam(Teams team) {
		this.team = team;
	}
	
}
