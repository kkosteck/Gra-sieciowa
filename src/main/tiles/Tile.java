package main.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

// map is render tile-based
// all fragments of the map has own tile assign

public class Tile {

	public static Tile[] tiles = new Tile[32];

	// all tiles in game with id assigned
	public static Tile grassTile = new GrassTile(0);
	public static Tile blueTower = new BlueTower(1);
	public static Tile redTower = new RedTower(2);
	public static Tile wallTile = new WallTile(3);
	public static Tile blueHealingTile = new BlueHealingTile(4);
	public static Tile redHealingTile = new RedHealingTile(5);

	public static final int TILEWIDTH = 16, TILEHEIGHT = 16;

	protected BufferedImage texture;
	protected final int id;

	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;

		tiles[id] = this;
	}

	public void tick() {

	}

	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}

	// if solid, entity collides
	public boolean isSolid() {
		return false;
	}

	public int getID() {
		return id;
	}
}
