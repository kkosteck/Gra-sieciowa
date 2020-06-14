package main.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import main.Handler;
import main.entities.creatures.PlayerMP;
import main.net.packets.Packet;
import main.net.packets.Packet.PacketTypes;
import main.net.packets.Packet00Login;
import main.net.packets.Packet01Disconnect;
import main.net.packets.Packet02Move;
import main.net.packets.Packet03Attack;
import main.net.packets.Packet04Start;
import main.utilities.Utilities.Teams;

public class GameServer extends Thread {

	private DatagramSocket socket;
	private Handler handler;
	private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>(); // all players who are online

	public GameServer(Handler handler) {
		this.handler = handler;
		try {
			this.socket = new DatagramSocket(1331);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet); // check for packets
			} catch (IOException e) {
				e.printStackTrace();
			}
			// if data is not null we can resolve information from packet
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());

		}
	}

	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type = Packet.lookupPacket(message.substring(0, 2)); // get packet ID
		Packet packet;

		// check for packet type
		switch (type) {
		default:
		case INVALID:
			break;
		case LOGIN:
			packet = new Packet00Login(data);
			// print out that player successfully connected
			System.out.println("[" + address.getHostAddress() + ":" + port + "]"
					+ ((Packet00Login) packet).getUsername() + " has connected.");
			// create new player
			PlayerMP player = new PlayerMP(handler, ((Packet00Login) packet).getX(), ((Packet00Login) packet).getY(),
					((Packet00Login) packet).getUsername(), ((Packet00Login) packet).getTeam(), address, port);
			// add player entity to server
			this.addConnection(player, (Packet00Login) packet);
			break;
		case DISCONNECT:
			packet = new Packet01Disconnect(data);
			// print out that player disconnected
			System.out.println("[" + address.getHostAddress() + ":" + port + "] "
					+ ((Packet01Disconnect) packet).getUsername() + " has left...");
			// remove move player entity from server
			this.removeConnection((Packet01Disconnect) packet);
			break;
		case MOVE:
			packet = new Packet02Move(data);
			this.handleMove((Packet02Move) packet);
			break;
		case ATTACK:
			packet = new Packet03Attack(data);
			packet.writeData(this);
			break;
		case START:
			packet = new Packet04Start(data);
			packet.writeData(this);
			break;
		}
	}

	public void addConnection(PlayerMP player, Packet00Login packet) {
		boolean alreadyConnected = false;
		for (PlayerMP p : this.connectedPlayers) {
			if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
				// ip and port are null and -1 if player is new in server
				if (p.ipAddress == null) { // so we set to it proper ip
					p.ipAddress = player.ipAddress;
				}
				if (p.port == -1) { // and port
					p.port = player.port;
				}
				alreadyConnected = true;
			} else {
				// relay to the current connected player that there is a new player
				sendData(packet.getData(), p.ipAddress, p.port);
				// relay to the new player that the currently connect player exists
				// and set proper castles health
				int redHealth = handler.getEntityManager().getCastle(Teams.RED).getHealth();
				int blueHealth = handler.getEntityManager().getCastle(Teams.BLUE).getHealth();
				Packet00Login packetPlayer = new Packet00Login(p.getUsername(), p.getX(), p.getY(), p.getTeam(),
						redHealth, blueHealth);
				sendData(packetPlayer.getData(), player.ipAddress, player.port);
			}
		}
		if (!alreadyConnected) {
			this.connectedPlayers.add(player);
		}
	}

	private void removeConnection(Packet01Disconnect packet) {
		// remove player from active players list
		this.connectedPlayers.remove(getPlayerMPIndex(packet.getUsername()));
		packet.writeData(this);
	}

	private int getPlayerMPIndex(String username) {
		int index = 0;
		for (PlayerMP player : this.connectedPlayers) {
			if (player.getUsername().equals(username))
				break;
			index++;
		}
		return index;
	}

	public PlayerMP getPlayerMP(String username) {
		for (PlayerMP player : this.connectedPlayers) {
			if (player.getUsername().equals(username)) {
				return player;
			}
		}
		return null;
	}

	private void handleMove(Packet02Move packet) {
		if (getPlayerMP(packet.getUsername()) != null) { // if player exists
			int index = getPlayerMPIndex(packet.getUsername()); // get player index in connected list
			PlayerMP player = this.connectedPlayers.get(index);
			player.setX(packet.getX()); // set his coordinates
			player.setY(packet.getY());
			packet.writeData(this);
		}
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			this.socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendDataToAllClients(byte[] data) {
		for (PlayerMP p : connectedPlayers) {
			sendData(data, p.ipAddress, p.port);
		}
	}
}
