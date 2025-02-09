package com.eaglevision.Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class ShopLocation {
	
	@Id
	@GeneratedValue
	private Integer locationId;
	
	private Double lattitude;
	
	private Double longitude;
	
	@JsonBackReference(value="shop-location")
	@OneToOne(cascade = CascadeType.PERSIST)
	private Shop shop;
	
	public ShopLocation() {
		
	}
	
	public ShopLocation(Integer locationId, Double lattitude, Double longitude,Shop shop) {
		super();
		this.locationId = locationId;
		this.lattitude = lattitude;
		this.longitude = longitude;
		this.shop = shop;
	}



	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Double getLattitude() {
		return lattitude;
	}

	public void setLattitude(Double lattitude) {
		this.lattitude = lattitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	
	
}
