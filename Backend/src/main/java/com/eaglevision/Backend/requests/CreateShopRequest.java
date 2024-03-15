package com.eaglevision.Backend.requests;

import java.util.List;

import com.eaglevision.Backend.model.Address;
import com.eaglevision.Backend.model.Hours;
import com.eaglevision.Backend.model.Item;

public class CreateShopRequest {
	
	private String shopName;
	
	private String contactNumber;
	
	private Address address;

	private Hours hours;
	
	private Integer userId;
	
	public List<Item> items;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Hours getHours() {
		return hours;
	}

	public void setHours(Hours hours) {
		this.hours = hours;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
