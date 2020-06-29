package com.pansoft.mode.observer;

import java.util.Observable;

/**
 * Java设计模式 ———— 观察者模式
 * @author liqin
 *
 */
public class CenterRoom extends Observable{
	
	private float temperature;
	
	public void setTemperature(float temperature){
		
		this.temperature = temperature;
		setChanged();
		notifyObservers();
	}
	
	public float getTemperature(){
		return this.temperature;
	}

}
