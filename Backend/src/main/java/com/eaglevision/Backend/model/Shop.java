package com.eaglevision.Backend.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.bind.DefaultValue;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "shopId", "shopName" }))
public class Shop {

	@Id
	@GeneratedValue
	private Integer shopId;

	private String shopName;

	private String description;

	private String contactNumber;

	@JsonBackReference(value = "vendor-shop")
	@ManyToOne
	@JoinColumn(name = "vendor_id")
	private Vendor vendor;

	@JsonIgnore
	@JsonManagedReference(value = "shop-review")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "shop",orphanRemoval = true)
	private List<ShopReview> shopReviews = new ArrayList<ShopReview>();

	@JsonIgnore
	@JsonManagedReference(value = "shop-item")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "shop")
	private List<Item> items = new ArrayList<Item>();

	@JsonManagedReference(value = "shop-address")
	@OneToOne(cascade = CascadeType.ALL)
	private Address address;

	@JsonManagedReference(value = "shop-hours")
	@OneToOne(cascade = CascadeType.ALL)
	private Hours hours;
	
	//@Enumerated(EnumType.STRING)
	private ShopCategory shopCategory;
	
	@JsonManagedReference(value="shop-location")
	@OneToOne(cascade = CascadeType.ALL)
	private ShopLocation shopLocation;
	
	private String imageName;
	
	private String imageType;
	
	
	// Not a good idea but ok for now 
	@Lob
	@JsonIgnore
	private byte[] shopImageData;
	
	@Transient
	private String shopImageDataB64;
	
	
	private String markerImageName;
	
	private String markerImageType;
	
	
	// Not a good idea but ok for now 
	@Lob
	@JsonIgnore
	private byte[] markerImageData;
	
	@Transient
	private String markerImageDataB64;

	public Shop() {
		super();
	}

	public Shop(String shopName, String description, String contactNumber, Address address, Hours hours,
			Vendor vendor,ShopCategory shopCategory,ShopLocation shopLocation) {
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
		this.shopCategory = shopCategory;
		this.shopLocation = shopLocation;
		shopLocation.setShop(this);
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

	public void updateAddress(Address address) {
		this.address.setLine1(address.getLine1());
		this.address.setLine2(address.getLine2());
		this.address.setCity(address.getCity());
		this.address.setState(address.getState());
		this.address.setCountry(address.getCountry());
		this.address.setPincode(address.getPincode());
	}

	public void updateHours(Hours hours) {
		this.hours.setOpeningTime(hours.getOpeningTime());
		this.hours.setClosingTime(hours.getClosingTime());
	}
	
	public void updateShopLocation(ShopLocation shopLocation) {
		this.shopLocation.setLattitude(shopLocation.getLattitude());
		this.shopLocation.setLongitude(shopLocation.getLongitude());
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public byte[] getShopImageData() {
		return shopImageData;
	}

	public void setShopImageData(byte[] shopImageData) {
		this.shopImageData = shopImageData;
	}

	public String getShopImageDataB64() {
		return shopImageDataB64;
	}

	public void setShopImageDataB64(String shopImageDataB64) {
		this.shopImageDataB64 = shopImageDataB64;
	}

	public String getMarkerImageName() {
		return markerImageName;
	}

	public void setMarkerImageName(String markerImageName) {
		this.markerImageName = markerImageName;
	}

	public String getMarkerImageType() {
		return markerImageType;
	}

	public void setMarkerImageType(String markerImageType) {
		this.markerImageType = markerImageType;
	}

	public byte[] getMarkerImageData() {
		return markerImageData;
	}

	public void setMarkerImageData(byte[] markerImageData) {
		this.markerImageData = markerImageData;
	}

	public String getMarkerImageDataB64() {
		return markerImageDataB64;
	}

	public void setMarkerImageDataB64(String markerImageDataB64) {
		this.markerImageDataB64 = markerImageDataB64;
	}
	
}
