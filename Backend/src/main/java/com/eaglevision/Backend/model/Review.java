package com.eaglevision.Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Review {
	
	@Id
	@GeneratedValue
	private Integer reviewId;
	
	@Min(value = 1)
	@Max(value = 5)
	private Integer stars;
	
	@Column(columnDefinition = "boolean default false")
	private Boolean isEdited;
	
	@JsonBackReference(value = "buyer-review")
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "buyer_id")
	private Buyer buyer;
	
	Review(){
		super();
	}
	
	Review(Integer stars){
		super();
		this.stars = stars;
	}
	
	Review(Integer stars,Boolean isEdited){
		super();
		this.stars = stars;
		this.isEdited = isEdited;
	}
	
	Review(Integer stars,Boolean isEdited,Buyer buyer){
		super();
		this.stars = stars;
		this.isEdited = isEdited;
		this.buyer = buyer;
		buyer.addReview(this);
	}
	
	public Integer getReviewId() {
		return reviewId;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public Boolean getIsEdited() {
		return isEdited;
	}

	public void setIsEdited(Boolean isEdited) {
		this.isEdited = isEdited;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	
}
