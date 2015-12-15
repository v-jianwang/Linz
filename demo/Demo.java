package jiang.linzDemo;


import java.util.Scanner;

import jiang.linz.LinzServer;

public class Demo {

	public static void main(String[] args) {
		
		httpHandler handler = new httpHandler();
		LinzServer server = new LinzServer(handler, 8071);
		
		Scanner scanner = new Scanner(System.in);
		String s;
		while (true) {
			s = scanner.nextLine();
			if (s.equals("stop")) 
				handler.setRunning(false);
			if (s.equals("start"))
				handler.setRunning(true);
			if (s.equals("done"))
				break;
		}
		scanner.close();
		server.stop();
		System.out.println("demo finish");
	}
	

}
