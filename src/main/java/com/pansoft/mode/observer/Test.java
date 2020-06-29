package com.pansoft.mode.observer;
/**
 * JAVA设计模式 ———— 观察者模式
 * 举例：
 * 1、中央空调控制每个房间的温度，而且每个房间都有自己的空调
 * 2、重要空调设置温度后，通知每个房间改变温度
 * @author liqin
 * 注意：
 * 1、注意区分与中介者模式的区别
 * 2、观察者更倾向于向下广播。
 * 3、中介者更倾向于转发。
 */
public class Test {

	public static void main(String[] args) {
		
		//创建主题房间
		CenterRoom centerRoom = new CenterRoom();
		//主题房间与其他房间关联(目前控制3个)
		Room1 room1 = new Room1(centerRoom);
		Room2 room2 = new Room2(centerRoom);
		Room3 room3 = new Room3(centerRoom);
		
		System.out.println("设定温度20度...");
		centerRoom.setTemperature(20);
		System.out.println("设定温度28度...");
		centerRoom.setTemperature(28);
		
		System.out.println("现在取消Room3的关联");
		centerRoom.deleteObserver(room3);
		System.out.println("重新设定温度20度...");
		centerRoom.setTemperature(20);
		
		
	}
}
