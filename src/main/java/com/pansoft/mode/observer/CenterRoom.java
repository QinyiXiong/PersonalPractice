package com.pansoft.mode.observer;

import java.util.Observable;

/**
 * Java���ģʽ �������� �۲���ģʽ
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
