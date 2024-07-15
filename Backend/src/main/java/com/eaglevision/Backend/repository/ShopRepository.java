package com.eaglevision.Backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eaglevision.Backend.dto.ShopDTO;
import com.eaglevision.Backend.model.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

	public List<Shop> findShopsByVendor_userId(Integer userId);

	public Shop findShopByVendor_userIdAndShopId(Integer userId, Integer shopId);

	@Query("SELECT s.shopId as shopId, s.shopName as shopName, s.description as description, s.contactNumber as contactNumber, s.vendor.userId as vendorId FROM Shop s")
	public ShopDTO findShopByShopIdCustom(Integer shopId);

}
