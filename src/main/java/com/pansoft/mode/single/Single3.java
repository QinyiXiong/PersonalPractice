package com.pansoft.mode.single;

public class Single3 {

	private static Single3 instance = new Single3();
	
	private Single3(){}
	
	public static Single3 getInstance(){
		return instance;
	}
}
