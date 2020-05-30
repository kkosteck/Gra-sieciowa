package dev.kk.proz.states;

import java.awt.Graphics;

import dev.kk.proz.Game;
import dev.kk.proz.entities.creatures.Player;
import dev.kk.proz.gfx.Assets;

public class GameState extends State {

	private Player player;
	
	public GameState(Game game) {
		super(game);
		player = new Player(game, 200, 200);
	}
	
	@Override
	public void tick() {
		player.tick();
	}

	@Override
	public void render(Graphics g) {
		player.render(g);
	}
	
	
	
}
