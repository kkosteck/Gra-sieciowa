package dev.kk.proz.states;

import java.awt.Color;
import java.awt.Graphics;

import dev.kk.proz.Handler;
import dev.kk.proz.gfx.Assets;
import dev.kk.proz.ui.ClickListener;
import dev.kk.proz.ui.UIButton;
import dev.kk.proz.ui.UIManager;

public class HowToPlay extends State{

	private UIManager uiManager;
	
	public HowToPlay(Handler handler) {
		super(handler);
		
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		UIButton backButton = new UIButton(1170, 660, 100, 50, Assets.backButton, new ClickListener(){
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().menuState);
		}});

		uiManager.addObject(backButton);
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
		g.drawImage(Assets.howToPlay, 0, 0, 1280, 720, null);
		uiManager.render(g);
		
	}

}
