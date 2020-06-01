package dev.kk.proz.maps;

import java.awt.Graphics;

import dev.kk.proz.tiles.Tile;
import dev.kk.proz.utilities.Utilities; 

public class Map {

	private int width, height;
	private int spawnX, spawnY;
	private int[][] tiles;
	
	public Map(String path) {
		loadMap(path);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				getTile(x,y).render(g, x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT);
			}	
		}
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tile.wallTile;
		
		Tile t = Tile.tiles[tiles[x][y]];
		if(t == null)
			return Tile.grassTile;
		return t;
	}
	
	
	private void loadMap(String path) {
		String file = Utilities.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utilities.parseInt(tokens[0]);
		height = Utilities.parseInt(tokens[1]);
		spawnX = Utilities.parseInt(tokens[2]);
		spawnY = Utilities.parseInt(tokens[3]);
		
		tiles = new int[width][height];
		
		for(int y = 0; y < height; y++) {
			for(int x =0; x < width; x++) {
				tiles[x][y] = Utilities.parseInt(tokens[x + y * width + 4]);
			}
		}
	}

	public int getSpawnX() {
		return spawnX * Tile.TILEWIDTH;
	}

	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}

	public int getSpawnY() {
		return spawnY * Tile.TILEHEIGHT;
	}

	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}
	
}
