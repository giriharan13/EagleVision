package com.eaglevision.Backend.dto;

public class CreateShopReviewDTO extends CreateItemReviewDTO {
	private String comment;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
