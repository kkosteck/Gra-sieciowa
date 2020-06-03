package dev.kk.proz;

import dev.kk.proz.entities.EntityManager;
import dev.kk.proz.input.KeyManager;
import dev.kk.proz.input.MouseManager;
import dev.kk.proz.maps.Map;
import dev.kk.proz.states.GameState;
import dev.kk.proz.states.PickSide;

public class Handler {

	private Game game;
	private Map map;
	
	public Handler(Game game) {
		this.game = game;
	}
	
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}
	
	public EntityManager getEntityManager() {
		return map.getEntityManager();
	}
	
	public int getWidth() {
		return game.getWidth();
	}
	public int getHeight() {
		return game.getHeight();
	}
	

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
	
}
