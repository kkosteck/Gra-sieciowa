package dev.kk.proz;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.kk.proz.display.Display;
import dev.kk.proz.gfx.Assets;
import dev.kk.proz.input.KeyManager;
import dev.kk.proz.input.MouseManager;
import dev.kk.proz.states.MenuState;
import dev.kk.proz.states.State;

public class Game implements Runnable {

	private Display display;
	public int width, height; // widht and height for our game window
	public String title; // title for game window

	private boolean running = false;
	private Thread thread;

	private BufferStrategy bs;
	private Graphics g;

	// states
	public State gameState;
	public State menuState;
	public State pickSide;

	// input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	// Handler
	private Handler handler;

	// multiplayer

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

		menuState = new MenuState(handler);
		State.setState(menuState);
	}

	private void tick() {

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

		if (State.getState() != null)
			State.getState().render(g);

		bs.show();
		g.dispose();
	}

	public void run() {

		init();

		int fps = 60; // frames per second
		double timePerTick = 1e9 / fps; // time between every frame
		double delta = 0; // time counter for ticking
		long now; // first timestamp
		long lastTime = System.nanoTime(); // second timestamp
		long timer = 0; // timer for fps counter
		@SuppressWarnings("unused")
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
				// System.out.println("FPS: " + ticks);
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

	public KeyManager getKeyManager() {
		return keyManager;
	}

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
