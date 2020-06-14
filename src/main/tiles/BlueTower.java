package main.tiles;

import main.gfx.Assets;

public class BlueTower extends Tile {

	public BlueTower(int id) {
		super(Assets.blueTower, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
