package com.eaglevision.Backend.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "itemId", "itemName" }))
public class Item {
	@Id
	@GeneratedValue
	private Integer itemId;

	private String itemName;

	private String itemDescription;

	private Double itemPrice;

	@JsonManagedReference(value = "item-ping")
	@OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
	private List<Ping> pingHistory = new ArrayList<Ping>();

	@JsonManagedReference(value = "item-review")
	@OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
	private List<ItemReview> itemReviews = new ArrayList<ItemReview>();

	@JsonBackReference(value = "shop-item")
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "shop_id")
	private Shop shop;
	
	
	@OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
	private List<EagleEye> eagleEyes = new ArrayList<EagleEye>();
	
	private String itemImageName;
	
	private String itemImageType;
	
	
	// Not a good idea but ok for now 
	@Lob
	@JsonIgnore
	private byte[] itemImageData;
	
	@Transient
	private String itemImageDataB64;

	public Item() {
		super();
	}

	public Item(String itemName, String itemDescription, Double itemPrice, List<Ping> pingHistory,
			List<ItemReview> itemReviews, Shop shop) {
		super();
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.itemPrice = itemPrice;
		this.pingHistory = pingHistory;
		this.itemReviews = itemReviews;
		this.shop = shop;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public Integer getItemId() {
		return itemId;
	}

	public List<Ping> getPingHistory() {
		return pingHistory;
	}

	public void setPingHistory(List<Ping> pingHistory) {
		this.pingHistory = pingHistory;
	}

	public List<ItemReview> getItemReviews() {
		return itemReviews;
	}

	public void setItemReviews(List<ItemReview> itemReviews) {
		this.itemReviews = itemReviews;
	}

	public void addItemReview(ItemReview itemReview) {
		this.itemReviews.add(itemReview);
	}

	public void addItemPing(Ping ping) {
		this.pingHistory.add(ping);
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<EagleEye> getEagleEyes() {
		return eagleEyes;
	}

	public void setEagleEyes(List<EagleEye> eagleEyes) {
		this.eagleEyes = eagleEyes;
	}

	public String getItemImageName() {
		return itemImageName;
	}

	public void setItemImageName(String itemImageName) {
		this.itemImageName = itemImageName;
	}

	public String getItemImageType() {
		return itemImageType;
	}

	public void setItemImageType(String itemImageType) {
		this.itemImageType = itemImageType;
	}

	public byte[] getItemImageData() {
		return itemImageData;
	}

	public void setItemImageData(byte[] itemImageData) {
		this.itemImageData = itemImageData;
	}

	public String getItemImageDataB64() {
		return itemImageDataB64;
	}

	public void setItemImageDataB64(String itemImageDataB64) {
		this.itemImageDataB64 = itemImageDataB64;
	}
	
	
	
	
	
}
