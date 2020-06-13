package dev.kk.proz.net.packets;

import dev.kk.proz.net.GameClient;
import dev.kk.proz.net.GameServer;

public class Packet04Start extends Packet {

	private int waiting;
	private long timer;

	public Packet04Start(byte[] data) {
		super(04);
		String[] dataArray = readData(data).split(",");
		this.waiting = Integer.parseInt(dataArray[0]);
		this.timer = Long.parseLong(dataArray[1]);
	}

	public Packet04Start(boolean waiting, long timer) {
		super(04);
		if (waiting)
			this.waiting = 1;
		else
			this.waiting = 0;
		this.timer = timer;
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
		return ("04" + waiting + "," + timer).getBytes();
	}

	public boolean getWaiting() {
		if (waiting == 1)
			return true;
		return false;
	}

	public long getTimer() {
		return timer;
	}
}
