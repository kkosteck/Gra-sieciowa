package main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import main.display.Display;
import main.gfx.Assets;
import main.input.MouseManager;
import main.states.GameState;
import main.states.MenuState;
import main.states.State;

/*Main class with all important staff for running the game machine
*/
public class Game implements Runnable {

	private Display display;
	public int width, height; // widht and height for our game window
	public String title; // title for game window

	private boolean running = false;
	private Thread thread;

	private BufferStrategy bs;
	private Graphics g;

	// states - all segments within the game are separated in states
	public State menuState, pickSide, gameOver, gameEnd, howToPlay;
	// gamestate is not declared as State to avoid annoying casts, for others states
	// this is unnecessary
	public GameState gameState;

	// input from mouse, other input like keyboard and window are player dependent
	// and are initialized with player
	private MouseManager mouseManager;
	// Handler - contains all important staff
	private Handler handler;

	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		mouseManager = new MouseManager();
	}

	private void init() { // initilize our game window
		display = new Display(title, width, height);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		Assets.init();

		handler = new Handler(this);

		// first state is menu
		menuState = new MenuState(handler);
		State.setState(menuState);
	}

	private void tick() {
		// tick only when we have state already setted
		if (State.getState() != null)
			State.getState().tick();
	}

	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height); // clear screen

		if (State.getState() != null) {
			State.getState().render(g);
		}

		bs.show();
		g.dispose();
	}

	public void run() {

		init();

		int fps = 30; // frames per second
		double timePerTick = 1e9 / fps; // time between every frame
		double delta = 0; // time counter for ticking
		long now; // first timestamp
		long lastTime = System.nanoTime(); // second timestamp
		long timer = 0; // timer for fps counter
		int ticks = 0; // fps counter

		// game loop
		while (running) {
			now = System.nanoTime(); // get current time
			delta += (now - lastTime) / timePerTick; // add time passed every loop
			timer += now - lastTime; // time counter for fps counter
			lastTime = now; // move timestamps

			if (delta >= 1) { // check if we need to do tick
				tick();
				render();
				ticks++;
				delta--;
			}

			if (timer >= 1e9) { // fps counter
				System.out.println("FPS: " + ticks);
				ticks = 0;
				timer = 0;
			}

		}
		stop();
	}

	public synchronized void start() {// starting game loop
		if (running)
			return;
		running = true;

		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() { // stoping game loop
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// getters and setters

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

}
