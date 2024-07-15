package com.eaglevision.Backend.dto;

import com.eaglevision.Backend.model.ShopReview;

public class ShopReviewDTO {

    private Integer reviewId;

    private String comment;

    private Integer stars, likes, dislikes;

    private String authorUserName;

    private Integer authorUserId;

    public ShopReviewDTO(String comment, Integer stars, Integer likes, Integer dislikes, String authorUserName,
            Integer authorUserId) {
        this.comment = comment;
        this.stars = stars;
        this.likes = likes;
        this.dislikes = dislikes;
        this.authorUserName = authorUserName;
        this.authorUserId = authorUserId;
    }

    public ShopReviewDTO(ShopReview shopReview) {
        this.reviewId = shopReview.getReviewId();
        this.comment = shopReview.getComment();
        this.stars = shopReview.getStars();
        this.likes = shopReview.getLikes();
        this.dislikes = shopReview.getDislikes();
        this.authorUserName = shopReview.getBuyer().getUserName();
        this.authorUserId = shopReview.getBuyer().getUserId();
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

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
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

}
