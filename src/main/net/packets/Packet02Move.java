package dev.kk.proz.net.packets;

import dev.kk.proz.net.GameClient;
import dev.kk.proz.net.GameServer;

public class Packet02Move extends Packet{

	private String username;
	private float xMove, yMove;
	private float x, y;
	
	public Packet02Move(byte[] data) {
		super(02);
		String[] dataArrayMove = readData(data).split(",");
		this.username = dataArrayMove[0];
		this.xMove = Float.parseFloat(dataArrayMove[1]);
		this.yMove = Float.parseFloat(dataArrayMove[2]);
		this.x = Float.parseFloat(dataArrayMove[3]);
		this.y = Float.parseFloat(dataArrayMove[4]);
	}
	
	public Packet02Move(String username, float xMove, float yMove, float x, float y) {
		super(02);
		
		this.username = username;
		this.xMove = xMove;
		this.yMove = yMove;
		this.x = x;
		this.y = y;
	}

	@Override
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		return ("02" + this.username+","+this.xMove+","+this.yMove+","+this.x+","+this.y).getBytes();
	}

	public String getUsername() {
		return username;
	}

	public float getxMove() {
		return xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
}
