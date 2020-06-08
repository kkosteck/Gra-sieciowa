package dev.kk.proz.entities;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.kk.proz.Handler;
import dev.kk.proz.entities.creatures.PlayerMP;

public class EntityManager {
	
	@SuppressWarnings("unused")
	private Handler handler;
	private ArrayList<Entity> entities;
	
	public EntityManager(Handler handler){
		this.handler = handler;
		entities = new ArrayList<Entity>();
	}
	
	public void tick(){
		for(int i = 0;i < getEntities().size();i++){
			Entity e = getEntities().get(i);
			e.tick();
			if(!e.isActive()) {
				getEntities().remove(e);
			}
		}
	}
	
	public void render(Graphics g){
		for(Entity e : getEntities()){
			e.render(g);
		}
	}
	
	public void addEntity(Entity e){
		getEntities().add(e);
	}
	
	//GETTERS SETTERS
	public void removePlayerMP(String username) {
		int index = 0;
		for(Entity e : getEntities()) {
			if(e instanceof PlayerMP && ((PlayerMP)e).getUsername().equals(username)) {
				break;
			}
			index++;
		}
		getEntities().remove(index);
	}
	
	public synchronized void movePlayer(String username, float xMove, float yMove) {
		int index = getPlayerMPIndex(username);
		PlayerMP player = (PlayerMP) getEntities().get(index);
		player.setxMove(xMove);
		player.setyMove(yMove);
	}

	private int getPlayerMPIndex(String username) {
	    int index = 0;
	    for (Entity e : getEntities()) {
	        if (e instanceof PlayerMP && ((PlayerMP) e).getUsername().equals(username)) {
	            break;
	        }
	        index++;
	    }
	    return index;
	}

	public synchronized ArrayList<Entity> getEntities() {
		return entities;
	}
	
}
