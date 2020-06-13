package main.net.packets;

import main.net.GameClient;
import main.net.GameServer;
import main.utilities.Utilities.Teams;

public class Packet03Attack extends Packet{

	private Teams team;
	private float x, y;
	private int way;
	
	public Packet03Attack(byte[] data) {
		super(03);
		String[] dataArray = readData(data).split(",");
		this.x = Float.parseFloat(dataArray[0]);
		this.y = Float.parseFloat(dataArray[1]);
		this.team = Teams.valueOf(Integer.parseInt(dataArray[2]));
		this.way = Integer.parseInt(dataArray[3]);
	}
	
	public Packet03Attack(float x, float y, Teams team, int way) {
		super(03);
		
		this.x = x;
		this.y = y;
		this.team = team;
		this.way = way;
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
		return ("03" + this.x + "," + this.y + "," + this.team.getId() + "," + this.way).getBytes();
	}
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	public Teams getTeam() {
		return team;
	}

	public int getWay() {
		return way;
	}
}
