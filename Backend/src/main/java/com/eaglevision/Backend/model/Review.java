package com.eaglevision.Backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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

}
