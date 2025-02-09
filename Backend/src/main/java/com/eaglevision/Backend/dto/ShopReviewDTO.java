package com.eaglevision.Backend.dto;

import java.util.Set;

import com.eaglevision.Backend.model.ShopReview;
import com.eaglevision.Backend.model.User;

public class ShopReviewDTO {

    private Integer reviewId;

    private String comment;

    private Integer stars;
    
    private Integer likesCount, dislikesCount;

    private String authorUserName;

    private Integer authorUserId;
    
    private Boolean currentUserLiked;
    
    private Boolean currentUserDisliked;


    public ShopReviewDTO(ShopReview shopReview,Integer userId) {
        this.reviewId = shopReview.getReviewId();
        this.comment = shopReview.getComment();
        this.likesCount = shopReview.getLikes().size();
        this.dislikesCount = shopReview.getDislikes().size();
        this.stars = shopReview.getStars();
        this.authorUserName = shopReview.getBuyer().getUserName();
        this.authorUserId = shopReview.getBuyer().getUserId();
        this.currentUserLiked = shopReview.getLikes().stream().anyMatch((User user)->user.getUserId()==userId);
        this.currentUserDisliked = shopReview.getDislikes().stream().anyMatch((User user)->user.getUserId()==userId);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
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

	public String getAuthorUserName() {
        return authorUserName;
    }

    public void setAuthorUserName(String authorUserName) {
        this.authorUserName = authorUserName;
    }

    public Integer getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(Integer authorUserId) {
        this.authorUserId = authorUserId;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
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
    
    

}
