package main;

import main.entities.EntityManager;
import main.input.MouseManager;
import main.maps.Map;
import main.net.GameClient;
import main.net.GameServer;

/* All important and often in use variables are stored in this class
 */

public class Handler {

	private Game game;
	private Map map;
	private GameServer socketServer;
	private GameClient socketClient;

	public Handler(Game game) {
		this.game = game;
	}

	// getters and setters

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
