package main.tiles;

import java.awt.Graphics;

import main.gfx.Assets;

public class WallTile extends Tile{
	
	@SuppressWarnings("unused")
	private int width, height;

	public WallTile(int id) {
		super(Assets.wallTile, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
	@Override
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
	}

}