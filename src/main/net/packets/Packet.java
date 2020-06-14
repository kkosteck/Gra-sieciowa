package main.net.packets;

import main.net.GameClient;
import main.net.GameServer;

/* abstract class for all types of packets
 */

public abstract class Packet {

	// enum for every packet type and its id
	public static enum PacketTypes {
		INVALID(-1), LOGIN(00), DISCONNECT(01), MOVE(02), ATTACK(03), START(04);

		private int packetId;

		private PacketTypes(int packetId) {
			this.packetId = packetId;
		}

		public int getId() {
			return packetId;
		}
	}

	public byte packetId;

	public Packet(int packetId) {
		this.packetId = (byte) packetId;
	}

	public String readData(byte[] data) { // read data from packet without its id
		String message = new String(data).trim();
		return message.substring(2);
	}

	public static PacketTypes lookupPacket(int id) { // get packet type
		for (PacketTypes p : PacketTypes.values()) {
			if (p.getId() == id)
				return p;
		}
		return PacketTypes.INVALID;
	}

	public static PacketTypes lookupPacket(String packetId) { // get packet type with use of packet id as string
		try {
			return lookupPacket(Integer.parseInt(packetId));
		} catch (NumberFormatException e) {
			return PacketTypes.INVALID;
		}
	}

	public abstract void writeData(GameClient client);

	public abstract void writeData(GameServer server);

	public abstract byte[] getData();

}
