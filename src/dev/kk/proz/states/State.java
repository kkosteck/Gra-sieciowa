package dev.kk.proz.states;

import java.awt.Graphics;

import dev.kk.proz.Game;
import dev.kk.proz.Handler;

public abstract class State {
	
	private static State currentState = null;
	private static int currentSide = 0;
	
	public static void setState(State state) {
		currentState = state;
	}
	
	public static State getState() {
		return currentState;
	}
	
	public static int getSide() {
		return currentSide;
	}

	public static void setSide(int currentSide) {
		State.currentSide = currentSide;
	}



	protected Handler handler;
	
	public State(Handler handler) {
		this.handler = handler;
	}
	
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
}
