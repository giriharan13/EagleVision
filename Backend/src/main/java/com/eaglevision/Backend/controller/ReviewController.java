package com.eaglevision.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.ItemReview;
import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.model.ShopReview;
import com.eaglevision.Backend.requests.CreateReviewRequest;
import com.eaglevision.Backend.requests.CreateShopReviewRequest;
import com.eaglevision.Backend.service.ReviewService;
import com.eaglevision.Backend.service.bridge.BuyerReviewService;
import com.eaglevision.Backend.service.bridge.ItemReviewService;
import com.eaglevision.Backend.service.bridge.ShopReviewService;

@RestController
@RequestMapping(value = "/")
public class ReviewController {
	
	private ReviewService reviewService;
	
	private BuyerReviewService buyerReviewService;
	
	private ShopReviewService shopReviewService;
	
	private ItemReviewService itemReviewService;
	
	@Autowired
	public ReviewController(ReviewService reviewService,BuyerReviewService buyerReviewService,ShopReviewService shopReviewService,ItemReviewService itemReviewService) {
		this.reviewService = reviewService;
		this.buyerReviewService = buyerReviewService;
		this.shopReviewService = shopReviewService;
		this.itemReviewService = itemReviewService;
	}
	
	@PostMapping(value = "shops/{shopId}/reviews")
	public ResponseEntity<ShopReview> createShopReview(@PathVariable Integer shopId,@RequestBody CreateShopReviewRequest createShopReviewRequest) {
		Shop shop = this.shopReviewService.getShopById(shopId);
		ShopReview shopReview = this.buyerReviewService.createShopReview(createShopReviewRequest,shop);
		shopReview = reviewService.createShopReview(shopReview);
		return ResponseEntity.ok(shopReview);
	}
	
	@PostMapping(value = "shops/{shopId}/items/{itemId}/reviews")
	public ResponseEntity<ItemReview> createItemReview(@PathVariable Integer shopId,@PathVariable Integer itemId, @RequestBody CreateReviewRequest createReviewRequest) {
		Item item = this.itemReviewService.getItemForReview(itemId);
		ItemReview itemReview = this.buyerReviewService.createItemReview(createReviewRequest, item);
		return ResponseEntity.ok(reviewService.createItemReview(itemReview));
		
	}
	 
	@GetMapping(value = "shops/{shopId}/reviews")
	public List<ShopReview> getAllShopReviews(@PathVariable Integer shopId) {
		return reviewService.getShopReviewsByShopId(shopId);
	}
	
	@GetMapping(value = "shops/{shopId}/items/{itemId}/reviews")
	public List<ItemReview> getAllShopReviews(@PathVariable Integer shopId,@PathVariable Integer itemId) {
		return reviewService.getItemReviewsByItemId(itemId);
	}
	
	@GetMapping(value = "users/buyers/{userId}/reviews/itemreviews")
	public List<ItemReview> getItemReviewsByUserId(@PathVariable Integer userId){
		return reviewService.getItemReviewsByUserId(userId);
	}
	
	@GetMapping(value = "users/buyers/{userId}/reviews/shopreviews")
	public List<ShopReview> getShopReviewsByUserId(@PathVariable Integer userId){
		return reviewService.getShopReviewsByUserId(userId);
	}
	
	@PutMapping(value = "shops/{shopId}/reviews/{reviewId}")
	public ResponseEntity<ShopReview> updateShopReview(@PathVariable Integer shopId,@PathVariable Integer reviewId,@RequestBody ShopReview updatedShopReview) {
		ShopReview shopReview = this.reviewService.updateShopReview(reviewId,updatedShopReview);
		return ResponseEntity.ok(shopReview);
	}
	
	@PutMapping(value = "shops/{shopId}/items/{itemId}/reviews/{reviewId}")
	public ResponseEntity<ItemReview> updateItemReview(@PathVariable Integer shopId,@PathVariable Integer itemId,@PathVariable Integer reviewId, @RequestBody ItemReview updatedItemReview) {
		ItemReview itemReview = this.reviewService.updateItemReview(reviewId, updatedItemReview);
		return ResponseEntity.ok(itemReview);
	}
	
	@DeleteMapping(value = "shops/{shopId}/reviews/{reviewId}")
	public ResponseEntity<String> deleteShopReview(@PathVariable Integer shopId,@PathVariable Integer reviewId){
		this.reviewService.deleteReviewById(reviewId);
		return ResponseEntity.ok("Review deleted sucessfully!");
	}
	
	@DeleteMapping(value = "shops/{shopId}/items/{itemId}/reviews/{reviewId}")
	public ResponseEntity<String> deleteItemReview(@PathVariable Integer shopId,@PathVariable Integer itemId,@PathVariable Integer reviewId){
		this.reviewService.deleteReviewById(reviewId);
		return ResponseEntity.ok("Review deleted sucessfully!");
	}	
	
}
