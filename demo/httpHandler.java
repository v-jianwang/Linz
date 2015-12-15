package jiang.linzDemo;

import jiang.linz.annotation.LinzAction;
import jiang.linz.annotation.LinzState;

public class httpHandler {
	
	private boolean running;
	
	public httpHandler() {
		running = true;
	}

	@LinzAction
	public void callStart() {
		running = true;
		System.out.println("calling start..");
	}
	
	@LinzAction
	public void callStop() {
		running = false;
		System.out.println("calling stop..");
	}
	
	@LinzState
	public boolean getRunning() {
		return running;
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}

	@LinzAction
	public void hello(String name) {
		System.out.println("hello " + name + "!");
	}
	
	@LinzAction
	public void Iam(String name, int age) {
		System.out.println("I am " + name + ", " + age + " years old.");
	}
	
	
}
