package com.pansoft.mode.observer;
/**
 * JAVA���ģʽ �������� �۲���ģʽ
 * ������
 * 1������յ�����ÿ��������¶ȣ�����ÿ�����䶼���Լ��Ŀյ�
 * 2����Ҫ�յ������¶Ⱥ�֪ͨÿ������ı��¶�
 * @author liqin
 * ע�⣺
 * 1��ע���������н���ģʽ������
 * 2���۲��߸����������¹㲥��
 * 3���н��߸�������ת����
 */
public class Test {

	public static void main(String[] args) {
		
		//�������ⷿ��
		CenterRoom centerRoom = new CenterRoom();
		//���ⷿ���������������(Ŀǰ����3��)
		Room1 room1 = new Room1(centerRoom);
		Room2 room2 = new Room2(centerRoom);
		Room3 room3 = new Room3(centerRoom);
		
		System.out.println("�趨�¶�20��...");
		centerRoom.setTemperature(20);
		System.out.println("�趨�¶�28��...");
		centerRoom.setTemperature(28);
		
		System.out.println("����ȡ��Room3�Ĺ���");
		centerRoom.deleteObserver(room3);
		System.out.println("�����趨�¶�20��...");
		centerRoom.setTemperature(20);
		
		
	}
}
