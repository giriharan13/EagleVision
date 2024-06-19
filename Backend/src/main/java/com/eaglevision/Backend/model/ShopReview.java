package com.eaglevision.Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.Size;

@Entity
@PrimaryKeyJoinColumn(name = "reviewId")
public class ShopReview extends Review {

	@Size(min = 5, max = 100)
	private String comment;

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
		super(stars, isEdited, buyer);
		this.comment = comment;
		this.likes = likes;
		this.dislikes = dislikes;
		this.shop = shop;
	}

	public ShopReview(Integer stars, Boolean isEdited, String comment, Integer likes, Integer dislikes, Buyer buyer) {
		super(stars, isEdited, buyer);
		this.comment = comment;
		this.likes = likes;
		this.dislikes = dislikes;
	}

	public ShopReview(Integer stars, Boolean isEdited, String comment, Integer likes, Integer dislikes) {
		super(stars, isEdited);
		this.comment = comment;
		this.likes = likes;
		this.dislikes = dislikes;
	}

	public ShopReview(Integer stars, String comment, Integer likes, Integer dislikes) {
		super(stars);
		this.comment = comment;
		this.likes = likes;
		this.dislikes = dislikes;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
