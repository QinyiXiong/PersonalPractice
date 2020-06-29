package com.pansoft.mode.mediator;

import java.util.ArrayList;
import java.util.List;

public class CompanyMediator extends Mediator{

	private List<Colleague> colleagueList = new ArrayList<Colleague>();

	@Override
	public void register(Colleague colleague) {
		if(!this.colleagueList.contains(colleague)){
			this.colleagueList.add(colleague);
			colleague.setMediator(this);
		}		
	}

	@Override
	public void relay(Colleague colleague) {
		
		for (Colleague tempColleague : colleagueList) {
			if(!tempColleague.equals(colleague)){
				tempColleague.receive();
			}
		}
				
	}
}
