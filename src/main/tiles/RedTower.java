package dev.kk.proz.tiles;

import dev.kk.proz.gfx.Assets;

public class RedTower extends Tile{

	public RedTower(int id) {
		super(Assets.redTower, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}