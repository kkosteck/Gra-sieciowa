package dev.kk.proz.states;

import java.awt.Color;
import java.awt.Graphics;

import dev.kk.proz.Game;
import dev.kk.proz.Handler;
import dev.kk.proz.gfx.Assets;
import dev.kk.proz.ui.ClickListener;
import dev.kk.proz.ui.UIButton;
import dev.kk.proz.ui.UIManager;

public class MenuState extends State {
	
	private UIManager uiManager;
	
	public MenuState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		UIButton startButton = new UIButton(540, 200, 200, 100, Assets.startButton, new ClickListener(){
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				handler.getGame().pickSide = new PickSide(handler);
				State.setState(handler.getGame().pickSide);
		}});
		
		UIButton exitButton = new UIButton(540, 350, 200, 100, Assets.exitButton, new ClickListener(){
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				System.exit(0);
		}});
		
		uiManager.addObject(startButton);
		uiManager.addObject(exitButton);
	}
	
	@Override
	public void tick() {
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0,0,1280,720);
		uiManager.render(g);
	}
}
