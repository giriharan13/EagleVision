package com.eaglevision.Backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.ItemReview;
import com.eaglevision.Backend.model.Review;
import com.eaglevision.Backend.model.ShopReview;
import com.eaglevision.Backend.repository.ReviewRepository;

@Service
public class ReviewService {
	
	private ReviewRepository reviewRepository;
	
	@Autowired
	public ReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	public List<Review> getReviews(){
		return reviewRepository.findAll();
	}
	
	public Review getReviewById(Integer id) {
		return reviewRepository.findById(id).orElse(null);
	}
	
	public ShopReview getShopReviewById(Integer id) {
		return reviewRepository.findShopReviewByReviewId(id);
	}
	
	public ItemReview getItemReviewById(Integer id) {
		return reviewRepository.findItemReviewByReviewId(id);
	}
	
	public List<Review> getAllReviewsByUserId(Integer userId){
		return reviewRepository.findReviewByBuyer_userId(userId);
	}
	
	public List<ShopReview> getShopReviewsByUserId(Integer userId){
		return reviewRepository.findShopReviewsByBuyer_userId(userId);
	}
	
	
	public List<ItemReview> getItemReviewsByUserId(Integer userId){
		return reviewRepository.findItemReviewsByBuyer_userId(userId);
	}
	
	
	public List<ShopReview> getShopReviewsByShopId(Integer shopId){
		return reviewRepository.findByShop_shopId(shopId);
	}
	
	public List<ItemReview> getItemReviewsByItemId(Integer itemId){
		return reviewRepository.findByItem_itemId(itemId);
	}
	
	public ShopReview createShopReview(ShopReview shopReview) {
		return reviewRepository.save(shopReview);
	}
	
	public ItemReview createItemReview(ItemReview itemReview) {
		return reviewRepository.save(itemReview);
	}
	
	public Review updateReview(Review review) {
		return reviewRepository.save(review);
	}
	
	public ShopReview updateShopReview(Integer id,ShopReview updatedShopReview) {
		ShopReview shopReview = this.reviewRepository.findShopReviewByReviewId(id);
		shopReview.setComment(updatedShopReview.getComment());
		shopReview.setStars(updatedShopReview.getStars());
		shopReview.setIsEdited(true);
		return reviewRepository.save(shopReview);
	}
	
	public ItemReview updateItemReview(Integer id,ItemReview updatedItemReview) {
		ItemReview itemReview = this.reviewRepository.findItemReviewByReviewId(id);
		itemReview.setStars(updatedItemReview.getStars());
		itemReview.setIsEdited(true);
		return reviewRepository.save(itemReview);
	}
	
	public void deleteReviewById(Integer id) {
		reviewRepository.deleteById(id);
	}
}
