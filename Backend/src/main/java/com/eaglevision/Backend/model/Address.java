package com.eaglevision.Backend.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

public class Address {
	
	@Id
	@GeneratedValue
	private Integer addressId;
	
	private String line1;
	
	private String line2;
	
	private String city;

	private String state;
	
	private String country;
	
	private Integer pinCode;
	
	@OneToOne
	private Shop shop;
	
	public Address() {
		super();
	}
	
	public Address(String line1,String line2,String city,String state,String country,Integer pinCode) {
		super();
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pinCode = pinCode;
	}
	
	public Address(String line1,String line2,String city,String state,String country,Integer pinCode,Shop shop) {
		super();
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pinCode = pinCode;
		this.shop = shop;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getPinCode() {
		return pinCode;
	}

	public void setPinCode(Integer pinCode) {
		this.pinCode = pinCode;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Integer getAddressId() {
		return addressId;
	}
}
