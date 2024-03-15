package com.eaglevision.Backend.requests;

public class CreateShopReviewRequest extends CreateReviewRequest {
	private String comment;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
