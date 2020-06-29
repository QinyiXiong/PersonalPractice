package com.pansoft.mode.proxy;

public class Test {

	public static void main(String[] args) {

		ChinaBank chinaBankCard = new ChinaBank();

		ConstructionBank constructionBankCard = new ConstructionBank();

		BankProxy bankProxy = new BankProxy(chinaBankCard);
		
		bankProxy.getMoney();
		bankProxy.saveMoney(100.00);
		bankProxy.search();

		
	}
}
