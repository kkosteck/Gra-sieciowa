package dev.kk.proz.maps;

import java.awt.Graphics;

import dev.kk.proz.Handler;
import dev.kk.proz.entities.EntityManager;
import dev.kk.proz.entities.creatures.Player;
import dev.kk.proz.entities.towers.BasicTower;
import dev.kk.proz.tiles.Tile;
import dev.kk.proz.utilities.Utilities; 

public class Map {

	private int width, height;
	private int[][] tiles;
	private Handler handler;
	
	private EntityManager entityManager;
	
	public Map(Handler handler, String path) {
		this.handler = handler;
		entityManager = new EntityManager(handler, new Player(handler, 0, 0));
		
		loadMap(path);
	}
	
	public void tick() {
		entityManager.tick();
	}
	
	public void render(Graphics g) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				getTile(x,y).render(g, x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT);
				if(getTile(x,y) == Tile.redTower || getTile(x,y) == Tile.blueTower)
					entityManager.addEntity(new BasicTower(handler, (int) x * Tile.TILEWIDTH - 16, (int)y * Tile.TILEHEIGHT - 16));
			}	
		}
		entityManager.render(g);
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
		
		tiles = new int[width][height];
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				tiles[x][y] = Utilities.parseInt(tokens[x + y * width + 2]);
			}
		}
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}
