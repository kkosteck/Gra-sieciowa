package dev.kk.proz.entities.creatures;

import java.net.InetAddress;

import dev.kk.proz.Handler;
import dev.kk.proz.input.KeyManager;
import dev.kk.proz.utilities.Utilities.Teams;

public class PlayerMP extends Player {

	public InetAddress ipAddress;
	public int port;
	
	public PlayerMP(Handler handler, KeyManager keyManager, float x, float y, String username, Teams team, InetAddress ipAddress, int port) {
		super(handler, keyManager, x, y, username, team);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public PlayerMP(Handler handler, float x, float y, String username, Teams team, InetAddress ipAddress, int port) {
		super(handler, null, x, y, username, team);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	@Override
	public void tick() {
		super.tick();
	}

}
