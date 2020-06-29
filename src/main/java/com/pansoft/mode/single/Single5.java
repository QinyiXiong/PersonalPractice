package com.pansoft.mode.single;

public class Single5 {

	private static class SingleHolder{
		private static Single5 instance = new Single5();
	}
	private Single5(){}
	
	public static Single5 getInstance(){
		return SingleHolder.instance;
	}
}
