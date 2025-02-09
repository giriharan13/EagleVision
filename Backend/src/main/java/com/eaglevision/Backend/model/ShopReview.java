package com.eaglevision.Backend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "reviewId")
public class ShopReview extends Review {


	@JsonBackReference(value = "shop-review")
	@ManyToOne
	@JoinColumn(name = "shop_id", nullable = false)
	private Shop shop;

	public ShopReview() {
		super();
	}

	public ShopReview(Integer stars, Boolean isEdited, String comment, Buyer buyer,
			Shop shop) {
		super(stars, comment, isEdited, buyer);
		this.shop = shop;
	}

	public ShopReview(Integer stars, Boolean isEdited, String comment, Buyer buyer) {
		super(stars, comment, isEdited, buyer);
	}



	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	
}
