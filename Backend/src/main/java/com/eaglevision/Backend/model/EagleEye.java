package com.eaglevision.Backend.model;

import java.time.ZonedDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class EagleEye {
	
	
	@Id
	@GeneratedValue
	private Integer eagleEyesId;
	
	
	private ZonedDateTime eagleEyesSetTime;
	
	@ManyToOne()
	@JoinColumn(name = "item_id")
	private Item item;
	
	@ManyToOne()
	@JoinColumn(name = "user_id")
	private Buyer buyer;
	
	public EagleEye() {
		super();
		this.eagleEyesSetTime = ZonedDateTime.now();
	}
	
	public EagleEye(Item item) {
		super();
		this.item=item;
		this.eagleEyesSetTime = ZonedDateTime.now();
	}

	public ZonedDateTime getEagleEyesSetTime() {
		return eagleEyesSetTime;
	}

	public void setEagleEyesSetTime(ZonedDateTime eagleEyesSetTime) {
		this.eagleEyesSetTime = eagleEyesSetTime;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getEagleEyesId() {
		return eagleEyesId;
	}

	public void setEagleEyesId(Integer eagleEyesId) {
		this.eagleEyesId = eagleEyesId;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	
	
	
	
}
