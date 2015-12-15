package jiang.linz;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import jiang.linz.LinzDispatcher;

public class linzListener extends Thread {
	private ServerSocket socket;
	
	private LinzDispatcher dispatcher;
	
	private volatile boolean running;

	public linzListener(int port, int backlog) {
		
		super.setName("http listener");
		
		try {
			socket = new ServerSocket(port, backlog);
			running = true;
		} catch (IOException e) {
			System.out.println("error when create server socket. " + e.getMessage());
		}
	}
	
	
	@Override
	public void run() {
		while (true) {
			try {
				Socket s = this.socket.accept();
				if (running) {
					dispatcher.actionPerformed(s);
				}
				else {
					break;
				}
			} catch (IOException e) {
				System.out.println("error when accept a socket. " + e.getMessage());
			}
		}
	}

	
	public void addActionDispatcher(LinzDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	public void end() throws IOException {
		running = false;
		new Socket(socket.getInetAddress(), socket.getLocalPort()).close();
	}
}
