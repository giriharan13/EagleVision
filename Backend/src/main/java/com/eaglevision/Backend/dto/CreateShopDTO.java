package com.eaglevision.Backend.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eaglevision.Backend.model.Address;
import com.eaglevision.Backend.model.Hours;
import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.ShopCategory;
import com.eaglevision.Backend.model.ShopLocation;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class CreateShopDTO implements Serializable{

	private String shopName;

	private String description;

	private String contactNumber;

	private Address address;
	
	private Hours hours;

	private String userName;

	private List<Item> items;
	
	private ShopCategory shopCategory;

	private ShopLocation shopLocation;
	
	

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public ShopCategory getShopCategory() {
		return shopCategory;
	}

	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}

	public ShopLocation getShopLocation() {
		return shopLocation;
	}

	public void setShopLocation(ShopLocation shopLocation) {
		this.shopLocation = shopLocation;
	}


	
}
