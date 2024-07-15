package com.eaglevision.Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "reviewId")
public class ShopReview extends Review {

	@Column(columnDefinition = "int default 0")
	private Integer likes;

	@Column(columnDefinition = "int default 0")
	private Integer dislikes;

	@JsonBackReference(value = "shop-review")
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "shop_id", nullable = false)
	private Shop shop;

	public ShopReview() {
		super();
	}

	public ShopReview(Integer stars, Boolean isEdited, String comment, Integer likes, Integer dislikes, Buyer buyer,
			Shop shop) {
		super(stars, comment, isEdited, buyer);
		this.likes = likes;
		this.dislikes = dislikes;
		this.shop = shop;
	}

	public ShopReview(Integer stars, Boolean isEdited, String comment, Integer likes, Integer dislikes, Buyer buyer) {
		super(stars, comment, isEdited, buyer);
		this.likes = likes;
		this.dislikes = dislikes;
	}

	public ShopReview(Integer stars, Boolean isEdited, String comment, Integer likes, Integer dislikes) {
		super(stars, comment, isEdited);
		this.likes = likes;
		this.dislikes = dislikes;
	}

	public ShopReview(Integer stars, String comment, Integer likes, Integer dislikes) {
		super(stars, comment);
		this.likes = likes;
		this.dislikes = dislikes;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Integer getDislikes() {
		return dislikes;
	}

	public void setDislikes(Integer dislikes) {
		this.dislikes = dislikes;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
}
