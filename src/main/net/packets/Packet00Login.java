package main.net.packets;

import main.net.GameClient;
import main.net.GameServer;
import main.utilities.Utilities.Teams;

public class Packet00Login extends Packet{

	private String username;
	private float x, y;
	private Teams team;
	private int redCastleHealth, blueCastleHealth;
	
	public Packet00Login(byte[] data) {
		super(00);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.x = Float.parseFloat(dataArray[1]);
		this.y = Float.parseFloat(dataArray[2]);
		this.team = Teams.valueOf(Integer.parseInt(dataArray[3]));
		this.redCastleHealth = Integer.parseInt(dataArray[4]);
		this.blueCastleHealth = Integer.parseInt(dataArray[5]);
	}
	
	public Packet00Login(String username, float x, float y, Teams team, int redCastleHealth, int blueCastleHealth) {
		super(00);
		
		this.username = username;
		this.x = x;
		this.y = y;
		this.team = team;
		this.redCastleHealth = redCastleHealth;
		this.blueCastleHealth = blueCastleHealth;
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
		return ("00" + username + "," + x + "," + y + "," + team.getId()+","+redCastleHealth+","+blueCastleHealth).getBytes();
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

	public int getRedCastleHealth() {
		return redCastleHealth;
	}

	public int getBlueCastleHealth() {
		return blueCastleHealth;
	}
    
}
