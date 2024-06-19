package com.eaglevision.Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "pingId")
public class VendorResponsePing extends Ping {

	private Integer quantity;

	@JsonBackReference(value = "vendor-ping")
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "vendor_id")
	private Vendor vendor;

	public VendorResponsePing() {
		super();
	}

	public VendorResponsePing(Integer quantity, Item item, Vendor vendor) {
		super(item);
		this.quantity = quantity;
		this.vendor = vendor;
		item.addItemPing(this);
		vendor.addVendorResponsePing(this);
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

}
