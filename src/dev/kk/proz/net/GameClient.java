package dev.kk.proz.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import dev.kk.proz.Handler;
import dev.kk.proz.entities.creatures.PlayerMP;
import dev.kk.proz.net.packets.Packet;
import dev.kk.proz.net.packets.Packet00Login;
import dev.kk.proz.net.packets.Packet.PacketTypes;

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
			
//			String message = new String(packet.getData());
//			System.out.println("SERVER > " + message); 
			
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
			System.out.println("["+address.getHostAddress()+":"+port+"]"+((Packet00Login) packet).getUsername()+ " has joined the game.");
			
			PlayerMP player = new PlayerMP(handler, 0, 0, ((Packet00Login) packet).getUsername(), address, port);
			handler.getMap().getEntityManager().addEntity(player);
			break;
		case DISCONNECT:
			break;
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
	
}
