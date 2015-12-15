package jiang.linz;

public class LinzServer {
	
	linzListener listener;
	
	public LinzServer(Object handler) {
		this(handler, 8028);
	}
	
	
	public LinzServer(Object handler, int port) {
		listener = new linzListener(port, 10);
		
		LinzDispatcher dispatcher = new LinzDispatcher(handler);
		listener.addActionDispatcher(dispatcher);
		
		listener.start();
	}
	
	
	public void stop() {
		try {
			listener.end();
			listener.join();
		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
		}
	}
}
