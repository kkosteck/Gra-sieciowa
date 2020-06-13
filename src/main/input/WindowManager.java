package main.input;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import main.Handler;
import main.net.packets.Packet01Disconnect;

public class WindowManager implements WindowListener{
	
	private Handler handler;
	private String username;
	
	public WindowManager(Handler handler, String username) {
		handler.getGame().getDisplay().getFrame().addWindowListener(this);
		this.handler = handler;
		this.username = username;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
        Packet01Disconnect packet = new Packet01Disconnect(username);
        packet.writeData(handler.getSocketClient());
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}
	
	
}
