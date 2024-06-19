package com.eaglevision.Backend.dto;

public class CreateReviewDTO {
	private Integer userId;
	private Integer stars;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}
}
