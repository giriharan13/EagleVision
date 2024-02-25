package com.eaglevision.Backend.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"shopId","shopName"}))
public class Shop {
	
	@Id
	@GeneratedValue
	private Integer shopId;
	
	private String shopName;
	
	private String contactNumber;
	
	@ManyToOne
	private Vendor vendor;
	
	@OneToMany
	private List<ShopReview> shopReviews;
	
	@OneToMany
	private List<Item> items;
	
	@OneToOne
	private Address address;
	
	@OneToOne
	private Hours hours;
	
	public Shop() {
		super();
	}
	
	public Shop(String shopName, String contactNumber,Address address,Hours hours,Vendor vendor) {
		super();
		this.shopName = shopName;
		this.contactNumber = contactNumber;
		this.address = address;
		this.hours = hours;
		this.vendor = vendor;
	}

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

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<ShopReview> getShopReviews() {
		return shopReviews;
	}

	public void setShopReviews(List<ShopReview> shopReviews) {
		this.shopReviews = shopReviews;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
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

	public Integer getShopId() {
		return shopId;
	}
	
	public void addShopReview(ShopReview shopReview) {
		this.shopReviews.add(shopReview);
	}
	
	public void addItem(Item item) {
		this.items.add(item);
	}
}
