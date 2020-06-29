package com.pansoft.mode.proxy;

public class BankProxy implements Bank{

	private Bank bank;
	
	public BankProxy(Bank bank){
		this.bank = bank;
	}
	@Override
	public void saveMoney(Double money) {
		// TODO Auto-generated method stub
		bank.saveMoney(money);
	}

	@Override
	public void search() {
		// TODO Auto-generated method stub
		bank.search();
	}

	@Override
	public Double getMoney() {
		// TODO Auto-generated method stub
		return bank.getMoney();
	}

	
}
