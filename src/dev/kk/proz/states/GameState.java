package dev.kk.proz.states;

import java.awt.Graphics;

import javax.swing.JOptionPane;

import dev.kk.proz.Handler;
import dev.kk.proz.entities.creatures.Player;
import dev.kk.proz.entities.creatures.PlayerMP;
import dev.kk.proz.gfx.Assets;
import dev.kk.proz.maps.Map;
import dev.kk.proz.net.GameClient;
import dev.kk.proz.net.GameServer;
import dev.kk.proz.net.packets.Packet00Login;
import dev.kk.proz.ui.ClickListener;
import dev.kk.proz.ui.UIButton;
import dev.kk.proz.ui.UIManager;

public class GameState extends State {

	private UIManager uiManager;
	private Map basicMap;
	
	//multiplayer
	private GameClient socketClient;
	private GameServer socketServer;
	private Player player;
	
	public GameState(Handler handler) {
		super(handler);	
		
		//multiplayer
		if(JOptionPane.showConfirmDialog(null, "Do you want to run the server?") == 0) {
			socketServer = new GameServer(handler);
			socketServer.start();
		}
		socketClient = new GameClient(handler, "localhost");
		socketClient.start();
		
		basicMap = new Map(handler, "resources/map/basicMap.txt");
		handler.setMap(basicMap);
		player = new PlayerMP(handler, 0, 0, JOptionPane.showInputDialog("Enter a username"), null, -1);
		basicMap.getEntityManager().addEntity(player);
		Packet00Login loginPacket = new Packet00Login(player.getUsername());
		if(socketServer != null) {
			socketServer.addConnection((PlayerMP) player, loginPacket);
		}
//		socketClient.sendData("ping".getBytes());
		loginPacket.writeData(socketClient);
		
		player.respawn(State.getSide());
		
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
		basicMap.tick();
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		basicMap.render(g);
		uiManager.render(g);
	}
	
}
