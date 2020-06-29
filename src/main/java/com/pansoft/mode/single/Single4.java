package com.pansoft.mode.single;

public class Single4 {
	private volatile static Single4 instance = null;
	
	private Single4(){}
	
	public static Single4 getInstance(){
		if(instance == null){
			synchronized (Single4.class) {
				if(instance == null){
					instance = new Single4();
				}
			}
		}
		return instance;
	}
}
