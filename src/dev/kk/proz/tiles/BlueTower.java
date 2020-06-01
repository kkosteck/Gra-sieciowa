package dev.kk.proz.tiles;

import dev.kk.proz.gfx.Assets;

public class BlueTower extends Tile{

	public BlueTower(int id) {
		super(Assets.blueTower, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
