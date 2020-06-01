package dev.kk.proz.entities.towers;

import java.awt.Graphics;

import dev.kk.proz.Handler;
import dev.kk.proz.entities.Entity;

public abstract class Tower extends Entity{

	public static final float DEFUALT_ATTACK_SPEED = 3.0f;
	public static final int DEFUALT_TOWER_WIDTH = 32, DEFUALT_TOWER_HEIGHT = 32;
	
	
	public Tower(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
	}	
}
