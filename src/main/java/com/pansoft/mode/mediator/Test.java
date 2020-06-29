package com.pansoft.mode.mediator;

public class Test {

	public static void main(String[] args) {

		Mediator mediator = new CompanyMediator();

		Colleague c1 = new Colleague1();
		Colleague c2 = new Colleague2();

		mediator.register(c1);
		mediator.register(c2);
		
		c1.send();
		System.out.println("-----------");
		c2.send();
	}
	
}
