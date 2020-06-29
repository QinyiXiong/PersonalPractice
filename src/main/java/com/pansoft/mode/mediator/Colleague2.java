package com.pansoft.mode.mediator;

public class Colleague2 extends Colleague{

	@Override
	public void receive() {
		System.out.println("");
	}

	@Override
	public void send() {
		System.out.println("");
		mediator.relay(this);
	}
	
}
