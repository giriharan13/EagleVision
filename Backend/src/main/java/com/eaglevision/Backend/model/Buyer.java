package com.eaglevision.Backend.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="userId")
public class Buyer extends Person{
	
	private List<BuyerCheckPing> pingHistory;
	
	public Buyer() {
		super();
	}
	
	public Buyer(String userName,String phoneNumber,Date dateOfBirth ) {
		super(userName,phoneNumber,dateOfBirth);
	}
	
	public Buyer(List<BuyerCheckPing> pingHistory) {
		super();
		this.pingHistory=pingHistory;
	}
	
	public Buyer(String userName,String phoneNumber,Date dateOfBirth,List<BuyerCheckPing> pingHistory ) {
		super(userName,phoneNumber,dateOfBirth);
		this.pingHistory = pingHistory;
	}

	public List<BuyerCheckPing> getPingHistory() {
		return pingHistory;
	}

	public void setPingHistory(List<BuyerCheckPing> pingHistory) {
		this.pingHistory = pingHistory;
	}
	
	public void addPing(BuyerCheckPing buyerCheckPing) {
		this.pingHistory.add(buyerCheckPing);
	}
	
	
}
