package com.eaglevision.Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="pingId")
public class BuyerCheckPing extends Ping{
	
	@JsonBackReference(value="buyer-ping")
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="buyer_id")
	private Buyer buyer;
	
	public BuyerCheckPing() {
		super();
	}
	
	public BuyerCheckPing(Buyer buyer,Item item) {
		super(item);
		this.buyer = buyer;
		item.addItemPing(this);
		buyer.addBuyerCheckPing(this);
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

}
