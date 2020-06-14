package main.tiles;

import main.gfx.Assets;

public class WallTile extends Tile {

	public WallTile(int id) {
		super(Assets.wallTile, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}