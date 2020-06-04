package dev.kk.proz.states;

import java.awt.Graphics;

import javax.swing.JOptionPane;

import dev.kk.proz.Handler;
import dev.kk.proz.entities.creatures.Player;
import dev.kk.proz.entities.creatures.PlayerMP;
import dev.kk.proz.gfx.Assets;
import dev.kk.proz.input.KeyManager;
import dev.kk.proz.maps.Map;
import dev.kk.proz.net.GameClient;
import dev.kk.proz.net.GameServer;
import dev.kk.proz.net.packets.Packet00Login;
import dev.kk.proz.net.packets.Packet01Disconnect;
import dev.kk.proz.ui.ClickListener;
import dev.kk.proz.ui.UIButton;
import dev.kk.proz.ui.UIManager;
import dev.kk.proz.utilities.Utilities.Teams;

public class GameState extends State {

	private UIManager uiManager;
	private Map basicMap;
	
	//multiplayer
	private GameClient socketClient;
	private GameServer socketServer;
	public Player player;
	private float spawnX = 50, spawnY = 50;
	private KeyManager keyManager;
	
	public GameState(Handler handler) {
		super(handler);	

		//multiplayer
		if(JOptionPane.showConfirmDialog(null, "Do you want to run the server?") == 0) {
			socketServer = new GameServer(handler);
			socketServer.start();
			handler.setSocketServer(socketServer);
		}
		socketClient = new GameClient(handler, JOptionPane.showInputDialog("Enter a server ip"));
		socketClient.start();

		basicMap = new Map(handler, "resources/map/basicMap.txt");
		keyManager = new KeyManager(handler);
		handler.setMap(basicMap);
		
		if(State.getSide() == Teams.RED) {
			spawnX = 256;
			spawnY = 344;
		}else if(State.getSide() == Teams.BLUE) {
			spawnX = 1024;
			spawnY = 344;
		}
		player = new PlayerMP(handler, keyManager, spawnX, spawnY, JOptionPane.showInputDialog("Enter a username"), State.getSide(), null, -1);
		basicMap.getEntityManager().addEntity(player);
		Packet00Login loginPacket = new Packet00Login(player.getUsername(), player.getX(), player.getY(), player.getTeam());
		if(socketServer != null) {
			socketServer.addConnection((PlayerMP) player, loginPacket);
		}
		loginPacket.writeData(socketClient);
		handler.setSocketClient(socketClient);
		
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		UIButton exitButton = new UIButton(1225, 690, 50, 25, Assets.exitButton, new ClickListener(){
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().menuState);
				
				Packet01Disconnect packet = new Packet01Disconnect(player.getUsername());
		        packet.writeData(socketClient);
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
