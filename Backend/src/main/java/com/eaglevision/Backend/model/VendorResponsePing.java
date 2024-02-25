package com.eaglevision.Backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="pingId")
public class VendorResponsePing {
	
	private Integer quantity;
	
	private Boolean isAvailable;
	
	@OneToOne
	private Item item;
	
	@OneToOne
	private Vendor vendor;
	
	public VendorResponsePing() {
		super();
	}
	
	public VendorResponsePing(Integer quantity,Boolean isAvailable) {
		super();
		this.quantity = quantity;
		this.isAvailable = isAvailable;
	}
	
	public VendorResponsePing(Integer quantity,Boolean isAvailable,Item item,Vendor vendor) {
		super();
		this.quantity = quantity;
		this.isAvailable = isAvailable;
		this.item = item;
		this.vendor = vendor;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
}
