package dev.kk.proz.entities.towers;

import dev.kk.proz.Handler;
import dev.kk.proz.entities.Entity;
import dev.kk.proz.utilities.Utilities.Teams;

public abstract class Tower extends Entity{

	public static final float DEFUALT_ATTACK_SPEED = 3.0f;
	public static final int DEFUALT_TOWER_WIDTH = 32, DEFUALT_TOWER_HEIGHT = 32;
	protected Teams team = Teams.NONE;
	
	
	public Tower(Handler handler, float x, float y, int width, int height, Teams team) {
		super(handler, x, y, width, height);
		this.team = team;
	}


	public Teams getTeam() {
		return team;
	}


	public void setTeam(Teams team) {
		this.team = team;
	}	
	
	
}
