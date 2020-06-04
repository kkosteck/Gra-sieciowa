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
		for(int i = 0;i < entities.size();i++){
			Entity e = entities.get(i);
			e.tick();
			if(!e.isActive())
				entities.remove(e);
		}
	}
	
	public void render(Graphics g){
		for(Entity e : entities){
			e.render(g);
		}
	}
	
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	//GETTERS SETTERS
	public void removePlayerMP(String username) {
		int index = 0;
		for(Entity e : entities) {
			if(e instanceof PlayerMP && ((PlayerMP)e).getUsername().equals(username)) {
				break;
			}
			index++;
		}
		this.entities.remove(index);
	}
	
	public synchronized void movePlayer(String username, float xMove, float yMove) {
		int index = getPlayerMPIndex(username);
		PlayerMP player = (PlayerMP) entities.get(index);
		player.setxMove(xMove);
		player.setyMove(yMove);
	}

	private int getPlayerMPIndex(String username) {
	    int index = 0;
	    for (Entity e : entities) {
	        if (e instanceof PlayerMP && ((PlayerMP) e).getUsername().equals(username)) {
	            break;
	        }
	        index++;
	    }
	    return index;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
}
