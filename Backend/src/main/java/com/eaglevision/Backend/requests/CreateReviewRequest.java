package com.eaglevision.Backend.requests;

public class CreateReviewRequest {
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
