package main.entities;

import java.awt.Graphics;
import java.util.ArrayList;

import main.Handler;
import main.entities.creatures.PlayerMP;
import main.entities.towers.Castle;
import main.utilities.Utilities.Teams;

/* entity managment class
 * all entities are stored in here
 * all action like adding and removing entity are made through here
 */

public class EntityManager {

	@SuppressWarnings("unused")
	private Handler handler;
	private ArrayList<Entity> entities;

	public EntityManager(Handler handler) {
		this.handler = handler;
		entities = new ArrayList<Entity>(); // list for entity storing
	}

	public void tick() {
		for (int i = 0; i < getEntities().size(); i++) { // tick every entity
			Entity e = getEntities().get(i);
			e.tick();
			if (!e.isActive()) {
				getEntities().remove(e);
			}
		}
	}

	public synchronized void render(Graphics g) { // render every entity
		for (Entity e : getEntities()) {
			e.render(g);
		}
	}

	public synchronized void movePlayer(String username, float xMove, float yMove) { // move players
		int index = getPlayerMPIndex(username);
		PlayerMP player = (PlayerMP) getEntities().get(index);
		player.setxMove(xMove);
		player.setyMove(yMove);
	}

	public synchronized void removePlayerMP(String username) { // if player disconnected remove him
		int index = 0;
		for (Entity e : getEntities()) {
			if (e instanceof PlayerMP && ((PlayerMP) e).getUsername().equals(username)) {
				break;
			}
			index++;
		}
		getEntities().remove(index);
	}

	private synchronized int getPlayerMPIndex(String username) { // return index in entity array of searched player
		int index = 0;
		for (Entity e : getEntities()) {
			if (e instanceof PlayerMP && ((PlayerMP) e).getUsername().equals(username)) {
				break;
			}
			index++;
		}
		return index;
	}

	public synchronized int countRedTeammates() { // check number of players in red team
		int index = 0;
		for (Entity e : getEntities()) {
			if (e instanceof PlayerMP && ((PlayerMP) e).getTeam() == Teams.RED) {
				index++;
			}
		}
		return index;
	}

	public synchronized int countBlueTeammates() { // check number of players in blue team
		int index = 0;
		for (Entity e : getEntities()) {
			if (e instanceof PlayerMP && ((PlayerMP) e).getTeam() == Teams.BLUE) {
				index++;
			}
		}
		return index;
	}

	public synchronized void addEntity(Entity e) {
		getEntities().add(e);
	}

	// GETTERS SETTERS

	public synchronized ArrayList<Entity> getEntities() {
		return entities;
	}

	public synchronized Castle getCastle(Teams team) {
		for (Entity e : getEntities()) {
			if (e instanceof Castle && ((Castle) e).getTeam() == team) {
				return (Castle) e;
			}
		}
		return null;
	}
}
