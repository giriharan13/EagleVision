package com.eaglevision.Backend.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	
	private String description;
	
	private String contactNumber;
	 
	@JsonBackReference(value="vendor-shop")
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="vendor_id")
	private Vendor vendor;
	
	@JsonManagedReference(value = "shop-review")
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "shop")
	private List<ShopReview> shopReviews = new ArrayList<ShopReview>();
	
	@JsonManagedReference(value = "shop-item")
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "shop") 
	private List<Item> items = new ArrayList<Item>();
	
	@JsonManagedReference(value="shop-address")
	@OneToOne(cascade = CascadeType.ALL) 
	private Address address;
	
	@JsonManagedReference(value="shop-hours")
	@OneToOne(cascade = CascadeType.ALL)
	private Hours hours;
	
	public Shop() {
		super();
	}
	
	public Shop(String shopName, String description ,String contactNumber,Address address,Hours hours,Vendor vendor) {
		super();
		this.shopName = shopName;
		this.description = description;
		this.contactNumber = contactNumber;
		this.address = address;
		address.setShop(this);
		this.hours = hours;
		hours.setShop(this);
		this.vendor = vendor;
		vendor.addShop(this);
	}
	
	public Shop(String shopName, String description, String contactNumber,Address address,Hours hours,List<Item> items,Vendor vendor) {
		super();
		this.shopName = shopName;
		this.description = description;
		this.contactNumber = contactNumber;
		this.address = address;
		this.hours = hours;
		this.items = items;
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
		item.setShop(this);
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void updateAddress(Address address) {
		this.address.setLine1(address.getLine1());
		this.address.setLine2(address.getLine2());
		this.address.setCity(address.getCity());
		this.address.setState(address.getState());
		this.address.setCountry(address.getCountry());
		this.address.setPinCode(address.getPinCode());
	}
	
	public void updateHours(Hours hours) {
		this.hours.setOpeningTime(hours.getOpeningTime());
		this.hours.setClosingTime(hours.getClosingTime());
	}
}
