package com.eaglevision.Backend.model;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Hours {
	
	@Id
	@GeneratedValue
	private Integer hoursId;
	
	private LocalTime openingTime;
	
	private LocalTime closingTime;
	
	@JsonBackReference(value="shop-hours")
	@OneToOne(cascade = CascadeType.PERSIST)
	private Shop shop;
	
	public Hours() {
		super();
	}
	
	public Hours(LocalTime openingTime,LocalTime closingTime) {
		super();
		this.openingTime = openingTime;
		this.closingTime = closingTime;
	}
	
	public Hours(LocalTime openingTime,LocalTime closingTime,Shop shop) {
		super();
		this.openingTime = openingTime;
		this.closingTime = closingTime;
		this.shop = shop;
	}

	public LocalTime getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(LocalTime openingTime) {
		this.openingTime = openingTime;
	}

	public LocalTime getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(LocalTime closingTime) {
		this.closingTime = closingTime;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
}
