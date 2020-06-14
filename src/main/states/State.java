package main.states;

import java.awt.Graphics;

import main.Handler;
import main.utilities.Utilities.Teams;

// abstract class for every state in the game
// state is responsible for rendering and ticking proper staff
// thanks to this we can easily change what we want to show for the player

public abstract class State {

	private static State currentState = null;
	private static Teams currentSide = Teams.NONE;

	protected Handler handler;

	public State(Handler handler) {
		this.handler = handler;
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	// getters and setters

	public static Teams getSide() {
		return currentSide;
	}

	public static void setSide(Teams currentSide) {
		State.currentSide = currentSide;
	}

	public static State getState() {
		return currentState;
	}

	public static void setState(State state) {
		currentState = state;
	}

}
