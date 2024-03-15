package com.eaglevision.Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "reviewId")
public class ItemReview extends Review{
	
	@JsonBackReference(value="item-review")
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="item_id",nullable = false)
	private Item item;
	
	public ItemReview() {
		super();
	}
	
	public ItemReview(Integer stars) {
		super(stars);
	}
	
	public ItemReview(Integer stars,Boolean isEdited) {
		super(stars,isEdited);
	}
	 
	public ItemReview(Integer stars,Boolean isEdited,Item item) {
		super(stars,isEdited);
		this.item = item;
	}
	
	public ItemReview(Integer stars,Boolean isEdited,Item item,Buyer buyer) {
		super(stars,isEdited,buyer);
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
