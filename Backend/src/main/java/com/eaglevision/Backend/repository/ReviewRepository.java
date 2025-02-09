package com.eaglevision.Backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eaglevision.Backend.model.ItemReview;
import com.eaglevision.Backend.model.Review;
import com.eaglevision.Backend.model.ShopReview;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	
	ItemReview findItemReviewByReviewId(Integer id);
	
	ShopReview findShopReviewByReviewId(Integer id);
	
	@Query(value="SELECT ir.*,r.* FROM item_review ir LEFT JOIN review r on r.review_id=ir.review_id LEFT JOIN item i ON ir.item_id=i.item_id LEFT JOIN shop s ON i.shop_id=s.shop_id LEFT JOIN shop_location l on l.shop_shop_id=s.shop_id WHERE s.shop_id=:shopId AND i.item_id=:itemId AND ir.review_id=:reviewId AND ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,:radius)",nativeQuery = true)
	ItemReview findItemReviewByReviewId(@Param("shopId") Integer shopId,@Param("itemId") Integer itemId,@Param("reviewId") Integer reviewId,@Param("longitude") Double longitude,@Param("latitude") Double latitud,@Param("radius") Double radius);
	
	@Query(value="SELECT sr.*,r.* FROM shop_review sr LEFT JOIN review r on r.review_id=sr.review_id LEFT JOIN shop s ON sr.shop_id=s.shop_id LEFT JOIN shop_location l ON l.shop_shop_id=s.shop_id WHERE s.shop_id=:shopId AND r.review_id=:reviewId AND ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,:radius)",nativeQuery = true)
	ShopReview findShopReviewByReviewId(@Param("shopId") Integer shopId,@Param("reviewId") Integer reviewId,@Param("longitude") Double longitude,@Param("latitude") Double latitude,@Param("radius") Double radius);
	
	public List<Review> findReviewByBuyer_userId(Integer userId);  
	
	public Page<ShopReview> findAllByShop_shopId(Integer shopId,Pageable pageable);
	
	@Query(value="SELECT sr.*,r.* FROM shop_review sr LEFT JOIN review r on r.review_id=sr.review_id LEFT JOIN shop s on s.shop_id=sr.shop_id LEFT JOIN shop_location l on l.shop_shop_id =s.shop_id WHERE s.shop_id=:shopId AND ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,:radius)",nativeQuery = true)
	public Page<ShopReview> findAllByShop(@Param("shopId") Integer shopId,@Param("longitude") Double longitude,@Param("latitude") Double latitude,@Param("radius")Double radius,Pageable pageable);
	
	@Query(value="SELECT ir.*,r.* FROM item_review ir LEFT JOIN review r on r.review_id=ir.review_id LEFT JOIN item i on i.item_id=ir.item_id LEFT JOIN shop s ON s.shop_id=i.shop_id LEFT JOIN shop_location l on l.shop_shop_id =s.shop_id WHERE i.item_id=:itemId AND s.shop_id=:shopId AND ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,:radius)",nativeQuery = true)
	public Page<ItemReview> findAllByItem(@Param("shopId") Integer shopId,@Param("itemId") Integer itemId,@Param("longitude") Double longitude,@Param("latitude") Double latitude,@Param("radius") Double radius,Pageable pageable);
	
	public Page<ItemReview> findAllByItem_itemId(Integer itemId,Pageable pageable);
	
	@Query(value="SELECT * FROM shop_review sr LEFT JOIN review r ON sr.review_id = r.review_id WHERE r.buyer_id=:userId",nativeQuery = true)
	public Page<ShopReview> findAllShopReviewsByUser(@Param("userId")Integer userId,Pageable pageable);
	
	@Query(value="SELECT sr.*,r.* FROM shop_review sr LEFT JOIN review r ON sr.review_id = r.review_id LEFT JOIN shop s ON s.shop_id=sr.shop_id LEFT JOIN shop_location l on l.shop_shop_id =s.shop_id WHERE r.buyer_id=:userId AND ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,:radius)",nativeQuery = true)
	public Page<ShopReview> findAllShopReviewsByUser(@Param("userId")Integer userId,@Param("longitude")Double longitude,@Param("latitude") Double latitude,@Param("radius") Double radius,Pageable pageable);
	
	@Query(value="SELECT * FROM item_review ir LEFT JOIN review r ON ir.review_id = r.review_id WHERE r.buyer_id=:userId",nativeQuery = true)
	public Page<ItemReview> findAllItemReviewsByUser(@Param("userId")Integer userId,Pageable pageable);

	@Query(value="SELECT ir.*,r.* FROM item_review ir LEFT JOIN review r ON ir.review_id = r.review_id LEFT JOIN item i ON i.item_id=ir.item_id LEFT JOIN shop s on s.shop_id=i.shop_id LEFT JOIN shop_location l ON l.shop_shop_id = s.shop_id WHERE r.buyer_id=:userId AND ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,:radius)",nativeQuery = true)
	public Page<ItemReview> findAllItemReviewsByUser(@Param("userId")Integer userId,@Param("longitude")Double longitude,@Param("latitude") Double latitude,@Param("radius") Double radius,Pageable pageable);
	
	public List<ShopReview> findShopReviewsByBuyer_userId(Integer userId);
	
	public List<ItemReview> findItemReviewsByBuyer_userId(Integer userId);

	Page<ShopReview> findAllByShop_vendor_userId(Integer userId, Pageable pageable);

	Page<ItemReview> findAllByItem_shop_vendor_userId(Integer userId, Pageable pageable);
}
