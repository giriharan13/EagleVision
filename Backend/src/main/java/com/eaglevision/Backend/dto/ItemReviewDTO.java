package com.eaglevision.Backend.dto;

import com.eaglevision.Backend.model.ItemReview;

public class ItemReviewDTO {

    private Integer reviewId;

    private String comment;

    private Integer stars;

    private String authorUserName;

    private Integer authorUserId;

    public ItemReviewDTO(String comment, Integer stars, String authorUserName,
            Integer authorUserId) {
        this.comment = comment;
        this.stars = stars;
        this.authorUserName = authorUserName;
        this.authorUserId = authorUserId;
    }

    public ItemReviewDTO(ItemReview itemReview) {
        this.reviewId = itemReview.getReviewId();
        this.comment = itemReview.getComment();
        this.stars = itemReview.getStars();
        this.authorUserName = itemReview.getBuyer().getUserName();
        this.authorUserId = itemReview.getBuyer().getUserId();
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
