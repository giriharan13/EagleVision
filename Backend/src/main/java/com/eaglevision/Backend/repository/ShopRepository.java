package com.eaglevision.Backend.repository;

import java.util.List;
import java.util.Locale.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eaglevision.Backend.dto.ShopDTO;
import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.model.ShopCategory;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ShopRepository extends JpaRepository<Shop, Integer> {

	public List<Shop> findShopsByVendor_userId(Integer userId);
	
	public Page<Shop> findShopsByVendor_userId(Integer userId,Pageable pageable);
	
	public Page<Shop> findShopsByVendor_userIdAndShopNameContainingIgnoreCase(Integer userId,String shopName,Pageable pageable);
	
	public Shop findShopByVendor_userIdAndShopId(Integer userId, Integer shopId);

	@Query("SELECT s.shopId as shopId, s.shopName as shopName, s.description as description, s.contactNumber as contactNumber, s.vendor.userId as vendorId FROM Shop s")
	public ShopDTO findShopByShopIdCustom(Integer shopId);
	
	@Query(value = "SELECT * FROM shop s WHERE s.shopCategory = ?4 AND  ST_DWithin(ST_SetSRID(ST_MakePoint(?1,?2),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,?3)",nativeQuery = true)
	public Page<Shop> findShopsByShopCategory(Double longitude,Double latitude,Double radius,ShopCategory shopCategory,Pageable pageable);
	
	@Query(value="SELECT * FROM shop s LEFT JOIN shop_location l ON s.shop_id = l.shop_shop_id WHERE s.vendor_id=:vendorId AND s.shopId=:shopId AND ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,:radius)",nativeQuery = true)
	public Shop findShopByVendor(@Param("vendorId")Integer vendorId,@Param("shopId")Integer shopId,@Param("longitude")Double longitude,@Param("latitude")Double latitude,@Param("radius")Double radius);
	
	@Query(value="SELECT * FROM shop s LEFT JOIN shop_location l ON s.shop_id = l.shop_shop_id WHERE s.shop_id=?1 AND ST_DWithin(ST_SetSRID(ST_MakePoint(?2,?3),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,?4)",nativeQuery = true)
	public Shop findShop(Integer shopId,Double longitude,Double latitude,Double radius);
	
	@Query(value="SELECT * FROM shop s LEFT JOIN vendor v on v.user_id=s.vendor_id LEFT JOIN shop_location l ON s.shop_id = l.shop_shop_id WHERE v.user_id=:vendorId AND ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,:radius) AND (LOWER(s.shop_name) LIKE LOWER(CONCAT('%',:query,'%')) or LOWER(s.description) LIKE LOWER(CONCAT('%',:query,'%')))",nativeQuery = true)
	public Page<Shop> findShopsByVendor(@Param("vendorId") Integer vendorId,@Param("longitude")Double longitude,@Param("latitude")Double latitude,@Param("radius")Double radius,@Param("query")String query,Pageable pageable);
	
	@Query("SELECT s from Shop s where LOWER(s.shopName) LIKE LOWER(CONCAT('%',':query','%')) or LOWER(s.description) LIKE LOWER(CONCAT('%',':query','%'))")
	public List<Shop> findShopsByQuery(@Param("query") String query);
	
	@Query(value = "SELECT s.* FROM shop s LEFT JOIN shop_location l ON s.shop_id = l.shop_shop_id WHERE ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,:radius) AND s.shop_category=:category AND (s.shop_name LIKE CONCAT('%',:query,'%') or s.description LIKE CONCAT('%',:query,'%')) ", nativeQuery = true)
	public Page<Shop> findAll(@Param("query") String query,@Param("category") Integer category,@Param("longitude") Double longitude,@Param("latitude") Double latitude,@Param("radius") Double radius,Pageable pageable);
	
	@Query(value = "SELECT s.* FROM shop s LEFT JOIN shop_location l ON s.shop_id = l.shop_shop_id WHERE ST_DWithin(ST_SetSRID(ST_MakePoint(:longitude,:latitude),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,:radius) AND (LOWER(s.shop_name) LIKE LOWER(CONCAT('%',:query,'%')) or LOWER(s.description) LIKE LOWER(CONCAT('%',:query,'%'))) ", nativeQuery = true)
	public Page<Shop> findAll(@Param("query") String query,@Param("longitude") Double longitude,@Param("latitude") Double latitude,@Param("radius") Double radius,Pageable pageable);
	
	@Query(value= "SELECT * FROM shop s LEFT JOIN shop_location l ON s.shop_id = l.shop_shop_id WHERE LOWER(s.shopName) LIKE LOWER(CONCAT('%',':query','%')) or LOWER(s.description) LIKE LOWER(CONCAT('%',':query','%')) AND ST_DWithin(ST_SetSRID(ST_MakePoint(?1,?2),4326),ST_SetSRID(ST_MakePoint(l.longitude,l.lattitude),4326)\\:\\:GEOGRAPHY,?3)",nativeQuery = true)
	public List<Shop> findShopsByQuery(@Param("query") String query,Double longitude,Double latitude,Double radius);

}
