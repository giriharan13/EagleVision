package com.eaglevision.Backend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Vendor extends User {

	@JsonManagedReference(value = "vendor-shop")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vendor",fetch=FetchType.LAZY)
	private List<Shop> shops = new ArrayList<Shop>();

	@JsonManagedReference(value = "vendor-ping")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vendor",fetch=FetchType.LAZY)
	private List<VendorResponsePing> vendorResponsePings = new ArrayList<VendorResponsePing>();

	private Boolean optedForTelegramNotifications;

	private String telegramUserId;

	public Vendor() {
		super();
		this.optedForTelegramNotifications = false;
	}

	public Vendor(List<Shop> shops) {
		this.shops = shops;
		this.optedForTelegramNotifications = false;
	}

	public Vendor(String userName, String phoneNumber, Date dateOfBirth) {
		super(userName, phoneNumber, dateOfBirth);
		this.optedForTelegramNotifications = false;
	}

	public Vendor(String username, String password, String phoneNumber, Date dateOfBirth, List<Role> roles) {
		super(username, password, phoneNumber, dateOfBirth, roles);
		this.optedForTelegramNotifications = false;
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

	public Boolean getOptedForTelegramNotifications() {
		return optedForTelegramNotifications;
	}

	public void setOptedForTelegramNotifications(Boolean optedForTelegramNotifications) {
		this.optedForTelegramNotifications = optedForTelegramNotifications;
	}

	public String getTelegramUserId() {
		return telegramUserId;
	}

	public void setTelegramUserId(String telegramUserId) {
		this.telegramUserId = telegramUserId;
	}

}
