package main.entities.towers;

import java.awt.Graphics;

import main.Handler;
import main.gfx.Assets;
import main.tiles.Tile;
import main.utilities.Utilities.Teams;

public class BasicTower extends Tower {
	
	

	public BasicTower(Handler handler, float x, float y, Teams team) {
		super(handler, x, y, 3 * Tile.TILEWIDTH, 3 * Tile.TILEHEIGHT, team);
		
		bounds.x = 2;
		bounds.y  = 2;
		bounds.width = 44;
		bounds.height = 44; 
		health = 100;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		if(team == Teams.RED)
			g.drawImage(Assets.redBasicTower, (int) x, (int) y, 3 * Tile.TILEWIDTH, 3 * Tile.TILEHEIGHT, null);
		else
			g.drawImage(Assets.blueBasicTower, (int) x, (int) y, 3 * Tile.TILEWIDTH, 3 * Tile.TILEHEIGHT, null);
			
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
