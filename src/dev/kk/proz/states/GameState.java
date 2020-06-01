package dev.kk.proz.states;

import java.awt.Graphics;

import dev.kk.proz.Game;
import dev.kk.proz.Handler;
import dev.kk.proz.entities.creatures.Player;
import dev.kk.proz.gfx.Assets;
import dev.kk.proz.maps.Map;
import dev.kk.proz.tiles.Tile;
import dev.kk.proz.ui.ClickListener;
import dev.kk.proz.ui.UIButton;
import dev.kk.proz.ui.UIManager;

public class GameState extends State {

	private Player player;
	private UIManager uiManager;
	private Map testMap;
	
	public GameState(Handler handler) {
		super(handler);
		
		testMap= new Map("resources/map/basicMap.txt");
		handler.setMap(testMap);
		player = new Player(handler, testMap.getSpawnX(), testMap.getSpawnY());
		
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		UIButton exitButton = new UIButton(1225, 690, 50, 25, Assets.exitButton, new ClickListener(){
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().menuState);
		}});
		
		uiManager.addObject(exitButton);
	}
	
	@Override
	public void tick() {
		handler.getMouseManager().setUIManager(uiManager);
		player.tick();
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		testMap.render(g);
		player.render(g);
		uiManager.render(g);
	}
	
	
	
}
