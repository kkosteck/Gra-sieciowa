package dev.kk.proz.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import dev.kk.proz.Handler;
import dev.kk.proz.entities.bullets.BasicBullet;
import dev.kk.proz.entities.creatures.PlayerMP;
import dev.kk.proz.net.packets.Packet;
import dev.kk.proz.net.packets.Packet.PacketTypes;
import dev.kk.proz.net.packets.Packet00Login;
import dev.kk.proz.net.packets.Packet01Disconnect;
import dev.kk.proz.net.packets.Packet02Move;
import dev.kk.proz.net.packets.Packet03Attack;

public class GameClient extends Thread{
	
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private Handler handler;
	
	public GameClient(Handler handler, String ipAddress) {
		this.handler = handler;
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}
	
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type = Packet.lookupPacket(message.substring(0, 2)); 
		Packet packet;
		
		switch(type) {
		default:
		case INVALID:
			break;
		case LOGIN:
			packet = new Packet00Login(data);
			handleLogin((Packet00Login) packet, address, port);
			break;
		case DISCONNECT:
			packet = new Packet01Disconnect(data);
			System.out.println("[" + address.getHostAddress() + ":" + port + "] " + ((Packet01Disconnect) packet).getUsername() + " has left the world.");
			handler.getEntityManager().removePlayerMP(((Packet01Disconnect) packet).getUsername());
			break;
		case MOVE:
            packet = new Packet02Move(data);
            handleMove((Packet02Move) packet);
            break;
		case ATTACK:
			packet = new Packet03Attack(data);
			handleAttack((Packet03Attack) packet);
		}
	}
	
	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void handleLogin(Packet00Login packet, InetAddress address, int port) {
		System.out.println("["+address.getHostAddress()+":"+port+"]"+packet.getUsername()+ " has joined the game.");
		PlayerMP player = new PlayerMP(handler, packet.getX(), packet.getY(), packet.getUsername(), packet.getTeam(), address, port);
		handler.getMap().getEntityManager().addEntity(player);
	}
	
	private void handleAttack(Packet03Attack packet) {
		BasicBullet bullet = new BasicBullet(handler, packet.getX(), packet.getY(), packet.getWay(), packet.getTeam());
		handler.getMap().getEntityManager().addEntity(bullet);
	}
	
    private void handleMove(Packet02Move packet) {
    	handler.getEntityManager().movePlayer(packet.getUsername(), packet.getX(), packet.getY());
    }
}
