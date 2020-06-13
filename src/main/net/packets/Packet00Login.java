package dev.kk.proz.net.packets;

import dev.kk.proz.net.GameClient;
import dev.kk.proz.net.GameServer;
import dev.kk.proz.utilities.Utilities.Teams;

public class Packet00Login extends Packet{

	private String username;
	private float x, y;
	private Teams team;
	
	public Packet00Login(byte[] data) {
		super(00);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.x = Float.parseFloat(dataArray[1]);
		this.y = Float.parseFloat(dataArray[2]);
		this.team = Teams.valueOf(Integer.parseInt(dataArray[3]));
	}
	
	public Packet00Login(String username, float x, float y, Teams team) {
		super(00);
		
		this.username = username;
		this.x = x;
		this.y = y;
		this.team = team;
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
		return ("00" + this.username + "," + this.x + "," + this.y + "," + this.team.getId()).getBytes();
	}

	public String getUsername() {
		return username;
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
    
}
