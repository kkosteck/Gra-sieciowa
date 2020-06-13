package dev.kk.proz.states;

import java.awt.Graphics;

import dev.kk.proz.Handler;
import dev.kk.proz.gfx.Assets;
import dev.kk.proz.ui.ClickListener;
import dev.kk.proz.ui.UIManager;
import dev.kk.proz.ui.UISimpleButton;
import dev.kk.proz.utilities.Utilities.Teams;

public class PickSide extends State{

	private UIManager uiManager;
	
	public PickSide(Handler handler) {
		super(handler);
		
		uiManager = new UIManager(handler);
		
		UISimpleButton redSide =  new UISimpleButton(320, 256, 256, 128, Assets.redSideButton, new ClickListener(){
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setSide(Teams.RED);
//				if(handler.getGame().gameState == null)
					handler.getGame().gameState = new GameState(handler);
				State.setState(handler.getGame().gameState);
		}});
		
		uiManager.addObject(redSide);
		
		UISimpleButton blueSide =  new UISimpleButton(704, 256, 256, 128, Assets.blueSideButton, new ClickListener(){
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setSide(Teams.BLUE);
//				if(handler.getGame().gameState == null)
					handler.getGame().gameState = new GameState(handler);
				State.setState(handler.getGame().gameState);
		}});
		
		uiManager.addObject(blueSide);
	}

	@Override
	public void tick() {
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		uiManager.render(g);
	}
}
