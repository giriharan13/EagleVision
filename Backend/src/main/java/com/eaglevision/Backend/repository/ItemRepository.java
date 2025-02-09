package com.eaglevision.Backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eaglevision.Backend.model.Item;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ItemRepository extends JpaRepository<Item,Integer> {
	
	public Item findByShop_shopIdAndItemId(Integer shopId,Integer itemId);
	
	List<Item> findByShop_shopId(Integer shopId);

	@Query(value="SELECT i.* FROM item i LEFT JOIN shop s on s.shop_id = i.shop_id  LEFT JOIN shop_location l on s.shop_id=l.shop_shop_id WHERE s.shop_id=:shopId AND i.item_id=:itemId AND ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,:radius) ",nativeQuery = true)
	public Item findByshopIdAnditemId(@Param("shopId") Integer shopId,@Param("itemId") Integer itemId,@Param("longitude") Double longitude,@Param("latitude") Double latitude,@Param("radius") Double radius);

	@Query(value="SELECT i.* FROM item i LEFT JOIN shop s on s.shop_id = i.shop_id  LEFT JOIN shop_location l on s.shop_id=l.shop_shop_id WHERE s.shop_id=:shopId  AND ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,:radius) AND (LOWER(i.item_name) LIKE LOWER(CONCAT('%',:query,'%')) OR LOWER(i.item_description) LIKE LOWER(CONCAT('%',:query,'%')))",nativeQuery = true)
	public Page<Item> findByShopId(@Param("shopId") Integer shopId,@Param("longitude") Double longitude,@Param("latitude") Double latitude,@Param("radius") Double radius,@Param("query") String query,Pageable pageable);

	public Page<Item> findByShop_shopIdAndShop_shopNameContainingIgnoreCase(Integer shopId, String query, Pageable pageable); 
	
	@Query(value="SELECT i.* FROM item i LEFT JOIN shop s on s.shop_id = i.shop_id  LEFT JOIN shop_location l on s.shop_id=l.shop_shop_id WHERE ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,:radius) AND (LOWER(i.item_name) LIKE LOWER(CONCAT('%',:query,'%')) OR LOWER(i.item_description) LIKE LOWER(CONCAT('%',:query,'%')))",nativeQuery = true)
	public Page<Item> findAll(@Param("longitude") Double lon,@Param("latitude") Double lat,@Param("radius") Double radius,@Param("query") String query,Pageable pageable);
}
