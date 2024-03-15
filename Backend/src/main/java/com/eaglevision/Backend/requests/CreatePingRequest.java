package com.eaglevision.Backend.requests;

import com.eaglevision.Backend.model.Buyer;
import com.eaglevision.Backend.model.Vendor;

public class CreatePingRequest {
	
	private Integer type;
	
	private Integer userId;
	
	private Buyer buyer;
	
	private Vendor vendor;
	
	private Integer quantity;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
