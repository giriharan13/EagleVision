package com.eaglevision.Backend.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"itemId","itemName"}))
public class Item {
	@Id
	@GeneratedValue
	private Integer itemId;
	
	private String itemName;
	
	private Double itemPrice;
	
	@OneToMany
	private List<Ping> pingHistory;
	
	@OneToMany
	private List<ItemReview> itemReviews;
	
	public Item() {
		super();
	}
	
	public Item(String itemName,Double itemPrice) {
		super();
		this.itemName = itemName;
		this.itemPrice = itemPrice;
	}
	
	public Item(String itemName,Double itemPrice,List<Ping> pingHistory,List<ItemReview> itemReviews) {
		super();
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.pingHistory = pingHistory;
		this.itemReviews = itemReviews;
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
}
