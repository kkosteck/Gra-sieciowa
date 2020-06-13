package main.states;

import java.awt.Font;
import java.awt.Graphics;

import main.Handler;
import main.gfx.Assets;
import main.ui.ClickListener;
import main.ui.UIButton;
import main.ui.UIManager;

public class GameOver extends State{

	private UIManager uiManager;
	
	public GameOver(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		UIButton exitButton = new UIButton(690, 410, 200, 100, Assets.exitButton, new ClickListener(){
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				System.exit(0);
		}});;
		UIButton tryAgain = new UIButton(390, 410, 200, 100, Assets.tryAgainButton, new ClickListener(){
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				handler.getGame().pickSide = new PickSide(handler);
				State.setState(handler.getGame().pickSide);
				
		}});;
		
		uiManager.addObject(exitButton);
		uiManager.addObject(tryAgain);
	}
	
	
	@Override
	public void tick() {
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.tick();
		
	}
	@Override
	public void render(Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 240));
		g.drawString("YOU DIED", 75, 360);
		uiManager.render(g);
	}
}
