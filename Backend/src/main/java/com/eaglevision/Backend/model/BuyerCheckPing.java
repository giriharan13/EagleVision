package com.eaglevision.Backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="pingId")
public class BuyerCheckPing extends Ping{
	
	@ManyToOne
	private Buyer buyer;
	
	@ManyToOne
	private Item item;
	
	public BuyerCheckPing() {
		super();
	}
	
	public BuyerCheckPing(Buyer buyer,Item item) {
		super();
		this.buyer = buyer;
		this.item = item;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
