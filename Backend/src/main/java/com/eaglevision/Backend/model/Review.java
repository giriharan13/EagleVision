package com.eaglevision.Backend.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

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

	@Size(min = 5, max = 100)
	private String comment;
	
	@ManyToMany(mappedBy = "likedReviews")
	private Set<User> likes = new HashSet<User>();

	@ManyToMany(mappedBy = "dislikedReviews")
	private Set<User> dislikes = new HashSet<User>();

	@JsonBackReference(value = "buyer-review")
	@ManyToOne()
	@JoinColumn(name = "buyer_id")
	private Buyer buyer;

	Review() {
		super();
	}

	Review(Integer stars, String comment) {
		super();
		this.stars = stars;
		this.comment = comment;
	}

	Review(Integer stars, String comment, Boolean isEdited) {
		super();
		this.stars = stars;
		this.comment = comment;
		this.isEdited = isEdited;
	}

	Review(Integer stars, String comment, Boolean isEdited, Buyer buyer) {
		super();
		this.stars = stars;
		this.comment = comment;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Set<User> getLikes() {
		return likes;
	}

	public void setLikes(Set<User> likes) {
		this.likes = likes;
	}

	public Set<User> getDislikes() {
		return dislikes;
	}

	public void setDislikes(Set<User> dislikes) {
		this.dislikes = dislikes;
	}
	

}
