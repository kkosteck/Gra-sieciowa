package dev.kk.proz;

import dev.kk.proz.entities.EntityManager;
import dev.kk.proz.input.MouseManager;
import dev.kk.proz.maps.Map;
import dev.kk.proz.net.GameClient;
import dev.kk.proz.net.GameServer;

public class Handler {

	private Game game;
	private Map map;
	private GameServer socketServer;
	private GameClient socketClient;
	
	public Handler(Game game) {
		this.game = game;
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

	public GameServer getSocketServer() {
		return socketServer;
	}

	public void setSocketServer(GameServer socketServer) {
		this.socketServer = socketServer;
	}

	public GameClient getSocketClient() {
		return socketClient;
	}

	public void setSocketClient(GameClient socketClient) {
		this.socketClient = socketClient;
	}
	
}
