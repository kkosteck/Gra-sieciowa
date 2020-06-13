package main.states;

import java.awt.Graphics;

import main.Handler;
import main.utilities.Utilities.Teams;

public abstract class State {
	
	private static State currentState = null;
	private static Teams currentSide = Teams.NONE;
	
	public static void setState(State state) {
		currentState = state;
	}
	
	public static State getState() {
		return currentState;
	}
	
	public static Teams getSide() {
		return currentSide;
	}

	public static void setSide(Teams currentSide) {
		State.currentSide = currentSide;
	}



	protected Handler handler;
	
	public State(Handler handler) {
		this.handler = handler;
	}
	
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
}
