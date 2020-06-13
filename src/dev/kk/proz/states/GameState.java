package dev.kk.proz.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JOptionPane;

import dev.kk.proz.Handler;
import dev.kk.proz.entities.creatures.Player;
import dev.kk.proz.entities.creatures.PlayerMP;
import dev.kk.proz.gfx.Assets;
import dev.kk.proz.input.KeyManager;
import dev.kk.proz.input.WindowManager;
import dev.kk.proz.maps.Map;
import dev.kk.proz.net.GameClient;
import dev.kk.proz.net.GameServer;
import dev.kk.proz.net.packets.Packet00Login;
import dev.kk.proz.net.packets.Packet01Disconnect;
import dev.kk.proz.net.packets.Packet04Start;
import dev.kk.proz.ui.ClickListener;
import dev.kk.proz.ui.HealthBar;
import dev.kk.proz.ui.UIButton;
import dev.kk.proz.ui.UIManager;
import dev.kk.proz.utilities.Utilities.Teams;

public class GameState extends State {

	private UIManager uiManager;
	private Map basicMap;

	// multiplayer
	private GameClient socketClient;
	private GameServer socketServer;
	public Player player;
	private float spawnX = 50, spawnY = 50;
	private KeyManager keyManager;
	@SuppressWarnings("unused")
	private WindowManager windowManager;
	private HealthBar playerHPBar;
	private boolean waiting = true;
	private long lastRespawnTimer, respawnCooldown = 10000, respawnTimer = respawnCooldown;

	public GameState(Handler handler) {
		super(handler);

		basicMap = new Map(handler, "resources/map/basicMap.txt");
		keyManager = new KeyManager(handler);
		handler.setMap(basicMap);
		handler.getMouseManager().setUIManager(uiManager);

		gui();
		multiplayer();
	}

	@Override
	public void tick() {
		if (socketServer != null) {
			respawnTimer += System.currentTimeMillis() - lastRespawnTimer;
			lastRespawnTimer = System.currentTimeMillis();
			if (respawnTimer >= respawnCooldown) {
				System.out.println("check state");
				Packet04Start packet = new Packet04Start(waiting, respawnTimer);
				packet.writeData(handler.getSocketClient());
				respawnTimer = 0;
			}else {
				waiting = checkForOponents();
				Packet04Start packet = new Packet04Start(waiting, respawnTimer);
				packet.writeData(handler.getSocketClient());
			}
		}
		handler.getMouseManager().setUIManager(uiManager);
		if (!waiting) {
			basicMap.tick();
			playerHPBar.setValue(player.getHealth());

			if (player.getHealth() <= 0) { // if player dies
				handler.getMouseManager().setUIManager(null);
				handler.getGame().gameOver = new GameOver(handler);
				State.setState(handler.getGame().gameOver);

				Packet01Disconnect packet = new Packet01Disconnect(player.getUsername());
				packet.writeData(handler.getSocketClient());
				player.setActive(false);
			}
		}
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		basicMap.render(g);
		uiManager.render(g);

		if (waiting) {
			g.setFont(new Font("Arial", Font.PLAIN, 100));
			g.drawString("WAIT FOR OPONENTS", 75, 360);
		}

	}

	public Player getPlayer() {
		return player;
	}

	public void gui() {
		uiManager = new UIManager(handler);

		if (State.getSide() == Teams.RED) // player health bar
			playerHPBar = new HealthBar(16, 675, 136, 32, 0, Player.MAX_HEALTH, Color.RED, true);
		else if (State.getSide() == Teams.BLUE)
			playerHPBar = new HealthBar(1128, 675, 136, 32, 0, Player.MAX_HEALTH, Color.BLUE, false);
		uiManager.addObject(playerHPBar);

		UIButton exitButton = new UIButton(1225, 690, 50, 25, Assets.exitButton, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().menuState);

				Packet01Disconnect packet = new Packet01Disconnect(player.getUsername());
				packet.writeData(socketClient);
			}
		});
		uiManager.addObject(exitButton);
	}

	public void multiplayer() {
		if (JOptionPane.showConfirmDialog(null, "Do you want to run the server?") == 0) {
			socketServer = new GameServer(handler);
			socketServer.start();
			handler.setSocketServer(socketServer);
		}
		socketClient = new GameClient(handler, JOptionPane.showInputDialog("Enter a server ip"));
		socketClient.start();
		handler.setSocketClient(socketClient);

		if (State.getSide() == Teams.RED) { // set spawn
			spawnX = 256;
			spawnY = 344;
		} else if (State.getSide() == Teams.BLUE) {
			spawnX = 1024;
			spawnY = 344;
		}
		player = new PlayerMP(handler, keyManager, spawnX, spawnY, JOptionPane.showInputDialog("Enter a username"),
				State.getSide(), null, -1);
		basicMap.getEntityManager().addEntity(player);
		Packet00Login loginPacket = new Packet00Login(player.getUsername(), player.getX(), player.getY(),
				player.getTeam());
		if (socketServer != null) {
			socketServer.addConnection((PlayerMP) player, loginPacket);
		}
		loginPacket.writeData(socketClient);

		windowManager = new WindowManager(handler, player.getUsername());
	}

	public boolean checkForOponents() {
		int redCount = basicMap.getEntityManager().countRedTeammates();
		int blueCount = basicMap.getEntityManager().countBlueTeammates();
		return !(redCount > 0 && blueCount > 0);
	}

	public boolean isWaiting() {
		return waiting;
	}

	public void setWaiting(boolean waiting) {
		this.waiting = waiting;
	}

	public long getRespawnCooldown() {
		return respawnCooldown;
	}

	public void setRespawnCooldown(long respawnCooldown) {
		this.respawnCooldown = respawnCooldown;
	}
}
