package com.eaglevision.Backend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Vendor extends User {

	@JsonManagedReference(value = "vendor-shop")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vendor")
	private List<Shop> shops = new ArrayList<Shop>();

	@JsonManagedReference(value = "vendor-ping")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vendor")
	private List<VendorResponsePing> vendorResponsePings = new ArrayList<VendorResponsePing>();

	public Vendor() {
		super();
	}

	public Vendor(List<Shop> shops) {
		this.shops = shops;
	}

	public Vendor(String userName, String phoneNumber, Date dateOfBirth) {
		super(userName, phoneNumber, dateOfBirth);
	}

	public Vendor(String username, String password, String phoneNumber, Date dateOfBirth, List<Role> roles) {
		super(username, password, phoneNumber, dateOfBirth, roles);
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

	public List<VendorResponsePing> getVendorResponsePings() {
		return vendorResponsePings;
	}

	public void setVendorResponsePings(List<VendorResponsePing> vendorResponsePings) {
		this.vendorResponsePings = vendorResponsePings;
	}

	public void addVendorResponsePing(VendorResponsePing vendorResponsePing) {
		this.vendorResponsePings.add(vendorResponsePing);
	}

}
