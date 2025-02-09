package com.eaglevision.Backend.dto;

import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import com.eaglevision.Backend.model.ItemReview;
import com.eaglevision.Backend.model.Review;
import com.eaglevision.Backend.model.ShopReview;
import com.eaglevision.Backend.model.User;

public class ReviewDTO {
	
	

	private Integer reviewId;


	private Integer stars;
	
	private Boolean isEdited;

	private String comment;
	
	private Integer likesCount;

	private Integer dislikesCount;

	private Integer authorUserId;
	
	private String authorUserName;
	
	private String profilePictureData;
	
	private Boolean currentUserLiked;
	
	private Boolean currentUserDisliked;
	
	private Integer shopId;
	
	private Integer itemId;

	
	
	public ReviewDTO(Review review,Integer userId) {
		this.reviewId=review.getReviewId();
		this.stars=review.getStars();
		this.comment=review.getComment();
		this.likesCount=review.getLikes().size();
		this.dislikesCount=review.getDislikes().size();
		this.authorUserId=review.getBuyer().getUserId();
		this.authorUserName=review.getBuyer().getUserName();
		if(review.getBuyer().getProfilePictureData()!=null)this.profilePictureData=Base64.getEncoder().encodeToString(review.getBuyer().getProfilePictureData());
		this.currentUserLiked=review.getLikes().stream().anyMatch((User user)-> user.getUserId().equals(userId));
		this.currentUserDisliked=review.getDislikes().stream().anyMatch((User user)-> user.getUserId().equals(userId));
		if(review instanceof ShopReview) {
			ShopReview shopReview = (ShopReview) review;
			shopId = shopReview.getShop().getShopId();
		}
		else if(review instanceof ItemReview) {
			ItemReview itemReview = (ItemReview) review;
			shopId = itemReview.getItem().getShop().getShopId();
			itemId = itemReview.getItem().getItemId();
		}
	}



	public Integer getReviewId() {
		return reviewId;
	}



	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
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



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}


	public Integer getLikesCount() {
		return likesCount;
	}



	public void setLikesCount(Integer likesCount) {
		this.likesCount = likesCount;
	}



	public Integer getDislikesCount() {
		return dislikesCount;
	}



	public void setDislikesCount(Integer dislikesCount) {
		this.dislikesCount = dislikesCount;
	}



	public Integer getAuthorUserId() {
		return authorUserId;
	}



	public void setAuthorUserId(Integer authorUserId) {
		this.authorUserId = authorUserId;
	}



	public String getAuthorUserName() {
		return authorUserName;
	}



	public void setAuthorUserName(String authorUserName) {
		this.authorUserName = authorUserName;
	}



	public Boolean getCurrentUserLiked() {
		return currentUserLiked;
	}



	public void setCurrentUserLiked(Boolean currentUserLiked) {
		this.currentUserLiked = currentUserLiked;
	}



	public Boolean getCurrentUserDisliked() {
		return currentUserDisliked;
	}



	public void setCurrentUserDisliked(Boolean currentUserDisliked) {
		this.currentUserDisliked = currentUserDisliked;
	}



	public String getProfilePictureData() {
		return profilePictureData;
	}



	public void setProfilePictureData(String profilePictureData) {
		this.profilePictureData = profilePictureData;
	}



	public Integer getShopId() {
		return shopId;
	}



	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}



	public Integer getItemId() {
		return itemId;
	}



	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	
	
	
	

}
