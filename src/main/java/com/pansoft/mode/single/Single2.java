package com.pansoft.mode.single;

public class Single2 {

	private static Single2 instance = null;
	
	private Single2(){}
	
	synchronized public static Single2 getInstance(){
		if(instance == null){
			instance = new Single2();
		}
		return instance;
	}
}
