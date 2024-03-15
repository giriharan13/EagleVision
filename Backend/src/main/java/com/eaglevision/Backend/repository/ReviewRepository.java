package com.eaglevision.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eaglevision.Backend.model.ItemReview;
import com.eaglevision.Backend.model.Review;
import com.eaglevision.Backend.model.ShopReview;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	
	ItemReview findItemReviewByReviewId(Integer id);
	
	ShopReview findShopReviewByReviewId(Integer id);
	
	public List<Review> findReviewByBuyer_userId(Integer userId);  
	
	public List<ShopReview> findByShop_shopId(Integer shopId);
	
	public List<ItemReview> findByItem_itemId(Integer itemId);
	
	public List<ShopReview> findShopReviewsByBuyer_userId(Integer userId);
	
	public List<ItemReview> findItemReviewsByBuyer_userId(Integer userId);
}
