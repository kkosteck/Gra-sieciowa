package main.states;

import java.awt.Graphics;

import main.Handler;
import main.gfx.Assets;
import main.ui.ClickListener;
import main.ui.UIManager;
import main.ui.UISimpleButton;
import main.utilities.Utilities.Teams;

// player here chooses team in which he wants to play
// he can choose clicking on one of two buttons

public class PickSide extends State {

	private UIManager uiManager;

	public PickSide(Handler handler) {
		super(handler);

		uiManager = new UIManager(handler);

		UISimpleButton redSide = new UISimpleButton(320, 256, 256, 128, Assets.redSideButton, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setSide(Teams.RED);
				handler.getGame().gameState = new GameState(handler);
				State.setState(handler.getGame().gameState);
			}
		});

		uiManager.addObject(redSide);

		UISimpleButton blueSide = new UISimpleButton(704, 256, 256, 128, Assets.blueSideButton, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setSide(Teams.BLUE);
				handler.getGame().gameState = new GameState(handler);
				State.setState(handler.getGame().gameState);
			}
		});

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
