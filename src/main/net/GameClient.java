package main.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import main.Handler;
import main.entities.creatures.BasicBullet;
import main.entities.creatures.PlayerMP;
import main.entities.towers.Castle;
import main.net.packets.Packet;
import main.net.packets.Packet.PacketTypes;
import main.net.packets.Packet00Login;
import main.net.packets.Packet01Disconnect;
import main.net.packets.Packet02Move;
import main.net.packets.Packet03Attack;
import main.net.packets.Packet04Start;
import main.utilities.Utilities.Teams;

/* class for clientside handle of multiplayer
 * all packets received are resolved in here
 */

public class GameClient extends Thread {

	private InetAddress ipAddress;
	private DatagramSocket socket;
	private Handler handler;

	public GameClient(Handler handler, String ipAddress) {
		this.handler = handler;
		try {
			this.socket = new DatagramSocket(); // create datagram
			this.ipAddress = InetAddress.getByName(ipAddress); // set client ipaddress
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length); // set datagram
			try {
				socket.receive(packet); // check for packets
			} catch (IOException e) {
				e.printStackTrace();
			} // if received data is not null, we can parse packet
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}

	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type = Packet.lookupPacket(message.substring(0, 2)); // take out packet ID
		Packet packet;

		// check which packet we received
		switch (type) {
		default:
		case INVALID: // do not do anything if we received wrong packet
			break;
		case LOGIN: // if player logged in
			packet = new Packet00Login(data);
			handleLogin((Packet00Login) packet, address, port);
			break;
		case DISCONNECT: // if player logged out
			packet = new Packet01Disconnect(data);
			System.out.println("[" + address.getHostAddress() + ":" + port + "] "
					+ ((Packet01Disconnect) packet).getUsername() + " has left the world.");
			handler.getEntityManager().removePlayerMP(((Packet01Disconnect) packet).getUsername());
			break;
		case MOVE: // moving packets of others players
			packet = new Packet02Move(data);
			handleMove((Packet02Move) packet);
			break;
		case ATTACK: // created bullets by other players
			packet = new Packet03Attack(data);
			handleAttack((Packet03Attack) packet);
			break;
		case START:
			packet = new Packet04Start(data); // information for synchronization tower respawn timer and starting play
			// set game in waiting or not waiting state for opponents
			handler.getGame().gameState.setWaiting(((Packet04Start) (packet)).getWaiting());
			// set respawn towers time properly
			handler.getMap().setRespawnTimer(((Packet04Start) (packet)).getTimer());
			break;
		}
	}

	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331); // create packet and send it to
																						// the server
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleLogin(Packet00Login packet, InetAddress address, int port) {
		// print ip and port of players who joined the game
		System.out.println(
				"[" + address.getHostAddress() + ":" + port + "]" + packet.getUsername() + " has joined the game.");
		// add new player with information received from packet
		PlayerMP player = new PlayerMP(handler, packet.getX(), packet.getY(), packet.getUsername(), packet.getTeam(),
				address, port);
		// add player to the manager
		handler.getEntityManager().addEntity(player);
		// set castles health properly, in case it was hit before player joined the game
		if (packet.getRedCastleHealth() < Castle.MAX_HEALTH)
			handler.getEntityManager().getCastle(Teams.RED).setHealth(packet.getRedCastleHealth());
		if (packet.getBlueCastleHealth() < Castle.MAX_HEALTH)
			handler.getEntityManager().getCastle(Teams.BLUE).setHealth(packet.getBlueCastleHealth());
	}

	private void handleAttack(Packet03Attack packet) {
		// create new bullet on client side from packet
		BasicBullet bullet = new BasicBullet(handler, packet.getX(), packet.getY(), packet.getWay(), packet.getTeam());
		// and add it to the manager
		handler.getMap().getEntityManager().addEntity(bullet);
	}

	private void handleMove(Packet02Move packet) {
		// set movement variables of the player from the packet
		handler.getEntityManager().movePlayer(packet.getUsername(), packet.getxMove(), packet.getyMove());
	}
}
