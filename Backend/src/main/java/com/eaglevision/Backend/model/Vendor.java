package com.eaglevision.Backend.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="userId")
public class Vendor extends Person{
	
	@OneToMany
	List<Shop> shops;
	
	public Vendor() {
		super();
	}
	
	public Vendor(List<Shop> shops) {
		this.shops = shops;
	}

	public List<Shop> getShops() {
		return shops;
	}

	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}
	
	public void addShop(Shop shop) {
		this.shops.add(shop);
	}
	
}
