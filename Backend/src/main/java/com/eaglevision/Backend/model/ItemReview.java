package com.eaglevision.Backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "reviewId")
public class ItemReview extends Review{
	
	@ManyToOne
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
