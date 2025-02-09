package com.eaglevision.Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "pingId")
public class BuyerCheckPing extends Ping {

	@JsonBackReference(value = "buyer-ping")
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "buyer_id")
	private Buyer buyer;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "response_id")
	private VendorResponsePing vendorResponsePing;


	public BuyerCheckPing() {
		super();
	}

	public BuyerCheckPing(Buyer buyer, Item item) {
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

	public VendorResponsePing getVendorResponsePing() {
		return vendorResponsePing;
	}

	public void setVendorResponsePing(VendorResponsePing vendorResponsePing) {
		this.vendorResponsePing = vendorResponsePing;
	}


}
