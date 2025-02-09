package com.eaglevision.Backend.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.dto.CreateItemReviewDTO;
import com.eaglevision.Backend.dto.CreateShopReviewDTO;
import com.eaglevision.Backend.dto.ItemReviewDTO;
import com.eaglevision.Backend.dto.ReviewDTO;
import com.eaglevision.Backend.dto.ShopReviewDTO;
import com.eaglevision.Backend.model.Buyer;
import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.ItemReview;
import com.eaglevision.Backend.model.Notification;
import com.eaglevision.Backend.model.Review;
import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.model.ShopReview;
import com.eaglevision.Backend.model.User;
import com.eaglevision.Backend.repository.ReviewRepository;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {

	private ReviewRepository reviewRepository;
	
	private ShopService shopService;
	
	private ItemService itemService;
	
	private BuyerService buyerService;
	
	private UserService userService;
	
	private NotificationService notificationService;
	
	private UtilityService utilityService;

	@Autowired
	public ReviewService(ReviewRepository reviewRepository,ShopService shopService,ItemService itemService,BuyerService buyerService,UserService userService,NotificationService notificationService,UtilityService utilityService) {
		this.reviewRepository = reviewRepository;
		this.shopService = shopService;
		this.itemService = itemService;
		this.buyerService = buyerService;
		this.userService = userService;
		this.notificationService = notificationService;
		this.utilityService = utilityService;
	}

	public ShopReview createShopReview(Integer shopId,CreateShopReviewDTO createShopReviewDTO, Double lat, Double lon, String authHeader) throws Exception {
		Shop shop = this.shopService.getShopById(shopId, lat, lon, authHeader);
		
		if(shop==null) {
			throw new Exception("No such shop exists.");
		}
		
		Integer userId = createShopReviewDTO.getUserId();
		Integer stars = createShopReviewDTO.getStars();
		String comment = createShopReviewDTO.getComment();
		Buyer buyer = this.buyerService.getBuyerById(userId, authHeader);
		ShopReview shopReview = new ShopReview(stars, false, comment, buyer, shop);
		shop.addShopReview(shopReview);
		
		shopReview = reviewRepository.save(shopReview);
		
		Notification notification = new Notification();
		notification.setTitle("Shop Review");
		notification.setContent(buyer.getUserName()+" has created a review on your shop "+shop.getShopName());
		notification.setSentTo(shop.getVendor());
		shop.getVendor().addNotification(notification);
		
		this.notificationService.create(notification);
		this.notificationService.sendNotificationToUser(shop.getVendor().getUserName(),notification);
		
		return shopReview;
	}

	public ItemReview createItemReview(Integer shopId,Integer itemId,CreateItemReviewDTO createItemReviewDTO, Double lat, Double lon, String authHeader) throws Exception {
		Item item = this.itemService.getItem(shopId, itemId, lat, lon, authHeader);
		
		if(item==null) {
			throw new Exception("No such item exists.");
		}
		
		Integer userId = createItemReviewDTO.getUserId();
		Integer stars = createItemReviewDTO.getStars();
		String comment = createItemReviewDTO.getComment();
		Buyer buyer = this.buyerService.getBuyerById(userId, authHeader);

		ItemReview itemReview = new ItemReview(stars, comment, false, item, buyer);
		item.addItemReview(itemReview);
		
		itemReview = reviewRepository.save(itemReview);
		Shop shop = item.getShop();
		
		
		Notification notification = new Notification();
		notification.setTitle("Item Review");
		notification.setContent(buyer.getUserName()+" has created a review on your item "+item.getItemName());
		notification.setSentTo(shop.getVendor());
		shop.getVendor().addNotification(notification);
		
		this.notificationService.create(notification);
		this.notificationService.sendNotificationToUser(shop.getVendor().getUserName(),notification);
		
		return itemReview;
	}
	
	public ShopReview getShopReview(Integer shopId,Integer reviewId,Double lat,Double lon,String authHeader) throws Exception{
		String roles = this.utilityService.getRoles(authHeader);
		Integer userId = this.utilityService.getUserId(authHeader);
		Integer vendorId = this.shopService.getOwnerId(shopId);
		Double radius = this.utilityService.getRadius(roles);
		
		if(roles.contains("VENDOR")) {
			if(!userId.equals(vendorId)) throw new Exception("Not allowed to fetch this shop review.");
			return this.reviewRepository.findShopReviewByReviewId(reviewId);
		}	
		return this.reviewRepository.findShopReviewByReviewId(shopId, reviewId, lon, lat, radius);
	}
	
	public ItemReview getItemReview(Integer shopId,Integer itemId,Integer reviewId,Double lat,Double lon,String authHeader) throws Exception {
		String roles = this.utilityService.getRoles(authHeader);
		Integer userId = this.utilityService.getUserId(authHeader);
		Integer vendorId = this.shopService.getOwnerId(shopId);
		Double radius = this.utilityService.getRadius(roles);
		
		if(roles.contains("VENDOR")) {
			if(userId!=vendorId) throw new Exception("Not allowed to fetch this item review.");
			return this.reviewRepository.findItemReviewByReviewId(reviewId);
		}
		return this.reviewRepository.findItemReviewByReviewId(shopId, itemId, reviewId, lon, lat, radius);
	}

	public Page<ShopReview> getShopReviewsByShopId(Integer shopId, Double lat, Double lon, Integer pageNumber, Integer pageSize,
			String authHeader) throws Exception {
		String roles = this.utilityService.getRoles(authHeader);
		Integer userId = this.utilityService.getUserId(authHeader);
		Integer vendorId = this.shopService.getOwnerId(shopId);
		Double radius = this.utilityService.getRadius(roles);
		
		Pageable pageable;
		
		if(pageSize==0) {
			pageable = Pageable.unpaged();
		}
		else {
			pageable = PageRequest.of(pageNumber,pageSize);
		}
		
		if(roles.contains("VENDOR")) {
			if(userId.equals(vendorId)) return reviewRepository.findAllByShop_shopId(shopId,pageable);
			throw new Exception("Not allowed to fetch shop reviews from this shop.");
		}
		
		return reviewRepository.findAllByShop(shopId,lon,lat,radius,pageable);
	}
	
	public Page<ReviewDTO> getShopReviewDTOsByShopId(Integer shopId, Double lat, Double lon, Integer pageNumber, Integer pageSize,
			String authHeader) throws Exception {
		Integer userId = this.utilityService.getUserId(authHeader);
		Page<ShopReview> shopReviews = this.getShopReviewsByShopId(shopId, lat, lon, pageNumber, pageSize, authHeader);
		return shopReviews.map((ShopReview shopReview)->new ReviewDTO(shopReview,userId));
	}
	
	public Page<ShopReview> getShopReviewsByUserId(Integer userId, Double lat, Double lon, Integer pageNumber, Integer pageSize,
			String authHeader) throws Exception {
		String roles = this.utilityService.getRoles(authHeader);
		Integer currentUserId = this.utilityService.getUserId(authHeader);
		Double radius = this.utilityService.getRadius(roles);
		
		if(roles.contains("VENDOR")) {
			throw new Exception("Vendors are not allowed to fetch shop reviews by buyers.");
		}
		
		Pageable pageable;
		
		if(pageSize==0) {
			pageable = Pageable.unpaged();
		}
		else {
			pageable = PageRequest.of(pageNumber,pageSize);
		}
		
		if(currentUserId.equals(userId)) {
			return reviewRepository.findAllShopReviewsByUser(userId, pageable);
		}
		
		return reviewRepository.findAllShopReviewsByUser(userId, lon, lat,radius, pageable);
	}

	public Page<ItemReview> getItemReviewsByItemId(Integer itemId, Integer shopId, Double lat, Double lon, Integer pageNumber,
			Integer pageSize, String authHeader) throws Exception {
		String roles = this.utilityService.getRoles(authHeader);
		Integer userId = this.utilityService.getUserId(authHeader);
		Integer vendorId = this.shopService.getOwnerId(shopId);
		Double radius = this.utilityService.getRadius(roles);
		
		Pageable pageable;
		
		if(pageSize==0) {
			pageable = Pageable.unpaged();
		}
		else {
			pageable = PageRequest.of(pageNumber,pageSize);
		}
		
		if(roles.contains("VENDOR")) {
			if(userId.equals(vendorId)) return reviewRepository.findAllByItem_itemId(itemId,pageable);
			throw new Exception("Not allowed to fetch item reviews from this item.");
		}
		
		return reviewRepository.findAllByItem(shopId,itemId,lon,lat,radius,pageable);
	}
	
	public Page<ReviewDTO> getItemReviewDTOsByItemId(Integer itemId, Integer shopId, Double lat, Double lon, Integer pageNumber,
			Integer pageSize, String authHeader) throws Exception {
		
		Integer userId = this.utilityService.getUserId(authHeader);
		Page<ReviewDTO> itemReviewDTOs = this.getItemReviewsByItemId(itemId, shopId, lat, lon, pageNumber, pageSize, authHeader).map((ItemReview itemReview)->new ReviewDTO(itemReview, userId));
		return itemReviewDTOs;
	}

	public Page<ItemReview> getItemReviewsByUserId(Integer userId, Double lat, Double lon, Integer pageNumber, Integer pageSize,
			String authHeader) throws Exception {
		String roles = this.utilityService.getRoles(authHeader);
		Integer currentUserId = this.utilityService.getUserId(authHeader);
		Double radius = this.utilityService.getRadius(roles);
		
		if(roles.contains("VENDOR")) {
			throw new Exception("Not allowed to fetch item reviews by buyers.");
		}
		
		Pageable pageable;
		
		if(pageSize==0) {
			pageable = Pageable.unpaged();
		}
		else {
			pageable = PageRequest.of(pageNumber,pageSize);
		}
		
		if(currentUserId.equals(userId)) {
			return reviewRepository.findAllItemReviewsByUser(userId, pageable);
		}
		
		return reviewRepository.findAllItemReviewsByUser(userId, lon, lat,radius , pageable);
	}

	public ReviewDTO updateShopReview(Integer shopId, Integer reviewId, ShopReview shopReview,
			String authHeader) throws Exception{
		String roles = this.utilityService.getRoles(authHeader);
		Integer userId = this.utilityService.getUserId(authHeader);
		
		if(roles.contains("VENDOR")) {
			throw new Exception("Not allowed to update the shop review.");
		}
		
		ShopReview persistentShopReview = reviewRepository.findShopReviewByReviewId(reviewId);
		if(shopReview==null) {
			throw new Exception("No such shop review with the given review ID exists.");
		}
		
		if(!persistentShopReview.getBuyer().getUserId().equals(userId)) {
			throw new Exception("Not allowed to update the shop review.");
		}
		
		persistentShopReview.setComment(shopReview.getComment());
		persistentShopReview.setIsEdited(true);
		persistentShopReview.setStars(shopReview.getStars());
		
		ShopReview updatedShopReview = reviewRepository.save(persistentShopReview);
		ReviewDTO updatedReviewDTO =  new ReviewDTO(updatedShopReview,userId);
		return updatedReviewDTO;
	}
	
	public ReviewDTO updateItemReview(Integer shopId,Integer itemId, Integer reviewId, ItemReview itemReview,
			String authHeader) throws Exception{
		String roles = this.utilityService.getRoles(authHeader);
		Integer userId = this.utilityService.getUserId(authHeader);
		
		if(roles.contains("VENDOR")) {
			throw new Exception("Not allowed to update the item review.");
		}
		
		ItemReview persistentItemReview = reviewRepository.findItemReviewByReviewId(reviewId);
		
		if(persistentItemReview==null) {
			throw new Exception("No such item review with the given review ID exists.");
		}
		
		if(!persistentItemReview.getBuyer().getUserId().equals(userId)) {
			throw new Exception("Not allowed to update the item review.");
		}
		
		persistentItemReview.setComment(itemReview.getComment());
		persistentItemReview.setIsEdited(true);
		persistentItemReview.setStars(itemReview.getStars());
		
		ItemReview updatedItemReview = reviewRepository.save(persistentItemReview);
		ReviewDTO updatedReviewDTO =  new ReviewDTO(updatedItemReview,userId);
		return updatedReviewDTO;
	}

	public void deleteShopReviewById(Integer shopId, Integer reviewId, String authHeader) throws Exception {
		String roles = this.utilityService.getRoles(authHeader);
		Integer userId = this.utilityService.getUserId(authHeader);
		
		if(roles.contains("VENDOR")) {
			throw new Exception("Not allowed to delete the shop review.");
		}
		
		ShopReview persistentShopReview = reviewRepository.findShopReviewByReviewId(reviewId);
		
		if(!persistentShopReview.getBuyer().getUserId().equals(userId)) {
			throw new Exception("Not allowed to delete the shop review.");
		}
		
		for (User user : persistentShopReview.getLikes()) {
	        user.getLikedReviews().remove(persistentShopReview);
	        userService.save(user);
	    }
	    for (User user : persistentShopReview.getDislikes()) {
	        user.getDislikedReviews().remove(persistentShopReview);
	        userService.save(user);
	    }
		
		reviewRepository.delete(persistentShopReview);
		return;
	}
	
	public void deleteItemReviewById(Integer shopId,Integer itemId, Integer reviewId, String authHeader) throws Exception {
		String roles = this.utilityService.getRoles(authHeader);
		Integer userId = this.utilityService.getUserId(authHeader);
		
		if(roles.contains("VENDOR")) {
			throw new Exception("Not allowed to delete the item review.");
		}
		
		ItemReview persistentItemReview = reviewRepository.findItemReviewByReviewId(reviewId);
		
		if(!persistentItemReview.getBuyer().getUserId().equals(userId)) {
			throw new Exception("Not allowed to delete the item review.");
		}
		
		for (User user : persistentItemReview.getLikes()) {
	        user.getLikedReviews().remove(persistentItemReview);
	        userService.save(user);
	    }
	    for (User user : persistentItemReview.getDislikes()) {
	        user.getDislikedReviews().remove(persistentItemReview);
	        userService.save(user);
	    }
		
		reviewRepository.delete(persistentItemReview);
		return;
	}
	
//	public Review getReviewByReviewId(Integer reviewId,String authHeader) throws Exception {
//		Review review = reviewRepository.findById(reviewId).orElse(null);
//		String roles = utilityService.getRoles(authHeader);
//		Integer userId = utilityService.getUserId(authHeader);
//		
//		if(review==null) {
//			throw new Exception("No such review with the given review Id exists.");
//		}
//		
//		if(roles.contains("VENDOR")) {
//			if(review instanceof ItemReview) {
//				ItemReview itemReview = (ItemReview) review;
//				if(itemReview.getItem().getShop().getVendor().getUserId()!=userId) {
//					throw new Exception("Not allowed to fetch this review");
//				}
//				
//			}
//			else if(review instanceof ShopReview) {
//				ShopReview shopReview = (ShopReview) review;
//				if(shopReview.getShop().getVendor().getUserId()!=userId) {
//					throw new Exception("Not allowed to fetch this review");
//				}
//			}
//		}
//		
//		return review;
//	}

	@Transactional
	public void likeShopReview(Integer shopId,Integer reviewId,Double lat,Double lon, String authHeader) throws Exception{
		String roles = utilityService.getRoles(authHeader);
		Integer userId = utilityService.getUserId(authHeader);
		Double radius = utilityService.getRadius(roles);
		
		Shop shop = shopService.getShopById(shopId, lat, lon, authHeader);
		
		if(shop==null) {
			throw new Exception("No such shop exists.");
		}
		
		ShopReview shopReview;
		if(roles.contains("VENDOR")) {
			if(shop.getVendor().getUserId()!=userId) {
				throw new Exception("Not allowed to like this review.");
			}
			shopReview = reviewRepository.findShopReviewByReviewId(reviewId);
			
		}
		else{
			shopReview = reviewRepository.findShopReviewByReviewId(shopId, reviewId, lon, lat, radius);
		}
		
		if(shopReview==null) {
			throw new Exception("No such shop review exists.");
		}
		
		User user = userService.getUserByUserId(userId); 
		
		Set<Review> likedReviews = user.getLikedReviews();
		Set<User> likes = shopReview.getLikes();
		Boolean exists = user.getLikedReviews().removeIf((Review review)->review.getReviewId().equals(reviewId)) && shopReview.getLikes().removeIf((User likedUser)->likedUser.getUserId().equals(userId));
		
		
		
		if(!exists) {
			likes.add(user);
			likedReviews.add(shopReview);
			
			Notification notification = new Notification();
			notification.setTitle("Review Like");
			notification.setContent(user.getUserName()+" has liked your shop review.");
			notification.setSentTo(shopReview.getBuyer());
			shopReview.getBuyer().addNotification(notification);
			
			this.notificationService.create(notification);
			
			user.getDislikedReviews().removeIf((Review review)->review.getReviewId().equals(reviewId));
			shopReview.getDislikes().removeIf((User dislikedUser)->dislikedUser.getUserId().equals(userId));
			this.notificationService.sendNotificationToUser(shopReview.getBuyer().getUserName(),notification);
		}
		
		shopReview.setLikes(likes);
		user.setLikedReviews(likedReviews);
		userService.save(user);
		reviewRepository.save(shopReview);
		
		
		return;
	}

	@Transactional
	public void dislikeShopReview(Integer shopId,Integer reviewId,Double lat,Double lon, String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		Integer userId = utilityService.getUserId(authHeader);
		Double radius = utilityService.getRadius(roles);
		
		Shop shop = shopService.getShopById(shopId, lat, lon, authHeader);
		
		if(shop==null) {
			throw new Exception("No such shop exists.");
		}
		
		ShopReview shopReview;
		if(roles.contains("VENDOR")) {
			shopReview = reviewRepository.findShopReviewByReviewId(reviewId);
		}
		else{
			shopReview = reviewRepository.findShopReviewByReviewId(shopId, reviewId, lon, lat, radius);
		}
		
		if(shopReview==null) {
			throw new Exception("No such shop review exists.");
		}
		
		User user = userService.getUserByUserId(userId); 
		
		Set<Review> dislikedReviews = user.getDislikedReviews();
		Set<User> dislikes = shopReview.getDislikes();
		Boolean exists = user.getDislikedReviews().removeIf((Review review)->review.getReviewId().equals(reviewId)) && shopReview.getDislikes().removeIf((User dislikedUser)->dislikedUser.getUserId().equals(userId));
		
		
		
		if(!exists) {
			dislikes.add(user);
			dislikedReviews.add(shopReview);
			
			user.getLikedReviews().removeIf((Review review)->review.getReviewId().equals(reviewId));
			shopReview.getLikes().removeIf((User likedUser)->likedUser.getUserId().equals(userId));
		}
		
		System.out.println(exists);
		
		shopReview.setDislikes(dislikes);
		user.setDislikedReviews(dislikedReviews);
		reviewRepository.save(shopReview);
		
		return;
		
	}

	@Transactional
	public void likeItemReview(Integer shopId, Integer itemId, Integer reviewId, Double lat, Double lon,
			String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		Integer userId = utilityService.getUserId(authHeader);
		Double radius = utilityService.getRadius(authHeader);
		
		Item item = this.itemService.getItem(shopId, itemId, lat, lon, authHeader);
		
		if(item==null) {
			throw new Exception("No such item exists.");
		}
		
		ItemReview itemReview;
		if(roles.contains("VENDOR")) {
			itemReview = reviewRepository.findItemReviewByReviewId(reviewId);
		}
		else {
			itemReview = reviewRepository.findItemReviewByReviewId(shopId, itemId, reviewId, lon, lat, radius);
		}
		
		if(itemReview==null) {
			throw new Exception("No such item review exists.");
		}
		
		User user = userService.getUserByUserId(userId);
		
		Set<Review> likedReviews = user.getLikedReviews();
		Set<User> likes = itemReview.getLikes();
		Boolean exists = user.getLikedReviews().removeIf((Review review)->review.getReviewId().equals(reviewId)) && itemReview.getLikes().removeIf((User likedUser)->likedUser.getUserId().equals(userId));
		
		
		
		if(!exists) {
			likes.add(user);
			likedReviews.add(itemReview);
			
			Notification notification = new Notification();
			notification.setTitle("Review Like");
			notification.setContent(user.getUserName()+" has liked your item review.");
			notification.setSentTo(itemReview.getBuyer());
			itemReview.getBuyer().addNotification(notification);
			
			this.notificationService.create(notification);
			this.notificationService.sendNotificationToUser(itemReview.getBuyer().getUserName(),notification);
			
			user.getDislikedReviews().removeIf((Review review)->review.getReviewId().equals(reviewId));
			itemReview.getDislikes().removeIf((User dislikedUser)->dislikedUser.getUserId().equals(userId));
		}
		
		itemReview.setLikes(likes);
		user.setLikedReviews(likedReviews);
		reviewRepository.save(itemReview);
		
		return; 
		
	}

	@Transactional
	public void dislikeItemReview(Integer shopId, Integer itemId, Integer reviewId, Double lat, Double lon,
			String authHeader) throws 	Exception {
		String roles = utilityService.getRoles(authHeader);
		Integer userId = utilityService.getUserId(authHeader);
		Double radius = utilityService.getRadius(authHeader);
		
		Item item = this.itemService.getItem(shopId, itemId, lat, lon, authHeader);
		
		if(item==null) {
			throw new Exception("No such item exists.");
		}
		
		ItemReview itemReview;
		if(roles.contains("VENDOR")) {
			itemReview = reviewRepository.findItemReviewByReviewId(reviewId);
		}
		else {
			itemReview = reviewRepository.findItemReviewByReviewId(shopId, itemId, reviewId, lon, lat, radius);
		}
		
		if(itemReview==null) {
			throw new Exception("No such item review exists.");
		}
		
		User user = userService.getUserByUserId(userId);
		
		Set<Review> dislikedReviews = user.getDislikedReviews();
		Set<User> dislikes = itemReview.getDislikes();
		Boolean exists = user.getDislikedReviews().removeIf((Review review)->review.getReviewId().equals(reviewId)) && itemReview.getDislikes().removeIf((User dislikedUser)->dislikedUser.getUserId().equals(userId));
		
		
		
		if(!exists) {
			dislikes.add(user);
			dislikedReviews.add(itemReview);
			
			user.getLikedReviews().removeIf((Review review)->review.getReviewId().equals(reviewId));
			itemReview.getLikes().removeIf((User likedUser)->likedUser.getUserId().equals(userId));
		}
		
		itemReview.setDislikes(dislikes);
		user.setDislikedReviews(dislikedReviews);
		reviewRepository.save(itemReview);
		
		return; 
		
	}

	public Page<ReviewDTO> getShopReviewDTOsForVendor(Integer pageNumber, Integer pageSize,
			String authHeader) throws Exception {
		String roles = this.utilityService.getRoles(authHeader);
		Integer userId = this.utilityService.getUserId(authHeader);
		
		if(!roles.contains("VENDOR")) {
			throw new Exception("No such endpoint exists.");
		}
		
		Pageable pageable;
		
		if(pageSize==0) {
			pageable = Pageable.unpaged();
		}
		else {
			pageable = PageRequest.of(pageNumber,pageSize);
		}
		
		return reviewRepository.findAllByShop_vendor_userId(userId,pageable).map((ShopReview shopReview)->new ReviewDTO(shopReview,userId));
	}
	
	public Page<ReviewDTO> getItemReviewDTOsForVendor(Integer pageNumber, Integer pageSize,
			String authHeader) throws Exception {
		String roles = this.utilityService.getRoles(authHeader);
		Integer userId = this.utilityService.getUserId(authHeader);
		
		if(!roles.contains("VENDOR")) {
			throw new Exception("No such endpoint exists.");
		}
		
		Pageable pageable;
		
		if(pageSize==0) {
			pageable = Pageable.unpaged();
		}
		else {
			pageable = PageRequest.of(pageNumber,pageSize);
		}
		
		return reviewRepository.findAllByItem_shop_vendor_userId(userId,pageable).map((ItemReview itemReview)->new ReviewDTO(itemReview,userId));
	}

	public Page<ReviewDTO> getItemReviewDTOsByUserId(Integer userId, Double lat, Double lon, Integer pageNumber,
			Integer pageSize, String authHeader) throws Exception {
		Integer currentUserId = this.utilityService.getUserId(authHeader);
		Page<ItemReview> itemReviews = this.getItemReviewsByUserId(userId, lat, lon, pageNumber, pageSize, authHeader);
		return itemReviews.map((ItemReview itemReview)-> new ReviewDTO(itemReview,currentUserId));
	}
	
	public Page<ReviewDTO> getShopReviewDTOsByUserId(Integer userId, Double lat, Double lon, Integer pageNumber,
			Integer pageSize, String authHeader) throws Exception {
		Integer currentUserId = this.utilityService.getUserId(authHeader);
		Page<ShopReview> itemReviews = this.getShopReviewsByUserId(userId, lat, lon, pageNumber, pageSize, authHeader);
		return itemReviews.map((ShopReview shopReview)-> new ReviewDTO(shopReview,currentUserId));
	}
	
	
	
	
	
	
}
