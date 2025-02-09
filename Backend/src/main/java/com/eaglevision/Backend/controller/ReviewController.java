package com.eaglevision.Backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eaglevision.Backend.dto.CreateItemReviewDTO;
import com.eaglevision.Backend.dto.CreateShopReviewDTO;
import com.eaglevision.Backend.model.ItemReview;
import com.eaglevision.Backend.model.ShopReview;
import com.eaglevision.Backend.service.ReviewService;

@RestController
@RequestMapping({ "/api/bot", "api/secure" })
public class ReviewController {

	private ReviewService reviewService;

	@Autowired
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	

//	@PostMapping(value = "shops/{shopId}/reviews")
//	public ResponseEntity<ShopReview> createShopReview(@PathVariable Integer shopId,
//			@RequestBody CreateShopReviewDTO createShopReviewRequest) {
//		
//		Shop shop = this.shopReviewService.getShopById(shopId);
//		ShopReview shopReview = this.buyerReviewService.createShopReview(createShopReviewRequest, shop);
//		shopReview = reviewService.createShopReview(shopReview);
//		return ResponseEntity.ok(shopReview);
//	}
	
	@PostMapping(value="shops/{shopId}/reviews")
	public ResponseEntity<Object> createShopReview(@PathVariable Integer shopId,@RequestBody CreateShopReviewDTO createShopReviewDTO,@RequestParam(required = true)Double lat,@RequestParam(required=true) Double lon,@RequestHeader("Authorization") String authHeader){
		try {
			if(shopId==null || createShopReviewDTO==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object shopReview = this.reviewService.createShopReview(shopId,createShopReviewDTO,lat,lon,authHeader);
			return ResponseEntity.ok(shopReview);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error creating shop review: "+ex.getMessage());
			
		}
	}

	@PostMapping(value = "shops/{shopId}/items/{itemId}/reviews")
	public ResponseEntity<Object> createItemReview(@PathVariable Integer shopId, @PathVariable Integer itemId,
			@RequestBody CreateItemReviewDTO createItemReviewDTO,@RequestParam(required = true)Double lat,@RequestParam(required=true) Double lon,@RequestHeader("Authorization") String authHeader) {
		try {
			if(shopId==null || itemId==null || createItemReviewDTO==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object itemReview = this.reviewService.createItemReview(shopId,itemId,createItemReviewDTO,lat,lon,authHeader);
			return ResponseEntity.ok(itemReview);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error creating item review: "+ex.getMessage());
		}
	}

	@GetMapping(value = "shops/{shopId}/reviews")
	public ResponseEntity<Object> getAllShopReviews(@PathVariable Integer shopId,@RequestParam(required = true)Double lat,@RequestParam(required=true) Double lon,@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "0")Integer pageSize,@RequestHeader("Authorization") String authHeader) {
		try {
			if(shopId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object shopReviews = reviewService.getShopReviewDTOsByShopId(shopId,lat,lon,pageNumber,pageSize,authHeader);
			return ResponseEntity.ok(shopReviews);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching shop reviews: "+ex.getMessage());
		}
	}
	
	@GetMapping(value = "/reviews/shop")
	public ResponseEntity<Object> getAllShopReviewsForVendor(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "0")Integer pageSize,@RequestHeader("Authorization") String authHeader) {
		try {
			if(authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object shopReviews = reviewService.getShopReviewDTOsForVendor(pageNumber,pageSize,authHeader);
			return ResponseEntity.ok(shopReviews);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching shop reviews: "+ex.getMessage());
		}
	}
	
	

	@GetMapping(value = "shops/{shopId}/items/{itemId}/reviews")
	public ResponseEntity<Object> getAllItemReviews(@PathVariable Integer shopId, @PathVariable Integer itemId,@RequestParam(required = true)Double lat,@RequestParam(required=true) Double lon,@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "0")Integer pageSize,@RequestHeader("Authorization") String authHeader) {
		try {
			if(shopId==null || itemId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object itemReviews = reviewService.getItemReviewDTOsByItemId(itemId,shopId,lat,lon,pageNumber,pageSize,authHeader);
			return ResponseEntity.ok(itemReviews);
		}
		catch(Exception ex){
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching item reviews: "+ex.getMessage());
		}
	}
	
	@GetMapping(value = "/reviews/item")
	public ResponseEntity<Object> getAllItemReviewsForVendor(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "0")Integer pageSize,@RequestHeader("Authorization") String authHeader) {
		try {
			if(authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object itemReviews = reviewService.getItemReviewDTOsForVendor(pageNumber,pageSize,authHeader);
			return ResponseEntity.ok(itemReviews);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching item reviews: "+ex.getMessage());
		}
	}

	@GetMapping(value = "users/buyers/{userId}/reviews/itemreviews")
	public ResponseEntity<Object> getItemReviewsByUserId(@PathVariable Integer userId,@RequestParam(required = true)Double lat,@RequestParam(required=true) Double lon,@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "0")Integer pageSize,@RequestHeader("Authorization") String authHeader) {
		try {
			if(userId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object itemReviewsByUser = reviewService.getItemReviewDTOsByUserId(userId,lat,lon,pageNumber,pageSize,authHeader);
			return ResponseEntity.ok(itemReviewsByUser);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching item reviews by user: "+ex.getMessage());
		}
	}

	@GetMapping(value = "users/buyers/{userId}/reviews/shopreviews")
	public ResponseEntity<Object> getShopReviewsByUserId(@PathVariable Integer userId,@RequestParam(required = true)Double lat,@RequestParam(required=true) Double lon,@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "0")Integer pageSize,@RequestHeader("Authorization") String authHeader) {
		try {
			if(userId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object shopReviewsByUser = reviewService.getShopReviewDTOsByUserId(userId,lat,lon,pageNumber,pageSize,authHeader);
			return ResponseEntity.ok(shopReviewsByUser);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching shop reviews by user: "+ex.getMessage());
		}
	}

	@PutMapping(value = "shops/{shopId}/reviews/{reviewId}")
	public ResponseEntity<Object> updateShopReview(@PathVariable Integer shopId, @PathVariable Integer reviewId,
			@RequestBody ShopReview shopReview,@RequestHeader("Authorization") String authHeader) {
		try {
			if(shopId==null || reviewId==null || shopReview==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object updatedShopReview = this.reviewService.updateShopReview(shopId,reviewId, shopReview,authHeader);
			return ResponseEntity.ok(updatedShopReview);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error updating shop review: "+ex.getMessage());
		}
	}

	@PutMapping(value = "shops/{shopId}/items/{itemId}/reviews/{reviewId}")
	public ResponseEntity<Object> updateItemReview(@PathVariable Integer shopId, @PathVariable Integer itemId,
			@PathVariable Integer reviewId, @RequestBody ItemReview itemReview,@RequestHeader("Authorization") String authHeader) {
		try {
			if(shopId==null || itemId==null || reviewId==null || itemReview==null || authHeader==null ) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object updatedItemReview = this.reviewService.updateItemReview(shopId,itemId,reviewId, itemReview,authHeader);
			return ResponseEntity.ok(updatedItemReview);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error updating item review: "+ex.getMessage());
		}
	}

	@DeleteMapping(value = "shops/{shopId}/reviews/{reviewId}")
	public ResponseEntity<Object> deleteShopReview(@PathVariable Integer shopId, @PathVariable Integer reviewId,@RequestHeader("Authorization") String authHeader) {
		try {
			if(shopId==null || reviewId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			this.reviewService.deleteShopReviewById(shopId,reviewId,authHeader);
			return ResponseEntity.ok("Shop Review deleted sucessfully!");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error deleting shop review: "+ex.getMessage());
		}
	}

	@DeleteMapping(value = "shops/{shopId}/items/{itemId}/reviews/{reviewId}")
	public ResponseEntity<Object> deleteItemReview(@PathVariable Integer shopId, @PathVariable Integer itemId,
			@PathVariable Integer reviewId,@RequestHeader("Authorization") String authHeader) {
		try {
			if(shopId==null || itemId==null || reviewId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			this.reviewService.deleteItemReviewById(shopId,itemId,reviewId,authHeader);
			return ResponseEntity.ok("Item Review deleted sucessfully!");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error deleting item review: "+ex.getMessage());
		}
	}
	
	@PutMapping(value="shops/{shopId}/reviews/{reviewId}/like")
	public ResponseEntity<Object> likeShopReview(@PathVariable Integer shopId,@PathVariable Integer reviewId,@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestHeader("Authorization") String authHeader){
		try {
			if(shopId==null || reviewId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			this.reviewService.likeShopReview(shopId,reviewId,lat,lon,authHeader);
			return ResponseEntity.ok("Liked the shop review successfully");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error liking the shop review: "+ex.getMessage());
		}
	}
	
	@PutMapping(value="shops/{shopId}/reviews/{reviewId}/dislike")
	public ResponseEntity<Object> dislikeShopReview(@PathVariable Integer shopId,@PathVariable Integer reviewId,@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestHeader("Authorization") String authHeader){
		try {
			if(shopId==null || reviewId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			this.reviewService.dislikeShopReview(shopId,reviewId,lat,lon,authHeader);
			return ResponseEntity.ok("Disliked the shop review successfully");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error disliking the shop review: "+ex.getMessage());
		}
	}
	
	@PutMapping(value="shops/{shopId}/items/{itemId}/reviews/{reviewId}/like")
	public ResponseEntity<Object> likeItemReview(@PathVariable Integer shopId,@PathVariable Integer itemId,@PathVariable Integer reviewId,@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestHeader("Authorization") String authHeader){
		try {
			if(shopId==null || itemId==null || reviewId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			this.reviewService.likeItemReview(shopId,itemId,reviewId,lat,lon,authHeader);
			return ResponseEntity.ok("Liked the item review successfully");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error liking the item review: "+ex.getMessage());
		}
	}
	
	@PutMapping(value="shops/{shopId}/items/{itemId}/reviews/{reviewId}/dislike")
	public ResponseEntity<Object> dislikeItemReview(@PathVariable Integer shopId,@PathVariable Integer itemId,@PathVariable Integer reviewId,@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestHeader("Authorization") String authHeader){
		try {
			if(shopId==null || itemId==null || reviewId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			this.reviewService.dislikeItemReview(shopId,itemId,reviewId,lat,lon,authHeader);
			
			return ResponseEntity.ok("Disliked the item review successfully");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error disliking the item review: "+ex.getMessage());
		}
	}

}
