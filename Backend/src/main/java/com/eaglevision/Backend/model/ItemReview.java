package com.eaglevision.Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Transient;

@Entity
@PrimaryKeyJoinColumn(name = "reviewId")
public class ItemReview extends Review {

	@JsonBackReference(value = "item-review")
	@ManyToOne()
	@JoinColumn(name = "item_id")
	private Item item;

	public ItemReview() {
		super();
	}

	public ItemReview(Integer stars, String comment) {
		super(stars, comment);
	}

	public ItemReview(Integer stars, String comment, Boolean isEdited) {
		super(stars, comment, isEdited);
	}

	public ItemReview(Integer stars, String comment, Boolean isEdited, Item item) {
		super(stars, comment, isEdited);
		this.item = item;
	}

	public ItemReview(Integer stars, String comment, Boolean isEdited, Item item, Buyer buyer) {
		super(stars, comment, isEdited, buyer);
		item.addItemReview(this);
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
