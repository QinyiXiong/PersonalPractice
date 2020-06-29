package com.pansoft.mode.observer;

import java.util.Observable;
import java.util.Observer;

public class Room2 implements Observer{

	private Observable observable;
	private float temperature;
	
	public Room2(Observable observable){
		this.observable = observable;
		this.observable.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if(o instanceof CenterRoom){
			CenterRoom centerRoom = (CenterRoom) o;
			this.temperature = centerRoom.getTemperature();
			System.out.println("Room2的温度现在是："+this.temperature);
		}
		
	}

}
